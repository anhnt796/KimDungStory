package dev.anhnt.kimdung.activities.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import java.text.BreakIterator;
import java.util.HashSet;
import java.util.Set;

public abstract class TextEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Phương thức này xóa html tags trong một đoạn string
     * @param html Dữ liệu vào
     * @return Đoạn string không chứa html tags
     */
    protected String stripHtml(String html) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(html).toString();
        }
    }

    /**
     * Phương thức này tách các từ trong một đoạn văn thành một tập hợp (các từ không trùng lặp).
     * @param text Đoạn văn bản đầu vào
     * @return Tập hợp các từ trong đoạn văn
     */
    protected static Set<String> getWords(String text) {
        Set<String> words = new HashSet<>();
        BreakIterator breakIterator = BreakIterator.getWordInstance();
        breakIterator.setText(text);
        int lastIndex = breakIterator.first();
        while (BreakIterator.DONE != lastIndex) {
            int firstIndex = lastIndex;
            lastIndex = breakIterator.next();
            if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(text.charAt(firstIndex))) {
                words.add(text.substring(firstIndex, lastIndex));
            }
        }
        return words;
    }
}
