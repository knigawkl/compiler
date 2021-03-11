from prettytable import PrettyTable

from scanner import Scanner
from exceptions import UnexpectedStartToken, UnexpectedToken, UnknownStatement
from tokens import TokenType
from logger import logger


START_TOKENS = (TokenType.SERVICES, TokenType.VERSION, TokenType.NETWORKS, TokenType.VOLUMES, TokenType.PORTS)
VALUE_TOKENS = (TokenType.NUMBER, TokenType.ID, TokenType.STRING, TokenType.BLANK)


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

    def array(self, item_type: str = TokenType.STRING):
        if self.token.type == TokenType.LI:
            self.take_token(TokenType.LI)
            self.value([item_type])
            self.array(item_type)
        else:
            pass

    def service_dict(self):  # services is a dict and dict values should be somehow heading to service_dict
        # I assume that there is no required field inside service dict, but it does not make sense to define one
        # without defining anything inside
        stmts = {TokenType.VOLUMES: self.volumes_stmt,
                 TokenType.PORTS: self.ports_stmt,
                 TokenType.BUILD: self.build_stmt,
                 TokenType.IMAGE: self.image_stmt,
                 TokenType.ENVIRONMENT: self.environment_stmt,
                 TokenType.DEPLOY: self.deploy_stmt}
        if self.token.type in stmts:
            stmts[self.token.type]()
        else:
            raise UnknownStatement(self.token)

    def services_dict(self):
        if self.token.type == TokenType.ID:
            self.take_token(TokenType.ID)
            self.take_token(TokenType.ASSIGN)
            self.service_dict()
            self.services_dict()
        else:
            pass

    def services_stmt(self):
        start_line = self.token.line
        self.take_token(TokenType.SERVICES)
        # if the next token is not indented, we assume that services dict is empty or invalid
        # hence this should probably lead to an error
        self.take_token(TokenType.ASSIGN)
        self.services_dict()
        self.table.add_row([start_line, self.token.line - 1, TokenType.SERVICES])

    def dictionary(self):
        if self.token.type == TokenType.ID:
            self.take_token(TokenType.ID)
            self.take_token(TokenType.ASSIGN)
            self.value()
            self.dictionary()
        # elif self.token.type in STMT_TOKENS
        else:
            pass

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
        self.array(item_type=TokenType.ID)
        self.table.add_row([start_line, self.token.line - 1, TokenType.NETWORKS])

    def volumes_stmt(self):
        start_line = self.token.line
        self.take_token(TokenType.VOLUMES)
        self.take_token(TokenType.ASSIGN)
        self.array(item_type=TokenType.ID)
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
