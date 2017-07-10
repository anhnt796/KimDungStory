/**
 * This part does not belong to my project, it's an addition request from lecturer when protecting the project.
 */

package dev.anhnt.kimdung.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dev.anhnt.kimdung.R;

public class SearchDialog2 extends Dialog {

    private EditText editSearch;
    private Button btnOk;
    private Button btnCancel;
    OnSearchDialogResult mDialogResult;

    public SearchDialog2(final Context context) {
        super(context);
        requestWindowFeature(1);
        setContentView(R.layout.dialog_search_2);
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
            SearchDialog2.this.dismiss();
        }
    }

    private class CancelListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            SearchDialog2.this.dismiss();
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
