package com.kapp.algorithm;

import cn.hutool.core.io.IoUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MergePractice {
    private static final String[] PROVINCE_CODES = {"110000", "120000", "650000"}; // 省份代码列表

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/y022/Desktop/id.txt");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        FileWriter writer = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        while (file.length() < 1024L * 1024 * 4 * 1024) {
            bufferedWriter.write(generateRandomID());
            bufferedWriter.newLine();
        }
    }


    public static String generateRandomID() {
        Random random = new Random();

        // 随机选择省份代码
        String provinceCode = PROVINCE_CODES[random.nextInt(PROVINCE_CODES.length)];

        // 随机生成出生日期
        LocalDate birthDate = LocalDate.of(1900 + random.nextInt(120), 1 + random.nextInt(12), 1 + random.nextInt(28));
        String birthDateString = birthDate.format(DateTimeFormatter.BASIC_ISO_DATE).replace("-", "");

        // 随机生成顺序码
        String sequenceCode = String.format("%03d", random.nextInt(1000));

        // 因为校验码的生成涉及到复杂算法（如ISO 7064:1983.MOD 11-2），这里简化处理，仅用X或0-9的一个随机数字代替
        String checkCode = String.valueOf(random.nextInt(10)); // 实际上应使用Luhn校验或其他专用算法

        return provinceCode + birthDateString + sequenceCode + checkCode;
    }


    public static class Merge {
        static long _B = 1;
        static long _KB = _B * 1024;
        static long _MB = _KB * 1024;
        static long perChunkSize = 200 * _MB;

        public static void main(String[] args) {
            System.out.println("current:" + new Date().toString());
            if (args.length != 0) {
                perChunkSize = Integer.parseInt(args[0]) * _MB;
                System.out.println("分块大小通过参数设置为: " + (perChunkSize / _MB) + " MB");
            } else {
                System.out.println("分块大小保持默认为: " + (perChunkSize / _MB) + " MB");
            }

            String oriFileName = "/Users/y022/Desktop/id.txt";
            String outFileName = "/Users/y022/Desktop/out.txt";

            List<String> chunkFileNames = divideIntoSortedChunks(oriFileName, perChunkSize);
            mergeSortedChunks(chunkFileNames, outFileName);
        }

        private static List<String> divideIntoSortedChunks(String oriFileName, long availableMemory) {

            List<String> chunkFileNames = new ArrayList<>();

            List<String> tempChunk = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(oriFileName))) {
                int numberOfChunk = 0;
                long tempChunkMemorySize = 0;

                String line;
                while ((line = reader.readLine()) != null) {

                    long lineSize = line.length() + System.lineSeparator().length();

                    if ((tempChunkMemorySize + lineSize >= availableMemory) && (!tempChunk.isEmpty())) {

                        System.out.println("已经收集到了完整的第 [" + numberOfChunk + "] 块");
                        String chunkFileName = sortAndWriteChunk(numberOfChunk, tempChunk);

                        chunkFileNames.add(chunkFileName);

                        tempChunk.clear();
                        tempChunkMemorySize = 0;
                        numberOfChunk++;
                    }
                    tempChunk.add(line);
                    tempChunkMemorySize += lineSize;
                }

                // 最后一个 chunk 如果有的话
                if (!tempChunk.isEmpty()) {
                    String chunkFileName = sortAndWriteChunk(numberOfChunk, tempChunk);
                    chunkFileNames.add(chunkFileName);
                }
                System.out.println("共计处理 [" + (numberOfChunk + 1) + "] 块成功");

            } catch (Exception ignore) {
            }

            return chunkFileNames;
        }

        private static String sortAndWriteChunk(int numberOfChunk, List<String> tempChunk) {
            System.out.println("对第 [" + numberOfChunk + "] 开始排序");
            Collections.sort(tempChunk);
            System.out.println("对第 [" + numberOfChunk + "] 排序结束");
            String chunkFileName = writeChunkToFile(tempChunk, numberOfChunk);

            System.out.println("处理第 [" + numberOfChunk + "] 块成功");
            return chunkFileName;
        }

        private static String getChunkFileName(int numberOfChunk) {
            return "tempChunk" + numberOfChunk + ".txt";
        }

        private static String writeChunkToFile(List<String> chunk, int numberOfChunk) {
            String chunkFileName = getChunkFileName(numberOfChunk);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(chunkFileName))) {
                for (String line : chunk) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (Exception ignore) {
            }

            return chunkFileName;
        }

        // 将排序后的小块合并成最终的输出文件
        private static void mergeSortedChunks(List<String> chunkFileNames, String outputFile) {
            System.out.println("开始合并排序");
            System.out.println("current:" + new Date().toString());
            PriorityQueue<ChunkReader> chunkReaderQueue = new PriorityQueue<>((o1, o2) -> {
                if (o1.currentLine == null && o2.currentLine == null) {
                    return 0;
                } else if (o1.currentLine == null) {
                    return 1;
                } else if (o2.currentLine == null) {
                    return -1;
                }
                return o1.currentLine.compareTo(o2.currentLine);
            });

            List<ChunkReader> chunkReaders = new ArrayList<>();

            try {
                for (String chunkFileName : chunkFileNames) {
                    ChunkReader chunkReader = ChunkReader.init(new BufferedReader(new FileReader(chunkFileName)));
                    chunkReaders.add(chunkReader);
                    chunkReaderQueue.offer(chunkReader);
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                    while (!chunkReaderQueue.isEmpty()) {

                        ChunkReader chunkReader = chunkReaderQueue.poll();
                        if (chunkReader == null || chunkReader.reader == null) continue;

                        String line = chunkReader.currentLine;

                        if (line != null) {
                            writer.write(line);
                            writer.newLine();
                            chunkReader.currentLine = chunkReader.reader.readLine() ;
                            chunkReaderQueue.offer(chunkReader);
                        } else {
                            chunkReader.reader.close();
                        }
                    }

                }

                for (ChunkReader chunkReader : chunkReaders) {
                    chunkReader.reader.close();
                    chunkReader.reader.close();
                }
            } catch (Exception ignore) {
            }

            // 删除临时文件
            for (String chunkFileName : chunkFileNames) {
                File file = new File(chunkFileName);
                if (!file.exists()) {
                    break;
                }
                file.delete();
            }

            System.out.println("结束合并排序");
            System.out.println("current:" + new Date().toString());
        }


        static class ChunkReader {
            BufferedReader reader;
            String currentLine;

            public static ChunkReader init(BufferedReader reader) {
                ChunkReader res = new ChunkReader();
                res.reader = reader;
                try {
                    res.currentLine = reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return res;
            }
        }
    }
}

