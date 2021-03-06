package dev.anhnt.kimdung.algorithm;

import java.util.Locale;

public class VnStringUtils {

    public static Locale VN_LOCALE = Locale.forLanguageTag("vi_VN");
    public static String toLowerCase(String vn_string) {

        // database exception, manual fix
        if (vn_string.startsWith("Ð")) {
            return vn_string.replace("Ð", "đ"); // kí tự Ð ở dòng này khác với kí tự Đ nhập từ bàn phím!
        }

        return vn_string.toLowerCase(VN_LOCALE);
    }
}
