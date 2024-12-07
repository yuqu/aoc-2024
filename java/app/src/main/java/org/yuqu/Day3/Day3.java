package org.yuqu.Day3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class Day3 {

    private static Pattern REGEX_PART1 = Pattern.compile("mul\\((\\d+),(\\d+)\\)", Pattern.MULTILINE);
    private static Pattern REGEX_PART2 = Pattern.compile("mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)", Pattern.MULTILINE);
    public static void main(String[] args) throws IOException, URISyntaxException {
        var inputFile = "java/app/src/main/resources/Day3/input.txt";

        System.out.println("Result part1: " + part1(inputFile));
        System.out.println("Result part2: " + part2(inputFile));
    }

    public static long part1(final String filePath) throws IOException, URISyntaxException {
        final var input = readInput(filePath);

        var matcher = Day3.REGEX_PART1.matcher(input);
        var result = 0;
        while(matcher.find()) {
            result += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
        }
        return result;
    }

    public static long part2(final String filePath) throws IOException, URISyntaxException {
        final var input = readInput(filePath);

        var matcher = Day3.REGEX_PART2.matcher(input);
        var result = 0;
        var enabled = true;
        while(matcher.find()) {
            switch (matcher.group(0)) {
                case "do()":
                    enabled = true;
                    break;
                case "don't()":
                    enabled = false;
                    break;
                default:
                    if (enabled) {
                        result += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
                    }
                    break;
            }
        }
        return result;
    }

    private static String readInput(String filePath) throws IOException, URISyntaxException {
        return Files.readString(Paths.get(filePath));
    }
}