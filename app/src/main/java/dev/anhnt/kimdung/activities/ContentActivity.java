package dev.anhnt.kimdung.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dev.anhnt.kimdung.R;
import dev.anhnt.kimdung.activities.base.TextEditorActivity;
import dev.anhnt.kimdung.algorithm.DictionaryReader;
import dev.anhnt.kimdung.dialog.AboutDialog;
import dev.anhnt.kimdung.dialog.CheckParagraphDialog;
import dev.anhnt.kimdung.dialog.SearchResultDialog;
import dev.anhnt.kimdung.models.Chapter;
import dev.anhnt.kimdung.ui.SAutoBgButton;
import dev.anhnt.kimdung.utils.DataBaseHelper;
import jp.wasabeef.richeditor.RichEditor;

public class ContentActivity extends TextEditorActivity implements NavigationView.OnNavigationItemSelectedListener {

    private int position;
    private RichEditor mEditor;
    private DataBaseHelper dataBaseHelper;
    private Toolbar toolbar;
    private String text;
    private DictionaryReader dict;
    private SAutoBgButton btnNext;
    private SAutoBgButton btnPrevious;
    private View.OnClickListener onClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
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
        new LoadContent().execute(ChapterListActivity.chapterList.get(position).getId());
    }

    private void initView() {
        this.position = getIntent().getIntExtra(ChapterListActivity.LIST_POSITION, -1);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(ChapterListActivity.chapterList.get(position).getName());
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
        this.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_previous: setButtonPrevious(); break;
                    case R.id.btn_next: setButtonNext(); break;
                }
            }
        };
        this.btnPrevious = (SAutoBgButton) findViewById(R.id.btn_previous);
        this.btnNext = (SAutoBgButton) findViewById(R.id.btn_next);
        this.btnPrevious.setOnClickListener(onClickListener);
        this.btnNext.setOnClickListener(onClickListener);
        if (position == 0) {
            disablePreviousButton();
        }
        if (position == ChapterListActivity.chapterList.size() - 1) {
            disableNextButton();
        }
    }

    private void check() {
        int numErr = 0;
        List<String> listResult = new ArrayList<>();
        removeCheck();
        String str_without_html = stripHtml(text);
        Set<String> words = getWords(str_without_html);
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

    private void check2(String s) {
        int numErr = 0;
        List<String> listResult = new ArrayList<>();
//        removeCheck();
        String str_without_html = stripHtml(s);
        Set<String> words = getWords(str_without_html);
        for (String word: words) {
            if (!dict.hasWord(word) && !StringUtils.isNumeric(word)) {
                numErr++;
                listResult.add(word);
                /*if (text.contains(word)) {
                    text = text.replaceAll("\\b(" + word + ")\\b", "<mark><b>"+word+"</b></mark>");
                }*/
            }
        }
//        mEditor.setHtml(text);
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
        new LoadContent().execute(ChapterListActivity.chapterList.get(position).getId());
    }

    private void setButtonPrevious() {
        if (position >= 1) {
            if (position == ChapterListActivity.chapterList.size() - 1) {
                enableNextButton();
            }
            if (position == 1) {
                disablePreviousButton();
            }
            position--;
            Chapter chapter = ChapterListActivity.chapterList.get(position);
            getSupportActionBar().setTitle(chapter.getName());
            new LoadContent().execute(chapter.getId());
        }
    }

    private void setButtonNext() {
        if (position < ChapterListActivity.chapterList.size() - 1) {
            if (position == 0) {
                enablePreviousButton();
            }
            if (position == ChapterListActivity.chapterList.size() - 2) {
                disableNextButton();
            }
            position++;
            Chapter chapter = ChapterListActivity.chapterList.get(position);
            getSupportActionBar().setTitle(chapter.getName());
            new LoadContent().execute(chapter.getId());
        }

    }

    private void enablePreviousButton() {
        this.btnPrevious.setEnabled(true);
        this.btnPrevious.setClickable(true);
        this.btnPrevious.setBackgroundResource(R.drawable.btn_open_file);
    }

    private void disablePreviousButton() {
        this.btnPrevious.setEnabled(false);
        this.btnPrevious.setClickable(false);
        this.btnPrevious.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
    }

    private void enableNextButton() {
        this.btnNext.setEnabled(true);
        this.btnNext.setClickable(true);
        this.btnNext.setBackgroundResource(R.drawable.btn_open_file);
    }

    private void disableNextButton() {
        this.btnNext.setEnabled(false);
        this.btnNext.setClickable(false);
        this.btnNext.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
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
        int id = item.getItemId();

        if (id == R.id.nav_check) {
            check();
        } else if (id == R.id.nav_uncheck) {
            removeCheck();
            Toast.makeText(this, "Xong!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_reload) {
            reload();
            Toast.makeText(this, "Xong!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_check_2) {
            CheckParagraphDialog searchDialog = new CheckParagraphDialog(this);
            searchDialog.setDialogResult(new CheckParagraphDialog.OnSearchDialogResult() {
                @Override
                public void finish(String result) {
                    if (!result.trim().equals("")) {
                        check2(result);
                    } else {
                        Toast.makeText(getApplicationContext(), "Nội dung không để trắng.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            searchDialog.show();
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

    private class LoadContent extends AsyncTask<Integer, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Integer... params) {
            text = dataBaseHelper.getContent(params[0]);
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
            progress = ProgressDialog.show(ContentActivity.this, null,
                    "Please wait...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
