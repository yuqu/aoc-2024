package org.yuqu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.yuqu.Day3.Day3;

public class Day3Test {
    @Test
    void test_part1() throws IOException, URISyntaxException {
        var result = Day3.part1("src/test/resources/Day3/input-part1.txt");

        assertEquals(161, result);
    }

    @Test
    void test_part2() throws IOException, URISyntaxException {
        var result = Day3.part2("src/test/resources/Day3/input-part2.txt");

        assertEquals(48, result);
    }
}
