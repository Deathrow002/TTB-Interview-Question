package com.ttb;

import com.ttb.sort.BinarySearch;

public class Main {

    public static void main(String[] args){
        // Create an array to hold numbers from 1 to 1,000,000
        int[] numbers = new int[1_000_000];

        int find = 9;

        // Populate the array with numbers from 1 to 1,000,000
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }

        long startTime = System.nanoTime();

        // Perform Binary Search
        int index = new BinarySearch().Sort(numbers, find);

        // Measure the end time
        long endTime = System.nanoTime();

        // Calculate the time taken
        long duration = endTime - startTime;

        if (index != -1) {
            System.out.println("Number " + find + " found at index " + index);
        } else {
            System.out.println("Number " + find + " not found.");
        }

        System.out.println("Time taken for Binary Searching: " + (duration / 1_000_000.0) + " milliseconds");
    }
}