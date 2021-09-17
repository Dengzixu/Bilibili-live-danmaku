package net.dengzixu.utils;

import org.apache.commons.compress.compressors.brotli.BrotliCompressorInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

public class UncompressUtils {
    public static final String ZLIB = "Zlib";
    public static final String BROTLI = "Brotli";

    public static byte[] uncompress(byte[] compressedData, String Compressor) {
        if (null == compressedData) {
            return null;
        }

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedData);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        InputStream inputStream;

        try {
            switch (Compressor) {
                case ZLIB:
                    inputStream = new InflaterInputStream(byteArrayInputStream);
                    break;
                case BROTLI:
                    inputStream = new BrotliCompressorInputStream(byteArrayInputStream);
                    break;
                default:
                    return null;
            }

            byte[] buffer = new byte[1024];

            int n;
            while ((n = inputStream.read(buffer)) >= 0) {
                byteArrayOutputStream.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }
}
