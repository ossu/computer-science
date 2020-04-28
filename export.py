#!/usr/bin/env python3
"""Create and/or populate a Trello board from the ossu/computer-science curriculum JSON.

This script requires requests>=2.23.0, as well as a valid Trello API key and token (https://trello.com/app-key).
Trello API credentials are provided using the following environment variables:

TRELLO_API_KEY
TRELLO_API_TOKEN

Examples:
    To download the curriculum and create a new board, simply run::

        $ python export.py

    To use your own curriculum.json, provide the filename using the -f flag::

        $ python export.py -f curriculum.json

    To populate an empty board, provide the ID using the -b flag (e.g., trello.com/b/Wvt0LK6d/ossu-compsci)::

        $ python export.py -b Wvt0LK6d

Attributes:
    CURRICULUM_PATH (str): The location of the curriculum JSON in the ossu/computer-science repository.
    JSON_OPTIONS (dict): A keyword dictionary provided to ``json.dump`` / ``json.dumps`` when writing JSON to disk.

"""

import json
import os
import os.path as path
import re
import sys
from argparse import ArgumentParser
from base64 import b64decode
from datetime import datetime
from enum import Enum

from requests import (
    Response,
    request
)

CURRICULUM_PATH = 'curriculum.json?ref=JSON'
JSON_OPTIONS = { 'indent': 2, 'ensure_ascii': False }


class GitHubContent(object):

    @property
    def json(self) -> dict:
        return self._data

    @property
    def content(self) -> str:
        return self._data['content']

    def __init__(self, data:dict):
        self._content = None
        self._data = data or None


class CurriculumContent(GitHubContent):

    @classmethod
    def from_cache_model(cls, cache_model:dict):
        curriculum_content = cls(dict())
        curriculum_content._content = cache_model
        return curriculum_content

    @classmethod
    def from_github_content(cls, github_content:GitHubContent):
        return cls(github_content.json)

    @property
    def content(self) -> dict:
        if self._content:
            return self._content
        content = b64decode(super().content)
        self._content = json.loads(content)
        return self.content


class OssuCurriculum(object):

    class SubsectionType(Enum):

        unknown         = (0, 'Unknown')
        intro           = (1, 'Introduction to Programming')
        programming     = (2, 'Programming')
        maths = math    = (3, 'Maths')
        systems         = (4, 'Systems')
        theory          = (5, 'Theory')
        applications    = (6, 'Applications')

        @classmethod
        def from_subsection_name(cls, s:str) -> Enum:
            match = re.search(r'(intro|programming|electives|maths?|systems|theory|applications)', s, flags=re.I)
            return cls.__members__.get(match.group(1).lower() if match else None, cls.unknown)

        def __init__(self, value:int, label:str):
            self._value = value
            self.label  = label

    class CurriculumNode(object):

        __slots__ = ('name',)

        def __init__(self, name:str):
            self.name = name

        def __str__(self):
            return self.name

    class Course(CurriculumNode):

        _RE_NONCONFORMATIVE:re.Pattern = re.compile(r'(.+?)\)?\|\s(.+)\s\|\s(.+)')

        __slots__ = ('url', 'url_alt', 'duration', 'effort', 'extra', 'prerequisites', '__repr__')

        def __init__(self, course:dict):

            duration, effort = course.get('Duration'), course.get('Effort')

            url = course.get('URL')
            if not (effort or duration):
                # Core Applications contains Markdown artefacts from the course table in the URL field:
                # Format: '{url}| {duration} | {effort}'
                match:re.Match = self._RE_NONCONFORMATIVE.search(url)
                if (match is not None and len(match.groups()) == 3):
                    url         = match.group(1)
                    duration    = match.group(2)
                    effort      = match.group(3)
                else:
                    raise ValueError('no effort, duration or supported URL defined')

            super().__init__(name=course['Name'])
            self.url:str            = url
            self.url_alt:str        = course.get('Alternative', str())
            self.duration:str       = duration
            self.effort:str         = effort
            self.extra:str          = course.get("Additional Text / Assignments", str())
            self.prerequisites:str  = course['Prerequisites']
            self.__repr__ = lambda: f"{type(self).__module__}.{type(self).__qualname__}({course})"

    class Discipline(CurriculumNode):

        __slots__ = ('courses', '__repr__')

        def __init__(self, discipline):
            super().__init__(name=discipline['Name'])
            self.courses:list   = [OssuCurriculum.Course(i) for i in discipline['Courses']]
            self.__repr__ = lambda: f"{type(self).__module__}.{type(self).__qualname__}({discipline})"

    class Subsection(CurriculumNode):

        __slots__ = ('explanation', 'topics', 'courses', 'disciplines', '__repr__')

        def __init__(self, subsection):
            super().__init__(name=subsection['Name'])
            self.explanation:str    = subsection.get('Explanation', str())
            self.topics:list        = [i['Name'] for i in subsection["Topics Covered"]]
            self.courses:list       = [OssuCurriculum.Course(i) for i in subsection.get('Courses', list())]
            # Only applies to core maths electives:
            self.disciplines:list   = [OssuCurriculum.Discipline(i) for i in subsection.get('Disciplines', list())]
            self.__repr__ = lambda: f"{type(self).__module__}.{type(self).__qualname__}({subsection})"

    class Section(CurriculumNode):

        __slots__ = ('explanation', 'subsections', '__repr__')

        def __init__(self, section):
            super().__init__(name=section['Section Name'])
            self.explanation:str  = section['Explanation']
            self.subsections:list = [OssuCurriculum.Subsection(i) for i in section['Subsections']]
            self.__repr__ = lambda: f"{type(self).__module__}.{type(self).__qualname__}({section})"

    __slots__ = ('sections', '__repr__')

    def __init__(self, curriculum:list):
        self.sections = [OssuCurriculum.Section(i) for i in curriculum]
        self.__repr__ = lambda: f"{type(self).__module__}.{type(self).__qualname__}({curriculum})"


class TrelloClient(object):

    TRELLO_ENDPOINT = 'https://api.trello.com/1'

    @property
    def params(self) -> dict:
        return { 'key': self.api_key, 'token': self.api_token }

    def create_board(self) -> str:
        response = self.request_method('POST', f'{self.TRELLO_ENDPOINT}/boards',
            params = self.params,
            data = {
                'name': "OSSU - CompSci",
                'defaultLabels': 'false',
                'defaultLists': 'false'
            }
        )
        data = json.loads(response.content)
        return data['id']

    def create_list(self, board_id:str, name:str):
        response = self.request_method('POST', f'{self.TRELLO_ENDPOINT}/boards/{board_id}/lists',
            params = self.params,
            data = {
                'name': name,
                'pos': 'bottom'
            }
        )
        data = json.loads(response.content)
        return data['id']

    def create_label(self, board_id:str, name:str, color:str) -> str:
        response = self.request_method('POST', f'{self.TRELLO_ENDPOINT}/boards/{board_id}/labels',
            params = self.params,
            data = {
                'name': name,
                'color': color
            }
        )
        data = json.loads(response.content)
        return data['id']

    def create_card(self, list_id:str, label_ids:list, name:str, desc:str) -> str:
        response = self.request_method('POST', f'{self.TRELLO_ENDPOINT}/cards',
            params = self.params,
            data = {
                'idList': list_id,
                'idLabels': label_ids,
                'name': name,
                'desc': desc,
                'pos': 'bottom'
            }
        )
        data = json.loads(response.content)
        return data['id']

    def __init__(self, api_key:str, api_token:str, request_method):
        self.api_key = api_key
        self.api_token = api_token
        self.request_method = request_method


def _parse_args(argv:list):

    parser = ArgumentParser('ossu-export')
    parser.add_argument('-b', '--board_id', type=str, metavar='S',
        help="the ID of a Trello board to populate"
    )
    parser.add_argument('-f', '--from_file', type=str, metavar='S',
        help="a JSON file to load decoded content from"
    )
    parser.add_argument('-t', '--to_file', type=str, metavar='S',
        help="a JSON file to save raw content to"
    )

    args = parser.parse_args(argv).__dict__
    return dict([(k,v) for k,v in args.items() if v])


def _request(method:str, url:str, **kwargs):
    response = request(method, url, **kwargs)
    response.raise_for_status()
    return response


def get_github_content() -> GitHubContent:
    response:Response = _request(
        'GET', f'https://api.github.com/repos/ossu/computer-science/contents/{CURRICULUM_PATH}',
        headers = { 'Accept': "application/vnd.github.v3+json" }
    )
    data = json.loads(response.content)
    return GitHubContent(data)


def get_curriculum_content(from_file:str) -> CurriculumContent:
    with open(from_file, 'r') as fp:
        content = fp.read()
    data = json.loads(content)
    return CurriculumContent.from_cache_model(data)


def main(board_id:str = None, from_file:str = None, to_file:str = None) -> None:

    # --------------- Curriculum retrieval --------------- #

    cache_path = path.abspath(f"{datetime.utcnow().strftime('%Y%m%d')}_curriculum.json")
    if (not from_file and path.exists(cache_path)):
        print(f"Using cached result from {cache_path}")
        from_file = cache_path

    curriculum_content:CurriculumContent
    if from_file:
        curriculum_content = get_curriculum_content(from_file)
    else:
        github_content = get_github_content()
        curriculum_content = CurriculumContent.from_github_content(github_content)

        out = to_file.strip() if to_file else None
        if out:
            out = path.abspath(out)
            print(f"Writing response to file... (-> {out})")
            with open(out, 'w') as fp:
                fp.write(json.dumps(github_content.json, **JSON_OPTIONS))

    if not from_file:
        print(f"Writing decoded response to file (-> {cache_path})")
        with open(cache_path, 'w') as fp:
            json.dump(curriculum_content.content, fp, **JSON_OPTIONS)

    curriculum = OssuCurriculum(curriculum_content.content['Curriculum'])

    # --------------- Board creation --------------- #

    trello_api_key = os.environ.get('TRELLO_API_KEY')
    trello_api_token = os.environ.get('TRELLO_API_TOKEN')

    if not (trello_api_key and trello_api_token):
        print("Trello API key not found")
        return

    trello_client = TrelloClient(trello_api_key, trello_api_token, _request)

    if board_id is None:
        board_id = trello_client.create_board()

    print("Creating lists... ", end='')
    lists = {
        'curriculum': trello_client.create_list(board_id, 'Curriculum'),
        'doing': trello_client.create_list(board_id, 'Doing'),
        'done': trello_client.create_list(board_id, 'Done')
    }
    print(lists)

    print("Creating labels... ", end='')
    labels = {
        'curriculum': trello_client.create_label(board_id, "Main Curriculum", 'black'),
        'extra': trello_client.create_label(board_id, 'Extra Resources', 'orange'),
        'subsection': trello_client.create_label(board_id, 'Subsection', 'purple')
    }
    print(labels)

    section_colors = ('sky', 'purple', 'yellow')

    course_number = 1
    section:OssuCurriculum.Section
    for (idx,section) in enumerate(curriculum.sections):
        label = trello_client.create_label(board_id, section.name, section_colors[idx])

        subsection:OssuCurriculum.Subsection
        for subsection in section.subsections:
            subsection_type = OssuCurriculum.SubsectionType.from_subsection_name(subsection.name)
            courses = subsection.courses or [c for d in subsection.disciplines for c in d.courses]

            print(f"Creating {section.name} / {subsection.name}")

            course:OssuCurriculum.Course
            for course in courses:
                name = f"{course_number:03d} - {course.name}"

                _alt  = f" ([alt]({course.url_alt})" if course.url_alt else ''

                desc  = f"**Subsection**: {section.name} - {subsection_type.label}\n"
                desc += f"**Course**: [{course.name}]({course.url}){_alt}\n"
                desc += f"**Final Project**: _link to your GitHub repository_"

                print(f"Creating {section.name} / {subsection.name} / {course.name}")

                trello_client.create_card(lists['curriculum'], [label, labels['curriculum']], name, desc)
                course_number += 1

    for subsection_type in list(OssuCurriculum.SubsectionType)[1:]:
        print(f"Creating divider '{subsection_type.label}'")
        trello_client.create_card(lists['done'], [labels['subsection'], ], subsection_type.label, desc=str())

    print("🎉")


if __name__ == '__main__':
    try:
        main(**_parse_args(sys.argv[1:]))
    except KeyboardInterrupt:
        pass
