package dev.anhnt.kimdung.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import dev.anhnt.kimdung.models.Chapter;

public class DataBaseHelper {
    public static final String DB_PATH = Environment.getDataDirectory().getPath() + "/data/dev.anhnt.kimdung/database/";
    public static final String DB_NAME = "kimdung.sqlite";
    private Context context;
    private SQLiteDatabase database;
    private ArrayList<Chapter> listChapterName = new ArrayList<>();
    public DataBaseHelper(Context context) {
        copyDatabase(context);
        this.context = context;
    }


    private void copyDatabase(Context context){
        try {
            InputStream inputStream = context.getAssets().open(DB_NAME);
            File file = new File(DB_PATH + DB_NAME);
            if(!file.exists()){
                File parent = file.getParentFile();
                parent.mkdirs();
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int count = inputStream.read(b);
                while(count != -1){
                    outputStream.write(b,0,count);
                    count = inputStream.read(b);
                }
                outputStream.close();
            }
            inputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void openDatabase (){
        database = context.openOrCreateDatabase(DB_PATH + DB_NAME,Context.MODE_APPEND,null);
    }
    public void closeDatabase(){
        database.close();
    }

    public String getContent(int order) {
        openDatabase();
        String result = "";
        String[] selectedColumns = new String[] { Constants.COLUMN_ST_CONTENT };
        Cursor c = database.query(Constants.TABLE_ST, selectedColumns, "deId=?", new String[]{"" + order}, null, null, null);
        c.moveToFirst();
        int indexContent = c.getColumnIndex(selectedColumns[0]);
        while (!c.isAfterLast()) {
            result = c.getString(indexContent);
            c.moveToNext();
        }
        c.close();
        closeDatabase();
        return result;
    }

    public String getIntroduce(int order) {
        openDatabase();
        String result = "";
        String[] selectedColumns = new String[] { Constants.COLUMN_KIMDUNG_CONTENT };
        Cursor c;
        switch (order) {
            case 1: c = database.query(Constants.TABLE_KIMDUNG, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_1}, null, null, null); break;
            case 2: c = database.query(Constants.TABLE_KIMDUNG, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_2}, null, null, null); break;
            case 3: c = database.query(Constants.TABLE_KIMDUNG, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_3}, null, null, null); break;
            case 4: c = database.query(Constants.TABLE_KIMDUNG, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_4}, null, null, null); break;
            case 5: c = database.query(Constants.TABLE_KIMDUNG, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_5}, null, null, null); break;
            case 6: c = database.query(Constants.TABLE_KIMDUNG, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_6}, null, null, null); break;
            case 7: c = database.query(Constants.TABLE_KIMDUNG, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_7}, null, null, null); break;
            case 8: c = database.query(Constants.TABLE_KIMDUNG, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_8}, null, null, null); break;
            case 9: c = database.query(Constants.TABLE_KIMDUNG, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_9}, null, null, null); break;
            case 10: c = database.query(Constants.TABLE_KIMDUNG, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_10}, null, null, null); break;
            case 11: c = database.query(Constants.TABLE_KIMDUNG, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_11}, null, null, null); break;
            case 12: c = database.query(Constants.TABLE_KIMDUNG, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_12}, null, null, null); break;
            case 13: c = database.query(Constants.TABLE_KIMDUNG, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_13}, null, null, null); break;
            case 14: c = database.query(Constants.TABLE_KIMDUNG, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_14}, null, null, null); break;
            default: return null;
        }
        c.moveToFirst();
        int indexContent = c.getColumnIndex(selectedColumns[0]);
        while (!c.isAfterLast()) {
            result = c.getString(indexContent);
            c.moveToNext();
        }
        c.close();
        closeDatabase();
        return result;
    }

    public List<Chapter> getChapterList(int order) {
        openDatabase();
        String[] selectedColumns = new String[] { Constants.COLUMN_ST_ID, Constants.COLUMN_ST_NAME };
        Cursor c;
        switch (order) {
            case 1: c = database.query(Constants.TABLE_ST, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_1}, null, null, null); break;
            case 2: c = database.query(Constants.TABLE_ST, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_2}, null, null, null); break;
            case 3: c = database.query(Constants.TABLE_ST, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_3}, null, null, null); break;
            case 4: c = database.query(Constants.TABLE_ST, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_4}, null, null, null); break;
            case 5: c = database.query(Constants.TABLE_ST, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_5}, null, null, null); break;
            case 6: c = database.query(Constants.TABLE_ST, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_6}, null, null, null); break;
            case 7: c = database.query(Constants.TABLE_ST, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_7}, null, null, null); break;
            case 8: c = database.query(Constants.TABLE_ST, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_8}, null, null, null); break;
            case 9: c = database.query(Constants.TABLE_ST, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_9}, null, null, null); break;
            case 10: c = database.query(Constants.TABLE_ST, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_10}, null, null, null); break;
            case 11: c = database.query(Constants.TABLE_ST, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_11}, null, null, null); break;
            case 12: c = database.query(Constants.TABLE_ST, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_12}, null, null, null); break;
            case 13: c = database.query(Constants.TABLE_ST, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_13}, null, null, null); break;
            case 14: c = database.query(Constants.TABLE_ST, selectedColumns, "stId=?", new String[]{"" + Constants.ID_STORY_14}, null, null, null); break;
            default: return null;
        }
        c.moveToFirst();
        int indexId = c.getColumnIndex(selectedColumns[0]);
        int indexName = c.getColumnIndex(selectedColumns[1]);
        while (!c.isAfterLast()) {
            Chapter chapter = new Chapter(c.getInt(indexId), c.getString(indexName));
            listChapterName.add(chapter);
            c.moveToNext();
        }
        c.close();
        closeDatabase();
        return listChapterName;
    }

    public List<Chapter> getListResults(int order, String search) {
        openDatabase();
        String[] selectedColumns = new String[] { Constants.COLUMN_ST_ID, Constants.COLUMN_ST_NAME };
        Cursor c;
//        String filter = encode(search);

        switch (order) {
            case 1: c = database.query(Constants.TABLE_ST, selectedColumns, "decontent LIKE ? AND stId=?", new String[]{"%" + search + "%", "" + Constants.ID_STORY_1}, null, null, null); break;
            case 2: c = database.query(Constants.TABLE_ST, selectedColumns, "decontent LIKE ? AND stId=?", new String[]{"%" + search + "%", "" + Constants.ID_STORY_2}, null, null, null); break;
            case 3: c = database.query(Constants.TABLE_ST, selectedColumns, "decontent LIKE ? AND stId=?", new String[]{"%" + search + "%", "" + Constants.ID_STORY_3}, null, null, null); break;
            case 4: c = database.query(Constants.TABLE_ST, selectedColumns, "decontent LIKE ? AND stId=?", new String[]{"%" + search + "%", "" + Constants.ID_STORY_4}, null, null, null); break;
            case 5: c = database.query(Constants.TABLE_ST, selectedColumns, "decontent LIKE ? AND stId=?", new String[]{"%" + search + "%", "" + Constants.ID_STORY_5}, null, null, null); break;
            case 6: c = database.query(Constants.TABLE_ST, selectedColumns, "decontent LIKE ? AND stId=?", new String[]{"%" + search + "%", "" + Constants.ID_STORY_6}, null, null, null); break;
            case 7: c = database.query(Constants.TABLE_ST, selectedColumns, "decontent LIKE ? AND stId=?", new String[]{"%" + search + "%", "" + Constants.ID_STORY_7}, null, null, null); break;
            case 8: c = database.query(Constants.TABLE_ST, selectedColumns, "decontent LIKE ? AND stId=?", new String[]{"%" + search + "%", "" + Constants.ID_STORY_8}, null, null, null); break;
            case 9: c = database.query(Constants.TABLE_ST, selectedColumns, "decontent LIKE ? AND stId=?", new String[]{"%" + search + "%", "" + Constants.ID_STORY_9}, null, null, null); break;
            case 10: c = database.query(Constants.TABLE_ST, selectedColumns, "decontent LIKE ? AND stId=?", new String[]{"%" + search + "%", "" + Constants.ID_STORY_10}, null, null, null); break;
            case 11: c = database.query(Constants.TABLE_ST, selectedColumns, "decontent LIKE ? AND stId=?", new String[]{"%" + search + "%", "" + Constants.ID_STORY_11}, null, null, null); break;
            case 12: c = database.query(Constants.TABLE_ST, selectedColumns, "decontent LIKE ? AND stId=?", new String[]{"%" + search + "%", "" + Constants.ID_STORY_12}, null, null, null); break;
            case 13: c = database.query(Constants.TABLE_ST, selectedColumns, "decontent LIKE ? AND stId=?", new String[]{"%" + search + "%", "" + Constants.ID_STORY_13}, null, null, null); break;
            case 14: c = database.query(Constants.TABLE_ST, selectedColumns, "decontent LIKE ? AND stId=?", new String[]{"%" + search + "%", "" + Constants.ID_STORY_14}, null, null, null); break;
            default: return null;
        }
        if(c!=null && c.getCount()>0) {
            c.moveToFirst();
            int indexId = c.getColumnIndex(selectedColumns[0]);
            int indexName = c.getColumnIndex(selectedColumns[1]);
            while (!c.isAfterLast()) {
                Chapter chapter = new Chapter(c.getInt(indexId), c.getString(indexName));
                listChapterName.add(chapter);
                c.moveToNext();
            }
            c.close();
            closeDatabase();
        } else {
            closeDatabase();
            return null;
        }
        return listChapterName;
    }

    private String encode(String s) {
        // String[] tokens = s.split("\\s");
        String[] tokens = s.split(" ");
        StringBuilder buffer = new StringBuilder();
        for (String s1: tokens) {
            buffer.append(s1);
            buffer.append("%");
        }
        return buffer.toString();
    }
}