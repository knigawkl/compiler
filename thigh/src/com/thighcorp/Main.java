package com.thighcorp;

import java.io.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        String base_dir = "resources" + File.separator + "%s" + File.separator + "%s.%s";
        String thigh_dir = String.format(base_dir, "thigh", "%s", "thigh");
        String ll_dir = String.format(base_dir, "ll", "%s", "ll");

        var test_filenames = Arrays.asList("test-print-variable",
                                                      "test-assign-print-variable",
                                                      "test-input-print-variable");

        test_filenames.forEach(t -> {
            System.setOut(new ProxyPrintStream(System.out, String.format(ll_dir, t)));
            Thigh tai = new Thigh(String.format(thigh_dir, t), String.format(ll_dir, t));
            try {
                tai.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        });
    }
}
