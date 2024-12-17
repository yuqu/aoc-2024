package org.yuqu.Day7;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class Day7 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        var inputFile = "java/app/src/main/resources/Day7/input.txt";

        System.out.println("Part 1: " + part1(inputFile));
    }

    public static long part1(final String filePath) throws IOException, URISyntaxException {
        final var equations = readInput(filePath);

        Operator[] possibleOps = {Operator.ADD, Operator.MULTIPLY};
        Map<Integer, List<List<Operator>>> state = new HashMap<>();
        long total = 0L;
        for (final Equation equation : equations) {
            var permutations = generatePermutationsIterative(possibleOps, equation.numbers.size() - 1, state);
            for (int i = 0; i < permutations.size(); i++) {
                var operators = permutations.get(i);
                long calculatedValue = equation.numbers.getFirst();
                for (int k = 1; k < equation.numbers.size(); k++) {
                    calculatedValue = operators.get(k - 1) == Operator.ADD ?
                            calculatedValue + equation.numbers.get(k) :
                            calculatedValue * equation.numbers.get(k);
                }
                if (calculatedValue == equation.finalValue) {
                    total += equation.finalValue;
                    break;
                }
            }
        }
        return total;
    }
    private static List<List<Operator>> getPermutationsRecursive(Operator[] possibleOps, int n, Map<Integer, List<List<Operator>>> state) {
        if (state.containsKey(n)) {
            return state.get(n);
        }

        List<List<Operator>> result = new ArrayList<>();
        if (n <= 0 || possibleOps == null || possibleOps.length == 0) {
            return result;
        }

        generatePermutationsRecursive(possibleOps, n, new ArrayList<>(), result);

        state.put(n, result);

        return result;
    }

    private static void generatePermutationsRecursive(Operator[] possibleOps, int n, List<Operator> current, List<List<Operator>> result) {
        // Base case: If the current length matches n, add to result and return
        if (current.size() == n) {
            result.add(current);
            return;
        }

        for (Operator c : possibleOps) {
            current.add(c);
            generatePermutationsRecursive(possibleOps, n, new ArrayList<>(current), result);
            current.removeLast();
        }
    }

    private static List<List<Operator>> generatePermutationsIterative(
            final Operator[] possibleOps,
            final int n,
            final Map<Integer, List<List<Operator>>> state
    ) {
        if (state.containsKey(n)) {
            return state.get(n);
        }

        List<List<Operator>> operatorListForSingle = new ArrayList<>();
        for (Operator op : possibleOps) {
            operatorListForSingle.add(List.of(op));
        }
        state.put(1, operatorListForSingle);

        for (int i = 2; i <= n; i++) {
            if (state.containsKey(i)) {
                continue;
            }
            var calculatedOperatorList = state.getOrDefault(i - 1, operatorListForSingle);
            var operatorList = new ArrayList<List<Operator>>();
            for (List<Operator> current : calculatedOperatorList) {
                for (Operator c : possibleOps) {
                    var copy = new ArrayList<>(current);
                    copy.add(c);
                    operatorList.add(List.copyOf(copy));
                    copy.removeLast();
                }
            }

            state.put(i, operatorList);
        }

        return state.get(n);
    }

    private record Equation(
            long finalValue,
            List<Long> numbers
    ) {
    }

    private enum Operator {
        ADD,
        MULTIPLY
    }

    private static List<Equation> readInput(String filePath) throws IOException {
        try (var fileStream = Files.lines(Paths.get(filePath))) {
            final var lines = fileStream.toArray(String[]::new);
            final List<Equation> input = new ArrayList<>();
            final Pattern pattern = Pattern.compile("(\\d+):(( \\d+)+)");
            for (String line : lines) {
                var matcher = pattern.matcher(line);
                if (matcher.find()) {
                    var finalValue = Long.parseLong(matcher.group(1));
                    var numbers = Arrays.stream(matcher.group(2).split(" "))
                            .filter(s -> !s.isBlank())
                            .map(Long::parseLong)
                            .toList();
                    input.add(new Equation(finalValue, numbers));
                }
            }

            return input;
        }
    }
}