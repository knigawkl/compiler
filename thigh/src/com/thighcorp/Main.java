package com.thighcorp;

import java.io.*;
import java.util.Arrays;

public class Main {
    private static final String base_dir = "resources" + File.separator + "%s" + File.separator + "%s.%s";
    private static final String thigh_dir = String.format(base_dir, "thigh", "%s", "thigh");
    private static final String ll_dir = String.format(base_dir, "ll", "%s", "ll");

    public static void main(String[] args) {
        var test_filenames = Arrays.asList(
                                                      "test-print-variable",
                                                      "test-assign-print-variable",
                                                      "test-input-print-variable",
//                                                      "test-int-double-casting",
                                                      "test-addition",
                "test-subtracting",
                "test-division",
                "test-division-zero"
        );
        test_filenames.forEach(filename -> {
            Thigh t = new Thigh(String.format(thigh_dir, filename), String.format(ll_dir, filename));
            try {
                t.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
