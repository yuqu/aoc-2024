package org.yuqu.Day1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Day1 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Result: " + solve("java/app/src/main/resources/input.txt"));
    }

    public static int solve(String filePath) throws IOException, URISyntaxException {
        var input = readInput(filePath);

        var total = 0;

        for (int i = 0; i < input[0].length; i++) {
            var leftSmallestIndex = findSmallestIndexInArray(input[0]);
            var rightSmallestIndex = findSmallestIndexInArray(input[1]);

            total += Math.abs(input[0][leftSmallestIndex].getValue() - input[1][rightSmallestIndex].getValue());

            input[0][leftSmallestIndex].setVisited();
            input[1][rightSmallestIndex].setVisited();
        }

        return total;
    }

    private static int findSmallestIndexInArray(Value[] array) {
        Integer current = null;
        for (int i = 0; i < array.length; i++) {
            if (!array[i].isVisited() && (current == null || array[i].getValue() < array[current].getValue())) {
                current = i;
            }
        }
        return current;
    }

    private static Value[][] readInput(String filePath) throws IOException, URISyntaxException {
        try (var fileStream = Files.lines(Paths.get(filePath))) {
            var lines = fileStream.toArray(String[]::new);
            Value[][] input = new Value[2][lines.length];
            for (int i = 0; i < lines.length; i++) {
                var split = lines[i].split("   ");
                input[0][i] = new Value(Integer.parseInt(split[0]));
                input[1][i] = new Value(Integer.parseInt(split[1]));
            }

            return input;
        }
    }

    private static class Value {
        int value;
        boolean visited = false;

        public Value(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited() {
            this.visited = true;
        }
    }
}