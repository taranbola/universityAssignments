package com.example.cw1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLoveButtonClicked(View view){
        TextView textView = (TextView) findViewById(R.id.textView6);
        textView.setVisibility(View.VISIBLE);
    }
}
