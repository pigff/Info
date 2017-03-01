package com.info.lin.infoproject.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;
import android.widget.TextView;

import com.info.lin.infoproject.App;
import com.info.lin.infoproject.R;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

import java.io.File;

/**
 * Created by lin on 2017/2/22.
 */
public class AppUtils {

    public static String getCacheDir() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            File cacheFile = App.getInstance().getExternalCacheDir();
            if (null != cacheFile) {
                return cacheFile.getPath();
            }
        }
        return App.getInstance().getCacheDir().getPath();
    }

    public static void setTextViewLeftDrawableForHeader(TextView textView, IIcon icon) {
        Drawable drawable = new IconicsDrawable(App.getInstance())
                .icon(icon)
                .color(ContextCompat.getColor(App.getInstance(), R.color.md_blue_500_color_code))
                .sizeDp(14);
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    public static String getGankDate(String date) {
        int index = date.indexOf("T");
        return date.substring(0, index);
    }

    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) App.getInstance()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) App.getInstance()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }
}
