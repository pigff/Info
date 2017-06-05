package com.info.lin.infoproject.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.info.lin.infoproject.utils.constant.Constants;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.io.Serializable;

/**
 * Created by greedy on 2017/5/11.
 */

public class BaseFragment extends RxFragment {

    private Dialog mProgressDialog;

    protected void openActivity(Class<?> clazz) {
        openActivity(clazz, new Bundle());
    }

    protected void openActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtra(Constants.PARAM, bundle);
        startActivity(intent);
    }

    protected void openActivity(Class<?> clazz, Object o) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getActivity(), clazz);
        if (o instanceof Integer) {
            bundle.putInt(Constants.DATA, (Integer) o);
        } else if (o instanceof String) {
            bundle.putString(Constants.DATA, (String) o);
        } else if (o instanceof Serializable) {
            bundle.putSerializable(Constants.DATA, (Serializable) o);
        } else if (o instanceof Parcelable) {
            bundle.putParcelable(Constants.DATA, (Parcelable) o);
        }
        intent.putExtra(Constants.PARAM, bundle);
        startActivity(intent);
    }

    protected void showShortToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    private void showProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog.Builder(getActivity())
                    .setMessage(getProgressMsg())
                    .create();
        }
        mProgressDialog.show();
    }

    private void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected String getProgressMsg() {
        return "";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgress();
    }
}
