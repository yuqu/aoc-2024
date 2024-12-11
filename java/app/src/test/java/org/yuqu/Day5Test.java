package org.yuqu;

import org.junit.jupiter.api.Test;
import org.yuqu.Day5.Day5;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day5Test {
    @Test
    void test_part1() throws IOException, URISyntaxException {
        var result = Day5.part1("src/test/resources/Day5/input.txt");

        assertEquals(143, result);
    }
    @Test
    void test_part2() throws IOException, URISyntaxException {
        var result = Day5.part2("src/test/resources/Day5/input.txt");

        assertEquals(123, result);
    }
}

