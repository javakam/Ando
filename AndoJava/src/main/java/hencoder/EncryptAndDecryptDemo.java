package hencoder;

import java.io.*;

/**
 * 最简单的加解密
 * <p>
 *     目前测试 txt , png 文件没有问题
 * </p>
 * @author ChangBao
 * @date 2019-10-16 16:02:46
 */
public class EncryptAndDecryptDemo {

    public static void main(String[] args) {
        //encrypt();
        decrypt();
    }

    private static final String FILE_PATH =
            "C:\\fastwork\\Project\\Idea\\AndoJava\\src\\main\\java\\hencoder\\加解密.txt";

    private static void encrypt() {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(FILE_PATH));
            byte[] data = new byte[65536];
            int length = inputStream.read(data);
            inputStream.close();
            for (int i = 0; i < length; i++) {
                data[i] += 1;
            }
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(FILE_PATH));
            outputStream.write(data, 0, length);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void decrypt() {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(FILE_PATH));
            byte[] data = new byte[65536];
            int length = inputStream.read(data);
            inputStream.close();
            for (int i = 0; i < length; i++) {
                data[i] -= 1;
            }
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(FILE_PATH));
            outputStream.write(data, 0, length);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
