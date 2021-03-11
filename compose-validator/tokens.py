import collections


Token = collections.namedtuple('Token', ['type', 'value', 'line', 'column'])

class TokenType:
    SERVICES = 'services'
    VERSION = 'version'
    NETWORKS = 'networks'
    VOLUMES = 'volumes'
    BUILD = 'build'
    PORTS = 'ports'
    IMAGE = 'image'
    ENVIRONMENT = 'environment'
    DEPLOY = 'deploy'

    NUMBER = 'number'
    ID = 'id'
    STRING = 'string'
    BLANK = ''

    ASSIGN = 'assign'
    LI = 'li'
    NEWLINE = 'newline'
    SKIP = 'skip'
    EOF = 'eof'

