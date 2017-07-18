package dev.anhnt.kimdung.activities;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dev.anhnt.kimdung.R;
import dev.anhnt.kimdung.algorithm.DictionaryReader;
import dev.anhnt.kimdung.dialog.AboutDialog;
import dev.anhnt.kimdung.dialog.SearchDialog;
import dev.anhnt.kimdung.dialog.SearchResultDialog;
import dev.anhnt.kimdung.ui.SAutoBgButton;
import dev.anhnt.kimdung.utils.DataBaseHelper;
import jp.wasabeef.richeditor.RichEditor;

public class IntroduceActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    public static final String INTRODUCE_CODE = "code";
    public static final String INTRODUCE_ACTION = "action";
    public static final String INTRODUCE_SEARCH = "search";
    public static final int NEXT_ACTION = 200;
    public static final int SEARCH_ACTION = 201;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private int code;
    private SAutoBgButton btnNext;
    private RichEditor mEditor;
    private ImageView introducePicture;
    private DataBaseHelper dataBaseHelper;
    private Toolbar toolbar;
    private String text;
    private DictionaryReader dict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dataBaseHelper = new DataBaseHelper(this);

        try {
            dict = new DictionaryReader(this, "VNsyl.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        new LoadIntroduce().execute();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ChapterListActivity.class);
        switch (view.getId()) {
            case R.id.btn_next:
                intent.putExtra(INTRODUCE_CODE, code);
                intent.putExtra(INTRODUCE_ACTION, NEXT_ACTION);
                break;
        }
        startActivity(intent);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.code = getIntent().getIntExtra(StartActivity.START_CODE, -1);
        btnNext = (SAutoBgButton) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
        mEditor = (RichEditor) findViewById(R.id.editor);
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(14);
        mEditor.setEditorFontColor(Color.BLACK);
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String s) {
                text = s;
            }
        });
        introducePicture = (ImageView) findViewById(R.id.header);
        switch (code) {
            case 1:
                getSupportActionBar().setTitle(R.string.menu_text1);
                introducePicture.setImageResource(R.drawable.img_01_ythiendolongky);
                break;
            case 2:
                getSupportActionBar().setTitle(R.string.menu_text2);
                introducePicture.setImageResource(R.drawable.img_02_tieungaogiangho);
                break;
            case 3:
                getSupportActionBar().setTitle(R.string.menu_text3);
                introducePicture.setImageResource(R.drawable.img_03_volamnguba);
                break;
            case 4:
                getSupportActionBar().setTitle(R.string.menu_text4);
                introducePicture.setImageResource(R.drawable.img_04_thukiemancuuluc);
                break;
            case 5:
                getSupportActionBar().setTitle(R.string.menu_text5);
                introducePicture.setImageResource(R.drawable.img_05_thandieuhieplu);
                break;
            case 6:
                getSupportActionBar().setTitle(R.string.menu_text6);
                introducePicture.setImageResource(R.drawable.img_06_thienlongbatbo);
                break;
            case 7:
                getSupportActionBar().setTitle(R.string.menu_text7);
                introducePicture.setImageResource(R.drawable.img_07_thandieudaihiep);
                break;
            case 8:
                getSupportActionBar().setTitle(R.string.menu_text8);
                introducePicture.setImageResource(R.drawable.img_08_phihongoaitruyen);
                break;
            case 9:
                getSupportActionBar().setTitle(R.string.menu_text9);
                introducePicture.setImageResource(R.drawable.img_10_lucmachthankiem);
                break;
            case 10:
                getSupportActionBar().setTitle(R.string.menu_text10);
                introducePicture.setImageResource(R.drawable.img_09_locdinhky);
                break;
            case 11:
                getSupportActionBar().setTitle(R.string.menu_text11);
                introducePicture.setImageResource(R.drawable.img_11_hiepkhachhanh);
                break;
            case 12:
                getSupportActionBar().setTitle(R.string.menu_text12);
                introducePicture.setImageResource(R.drawable.img_12_cogaidolong);
                break;
            case 13:
                getSupportActionBar().setTitle(R.string.menu_text13);
                introducePicture.setImageResource(R.drawable.img_13_anhhungxadieu);
                break;
            case 14:
                getSupportActionBar().setTitle(R.string.menu_text14);
                introducePicture.setImageResource(R.drawable.img_14_lienthanhquyet);
                break;
        }
    }

    /**
     * Phương thức này xóa html tags trong một đoạn string
     * @param html Dữ liệu vào
     * @return Đoạn string không chứa html tags
     */
    private String stripHtml(String html) {
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
    private static Set<String> getWords(String text) {
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

    private void check() {
        int numErr = 0;
        List<String> listResult = new ArrayList<>();
        removeCheck();
        String stringWithoutHtml = stripHtml(text);
        Set<String> words = getWords(stringWithoutHtml);
        for (String word: words) {
            if (!dict.hasWord(word) && !StringUtils.isNumeric(word)) {
                numErr++;
                listResult.add(word);
                if (text.contains(word)) {
                    text = text.replaceAll("\\b(" + word + ")\\b", "<mark><b>"+word+"</b></mark>");
                }
            }
        }
        mEditor.setHtml(text);
        SearchResultDialog dialog = new SearchResultDialog(this);
        dialog.setTvResult(numErr);
        if (numErr > 0)
            dialog.setList(listResult);
        dialog.show();
    }

    private void removeCheck() {
        if (text.contains("<mark><b>")) {
            text = text.replace("<mark><b>", "").replace("</b></mark>","");
            mEditor.setHtml(text);
        }
    }

    private void reload() {
        new LoadIntroduce().execute();
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-VN");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(this,
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_url));
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void sendFeedback() {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("text/email");
        email.putExtra(Intent.EXTRA_EMAIL, new String[] { getResources().getString(R.string.email) });
        email.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.feedback_subject));
        email.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.feedback_text));
        startActivity(Intent.createChooser(email, "Send Feedback:"));
    }

    private void aboutUs() {
        AboutDialog dialog = new AboutDialog(this);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    SearchDialog searchDialog = new SearchDialog(this);
                    searchDialog.setEditSearch(result.get(0));
                    searchDialog.setDialogResult(new SearchDialog.OnSearchDialogResult() {
                        @Override
                        public void finish(String result) {
                            Intent intent = new Intent(IntroduceActivity.this, ChapterListActivity.class);
                            intent.putExtra(INTRODUCE_CODE, code);
                            intent.putExtra(INTRODUCE_ACTION, SEARCH_ACTION);
                            intent.putExtra(INTRODUCE_SEARCH, result);
                            startActivity(intent);
                        }
                    });
                    searchDialog.show();
                }
                break;
            }

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_check) {
            check();
        } else if (id == R.id.nav_uncheck) {
            removeCheck();
            Toast.makeText(this, "Xong!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_reload) {
            reload();
            Toast.makeText(this, "Xong!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_search) {
            SearchDialog searchDialog = new SearchDialog(this);
            searchDialog.setDialogResult(new SearchDialog.OnSearchDialogResult() {
                @Override
                public void finish(String result) {
                    if (!result.trim().equals("")) {
                        Intent intent = new Intent(IntroduceActivity.this, ChapterListActivity.class);
                        intent.putExtra(INTRODUCE_CODE, code);
                        intent.putExtra(INTRODUCE_ACTION, SEARCH_ACTION);
                        intent.putExtra(INTRODUCE_SEARCH, result);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Nội dung không để trắng.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            searchDialog.show();
        } else if (id == R.id.nav_voice) {
            promptSpeechInput();
        } else if (id == R.id.nav_share) {
            share();
        } else if (id == R.id.nav_send) {
            sendFeedback();
        } else if (id == R.id.nav_about) {
            aboutUs();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class LoadIntroduce extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            text = dataBaseHelper.getIntroduce(code);
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            mEditor.setHtml(text);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(IntroduceActivity.this, null,
                    "Please wait...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
