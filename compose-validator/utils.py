import argparse

from logger import logger


def read_file(filepath: str) -> str:
    with open(filepath, 'r') as f:
        return f.read()


def print_file(filepath: str):
    with open(filepath, 'r') as f:
        for i, line in enumerate(f, start=1):
            logger.info('{} | {}'.format(i, line[:-1]))


def print_namedtuple(t, field_widths=25):
    if isinstance(field_widths, int):
        field_widths = [field_widths] * len(t)
    field_pairs = ['{}={}'.format(field, getattr(t, field)) for field in t._fields]
    s = ' '.join('{{:{}}}'.format(w).format(f) for w,f in zip(field_widths, field_pairs))
    result = '{}( {} )'.format(type(t).__name__, s)
    logger.info(result)


def get_parser() -> argparse.PARSER:
    parser = argparse.ArgumentParser()
    parser.add_argument('--filepath',
                        type=str,
                        help='Path to docker-compose file',
                        default='fixtures/isod.yaml')
    return parser
