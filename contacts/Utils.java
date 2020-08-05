package contacts;

import java.util.Arrays;

public class Utils {
    public static String[] concateStringArrays(String[] dst, String[] src) {
        String[] result = Arrays.copyOf(dst, dst.length + src.length);
        System.arraycopy(src, 0, result, dst.length, src.length);
        return result;
    }
}
