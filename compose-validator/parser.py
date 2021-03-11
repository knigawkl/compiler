from prettytable import PrettyTable

from scanner import Scanner
from exceptions import UnexpectedStartToken, UnexpectedToken, UnknownStatement
from tokens import TokenType
from logger import logger


START_TOKENS = (TokenType.SERVICES, TokenType.VERSION, TokenType.NETWORKS, TokenType.VOLUMES, TokenType.PORTS)
VALUE_TOKENS = (TokenType.NUMBER, TokenType.ID, TokenType.STRING)


class Parser:
    """ Performs syntax analysis
    Backus-Naur Form:
    start -> program EOF
    program -> statement program
            -> eps

    statement -> services_stmt
    statement -> networks_stmt
    statement -> volumes_stmt
    statement -> version_stmt

    ports_stmt -> ports assign array
    # volumes_stmt -> volumes assign id assign
    #              -> volumes assign id assign id 
    version_stmt -> version assign string
    # networks_stmt -> networks assign id assign



    array -> li value array
          -> eps

    value -> number
          -> id
          -> string
          -> eps
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

    def take_token(self, token_type: str):
        if self.token.type != token_type:
            raise UnexpectedToken(f'Expected type: {token_type}, but got {self.token}')
        if token_type != TokenType.EOF:
            self.token = self.next_token()

    def parse(self):
        if self.token.type in START_TOKENS or self.token.type == TokenType.EOF:
            self.program()
            self.take_token(TokenType.EOF)
        else:
            raise UnexpectedStartToken(self.token)

    def program(self):
        if self.token.type in START_TOKENS:
            self.statement()
            self.program()
        else:
            pass

    def statement(self):
        stmts = {TokenType.VERSION: self.version_stmt,
                 TokenType.NETWORKS: self.networks_stmt,
                 TokenType.VOLUMES: self.volumes_stmt,
                 TokenType.SERVICES: self.services_stmt}
        if self.token.type in stmts:
            stmts[self.token.type]()
        else:
            raise UnknownStatement(self.token)

    def service_dict(self):
        """
        Statements inside service are read here.
        List of possible statements inside service is strictly constrained and listed below inside service_stmts
        """
        start_line, service_name, indent_level = self.token.line, self.token.value, self.token.column
        service_stmts = {TokenType.PORTS: self.ports_stmt,
                         TokenType.BUILD: self.build_stmt,
                         TokenType.IMAGE: self.image_stmt,
                         TokenType.ENVIRONMENT: self.environment_stmt,
                         TokenType.DEPLOY: self.deploy_stmt,
                         TokenType.NETWORKS: self.service_networks_stmt,
                         TokenType.VOLUMES: self.service_volumes_stmt,}
        if self.token.type in service_stmts:
            service_stmts[self.token.type]()
        elif self.token.type == TokenType.EOF:
            pass
        else:
            raise UnknownStatement(self.token)
        if self.token.column == indent_level:
            self.service_dict()

    def array(self, item_type: str = TokenType.STRING):
        if self.token.type == TokenType.LI:
            self.take_token(TokenType.LI)
            self.value([item_type])
            self.array(item_type)
        else:
            pass

    def volume_array(self):
        if self.token.type == TokenType.LI:
            self.take_token(TokenType.LI)
            self.value([TokenType.ID])
            self.take_token(TokenType.ASSIGN)
            self.value([TokenType.ID])
            self.volume_array()
        else:
            pass

    def services_dict(self):
        """
        IDs here are names of the services. Phrases like "wordpress:" are read
        and then self.service_dict() is called in order to read content of each service
        """
        start_line, service_name, indent_level = self.token.line, self.token.value, self.token.column
        if self.token.type == TokenType.ID:
            self.take_token(TokenType.ID)
            self.take_token(TokenType.ASSIGN)
            self.service_dict()
            self.table.add_row([start_line, self.token.line - 1, f"{TokenType.SERVICES}:{service_name}"])
            if self.token.column == indent_level:
                self.services_dict()
        else:
            pass

    # def dictionary(self):
    #     if self.token.type == TokenType.ID:
    #         self.take_token(TokenType.ID)
    #         self.take_token(TokenType.ASSIGN)
    #         self.value()
    #         self.dictionary()
    #     else:
    #         pass

    def services_stmt(self):
        """
        self.statement() has found services keyword and routes us here
        phrase "services:" is read and then we start reading the services one by one
        """
        start_line = self.token.line
        self.take_token(TokenType.SERVICES)
        self.take_token(TokenType.ASSIGN)
        self.services_dict()
        self.table.add_row([start_line, self.token.line - 1, TokenType.SERVICES])

    def version_stmt(self):
        start_line = self.token.line
        self.take_token(TokenType.VERSION)
        self.take_token(TokenType.ASSIGN)
        self.value([TokenType.STRING])
        self.table.add_row([start_line, self.token.line - 1, TokenType.VERSION])

    def ports_stmt(self):
        start_line = self.token.line
        self.take_token(TokenType.PORTS)
        self.take_token(TokenType.ASSIGN)
        self.array(item_type=TokenType.STRING)
        self.table.add_row([start_line, self.token.line - 1, TokenType.PORTS])

    def networks_stmt(self):
        start_line = self.token.line
        self.take_token(TokenType.NETWORKS)
        self.take_token(TokenType.ASSIGN)
        # self.array(item_type=TokenType.ID) # this should be any dictionary
        self.table.add_row([start_line, self.token.line - 1, TokenType.NETWORKS])

    def service_networks_stmt(self):
        start_line = self.token.line
        self.take_token(TokenType.NETWORKS)
        self.take_token(TokenType.ASSIGN)
        self.array(item_type=TokenType.ID)
        self.table.add_row([start_line, self.token.line - 1, TokenType.NETWORKS])

    def service_volumes_stmt(self):
        start_line = self.token.line
        self.take_token(TokenType.VOLUMES)
        self.take_token(TokenType.ASSIGN)
        self.volume_array()
        self.table.add_row([start_line, self.token.line - 1, TokenType.VOLUMES])

    def volumes_stmt(self):
        start_line = self.token.line
        # self.take_token(TokenType.VOLUMES)
        # self.take_token(TokenType.ASSIGN)
        # self.volume_array()
        self.table.add_row([start_line, self.token.line - 1, TokenType.VOLUMES])

    def build_stmt(self):
        pass

    def image_stmt(self):
        pass

    def environment_stmt(self):
        pass

    def deploy_stmt(self):
        pass

    def value(self, allowed_types: [str] = VALUE_TOKENS):
        if self.token.type in allowed_types:
            self.take_token(self.token.type)
        else:
            raise UnexpectedToken(f'Expected types: {allowed_types}, but got {self.token}')
