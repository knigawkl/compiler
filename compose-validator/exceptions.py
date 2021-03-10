class UnexpectedChar(Exception):
    pass


class NoMoreTokens(Exception):
    pass


class UnexpectedToken(Exception):
    pass


class UnexpectedStartToken(UnexpectedToken):
    pass


class UnknownStatement(Exception):
    pass
