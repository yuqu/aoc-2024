package org.yuqu.Day8;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day8 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        var inputFile = "java/app/src/main/resources/Day8/input.txt";

        System.out.println("Part 1: " + part1(inputFile));
        System.out.println("Part 2: " + part2(inputFile));
    }

    public static long part1(final String filePath) throws IOException, URISyntaxException {
        final var input = readInput(filePath);
        var map = mapCharToCoordinatesList(input);

//        char[][] grid = new char[input.length][input.length];
//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid.length; j++) {
//                grid[i][j] = '.';
//            }
//        }

        Set<Coordinates> antinodes = new HashSet<>();

        for (var entry : map.entrySet()) {
            if (entry.getKey().equals('.')) {
                continue;
            }
            final var combinations = getCombinations(entry.getValue());

            for (Set<Coordinates> combination : combinations) {
                antinodes.addAll(findAntinodes(combination, input.length, true));
            }
        }

//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid.length; j++) {
//                System.out.print(grid[i][j]);
//            }
//            System.out.print('\n');
//        }
        return antinodes.size();
    }

    public static long part2(final String filePath) throws IOException, URISyntaxException {
        final var input = readInput(filePath);
        var map = mapCharToCoordinatesList(input);

        Set<Coordinates> antinodes = new HashSet<>();

        for (var entry : map.entrySet()) {
            if (entry.getKey().equals('.')) {
                continue;
            }
            final var combinations = getCombinations(entry.getValue());

            for (Set<Coordinates> combination : combinations) {
                antinodes.addAll(findAntinodes(combination, input.length, false));
            }
        }
        char[][] grid = new char[input.length][input.length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (antinodes.contains(new Coordinates(i, j))) {
                    grid[i][j] = '#';
                } else {
                    grid[i][j] = '.';

                }
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.print('\n');
        }
        return antinodes.size();
    }

    private static Set<Coordinates> findAntinodes(Set<Coordinates> coorSet, int gridLength, boolean onlyFirst) {
        var pairList = new ArrayList<>(coorSet);
        var first = pairList.get(0);
        var second = pairList.get(1);
        var horizontalDiff = first.x - second.x;
        var verticalDiff = first.y - second.y;

        final Set<Coordinates> antinodes = new HashSet<>(coorSet);
        antinodes.addAll(getAntinodes(gridLength, onlyFirst, first, horizontalDiff, verticalDiff, true));
        antinodes.addAll(getAntinodes(gridLength, onlyFirst, second, horizontalDiff, verticalDiff, false));

        return antinodes;
    }

    private static Set<Coordinates> getAntinodes(int gridLength, boolean onlyFirst, Coordinates source, int horizontalDiff, int verticalDiff, boolean positive) {
        final Set<Coordinates> antinodes = new HashSet<>();

        var firstAntinodeX = source.x + (positive ? 1 : -1) * horizontalDiff;
        var firstAntinodeY = source.y + (positive ? 1 : -1) * verticalDiff;
        while (firstAntinodeX >= 0 && firstAntinodeX < gridLength
                && firstAntinodeY >= 0 && firstAntinodeY < gridLength) {
//            System.out.println(String.format("(%s,%s) -- %s", firstAntinodeX, firstAntinodeY, entry.getKey()));
//            grid[firstAntinodeX][firstAntinodeY] = '#';
            antinodes.add(new Coordinates(firstAntinodeX, firstAntinodeY));
            if (onlyFirst) {
                break;
            }
            firstAntinodeX += (positive ? 1 : -1) * horizontalDiff;
            firstAntinodeY += (positive ? 1 : -1) * verticalDiff;
        }

        return antinodes;
    }

    private static Set<Set<Coordinates>> getCombinations(List<Coordinates> coordinates) {
        final Set<Set<Coordinates>> combinations = new HashSet<>();
        for (Coordinates coordinate : coordinates) {
            for (Coordinates other : coordinates) {
                if (!coordinate.equals(other)) {
                    final var pair = Set.of(coordinate, other);
                    combinations.add(pair);
                }
            }
        }
        return combinations;
    }

    private record Coordinates(
            int x,
            int y
    ) {
    }

    private static Map<Character, List<Coordinates>> mapCharToCoordinatesList(char[][] input) {
        final var map = new HashMap<Character, List<Coordinates>>();
        for (var i = 0; i < input.length; i++) {
            for (var j = 0; j < input[i].length; j++) {
                char location = input[i][j];
                var coorList = map.getOrDefault(location, new ArrayList<>());
                coorList.add(new Coordinates(i, j));
                map.put(location, coorList);
            }
        }
        return map;
    }

    private static char[][] readInput(String filePath) throws IOException {
        try (var fileStream = Files.lines(Paths.get(filePath))) {
            final var lines = fileStream.toArray(String[]::new);
            final char[][] input = new char[lines.length][lines.length];
            for (int i = 0; i < lines.length; i++) {
                var lineArray = lines[i].toCharArray();
                System.arraycopy(lineArray, 0, input[i], 0, lineArray.length);
            }

            return input;
        }
    }
}