package org.yuqu;

import org.junit.jupiter.api.Test;
import org.yuqu.Day7.Day7;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day7Test {
    @Test
    void test_part1() throws IOException, URISyntaxException {
        var result = Day7.part1("src/test/resources/Day7/input.txt");

        assertEquals(3749, result);
    }
    @Test
    void test_part2() throws IOException, URISyntaxException {
        var result = Day7.part2("src/test/resources/Day7/input.txt");

        assertEquals(11387, result);
    }
}

