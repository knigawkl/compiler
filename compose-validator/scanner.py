import re

from exceptions import UnexpectedChar, NoMoreTokens
from tokens import TokenType, Token
from utils import print_namedtuple
from logger import logger


KEYWORDS = {TokenType.VERSION, TokenType.SERVICES, TokenType.BUILD, TokenType.PORTS, TokenType.IMAGE,
            TokenType.VOLUMES, TokenType.ENVIRONMENT, TokenType.NETWORKS, TokenType.DEPLOY}
TOKEN_SPECIFICATION = [
    (TokenType.STRING, r'\"(.*?)\"'),
    (TokenType.NUMBER, r'\d+(\.\d*)?'),
    (TokenType.ID, r'[A-Za-z_./-]+'),
    (TokenType.ASSIGN, r':'),
    (TokenType.LI, r'-'),
    (TokenType.NEWLINE, r'\n'),
    (TokenType.SKIP, r'[ \t]')
]
TOKEN_RE = '|'.join('(?P<%s>%s)' % pair for pair in TOKEN_SPECIFICATION)
get_token = re.compile(pattern=TOKEN_RE).match


class Scanner:
    """ Performs lexical analysis """
    def __init__(self, string: str):
        logger.info("\nPerforming lexical analysis")
        self.tokens = []
        self.token_cnt = 0
        for token in self.__tokenize(string):
            self.tokens.append(token)
        for t in self.tokens:
            print_namedtuple(t)

    def __tokenize(self, string: str) -> Token:
        line_number = 1
        position = 0
        line_start = 0

        match = get_token(string)
        while match is not None:
            match_type = match.lastgroup
            if match_type == TokenType.NEWLINE:
                line_start = position
                line_number += 1
            elif match_type != TokenType.SKIP:
                value = match.group(match_type)
                if match_type == TokenType.ID and value in KEYWORDS:
                    match_type = value
                yield Token(match_type, value, line_number, match.start() - line_start)
            position = match.end()
            match = get_token(string, position)

        if position != len(string):
            raise UnexpectedChar(f'Unexpected character {string[position]} in line {line_number}')
        yield Token(TokenType.EOF, '', line_number, str())

    def next_token(self):
        self.token_cnt += 1
        if self.token_cnt - 1 < len(self.tokens):
            return  self.tokens[self.token_cnt - 1]
        else:
            raise NoMoreTokens
