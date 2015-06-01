// Some utility functions in js

var u = module.exports = {
  array: {
    // Returns a copy of the array with the value removed once
    //
    //     [1, 2, 3, 1].del 1 #=> [2, 3, 1]
    //     [1, 2, 3].del 4    #=> [1, 2, 3]
    del: function (arr, val) {
      var index = arr.indexOf(val);
      if (index != -1) {
        if (index == 0) {
         return arr.slice(1)
        } else {
          return arr.slice(0, index).concat(arr.slice(index+1));
        }
      } else {
        return arr;
      }
    },

    // Returns the first element of the array
    //
    //     [1, 2, 3].first() #=> 1
    first: function(arr) {
      return arr[0];
    },

    // Returns the last element of the array
    //
    //     [1, 2, 3].last()  #=> 3
    last: function(arr) {
      return arr[arr.length-1];
    }
  },
  string: {
    // Returns a copy of str with all occurrences of pattern replaced with either replacement or the return value of a function.
    // The pattern will typically be a Regexp; if it is a String then no regular expression metacharacters will be interpreted
    // (that is /\d/ will match a digit, but ‘\d’ will match a backslash followed by a ‘d’).
    //
    // In the function form, the current match object is passed in as a parameter to the function, and variables such as
    // $[1], $[2], $[3] (where $ is the match object) will be set appropriately. The value returned by the function will be
    // substituted for the match on each call.
    //
    // The result inherits any tainting in the original string or any supplied replacement string.
    //
    //     "hello".gsub /[aeiou]/, '*'      #=> "h*ll*"
    //     "hello".gsub /[aeiou]/, '<$1>'   #=> "h<e>ll<o>"
    //     "hello".gsub /[aeiou]/, ($) {
    //       "<#{$[1]}>"                    #=> "h<e>ll<o>"
    //
    gsub: function (str, pattern, replacement) {
      var i, match, matchCmpr, matchCmprPrev, replacementStr, result, self;
      if (!((pattern != null) && (replacement != null))) return u.string.value(str);
      result = '';
      self = str;
      while (self.length > 0) {
        if ((match = self.match(pattern))) {
          result += self.slice(0, match.index);
          if (typeof replacement === 'function') {
            match[1] = match[1] || match[0];
            result += replacement(match);
          } else if (replacement.match(/\$[1-9]/)) {
            matchCmprPrev = match;
            matchCmpr = u.array.del(match, void 0);
            while (matchCmpr !== matchCmprPrev) {
              matchCmprPrev = matchCmpr;
              matchCmpr = u.array.del(matchCmpr, void 0);
            }
            match[1] = match[1] || match[0];
            replacementStr = replacement;
            for (i = 1; i <= 9; i++) {
              if (matchCmpr[i]) {
                replacementStr = u.string.gsub(replacementStr, new RegExp("\\\$" + i), matchCmpr[i]);
              }
            }
            result += replacementStr;
          } else {
            result += replacement;
          }
          self = self.slice(match.index + match[0].length);
        } else {
          result += self;
          self = '';
        }
      }
      return result;
    },

    // Returns a copy of the String with the first letter being upper case
    //
    //     "hello".upcase #=> "Hello"
    upcase: function(str) {
      var self = u.string.gsub(str, /_([a-z])/, function ($) {
        return "_" + $[1].toUpperCase();
      });
      self = u.string.gsub(self, /\/([a-z])/, function ($) {
        return "/" + $[1].toUpperCase();
      });
      return self[0].toUpperCase() + self.substr(1);
    },

    // Returns a copy of capitalized string
    //
    //     "employee salary" #=> "Employee Salary"
    capitalize: function (str, spaces) {
      var self = str.toLowerCase();
      if(!spaces) {
        self = u.string.gsub(self, /\s([a-z])/, function ($) {
          return " " + $[1].toUpperCase();
        });
      }
      return self[0].toUpperCase() + self.substr(1);
    },

    // Returns a copy of the String with the first letter being lower case
    //
    //     "HELLO".downcase #=> "hELLO"
    downcase: function(str) {
      var self = u.string.gsub(str, /_([A-Z])/, function ($) {
        return "_" + $[1].toLowerCase();
      });
      self = u.string.gsub(self, /\/([A-Z])/, function ($) {
        return "/" + $[1].toLowerCase();
      });
      return self[0].toLowerCase() + self.substr(1);
    },

    // Returns a string value for the String object
    //
    //     "hello".value() #=> "hello"
    value: function (str) {
      return str.substr(0);
    }
  }
}
