package com.info.lin.infoproject.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.info.lin.infoproject.R;

/**
 * Created by lin on 2017/2/21.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setTitle(String title) {
        TextView textView = (TextView) findViewById(R.id.tv_title);
        if (textView != null) {
            textView.setText(title);
        }
    }

    protected void back() {
        ImageView imageView = (ImageView) findViewById(R.id.back_title);
        if (imageView != null) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}
