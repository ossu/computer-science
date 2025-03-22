/*
 * Copyright (c) 2015, 2020, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

var noResult = {l: "No results found"};
var loading = {l: "Loading search index..."};
var catModules = "Modules";
var catPackages = "Packages";
var catTypes = "Classes and Interfaces";
var catMembers = "Members";
var catSearchTags = "Search Tags";
var highlight = "<span class=\"result-highlight\">$&</span>";
var searchPattern = "";
var fallbackPattern = "";
var RANKING_THRESHOLD = 2;
var NO_MATCH = 0xffff;
var MIN_RESULTS = 3;
var MAX_RESULTS = 500;
var UNNAMED = "<Unnamed>";
function escapeHtml(str) {
    return str.replace(/</g, "&lt;").replace(/>/g, "&gt;");
}
function getHighlightedText(item, matcher, fallbackMatcher) {
    var escapedItem = escapeHtml(item);
    var highlighted = escapedItem.replace(matcher, highlight);
    if (highlighted === escapedItem) {
        highlighted = escapedItem.replace(fallbackMatcher, highlight)
    }
    return highlighted;
}
function getURLPrefix(ui) {
    var urlPrefix="";
    var slash = "/";
    if (ui.item.category === catModules) {
        return ui.item.l + slash;
    } else if (ui.item.category === catPackages && ui.item.m) {
        return ui.item.m + slash;
    } else if (ui.item.category === catTypes || ui.item.category === catMembers) {
        if (ui.item.m) {
            urlPrefix = ui.item.m + slash;
        } else {
            $.each(packageSearchIndex, function(index, item) {
                if (item.m && ui.item.p === item.l) {
                    urlPrefix = item.m + slash;
                }
            });
        }
    }
    return urlPrefix;
}
function createSearchPattern(term) {
    var pattern = "";
    var isWordToken = false;
    term.replace(/,\s*/g, ", ").trim().split(/\s+/).forEach(function(w, index) {
        if (index > 0) {
            // whitespace between identifiers is significant
            pattern += (isWordToken && /^\w/.test(w)) ? "\\s+" : "\\s*";
        }
        var tokens = w.split(/(?=[A-Z,.()<>[\/])/);
        for (var i = 0; i < tokens.length; i++) {
            var s = tokens[i];
            if (s === "") {
                continue;
            }
            pattern += $.ui.autocomplete.escapeRegex(s);
            isWordToken =  /\w$/.test(s);
            if (isWordToken) {
                pattern += "([a-z0-9_$<>\\[\\]]*?)";
            }
        }
    });
    return pattern;
}
function createMatcher(pattern, flags) {
    var isCamelCase = /[A-Z]/.test(pattern);
    return new RegExp(pattern, flags + (isCamelCase ? "" : "i"));
}
var watermark = 'Search';
$(function() {
    var search = $("#search-input");
    var reset = $("#reset-button");
    search.val('');
    search.prop("disabled", false);
    reset.prop("disabled", false);
    search.val(watermark).addClass('watermark');
    search.blur(function() {
        if ($(this).val().length === 0) {
            $(this).val(watermark).addClass('watermark');
        }
    });
    search.on('click keydown paste', function() {
        if ($(this).val() === watermark) {
            $(this).val('').removeClass('watermark');
        }
    });
    reset.click(function() {
        search.val('').focus();
    });
    search.focus()[0].setSelectionRange(0, 0);
});
$.widget("custom.catcomplete", $.ui.autocomplete, {
    _create: function() {
        this._super();
        this.widget().menu("option", "items", "> :not(.ui-autocomplete-category)");
    },
    _renderMenu: function(ul, items) {
        var rMenu = this;
        var currentCategory = "";
        rMenu.menu.bindings = $();
        $.each(items, function(index, item) {
            var li;
            if (item.category && item.category !== currentCategory) {
                ul.append("<li class=\"ui-autocomplete-category\">" + item.category + "</li>");
                currentCategory = item.category;
            }
            li = rMenu._renderItemData(ul, item);
            if (item.category) {
                li.attr("aria-label", item.category + " : " + item.l);
                li.attr("class", "result-item");
            } else {
                li.attr("aria-label", item.l);
                li.attr("class", "result-item");
            }
        });
    },
    _renderItem: function(ul, item) {
        var label = "";
        var matcher = createMatcher(escapeHtml(searchPattern), "g");
        var fallbackMatcher = new RegExp(fallbackPattern, "gi")
        if (item.category === catModules) {
            label = getHighlightedText(item.l, matcher, fallbackMatcher);
        } else if (item.category === catPackages) {
            label = getHighlightedText(item.l, matcher, fallbackMatcher);
        } else if (item.category === catTypes) {
            label = (item.p && item.p !== UNNAMED)
                    ? getHighlightedText(item.p + "." + item.l, matcher, fallbackMatcher)
                    : getHighlightedText(item.l, matcher, fallbackMatcher);
        } else if (item.category === catMembers) {
            label = (item.p && item.p !== UNNAMED)
                    ? getHighlightedText(item.p + "." + item.c + "." + item.l, matcher, fallbackMatcher)
                    : getHighlightedText(item.c + "." + item.l, matcher, fallbackMatcher);
        } else if (item.category === catSearchTags) {
            label = getHighlightedText(item.l, matcher, fallbackMatcher);
        } else {
            label = item.l;
        }
        var li = $("<li/>").appendTo(ul);
        var div = $("<div/>").appendTo(li);
        if (item.category === catSearchTags && item.h) {
            if (item.d) {
                div.html(label + "<span class=\"search-tag-holder-result\"> (" + item.h + ")</span><br><span class=\"search-tag-desc-result\">"
                                + item.d + "</span><br>");
            } else {
                div.html(label + "<span class=\"search-tag-holder-result\"> (" + item.h + ")</span>");
            }
        } else {
            if (item.m) {
                div.html(item.m + "/" + label);
            } else {
                div.html(label);
            }
        }
        return li;
    }
});
function rankMatch(match, category) {
    if (!match) {
        return NO_MATCH;
    }
    var index = match.index;
    var input = match.input;
    var leftBoundaryMatch = 2;
    var periferalMatch = 0;
    // make sure match is anchored on a left word boundary
    if (index === 0 || /\W/.test(input[index - 1]) || "_" === input[index]) {
        leftBoundaryMatch = 0;
    } else if ("_" === input[index - 1] || (input[index] === input[index].toUpperCase() && !/^[A-Z0-9_$]+$/.test(input))) {
        leftBoundaryMatch = 1;
    }
    var matchEnd = index + match[0].length;
    var leftParen = input.indexOf("(");
    var endOfName = leftParen > -1 ? leftParen : input.length;
    // exclude peripheral matches
    if (category !== catModules && category !== catSearchTags) {
        var delim = category === catPackages ? "/" : ".";
        if (leftParen > -1 && leftParen < index) {
            periferalMatch += 2;
        } else if (input.lastIndexOf(delim, endOfName) >= matchEnd) {
            periferalMatch += 2;
        }
    }
    var delta = match[0].length === endOfName ? 0 : 1; // rank full match higher than partial match
    for (var i = 1; i < match.length; i++) {
        // lower ranking if parts of the name are missing
        if (match[i])
            delta += match[i].length;
    }
    if (category === catTypes) {
        // lower ranking if a type name contains unmatched camel-case parts
        if (/[A-Z]/.test(input.substring(matchEnd)))
            delta += 5;
        if (/[A-Z]/.test(input.substring(0, index)))
            delta += 5;
    }
    return leftBoundaryMatch + periferalMatch + (delta / 200);

}
function doSearch(request, response) {
    var result = [];
    searchPattern = createSearchPattern(request.term);
    fallbackPattern = createSearchPattern(request.term.toLowerCase());
    if (searchPattern === "") {
        return this.close();
    }
    var camelCaseMatcher = createMatcher(searchPattern, "");
    var fallbackMatcher = new RegExp(fallbackPattern, "i");

    function searchIndexWithMatcher(indexArray, matcher, category, nameFunc) {
        if (indexArray) {
            var newResults = [];
            $.each(indexArray, function (i, item) {
                item.category = category;
                var ranking = rankMatch(matcher.exec(nameFunc(item)), category);
                if (ranking < RANKING_THRESHOLD) {
                    newResults.push({ranking: ranking, item: item});
                }
                return newResults.length <= MAX_RESULTS;
            });
            return newResults.sort(function(e1, e2) {
                return e1.ranking - e2.ranking;
            }).map(function(e) {
                return e.item;
            });
        }
        return [];
    }
    function searchIndex(indexArray, category, nameFunc) {
        var primaryResults = searchIndexWithMatcher(indexArray, camelCaseMatcher, category, nameFunc);
        result = result.concat(primaryResults);
        if (primaryResults.length <= MIN_RESULTS && !camelCaseMatcher.ignoreCase) {
            var secondaryResults = searchIndexWithMatcher(indexArray, fallbackMatcher, category, nameFunc);
            result = result.concat(secondaryResults.filter(function (item) {
                return primaryResults.indexOf(item) === -1;
            }));
        }
    }

    searchIndex(moduleSearchIndex, catModules, function(item) { return item.l; });
    searchIndex(packageSearchIndex, catPackages, function(item) {
        return (item.m && request.term.indexOf("/") > -1)
            ? (item.m + "/" + item.l) : item.l;
    });
    searchIndex(typeSearchIndex, catTypes, function(item) {
        return request.term.indexOf(".") > -1 ? item.p + "." + item.l : item.l;
    });
    searchIndex(memberSearchIndex, catMembers, function(item) {
        return request.term.indexOf(".") > -1
            ? item.p + "." + item.c + "." + item.l : item.l;
    });
    searchIndex(tagSearchIndex, catSearchTags, function(item) { return item.l; });

    if (!indexFilesLoaded()) {
        updateSearchResults = function() {
            doSearch(request, response);
        }
        result.unshift(loading);
    } else {
        updateSearchResults = function() {};
    }
    response(result);
}
$(function() {
    $("#search-input").catcomplete({
        minLength: 1,
        delay: 300,
        source: doSearch,
        response: function(event, ui) {
            if (!ui.content.length) {
                ui.content.push(noResult);
            } else {
                $("#search-input").empty();
            }
        },
        autoFocus: true,
        focus: function(event, ui) {
            return false;
        },
        position: {
            collision: "flip"
        },
        select: function(event, ui) {
            if (ui.item.category) {
                var url = getURLPrefix(ui);
                if (ui.item.category === catModules) {
                    url += "module-summary.html";
                } else if (ui.item.category === catPackages) {
                    if (ui.item.u) {
                        url = ui.item.u;
                    } else {
                        url += ui.item.l.replace(/\./g, '/') + "/package-summary.html";
                    }
                } else if (ui.item.category === catTypes) {
                    if (ui.item.u) {
                        url = ui.item.u;
                    } else if (ui.item.p === UNNAMED) {
                        url += ui.item.l + ".html";
                    } else {
                        url += ui.item.p.replace(/\./g, '/') + "/" + ui.item.l + ".html";
                    }
                } else if (ui.item.category === catMembers) {
                    if (ui.item.p === UNNAMED) {
                        url += ui.item.c + ".html" + "#";
                    } else {
                        url += ui.item.p.replace(/\./g, '/') + "/" + ui.item.c + ".html" + "#";
                    }
                    if (ui.item.u) {
                        url += ui.item.u;
                    } else {
                        url += ui.item.l;
                    }
                } else if (ui.item.category === catSearchTags) {
                    url += ui.item.u;
                }
                if (top !== window) {
                    parent.classFrame.location = pathtoroot + url;
                } else {
                    window.location.href = pathtoroot + url;
                }
                $("#search-input").focus();
            }
        }
    });
});
