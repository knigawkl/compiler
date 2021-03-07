from scanner import Scanner


class Parser:
    """
    Backus-Naur Form

    """
    def __init__(self, scanner: Scanner):
        self.next_token = scanner.next_token()
        self.token = self.next_token()

    def take_token(self, token_type: str):
        if self.token.type != token_type:
            raise RuntimeError(f'Unexpected token: {token_type}')
        if token_type != 'EOF':
            self.token = self.next_token()

    def parse(self):
        """ start -> program EOF """
        if self.token.type == 'ID':
            self.program()
            self.take_token('EOF')
        else:
            raise RuntimeError

    def program(self):
        """ program -> statement program OR program -> eps """
