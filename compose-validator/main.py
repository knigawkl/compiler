from scanner import Scanner
from parser import Parser
from utils import read_file, print_file, get_parser
from logger import logger


if __name__ == "__main__":
    parser = get_parser()
    args = parser.parse_args()
    input_string = read_file(args.filepath)
    logger.info(f"Starting compose-validator for {args.filepath}")
    print_file(args.filepath)
    Parser(Scanner(input_string)).parse()
