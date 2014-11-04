package com.nilhcem.kata.fizzbuzz;

import java.util.ArrayList;
import java.util.List;

public class FizzBuzz {

    private static final String FIZZ = "Fizz";
    private static final String BUZZ = "Buzz";

    public List<String> getNumbersInRange(int from, int to) {
        List<String> result = new ArrayList<>(to - from + 1);

        for (int i = from; i <= to; i++) {
            result.add(getFormattedNumber(i));
        }
        return result;
    }

    public String getFormattedNumber(int number) {
        String result = "";

        if (number % 3 == 0) {
            result += FIZZ;
        }

        if (number % 5 == 0) {
            result += BUZZ;
        }

        if (result.isEmpty()) {
            result = Integer.toString(number);
        }
        return result;
    }
}
