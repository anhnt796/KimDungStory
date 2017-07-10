package dev.anhnt.kimdung.algorithm;

import java.util.Locale;

public class VnStringUtils {

    public static Locale VN_LOCALE = Locale.forLanguageTag("vi_VN");
    public static String toLowerCase(String vn_string) {
        // TODO: database exception
        if (vn_string.startsWith("Ð")) {
            return vn_string.replace("Ð", "đ");
        }
        return vn_string.toLowerCase(VN_LOCALE);
    }
}
