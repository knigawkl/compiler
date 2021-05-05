package com.thighcorp;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.io.PrintStream;

public class Thigh {
    String in_filepath;
    String out_filepath;

    public Thigh(String thigh_filepath, String ir_filepath) {
        in_filepath = thigh_filepath;
        out_filepath = ir_filepath;
    }

    ParseTree getParseTree() throws IOException {
        ThighLexer lexer = new ThighLexer(CharStreams.fromFileName(this.in_filepath));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ThighParser parser = new ThighParser(tokens);
        ParseTree tree = parser.program();
        return tree;
    }

     void parse(String out_filepath, ParseTree tree) {
        /* Parsing to IR LLVM **/
//        System.setOut(new ProxyPrintStream(System.out, out_filepath));
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new ThighCustomListener(out_filepath), tree);
    }

    void getANTLRErrors() throws IOException {
        PrintStream console = System.out;
        System.setOut(console);
        ThighLexer lexer_er = new ThighLexer(CharStreams.fromFileName(this.in_filepath));
        CommonTokenStream tokens_er = new CommonTokenStream(lexer_er);
        ThighParser parser_er = new ThighParser(tokens_er);
        parser_er.program();
    }

    void run() throws IOException {
        var tree = getParseTree();
        parse(out_filepath, tree);
        getANTLRErrors();
    }
}
