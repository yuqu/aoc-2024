package org.yuqu;

import org.junit.jupiter.api.Test;
import org.yuqu.Day6.Day6;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day6Test {
    @Test
    void test_part1() throws IOException, URISyntaxException {
        var result = Day6.part1("src/test/resources/Day6/input.txt");

        assertEquals(41, result);
    }
    @Test
    void test_part2() throws IOException, URISyntaxException {
        var result = Day6.part2("src/test/resources/Day6/input.txt");

        assertEquals(6, result);
    }
}

