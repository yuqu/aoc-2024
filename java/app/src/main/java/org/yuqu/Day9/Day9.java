package org.yuqu.Day9;

import org.checkerframework.common.value.qual.IntRange;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day9 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        var inputFile = "java/app/src/main/resources/Day9/input.txt";

//        System.out.println("Part 1: " + part1(inputFile));
        System.out.println("Part 2: " + part2(inputFile));
    }

    public static long part1(final String filePath) throws IOException, URISyntaxException {
        final var input = readInput(filePath);

        var diskBlock = expand(input);
//        System.out.println(Arrays.toString(diskBlock));
        var compactDiskBlock = compact(diskBlock);
//        System.out.println(Arrays.toString(compactDiskBlock));

        return checksum(compactDiskBlock);
    }

    public static long part2(final String filePath) throws IOException, URISyntaxException {
        final var input = readInput(filePath);

        var diskBlock = expand(input);
//        System.out.println(Arrays.toString(diskBlock));
        var compactDiskBlock = compact2(diskBlock);
//        System.out.println(Arrays.toString(compactDiskBlock));

        return checksum(compactDiskBlock);
    }

    private static int[] expand(char[] diskMap) {
        var isFile = true;
        var id = 0;
        var list = new ArrayList<Integer>();
        for (char value : diskMap) {
            for (int j = 0; j < Integer.parseInt(String.valueOf(value)); j++) {
                if (isFile) {
                    list.add(id);
                } else {
                    list.add(-1);
                }
            }
            if (isFile) {
                id++;
            }
            isFile = !isFile;
        }

        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    private static int[] compact(int[] diskBlock) {
        var rightIndex = diskBlock.length - 1;
        for (int i = 0; i < diskBlock.length && i < rightIndex; i++) {
            var value = diskBlock[i];
            if (value < 0) {
                diskBlock[i] = diskBlock[rightIndex];
                diskBlock[rightIndex] = -1;
                while (diskBlock[rightIndex] < 0) {
                    rightIndex--;
                }
            }
//            System.out.println(Arrays.toString(diskBlock));
        }
        return diskBlock;
    }

    private static int[] compact2(int[] diskBlock) {
        int i = diskBlock.length - 1;
        var markArray = new int[diskBlock.length];
        Arrays.fill(markArray, -1);
        while (i > 0) {
            var currentId = diskBlock[i];
            var sourceIndex = i;
            var sourceLength = 0;
            if (markArray[i] != -1) {
                i--;
                continue;
            }
            while (i > 0 && diskBlock[i] == currentId) {
                i--;
                sourceLength++;
            }
            if (currentId == -1) {
                continue;
            }

            var moved = moveIfPossible(diskBlock, markArray, i, sourceIndex, sourceLength, currentId);
            if (moved) {
                System.out.println(String.format("Moved: currentId %s", currentId));
            }
        }
        return diskBlock;
    }

    private static boolean moveIfPossible(int[] diskBlock, int[] markArray, int searchUntilIndex, int sourceIndex, int sourceLength, int sourceValue) {
        var leftIndex = 0;
        var moved = false;
        while (leftIndex < searchUntilIndex && !moved) {
            while(leftIndex < searchUntilIndex && diskBlock[leftIndex] != -1) {
                leftIndex++;
            }
            var freeSpaceStart = leftIndex;
            while(leftIndex < searchUntilIndex && diskBlock[leftIndex] == -1) {
                leftIndex++;
            }

            var freeBlockSize = leftIndex - freeSpaceStart;

            if (freeBlockSize >= sourceLength) {
                int[] source = new int[sourceLength];
                Arrays.fill(source, sourceValue);
                System.arraycopy(source, 0, diskBlock, freeSpaceStart, sourceLength);
//                System.arraycopy(source, 0, markArray, freeSpaceStart, sourceLength);

                moved = true;

                for (int j = 0; j < sourceIndex - searchUntilIndex; j++) {
                    diskBlock[sourceIndex - j] = -1;
                }
            }
        }
        return moved;
    }

    private static int[] compact3(int[] diskBlock) {
        var rightIndex = diskBlock.length - 1;
        var freeBlockSpace = 0;
        for (int i = 0; i < diskBlock.length && i < rightIndex; i++) {
            var value = diskBlock[i];
            if (value < 0) {
                freeBlockSpace++;
            } else if (freeBlockSpace > 0) {
                var filledFreeBlockSize = 0;
                while (filledFreeBlockSize < freeBlockSpace) {
                    var r = rightIndex;
                    var currentId = diskBlock[r];
                    var block = new ArrayList<Integer>();
                    while (r > 0 && diskBlock[r] == currentId) {
                        block.add(diskBlock[r]);
                        r--;
                    }
                    if (currentId > 0 && block.size() <= freeBlockSpace - filledFreeBlockSize) {
                        for (int a = 0; a < block.size(); a++) {
                            diskBlock[i - freeBlockSpace + filledFreeBlockSize] = diskBlock[rightIndex];
                            diskBlock[rightIndex] = -1;
                            rightIndex--;
                            filledFreeBlockSize++;
                        }
                    }
                    rightIndex = r;
                }

                freeBlockSpace = 0;
                rightIndex = diskBlock.length - 1;
            }
//            System.out.println(Arrays.toString(diskBlock));
        }
        return diskBlock;
    }

    private static long checksum(int[] compactDiskBlock) {
        long total = 0;
        for (int i = 0; i < compactDiskBlock.length; i++) {
            if (compactDiskBlock[i] >= 0) {
                total += (long) compactDiskBlock[i] * i;
            }
        }
        return total;
    }

    private static char[] readInput(String filePath) throws IOException {
        try (var fileStream = Files.lines(Paths.get(filePath))) {
            final var lines = fileStream.toArray(String[]::new);
            var lineArray = lines[0].toCharArray();
            final char[] input = new char[lineArray.length];
            System.arraycopy(lineArray, 0, input, 0, lineArray.length);

            return input;
        }
    }
}