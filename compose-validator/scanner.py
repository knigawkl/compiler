import re
import collections


Token = collections.namedtuple('Token', ['type', 'value', 'line', 'column'])


class Scanner:
    def __init__(self, input_string: str):
        self.tokens = []
        self.token_cnt = 0

    def tokenize(self) -> Token:
        keywords = {}
        token_specification = [
            (),
            ()
        ]
        line_number = 1
        # todo
        yield Token('EOF', '', line_number, str())
