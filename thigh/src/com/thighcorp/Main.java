package com.thighcorp;

import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {
        HashMap<String, String> test_scenarios = new HashMap<>();

//        test_scenarios.put("resources/test-print-int-real-string.thigh", "resources/test-print-int-real-string.ll");
        test_scenarios.put("resources/test-input-print-variable.thigh", "resources/test-input-print-variable.ll");

        for (var test: test_scenarios.entrySet()) {
            Thigh t = new Thigh(test.getKey(), test.getValue());
            t.run();
        }
    }
}
