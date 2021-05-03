package com.thighcorp;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {

    public static void main(String[] args) {
	    System.out.println("Welcome to Thigh programming");
        String code = "a = 10; print a;";
        ThighLexer lexer = new ThighLexer(CharStreams.fromString(code));
        ThighParser parser = new ThighParser(new CommonTokenStream(lexer));
        parser.addParseListener(new ThighCustomListener());
        parser.program();
    }
}
