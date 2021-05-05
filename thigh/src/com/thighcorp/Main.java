package com.thighcorp;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String base_dir = "resources" + File.separator + "%s" + File.separator + "%s.%s";
        String thigh_dir = String.format(base_dir, "thigh", "%s", "thigh");
        String ll_dir = String.format(base_dir, "ll", "%s", "ll");

//        String filename = "test-print-variable";
        String filename = "test-assign-print-variable";
//        String filename = "test-input-print-variable";

        Thigh t = new Thigh(String.format(thigh_dir, filename), String.format(ll_dir, filename));
        t.run();
    }
}
