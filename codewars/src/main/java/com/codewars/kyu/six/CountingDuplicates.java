package com.codewars.kyu.six;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CountingDuplicates {
    public static int duplicateCount(String text) {
        // Write your code here
        String lowerCase = text.toLowerCase();
        Set<String> temp = new HashSet<>();
        Set<String> collect = Arrays.stream(lowerCase.split(""))
                .filter(c -> !temp.add(c)) // set에 이미 값이 있으면 false를 리턴한다
                .collect(Collectors.toSet());
        return collect.size();
    }


}

class Metro {

    public static int countPassengers(ArrayList<int[]> stops) {

        Integer reduce = stops.stream()
                .map(stop -> stop[0] - stop[1])
                .reduce(0, Integer::sum);

        return reduce;
    }

}