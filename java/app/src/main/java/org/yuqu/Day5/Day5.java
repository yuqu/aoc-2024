package org.yuqu.Day5;

import com.google.common.collect.Sets;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day5 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        var inputFile = "java/app/src/main/resources/Day5/input.txt";

        System.out.println("Part 1: " + part1(inputFile));
        System.out.println("Part 2: " + part2(inputFile));
    }

    public static int part1(final String filePath) throws IOException, URISyntaxException {
        final var input = readInput(filePath);

        int total = 0;
        for (Update u : input.updates()) {
            if (isSafe(u.pages(), input.rules())) {
                total += getMiddle(u.pages());
            }
        }
        return total;
    }

    public static int part2(final String filePath) throws IOException, URISyntaxException {
        final var input = readInput(filePath);

        int total = 0;
        final var rules = input.rules();

        for (Update u : input.updates()) {
            final var pages = u.pages();

            if (!isSafe(pages, rules)) {
                var sortedPage = new ArrayList<>(pages);
                sortedPage.sort(sortByRules(rules));

                total += getMiddle(sortedPage);
            }
        }
        return total;
    }

    private static Comparator<Integer> sortByRules(Map<Integer, Set<Integer>> rules) {
        return (o1, o2) -> {
            if (o1.equals(o2)) {
                return 0;
            }

            var shouldNotPrecede = rules.getOrDefault(o2, Collections.emptySet());
            if (shouldNotPrecede.contains(o1)) {
                return 1;
            }
            return -1;
        };
    }

    private static boolean isSafe(List<Integer> pages, Map<Integer, Set<Integer>> rules) {
        for (int i = 0; i < pages.size(); i++) {
            Integer current = pages.get(i);
            var currentPageRule = rules.getOrDefault(current, Collections.emptySet());
            Set<Integer> pagesUntilCurrent = new HashSet<>(pages.subList(0, i));
            if (!Sets.intersection(pagesUntilCurrent, currentPageRule).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private record Update(List<Integer> pages) {
    }

    private record Input(Map<Integer, Set<Integer>> rules, List<Update> updates) {
    }

    private static Integer getMiddle(final List<Integer> page) {
        return page.get(page.size() / 2 - (page.size() % 2) + 1);
    }

    private static Input readInput(String filePath) throws IOException, URISyntaxException {
        try (var fileStream = Files.lines(Paths.get(filePath))) {
            final var lines = fileStream.toArray(String[]::new);
            final var rules = new HashMap<Integer, Set<Integer>>();
            final var updates = new ArrayList<Update>();
            var readRules = true;
            for (final String line : lines) {
                if (line.isEmpty()) {
                    readRules = false;
                } else if (readRules) {
                    final int[] rulePages = Arrays.stream(line.split("\\|"))
                            .mapToInt(Integer::parseInt).toArray();
                    var rule = rules.getOrDefault(rulePages[0], new HashSet<>());
                    rule.add(rulePages[1]);
                    rules.put(rulePages[0], rule);
                } else {
                    updates.add(new Update(Arrays.stream(line.split(","))
                            .map(Integer::parseInt).toList()));
                }
            }

            return new Input(rules, updates);
        }
    }
}