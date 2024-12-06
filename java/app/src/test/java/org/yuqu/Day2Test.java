package org.yuqu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.yuqu.Day2.Day2;

public class Day2Test {
    @Test
    void test_part1() throws IOException, URISyntaxException {
        var result = Day2.part1("src/test/resources/Day2/input.txt");

        assertEquals(2, result);
    }

    @Test
    void test_part2() throws IOException, URISyntaxException {
        var result = Day2.part2("src/test/resources/Day2/input.txt");

        assertEquals(4, result);
    }

}
