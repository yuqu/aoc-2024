package org.yuqu.Day4;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day4 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        var inputFile = "java/app/src/main/resources/Day4/input.txt";

        System.out.println("Count: " + part1(inputFile));

    }

    public static int part1(final String filePath) throws IOException, URISyntaxException {
        final var input = readInput(filePath);

        int count = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                count += search(input, i, j, Xmas.X, Direction.UP);
                count += search(input, i, j, Xmas.X, Direction.DOWN);
                count += search(input, i, j, Xmas.X, Direction.LEFT);
                count += search(input, i, j, Xmas.X, Direction.RIGHT);
                count += search(input, i, j, Xmas.X, Direction.UP_LEFT);
                count += search(input, i, j, Xmas.X, Direction.UP_RIGHT);
                count += search(input, i, j, Xmas.X, Direction.DOWN_LEFT);
                count += search(input, i, j, Xmas.X, Direction.DOWN_RIGHT);
            }
        }

        return count;
    }

    private enum Xmas {
        X('X', 'M'),
        M('M', 'A'),
        A('A', 'S'),
        S('S', null);

        private final Character current;
        private final Character next;

        Xmas(Character current, Character next) {
            this.current = current;
            this.next = next;
        }
    }

    private enum Direction {
        UP(-1, 0),
        DOWN(1, 0),
        LEFT(0, -1),
        RIGHT(0, 1),
        UP_RIGHT(-1, 1),
        UP_LEFT(-1, -1),
        DOWN_LEFT(1, -1),
        DOWN_RIGHT(1, 1);

        private final int vertical;
        private final int horizontal;

        Direction(int vertical, int horizontal) {
            this.vertical = vertical;
            this.horizontal = horizontal;
        }
    }


    private static int search(List<List<Character>> input, int i, int j, Xmas lookFor, Direction direction) {
        int count = 0;

        if (i < input.size() && j < input.getFirst().size() && i >= 0 && j >= 0) {
            final var current = input.get(i).get(j);
            if (current.equals(lookFor.current)) {
                if (lookFor.next != null) {
                    count += search(
                            input,
                            i + direction.vertical,
                            j + direction.horizontal,
                            Xmas.valueOf(lookFor.next.toString()),
                            direction);
                } else {
                    return 1;
                }
            }
        }

        return count;
    }

    private static List<List<Character>> readInput(String filePath) throws IOException, URISyntaxException {
        try (var fileStream = Files.lines(Paths.get(filePath))) {
            final var lines = fileStream.toArray(String[]::new);
            final List<List<Character>> input = new ArrayList<>();
            for (final String line : lines) {
                final List<Character> row = new ArrayList<>();
                for (final char a : line.toCharArray()) {
                    row.add(a);
                }
                input.add(row);
            }

            return input;
        }
    }
}