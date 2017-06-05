package com.info.lin.infoproject.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.info.lin.infoproject.R;
import com.info.lin.infoproject.utils.BarUtils;
import com.info.lin.infoproject.utils.SystemBarUtil;
import com.info.lin.infoproject.utils.ViewLayoutUtil;
import com.info.lin.infoproject.utils.constant.Constants;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.Serializable;

/**
 * Created by greedy on 2017/5/11.
 */

public class BaseActivity extends RxAppCompatActivity {

    protected static final String TAG = "BaseActivity";

    private Dialog mProgressDialog;

    protected void openActivity(Class<?> clazz) {
        openActivity(clazz, new Bundle());
    }

    protected void openActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(Constants.PARAM, bundle);
//        startActivity(intent);
        startWithAnimActivty(intent);
    }

    protected void openActivity(Class<?> clazz, Object o) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, clazz);
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
//        startActivity(intent);
        startWithAnimActivty(intent);
    }

    protected void openActivityForResult(Class<?> clazz) {
        openActivityForResult(clazz, Constants.DEFAULT_REQUEST_CODE);
    }

    protected void openActivityForResult(Class<?> clazz, int requestCode) {
        Bundle bundle = new Bundle();
        openActivityForResult(clazz, bundle, requestCode);
    }

    protected void openActivityForResult(Class<?> clazz, Bundle bundle) {
        openActivityForResult(clazz, bundle, Constants.DEFAULT_REQUEST_CODE);
    }


    protected void openActivityForResult(Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(Constants.PARAM, bundle);
//        startActivityForResult(intent, requestCode);
        startWithAnimActivtyForResult(intent, requestCode);
    }

    protected Bundle getBundleData() {
        Bundle bundle = getIntent().getBundleExtra(Constants.PARAM);
        return bundle == null ? new Bundle() : bundle;
    }

    protected void showShortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected int[] getInAnimas() {
        return new int[]{R.anim.common_whole_right_in, R.anim.common_part_left_out};
    }

    protected int[] getOutAnimas() {
        return new int[]{R.anim.common_part_left_in, R.anim.common_whole_right_out};
    }

    private void loadTransitionAnim(boolean inTransition) {
        int animas[];
        if (inTransition) {
            animas = getInAnimas();
        } else {
            animas = getOutAnimas();
        }

        if (animas == null || animas.length < 2) {
            try {
                throw new Exception("必须传入两个以上的动画资源");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            overridePendingTransition(animas[0], animas[1]);
        }
    }

    protected boolean openTransitionAnim() {
        return false;
    }

    @Override
    public void finish() {
        super.finish();
        if (openTransitionAnim()) {
            loadTransitionAnim(false);
        }
    }

    private void startWithAnimActivty(Intent intent) {
        startActivity(intent);
        if (openTransitionAnim()) {
            loadTransitionAnim(true);
        }
    }

    private void startWithAnimActivtyForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        if (openTransitionAnim()) {
            loadTransitionAnim(true);
        }
    }

    private void showProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog.Builder(this)
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

    /**
     * Get status bar height.
     *
     * @return
     */
    public final int getStatusBarHeight() {
        return BarUtils.getStatusBarHeight(this);
    }

    /**
     * Hide navigation bar.
     */
    public final void hideNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;

            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(uiOptions);

        } else {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
        }
    }

    /**
     * Make full screen in all Android version.
     */
    public final void makeFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Hide the status bar and navigation bar.
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(uiOptions);

        } else {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
        }
    }

    /**
     * If the Android version is higher than KitKat(API>=19) <br> use this call to show & hide
     *
     * @param enable
     */
    @SuppressLint("NewApi")
    public final void makeFullScreenAfterKitKat(boolean enable) {
        try {
            View decorView = getWindow().getDecorView();
            if (enable) {
                int uiOptionsEnable = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                decorView.setSystemUiVisibility(uiOptionsEnable);

            } else {
                int uiOptionsDisable = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                decorView.setSystemUiVisibility(uiOptionsDisable);
            }

        } catch (Exception e) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            if (enable) {
                lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            } else {
                lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
            getWindow().setAttributes(lp);
        }
    }

    /**
     * 透明导航栏 API>=19才有效
     */
    private void translucentNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        SystemBarUtil systemBarUtil = new SystemBarUtil(this);
        systemBarUtil.setNavigationBarTintEnabled(true);
        systemBarUtil.setNavigationBarTintResource(android.R.color.transparent);
    }

    /**
     * 透明状态栏 API>=19才有效
     */
    private void translucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // setTranslucentStatus(true);
        }

        SystemBarUtil systemBarUtil = new SystemBarUtil(this);
        systemBarUtil.setStatusBarTintEnabled(true);
        systemBarUtil.setStatusBarTintResource(R.color.colorPrimary);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //修复软键盘内存泄露
        ViewLayoutUtil.fixInputMethodManagerLeak(this);
        hideProgress();
    }


}
