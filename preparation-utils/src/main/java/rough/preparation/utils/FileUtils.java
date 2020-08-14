package rough.preparation.utils;

import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;

import java.io.*;

@CommonsLog
public final class FileUtils {
    /**
     * 可读写文件最大尺寸，默认为 64M Bype。
     */
    public static final int DEFAULT_MAX_SIZE_OF_FILE = 1024 * 1024 * 64;

    private static int maxSizeOfFile = DEFAULT_MAX_SIZE_OF_FILE;

    public static int getMaxSizeOfFile() {
        return maxSizeOfFile;
    }

    public static void setMaxSizeOfFile(int maxSizeOfFile) {
        FileUtils.maxSizeOfFile = maxSizeOfFile;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static byte[] read(@NotNull File file) throws IOException {
        try (FileInputStream in = new FileInputStream(file)) {
            int size = in.available();
            if (size > getMaxSizeOfFile()) {
                log.error("Too big file: " + size);
                throw new IOException("Too big file: " + size);
            }
            byte[] result = new byte[size];
            in.read(result);
            return result;
        }
    }

    public static byte[] read(@NotNull String fileName) throws IOException {
        return read(new File(fileName));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File write(@NotNull byte[] data, @NotNull File file) throws IOException {
        if (data.length > getMaxSizeOfFile()) {
            log.error("Data length too long: " + data.length);
            throw new IOException("Data length too long: " + data.length);
        }

        if (file.exists()) {
            file.delete();
        }
        try (OutputStream out = new FileOutputStream(file)) {
            out.write(data);
        }
        return file;
    }

    public static File write(@NotNull byte[] data, @NotNull String fileName) throws IOException {
        return write(data, new File(fileName));
    }
}
