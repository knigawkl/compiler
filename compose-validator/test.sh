#!/bin/zsh

# in order to cat Windows file endings (in order to run under WSL2):
# sed -i -e 's/\r$//' test.sh

fixtures="/mnt/c/Users/lk/OneDrive - Politechnika Warszawska/JFIK/projekt/compiler/compose-validator/fixtures/"
python main.py --filepath "${fixtures}blank.yaml"
python main.py --filepath "${fixtures}version.yaml"
python main.py --filepath "${fixtures}services.yaml"
python main.py --filepath "${fixtures}version-services.yaml"
# python main.py --filepath "${fixtures}isod.yaml"
