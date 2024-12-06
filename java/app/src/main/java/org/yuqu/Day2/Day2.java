package org.yuqu.Day2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Day2 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        var inputFile = "java/app/src/main/resources/Day2/input.txt";

        System.out.println("Number of safe reports: " + part1(inputFile));
        System.out.println("Number of safe reports with Problem Dampener: " + part2(inputFile));
    }

    public static long part1(final String filePath) throws IOException, URISyntaxException {
        final var input = readInput(filePath);
        return input.stream()
                .filter(Day2::isSafe)
                .count();
    }

    public static long part2(final String filePath) throws IOException, URISyntaxException {
        final var input = readInput(filePath);
        return input.stream()
                .filter(Day2::isSafeWithProblemDampener)
                .count();
    }

    private enum DIRECTION {
        ASCENDING,
        DESCENDING,
        UNKNOWN;

        public static DIRECTION fromValue(int value) {
            if (value > 0) {
                return ASCENDING;
            } else if (value < 0) {
                return DESCENDING;
            }

            return UNKNOWN;
        }
    }

    private static boolean isSafeWithProblemDampener(final List<Integer> list) {
        return isSafe(list, true) || isSafe(list.reversed(), true);
    }

    private static boolean isSafe(final List<Integer> list) {
        return isSafe(list, false);
    }

    private static boolean isSafe(final List<Integer> list, boolean useProblemDampener) {
        Optional<DIRECTION> latestDirection = Optional.empty();
        var errorState = false;
        var errorCount = 0;

        for (int i = 1; i < list.size(); i++) {
            final var diff = list.get(i) - list.get(i - 1 - (errorState ? 1 : 0));
            final var currentDirection = DIRECTION.fromValue(diff);

            if (currentDirection == DIRECTION.UNKNOWN ||
                    (latestDirection.isPresent() && currentDirection != latestDirection.get()) ||
                    Math.abs(diff) > 3) {

                if (!useProblemDampener) {
                    return false;
                }

                errorCount++;
                if (errorCount > 1) {
                    return false;
                }

                if (!errorState) {
                    errorState = true;
                    continue;
                } else {
                    return false;
                }
            }

            if (currentDirection != DIRECTION.UNKNOWN) {
                latestDirection = Optional.of(currentDirection);
            }
            errorState = false;
        }

        return true;
    }

    private static List<List<Integer>> readInput(String filePath) throws IOException, URISyntaxException {
        try (var fileStream = Files.lines(Paths.get(filePath))) {
            final var lines = fileStream.toArray(String[]::new);
            final List<List<Integer>> input = new ArrayList<>();
            for (int i = 0; i < lines.length; i++) {
                var split = lines[i].split(" ");
                if (split.length < 2) {
                    continue;
                }
                input.add(
                        Arrays.stream(split)
                                .map(Integer::parseInt)
                                .toList());
            }

            return input;
        }
    }
}