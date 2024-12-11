package org.yuqu;

import org.junit.jupiter.api.Test;
import org.yuqu.Day4.Day4;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day4Test {
    @Test
    void test_part1() throws IOException, URISyntaxException {
        var result = Day4.part1("src/test/resources/Day4/input.txt");

        assertEquals(18, result);
    }
}

