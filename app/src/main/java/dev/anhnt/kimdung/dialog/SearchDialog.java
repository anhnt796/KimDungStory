package dev.anhnt.kimdung.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import dev.anhnt.kimdung.R;

public class SearchDialog extends Dialog {

    private EditText editSearch;
    private Button btnOk;
    private Button btnCancel;
    OnSearchDialogResult mDialogResult;

    public SearchDialog(final Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_search);
        setCancelable(false);
        setCanceledOnTouchOutside(true);
        editSearch = (EditText) findViewById(R.id.edit_search);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnCancel = (Button) findViewById(R.id.btn_cancel);

        btnOk.setOnClickListener(new OkListener());
        btnCancel.setOnClickListener(new CancelListener());
    }

    private class OkListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if( mDialogResult != null ){
                mDialogResult.finish(String.valueOf(editSearch.getText()));
            }
            SearchDialog.this.dismiss();
        }
    }

    private class CancelListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            SearchDialog.this.dismiss();
        }
    }

    public void setEditSearch(String string) {
        editSearch.setText(string);
    }

    public void setDialogResult(OnSearchDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnSearchDialogResult {
        void finish(String result);
    }

}
