package org.yuqu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.yuqu.Day1.Day1;

public class Day1Test {
    @Test
    void test() throws IOException, URISyntaxException {
        var result = Day1.solve("src/test/resources/input.txt");

        assertEquals(11, result);
    }
}
