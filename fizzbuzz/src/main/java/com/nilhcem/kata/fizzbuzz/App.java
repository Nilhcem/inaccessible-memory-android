package com.nilhcem.kata.fizzbuzz;

import java.util.List;

public class App {

    public static void main(String[] args) {
        List<String> numbers = new FizzBuzz().getNumbersInRange(1, 100);
        for (String number : numbers) {
            System.out.println(number);
        }
    }
}
