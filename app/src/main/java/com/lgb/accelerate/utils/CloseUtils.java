package com.lgb.accelerate.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by linguobiao on 16/8/20.
 */
public class CloseUtils {
    public CloseUtils() {
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
