package dev.anhnt.kimdung.algorithm;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class DictionaryReader {
    private Map<String, Integer> dictionary = new HashMap<String, Integer>();
    public DictionaryReader() {
    }

    public DictionaryReader(Context context, String dictionaryFile) throws IOException
    {
        LoadDictionary(context, dictionaryFile);
    }
    public void LoadDictionary(Context context, String dictionaryFile) throws IOException {
        InputStream inputStream = context.getAssets().open(dictionaryFile);
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        String str;
        int i = 0;
        while ((str = in.readLine()) != null) {
            String line = str.trim();
            byte ptext[] = line.getBytes(Charset.forName("UTF-8"));
            line = new String(ptext, Charset.forName("UTF-8"));

            if (line.contains("#"))
                line = line.substring(0, line.indexOf("#")).trim();
            if (line.length() > 0)
                dictionary.put(line, i++);
        }
        in.close();
    }

    public int lookUp(String word)
    {
        if (dictionary.containsKey(word))
            return dictionary.get(word);
        return -1;
    }

    public boolean hasWord(String word)
    {
        return dictionary.containsKey(VnStringUtils.toLowerCase(word));
    }
}
