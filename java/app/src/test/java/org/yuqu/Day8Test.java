package org.yuqu;

import org.junit.jupiter.api.Test;
import org.yuqu.Day8.Day8;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day8Test {
    @Test
    void test_part1() throws IOException, URISyntaxException {
        var result = Day8.part1("src/test/resources/Day8/input.txt");

        assertEquals(14, result);
    }

    @Test
    void test_part2() throws IOException, URISyntaxException {
        var result = Day8.part2("src/test/resources/Day8/input.txt");

        assertEquals(34, result);
    }
}

