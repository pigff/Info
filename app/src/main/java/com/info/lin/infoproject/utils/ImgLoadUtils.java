package com.info.lin.infoproject.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.info.lin.infoproject.R;

/**
 * Created by lin on 2017/2/22.
 */
public class ImgLoadUtils {

    public static void loadUrl(Context context, String url, Integer errorImg, ImageView view) {
        Glide.with(context)
                .load(url)
                .error(errorImg)
                .centerCrop()
                .into(view);
    }

    public static void loadUrl(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .error(R.drawable.img_load_error)
                .dontAnimate()
                .into(view);
    }

    public static void loadAdapterUrl(Context context, String url, final ImageView view) {
        Glide.with(context).
                load(url)
                .asBitmap()
                .error(R.drawable.img_load_error)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        int imageWidth = resource.getWidth();
                        int imageHeight = resource.getHeight();
                        int height = AppUtils.getScreenWidth() * imageHeight / imageWidth;
                        ViewGroup.LayoutParams para = view.getLayoutParams();
                        para.height = height;
                        view.setLayoutParams(para);
                        view.setImageBitmap(resource);
                    }
                });
    }

    public static void loadHalfAdapterUrl(Context context, String url, final ImageView view) {
        Glide.with(context).
                load(url)
                .asBitmap()
                .error(R.drawable.img_load_error)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        int imageWidth = resource.getWidth();
                        int imageHeight = resource.getHeight();
                        int height = AppUtils.getScreenWidth() / 2 * imageHeight / imageWidth;
                        ViewGroup.LayoutParams para = view.getLayoutParams();
                        para.height = height;
                        view.setLayoutParams(para);
                        view.setImageBitmap(resource);
                    }
                });
    }

    public static void loadUrl(Context context, String url, Integer errorImg, ImageView view, Integer wide, Integer height) {
        Glide.with(context)
                .load(url)
                .error(errorImg)
                .override(wide, height)
                .crossFade()
                .fitCenter()
                .into(view);
    }

    public static void loadCircleUrl(Context context, String url, int errorImg, ImageView view) {
        Glide.with(context)
                .load(url)
                .error(errorImg)
                .crossFade()
                .bitmapTransform(new GlideCircleTransform(context))
                .into(view);
    }

    public static void loadCircleUrl(Context context, String url, int errorImg, ImageView view, int width, int height) {
        Glide.with(context)
                .load(url)
                .error(errorImg)
                .crossFade()
                .override(width, height)
                .bitmapTransform(new GlideCircleTransform(context))
                .into(view);
    }

    public static void loadCircleUrl(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .error(R.drawable.img_load_error)
                .crossFade()
                .bitmapTransform(new GlideCircleTransform(context))
                .into(view);
    }

}
