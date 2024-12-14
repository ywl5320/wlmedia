package com.ywl5320.wlmedia.example;

import com.ywl5320.wlmedia.log.WlLog;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/9/23
 */
public class BufferByteStream {

    private String path;
    private RandomAccessFile randomAccessFile;

    public BufferByteStream(String path) {
        this.path = path;
    }

    long getLength() {
        if (randomAccessFile == null) {
            try {
                randomAccessFile = new RandomAccessFile(new File(path), "r");
            } catch (Exception e) {
                WlLog.d(e.getMessage());
            }
        }
        try {
            if (randomAccessFile != null) {
                randomAccessFile.seek(0);
                return randomAccessFile.length();
            }
        } catch (IOException e) {
        }
        return 0;
    }

    byte[] read(int size, long seekPosition) {
        byte[] buffer = null;
        if (randomAccessFile != null) {
            buffer = new byte[size];
            try {
                if (seekPosition >= 0) {
                    randomAccessFile.seek(seekPosition);
                }
                int length = randomAccessFile.read(buffer);
                if (length > 0) {
                    if (length == size) {
                        return buffer;
                    }
                    byte[] b = new byte[length];
                    System.arraycopy(buffer, 0, b, 0, length);
                    return b;
                }
                return new byte[0];
            } catch (IOException e) {

            }
        }
        return null;
    }

}
