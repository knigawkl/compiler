from scanner import Scanner
from parser import Parser
from utils import read_file


COMPOSE_PATH = "/mnt/c/Users/lk/OneDrive/Pulpit/JFIK/projekt/compiler/compose-validator/fixtures/isod.yaml"


if __name__ == "__main__":
    input_string = read_file(COMPOSE_PATH)
    Parser(Scanner(input_string)).parse()
