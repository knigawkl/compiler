from prettytable import PrettyTable

from scanner import Scanner
from exceptions import UnexpectedStartToken, UnexpectedToken, UnknownStatement
from tokens import TokenType
from logger import logger


START_TOKENS = (TokenType.SERVICES, TokenType.VERSION, TokenType.NETWORKS, TokenType.VOLUMES, TokenType.PORTS)
VALUE_TOKENS = (TokenType.NUMBER, TokenType.ID, TokenType.STRING)


class Parser:
    """ Performs syntax analysis

    Backus-Naur Form (BNF grammar):

    <services> ::= 'services'
    <version> ::= 'version'
    <networks> ::= 'networks'
    <volumes> ::= 'volumes'
    <build> ::= 'build'
    <ports> ::= 'ports'
    <image> ::= 'image'
    <environment> ::= 'environment'
    <deploy> ::= 'deploy'

    <number> ::= r'\d+(\.\d*)?'
    <id> ::= r'[A-Za-z_./-]+'
    <string> ::= r'\"(.*?)\"'

    <assign> ::= ':'
    <li> ::= '-'
    <eof> ::= end of file
    <eps> ::= blank

    <array> ::= <li> <value> <array> | <eps>
    <volume_array> ::= <li> <value> <assign> <value> <volume_array> | <eps>
    <dictionary> ::= <id> <assign> <value> <dictionary> | <eps>
    <value> ::= <number> | <id> | <string> | <eps>

    <start> ::= <program> <eof>
    <program> ::= <statement> <program> | <eps>
    <statement> ::= <version_stmt> | <services_stmt> | <networks_stmt> | <volumes_stmt>
    <services_stmt> ::= <ports_stmt> | <build_stmt> | <image_stmt> | <environment_stmt> | <deploy_stmt> |
                        <service_networks_stmt> | <service_volumes_stmt>
    <version_stmt> ::= <version> <assign> <string>
    <networks_stmt> ::= <networks> <dictionary>
    <volumes_stmt> ::= <volumes> <dictionary>

    <ports_stmt> ::= <ports> <assign> <array>
    <build_stmt> ::= <build> <assign> <id>
    <image_stmt> ::= <image> <assign> <id>
    <environment_stmt> ::= <environment> <dictionary>
    <deploy_stmt> ::= <deploy> <dictionary>
    <service_networks_stmt> ::= <networks> <assign> <array>
    <service_volumes_stmt> ::= <volumes> <assign> <volume_array>
    """
    def __init__(self, scanner: Scanner):
        logger.info("\nPerforming syntax analysis")
        self.next_token = scanner.next_token
        self.token = self.next_token()
        self.table = PrettyTable()
        self.table.field_names = ['Start line', 'End line', 'Statement type']
        self.indent_level = 0

    def __del__(self):
        logger.info(self.table.get_string(sortby='Start line', sort_key=lambda row: int(row[0])))

    def __take_token(self, token_type: str):
        if self.token.type != token_type:
            raise UnexpectedToken(f'Expected type: {token_type}, but got {self.token}')
        if token_type != TokenType.EOF:
            self.token = self.next_token()

    def parse(self):
        if self.token.type in START_TOKENS or self.token.type == TokenType.EOF:
            self.__program()
            self.__take_token(TokenType.EOF)
        else:
            raise UnexpectedStartToken(self.token)

    def __program(self):
        if self.token.type in START_TOKENS:
            self.__statement()
            self.__program()
        else:
            pass

    def __statement(self):
        stmts = {TokenType.VERSION: self.__version_stmt,
                 TokenType.NETWORKS: self.__networks_stmt,
                 TokenType.VOLUMES: self.__volumes_stmt,
                 TokenType.SERVICES: self.__services_stmt}
        if self.token.type in stmts:
            stmts[self.token.type]()
        else:
            raise UnknownStatement(self.token)

    def __service_statement(self):
        """
        Statements inside service are read here.
        List of possible statements inside service is strictly constrained and listed below inside service_stmts.
        """
        start_line, service_name, indent_level = self.token.line, self.token.value, self.token.column
        service_stmts = {TokenType.PORTS: self.__ports_stmt,
                         TokenType.BUILD: self.__build_stmt,
                         TokenType.IMAGE: self.__image_stmt,
                         TokenType.ENVIRONMENT: self.__environment_stmt,
                         TokenType.DEPLOY: self.__deploy_stmt,
                         TokenType.NETWORKS: self.__service_networks_stmt,
                         TokenType.VOLUMES: self.__service_volumes_stmt, }
        if self.token.type in service_stmts:
            service_stmts[self.token.type]()
        elif self.token.type == TokenType.EOF:
            pass
        else:
            raise UnknownStatement(self.token)
        if self.token.column == indent_level:
            self.__service_statement()

    def __array(self, item_type: str = TokenType.STRING):
        if self.token.type == TokenType.LI:
            self.__take_token(TokenType.LI)
            self.__value([item_type])
            self.__array(item_type)
        else:
            pass

    def __volume_array(self):
        if self.token.type == TokenType.LI:
            self.__take_token(TokenType.LI)
            self.__value([TokenType.ID])
            self.__take_token(TokenType.ASSIGN)
            self.__value([TokenType.ID])
            self.__volume_array()
        else:
            pass

    def __dictionary(self):
        start_line, service_name, indent_level = self.token.line, self.token.value, self.token.column
        if self.token.type == TokenType.ID:
            self.__take_token(TokenType.ID)
            self.__take_token(TokenType.ASSIGN)
            if self.token.line == start_line:
                self.__value()
            if self.token.column == indent_level:
                self.__dictionary()
        else:
            pass

    def __take_dict(self):
        self.__take_token(TokenType.ASSIGN)
        self.__dictionary()

    def __services_dict(self):
        """
        IDs here are names of the services. Phrases like "wordpress:" are read
        and then self.service_dict() is called in order to read content of each service
        """
        start_line, service_name, indent_level = self.token.line, self.token.value, self.token.column
        if self.token.type == TokenType.ID:
            self.__take_token(TokenType.ID)
            self.__take_token(TokenType.ASSIGN)
            self.__service_statement()
            self.table.add_row([start_line, self.token.line - 1, f"{TokenType.SERVICES}:{service_name}"])
            if self.token.column == indent_level:
                self.__services_dict()
        else:
            pass

    def __services_stmt(self):
        """
        self.statement() has found services keyword and routes us here
        phrase "services:" is read and then we start reading the services one by one
        """
        start_line = self.token.line
        self.__take_token(TokenType.SERVICES)
        self.__take_token(TokenType.ASSIGN)
        self.__services_dict()
        self.table.add_row([start_line, self.token.line - 1 if (self.token.line - 1) > 0 else 1, TokenType.SERVICES])

    def __version_stmt(self):
        start_line = self.token.line
        self.__take_token(TokenType.VERSION)
        self.__take_token(TokenType.ASSIGN)
        self.__value([TokenType.STRING])
        self.table.add_row([start_line, self.token.line - 1 if (self.token.line - 1) > 0 else 1, TokenType.VERSION])

    def __ports_stmt(self):
        start_line = self.token.line
        self.__take_token(TokenType.PORTS)
        self.__take_token(TokenType.ASSIGN)
        self.__array(item_type=TokenType.STRING)
        self.table.add_row([start_line, self.token.line - 1, TokenType.PORTS])

    def __service_networks_stmt(self):
        start_line = self.token.line
        self.__take_token(TokenType.NETWORKS)
        self.__take_token(TokenType.ASSIGN)
        self.__array(item_type=TokenType.ID)
        self.table.add_row([start_line, self.token.line - 1, TokenType.NETWORKS])

    def __service_volumes_stmt(self):
        start_line = self.token.line
        self.__take_token(TokenType.VOLUMES)
        self.__take_token(TokenType.ASSIGN)
        self.__volume_array()
        self.table.add_row([start_line, self.token.line - 1, TokenType.VOLUMES])

    def __volumes_stmt(self):
        start_line = self.token.line
        self.__take_token(TokenType.VOLUMES)
        self.__take_dict()
        self.table.add_row([start_line, self.token.line - 1 if (self.token.line - 1) > 0 else 1, TokenType.VOLUMES])

    def __build_stmt(self):
        start_line = self.token.line
        self.__take_token(TokenType.BUILD)
        self.__take_token(TokenType.ASSIGN)
        self.__value([TokenType.ID])
        self.table.add_row([start_line, self.token.line - 1, TokenType.BUILD])

    def __image_stmt(self):
        start_line = self.token.line
        self.__take_token(TokenType.IMAGE)
        self.__take_token(TokenType.ASSIGN)
        self.__value([TokenType.ID])
        self.table.add_row([start_line, self.token.line - 1, TokenType.IMAGE])

    def __networks_stmt(self):
        start_line = self.token.line
        self.__take_token(TokenType.NETWORKS)
        self.__take_dict()
        self.table.add_row([start_line, self.token.line - 1 if (self.token.line - 1) > 0 else 1, TokenType.NETWORKS])

    def __environment_stmt(self):
        start_line = self.token.line
        self.__take_token(TokenType.ENVIRONMENT)
        self.__take_dict()
        self.table.add_row([start_line, self.token.line - 1, TokenType.ENVIRONMENT])

    def __deploy_stmt(self):
        start_line = self.token.line
        self.__take_token(TokenType.DEPLOY)
        self.__take_dict()
        self.table.add_row([start_line, self.token.line - 1, TokenType.DEPLOY])

    def __value(self, allowed_types: [str] = VALUE_TOKENS):
        if self.token.type in allowed_types:
            self.__take_token(self.token.type)
        else:
            raise UnexpectedToken(f'Expected types: {allowed_types}, but got {self.token}')
