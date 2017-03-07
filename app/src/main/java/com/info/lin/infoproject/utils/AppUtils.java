package com.info.lin.infoproject.utils;

import android.content.Context;
import android.graphics.Point;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public static Point getScreenPoint() {
        WindowManager wm = (WindowManager) App.getInstance()
                .getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point;
    }

    public static int getScreenWidth() {
        return getScreenPoint().x;
    }

    public static int getScreenHeight() {
        return getScreenPoint().y;
    }

    public static String getZhiData(int page) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1 - page);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        Date date = calendar.getTime();
        return simpleDateFormat.format(date);
    }
}
