package org.yuqu;

import org.junit.jupiter.api.Test;
import org.yuqu.Day9.Day9;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day9Test {
    @Test
    void test_part1() throws IOException, URISyntaxException {
        var result = Day9.part1("src/test/resources/Day9/input.txt");

        assertEquals(1928, result);
    }

    @Test
    void test_part2() throws IOException, URISyntaxException {
        var result = Day9.part2("src/test/resources/Day9/input.txt");

        assertEquals(2858, result);
    }
}

