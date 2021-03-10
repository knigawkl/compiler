from scanner import Scanner
from parser import Parser
from utils import read_file, get_parser


if __name__ == "__main__":
    parser = get_parser()
    args = parser.parse_args()
    input_string = read_file(args.filepath)
    Parser(Scanner(input_string)).parse()

