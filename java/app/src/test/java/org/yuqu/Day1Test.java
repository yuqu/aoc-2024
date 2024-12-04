package org.yuqu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.yuqu.Day1.Day1;

public class Day1Test {
    @Test
    void test_part1() throws IOException, URISyntaxException {
        var result = Day1.part1("src/test/resources/input-part1.txt");

        assertEquals(11, result);
    }

    @Test
    void test_part2() throws IOException, URISyntaxException {
        var result = Day1.part2("src/test/resources/input-part2.txt");

        assertEquals(31, result);
    }
}
