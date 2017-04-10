package com.wepiao.admin.user.test.clarification;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.zip.CRC32;

public class TestMySqlHash {

    private static ThreadLocal<CRC32> crc32Provider = new ThreadLocal<CRC32>() {
                                                        @Override
                                                        protected CRC32 initialValue() {
                                                            return new CRC32();
                                                        }
                                                    };

    public static int getHash4split(long id, int splitCount) {
        try {
            long h = getCrc32(String.valueOf(id).getBytes("utf-8"));
            if (h < 0) {
                h = -1 * h;
            }
            int hash = (int) (h / splitCount % splitCount);
            return hash;
        } catch (UnsupportedEncodingException e) {
            return -1;
        }
    }

    public static long getCrc32(byte[] b) {
        CRC32 crc = crc32Provider.get();
        crc.reset();
        crc.update(b);
        return crc.getValue();
    }

    public static void main(String[] args) throws IOException {
        int tableCount = 128;
        int dbcount = 8;
        int splitCount = dbcount * tableCount;
        System.out.println("Please input your key:");
        //    byte[] input = null;
        //    System.in.read(input);
        Scanner scanner = new Scanner(System.in);
        String inputStr = scanner.next();
        scanner.close();
        int hash = TestMySqlHash.getHash4split(TestMySqlHash.getCrc32((inputStr + "").getBytes()), splitCount);
        int dbIndex = hash / tableCount;
        int tblIndex = hash;
        System.out.println("The result of SQL hash:");
        System.out.println("dbIndex:" + dbIndex);
        System.out.println("tableIndex:" + tblIndex);
    }
}
