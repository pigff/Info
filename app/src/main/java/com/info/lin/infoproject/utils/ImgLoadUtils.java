package com.info.lin.infoproject.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by lin on 2017/2/22.
 */
public class ImgLoadUtils {

    public static void loadUrl(Context context, String url, Integer errorImg, ImageView view) {
        Glide.with(context)
                .load(url)
                .error(errorImg)
                .fitCenter()
                .into(view);
    }

    public static void loadUrl(Context context, String url, Integer errorImg, ImageView view, Integer wide, Integer height) {
        Glide.with(context)
                .load(url)
                .error(errorImg)
                .override(wide, height)
                .fitCenter()
                .into(view);
    }
}
