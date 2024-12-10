package org.yuqu.Day1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

public class Day1 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Total distance: " + part1("java/app/src/main/resources/Day1/input-part1.txt"));
        System.out.println("Total similarity score: " + part2("java/app/src/main/resources/Day1/input-part2.txt"));
    }

    public static int part1(final String filePath) throws IOException, URISyntaxException {
        final var input = readInput(filePath);

        Arrays.sort(input[0]);
        Arrays.sort(input[1]);

        var total = 0;
        for (int i = 0; i < input[0].length; i++) {
            total += Math.abs(input[0][i] - input[1][i]);
        }

        return total;
    }

    public static int part2(final String filePath) throws IOException, URISyntaxException {
        final var input = readInput(filePath);

        final var countMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < input[1].length; i++) {
            var value = input[1][i];
            if (countMap.containsKey(value)) {
                countMap.put(value, countMap.get(value) + 1);
            } else {
                countMap.put(value, 1);
            }
        }

        var score = 0;
        for (int i = 0; i < input[0].length; i++) {
            var value = input[0][i];
            score += value * (countMap.containsKey(value) ? countMap.get(value) : 0);
        }

        return score;
    }

    private static int[][] readInput(String filePath) throws IOException, URISyntaxException {
        try (var fileStream = Files.lines(Paths.get(filePath))) {
            var lines = fileStream.toArray(String[]::new);
            int[][] input = new int[2][lines.length];
            for (int i = 0; i < lines.length; i++) {
                var split = lines[i].split("   ");
                input[0][i] = Integer.parseInt(split[0]);
                input[1][i] = Integer.parseInt(split[1]);
            }

            return input;
        }
    }
}