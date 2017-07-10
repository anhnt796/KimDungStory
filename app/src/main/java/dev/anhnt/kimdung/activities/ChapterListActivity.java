package dev.anhnt.kimdung.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dev.anhnt.kimdung.R;
import dev.anhnt.kimdung.adapter.ListChapterAdapter;
import dev.anhnt.kimdung.models.Chapter;
import dev.anhnt.kimdung.utils.DataBaseHelper;

public class ChapterListActivity extends AppCompatActivity {

    public static final String LIST_POSITION = "position";

    private int code;
    private int action;
    private String search;
    private ListView listChapter;
    public static List<Chapter> chapterList = new ArrayList<>();
    private ListChapterAdapter listChapterAdapter;
    private DataBaseHelper dataBaseHelper;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
        this.action = getIntent().getIntExtra(IntroduceActivity.INTRODUCE_ACTION, IntroduceActivity.NEXT_ACTION);

        dataBaseHelper = new DataBaseHelper(this);
        if (chapterList != null && chapterList.size() > 0) {
            chapterList.clear();
        }
        new LoadList().execute();
    }

    public void initView() {
        this.code = getIntent().getIntExtra(IntroduceActivity.INTRODUCE_CODE, -1);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        switch (code) {
            case 1: getSupportActionBar().setTitle(R.string.menu_text1); break;
            case 2: getSupportActionBar().setTitle(R.string.menu_text2); break;
            case 3: getSupportActionBar().setTitle(R.string.menu_text3); break;
            case 4: getSupportActionBar().setTitle(R.string.menu_text4); break;
            case 5: getSupportActionBar().setTitle(R.string.menu_text5); break;
            case 6: getSupportActionBar().setTitle(R.string.menu_text6); break;
            case 7: getSupportActionBar().setTitle(R.string.menu_text7); break;
            case 8: getSupportActionBar().setTitle(R.string.menu_text8); break;
            case 9: getSupportActionBar().setTitle(R.string.menu_text9); break;
            case 10: getSupportActionBar().setTitle(R.string.menu_text10); break;
            case 11: getSupportActionBar().setTitle(R.string.menu_text11); break;
            case 12: getSupportActionBar().setTitle(R.string.menu_text12); break;
            case 13: getSupportActionBar().setTitle(R.string.menu_text13); break;
            case 14: getSupportActionBar().setTitle(R.string.menu_text14); break;
        }
        this.listChapter = (ListView) findViewById(R.id.listview);
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.hold, R.anim.push_right_out);
        super.onBackPressed();
    }

    private class LoadList extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            switch (action) {
                case IntroduceActivity.NEXT_ACTION:
                    chapterList = dataBaseHelper.getChapterList(code);
                    break;
                case IntroduceActivity.SEARCH_ACTION:
                    search = getIntent().getStringExtra(IntroduceActivity.INTRODUCE_SEARCH);
                    chapterList = dataBaseHelper.getListResults(code, search);
                    break;
                default:
                    chapterList = dataBaseHelper.getChapterList(code);
                    break;
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            if (chapterList == null) {
                onBackPressed();
                Toast.makeText(getApplicationContext(), "Xin lỗi, không có kết quả.", Toast.LENGTH_SHORT).show();
            } else {
                listChapterAdapter = new ListChapterAdapter(ChapterListActivity.this, chapterList);
                listChapter.setAdapter(listChapterAdapter);
                listChapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                        intent.putExtra(LIST_POSITION, position);
                        startActivity(intent);
                    }
                });
            }
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(ChapterListActivity.this, null,
                    "Please wait...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
