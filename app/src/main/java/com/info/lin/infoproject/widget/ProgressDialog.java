package com.info.lin.infoproject.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.info.lin.infoproject.R;
/**
 * Created by greedy on 2017/5/26.
 */

public class ProgressDialog extends Dialog {
    public ProgressDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public ProgressDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    public static class Builder {
        private String mMessage;
        private Context mContext;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setMessage(String message) {
            mMessage = message;
            return this;
        }

        public Dialog create() {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ProgressDialog dialog = new ProgressDialog(mContext, R.style.ProgressDialog);
            View layout = inflater.inflate(R.layout.dialog_progress_item, null);
            if (!TextUtils.isEmpty(mMessage)) {
                TextView textView = (TextView) layout.findViewById(R.id.tv_progress);
                textView.setText(mMessage);
                textView.setVisibility(View.VISIBLE);
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }

}
