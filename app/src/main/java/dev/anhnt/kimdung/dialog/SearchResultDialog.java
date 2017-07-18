package dev.anhnt.kimdung.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import dev.anhnt.kimdung.R;

public class SearchResultDialog extends Dialog {

    private TextView tvResult;
    private TextView tvHasResultList;
    private TextView tvResultList;
    private ScrollView scrollView;
    private Button buttonOk;

    public SearchResultDialog(final Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_result);
        setCancelable(false);
        setCanceledOnTouchOutside(true);

        tvResult = (TextView) findViewById(R.id.tvResult);
        tvHasResultList = (TextView) findViewById(R.id.tvResultList);
        tvResultList = (TextView) findViewById(R.id.listResult);
        scrollView = (ScrollView) findViewById(R.id.scrollList);
        buttonOk = (Button) findViewById(R.id.btn_ok);

        buttonOk.setOnClickListener(new OkListener());
    }

    private class OkListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            SearchResultDialog.this.dismiss();
        }
    }

    public void setTvResult(String s) {
        tvResult.setText(s);
    }

    public void setTvResult(int error) {
        tvResult.setText("Có " + error + " tiếng sai chính tả.");
    }

    public void setList(List<String> list) {
        StringBuilder s = new StringBuilder("");
        tvHasResultList.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);
        tvHasResultList.setText("Danh sách lỗi:");
        for (String word: list) {
            s.append(word);
            s.append("\n");
        }
        tvResultList.setText(s.toString());
    }
}
