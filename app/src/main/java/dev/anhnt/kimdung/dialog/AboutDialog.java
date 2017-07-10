package dev.anhnt.kimdung.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import dev.anhnt.kimdung.R;

public class AboutDialog extends Dialog {
    private Button buttonOk;

    public AboutDialog(final Context context) {
        super(context);
        requestWindowFeature(1);
        setContentView(R.layout.dialog_about);
        setCancelable(false);
        setCanceledOnTouchOutside(true);

        buttonOk = (Button) findViewById(R.id.btn_ok);

        buttonOk.setOnClickListener(new OkListener());
    }

    private class OkListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AboutDialog.this.dismiss();
        }
    }
}
