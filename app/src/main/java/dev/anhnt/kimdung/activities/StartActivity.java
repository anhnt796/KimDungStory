package dev.anhnt.kimdung.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import dev.anhnt.kimdung.R;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String START_CODE = "code";

    private int objectLength;

    public StartActivity() {
        this.objectLength = 14;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bindWidget();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, IntroduceActivity.class);
        switch (view.getId()) {
            case R.id.button_menu01:
                intent.putExtra(START_CODE, 1);
                break;
            case R.id.button_menu02:
                intent.putExtra(START_CODE, 2);
                break;
            case R.id.button_menu03:
                intent.putExtra(START_CODE, 3);
                break;
            case R.id.button_menu04:
                intent.putExtra(START_CODE, 4);
                break;
            case R.id.button_menu05:
                intent.putExtra(START_CODE, 5);
                break;
            case R.id.button_menu06:
                intent.putExtra(START_CODE, 6);
                break;
            case R.id.button_menu07:
                intent.putExtra(START_CODE, 7);
                break;
            case R.id.button_menu08:
                intent.putExtra(START_CODE, 8);
                break;
            case R.id.button_menu09:
                intent.putExtra(START_CODE, 9);
                break;
            case R.id.button_menu10:
                intent.putExtra(START_CODE, 10);
                break;
            case R.id.button_menu11:
                intent.putExtra(START_CODE, 11);
                break;
            case R.id.button_menu12:
                intent.putExtra(START_CODE, 12);
                break;
            case R.id.button_menu13:
                intent.putExtra(START_CODE, 13);
                break;
            case R.id.button_menu14:
                intent.putExtra(START_CODE, 14);
                break;
        }
        startActivity(intent);
    }

    private void bindWidget() {
        Button[] buttons = new Button[this.objectLength];
        buttons[0] = (Button) findViewById(R.id.button_menu01);
        buttons[0].setOnClickListener(this);
        buttons[1] = (Button) findViewById(R.id.button_menu02);
        buttons[1].setOnClickListener(this);
        buttons[2] = (Button) findViewById(R.id.button_menu03);
        buttons[2].setOnClickListener(this);
        buttons[3] = (Button) findViewById(R.id.button_menu04);
        buttons[3].setOnClickListener(this);
        buttons[4] = (Button) findViewById(R.id.button_menu05);
        buttons[4].setOnClickListener(this);
        buttons[5] = (Button) findViewById(R.id.button_menu06);
        buttons[5].setOnClickListener(this);
        buttons[6] = (Button) findViewById(R.id.button_menu07);
        buttons[6].setOnClickListener(this);
        buttons[7] = (Button) findViewById(R.id.button_menu08);
        buttons[7].setOnClickListener(this);
        buttons[8] = (Button) findViewById(R.id.button_menu09);
        buttons[8].setOnClickListener(this);
        buttons[9] = (Button) findViewById(R.id.button_menu10);
        buttons[9].setOnClickListener(this);
        buttons[10] = (Button) findViewById(R.id.button_menu11);
        buttons[10].setOnClickListener(this);
        buttons[11] = (Button) findViewById(R.id.button_menu12);
        buttons[11].setOnClickListener(this);
        buttons[12] = (Button) findViewById(R.id.button_menu13);
        buttons[12].setOnClickListener(this);
        buttons[13] = (Button) findViewById(R.id.button_menu14);
        buttons[13].setOnClickListener(this);
    }
}
