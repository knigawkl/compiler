import logging
import sys

logger = logging.getLogger("compose-validator")
logging.basicConfig(stream=sys.stdout,
                    level=logging.DEBUG,
                    format="%(message)s")
