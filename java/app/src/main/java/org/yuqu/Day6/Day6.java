package org.yuqu.Day6;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day6 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        var inputFile = "java/app/src/main/resources/Day6/input.txt";

        System.out.println("Part 1: " + part1(inputFile));
        System.out.println("Part 2: " + part2(inputFile));
    }

    public static int part1(final String filePath) throws IOException, URISyntaxException {
        final var grid = readInput(filePath);

        int count = 0;

        final var guardLocation = findGuard(grid);

        if (guardLocation.isPresent()) {
            count = moveGuard(grid, guardLocation.get(), null);
        }

        return count;
    }

    public static int part2(final String filePath) throws IOException, URISyntaxException {
        final var grid = readInput(filePath);

        final Set<Coordinates> possibleLoopCoors = new HashSet<>();
        final var guardLocation = findGuard(grid);

        guardLocation.ifPresent(coordinates ->
                moveGuard(grid, coordinates, possibleLoopCoors));

        return possibleLoopCoors.size();
    }

    private record Grid(char[][] grid) {
        Character getLocation(Coordinates coors) {
            return getLocation(coors.i, coors.j);
        }

        Character getLocation(int i, int j) {
            return grid[i][j];
        }

        int getDimensionLength() {
            return grid.length;
        }
    }

    private enum Direction {
        UP(0, -1, 0),
        RIGHT(1, 0, 1),
        DOWN(2, 1, 0),
        LEFT(3, 0, -1);

        private final int value;
        private final int vertical;
        private final int horizontal;

        Direction(int value, int vertical, int horizontal) {
            this.value = value;
            this.vertical = vertical;
            this.horizontal = horizontal;
        }

        public Direction nextDirection() {
            return Arrays.stream(Direction.values())
                    .filter(d -> d.value == (this.value + 1) % 4)
                    .findFirst()
                    .get();
        }
    }

    private record Coordinates(
            int i,
            int j
    ) {
        Coordinates move(Direction direction) {
            return new Coordinates(i + direction.vertical, j + direction.horizontal);
        }
    }

    private static int moveGuard(
            final Grid grid,
            final Coordinates guardLocation,
            final Set<Coordinates> possibleLoopCoors
    ) {
        Direction direction = Direction.UP;
        Coordinates current = guardLocation;
        final Set<Coordinates> visited = new HashSet<>();

        int count = 1;
        while (current.i > 0 &&
                current.j > 0 &&
                current.i < grid.getDimensionLength() - 1 &&
                current.j < grid.getDimensionLength() - 1) {
            final Coordinates next = current.move(direction);
            if (grid.getLocation(next) == '#') {
                direction = direction.nextDirection();
            } else {
                if (visited.add(current)) {
                    count++;
                }
                if (possibleLoopCoors != null && checkLoop(
                        putObstruction(grid, next),
                        guardLocation)
                ) {
                    possibleLoopCoors.add(next);
                }
                current = next;
            }
        }

        return count;
    }

    private static Grid putObstruction(Grid originalGrid, Coordinates coordinates) {
        var array2d = Arrays.stream(originalGrid.grid())
                .map(char[]::clone)
                .toArray(char[][]::new);
        array2d[coordinates.i][coordinates.j] = '#';
        return new Grid(array2d);
    }

    private static boolean checkLoop(
            final Grid grid,
            final Coordinates startLocation
    ) {
        Direction direction = Direction.UP;
        Coordinates current = startLocation;

        record Visit(
                Coordinates coordinates,
                Direction direction
        ) { };

        Set<Visit> visits = new HashSet<>();

        while (current.i > 0 &&
                current.j > 0 &&
                current.i < grid.getDimensionLength() - 1 &&
                current.j < grid.getDimensionLength() - 1) {
            final Coordinates next = current.move(direction);
            if (grid.getLocation(next) == '#') {
                direction = direction.nextDirection();
            } else {
                Visit visit = new Visit(current, direction);
                if (!visits.add(visit)) {
                    return true;
                }
                current = next;
            }
        }

        return false;
    }

    private static Optional<Coordinates> findGuard(Grid grid) {
        for (int i = 0; i < grid.getDimensionLength(); i++) {
            for (int j = 0; j < grid.getDimensionLength(); j++) {
                if ('^' == grid.getLocation(new Coordinates(i, j))) {
                    return Optional.of(new Coordinates(i, j));
                }
            }
        }
        return Optional.empty();
    }

    private static Grid readInput(String filePath) throws IOException {
        try (var fileStream = Files.lines(Paths.get(filePath))) {
            final var lines = fileStream.toArray(String[]::new);
            final char[][] input = new char[lines.length][lines.length];
            for (int i = 0; i < lines.length; i++) {
                var lineArray = lines[i].toCharArray();
                System.arraycopy(lineArray, 0, input[i], 0, lineArray.length);
            }

            return new Grid(input);
        }
    }
}