package org.yuqu.Day1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Day1 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Result: " + solve("java/app/src/main/resources/input.txt"));
    }

    public static int solve(String filePath) throws IOException, URISyntaxException {
        var input = readInput(filePath);

        Arrays.sort(input[0]);
        Arrays.sort(input[1]);

        var total = 0;
        for (int i = 0; i < input[0].length; i++) {
            total += Math.abs(input[0][i] - input[1][i]);
        }

        return total;
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