package com.info.lin.infoproject.adapter;

import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.info.lin.infoproject.R;
import com.info.lin.infoproject.data.net.GankItemBean;
import com.info.lin.infoproject.utils.AppUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by greedy on 2017/8/5.
 */

public class GirlAdapter extends BaseQuickAdapter<GankItemBean, BaseViewHolder> {

    private int[] colrSrc = {R.color.md_light_blue_700_color_code, R.color.md_yellow_300_color_code, R.color.md_light_blue_300_color_code};
    private Map<String, Integer> mMap;

    public GirlAdapter(int layoutResId) {
        super(layoutResId);
        mMap = new HashMap<>();
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GankItemBean item) {
        helper.addOnClickListener(R.id.iv_girl_card);
        final ImageView imageView = helper.getView(R.id.iv_girl_card);
        if (!mMap.containsKey(item.get_id())) {
            Glide.with(mContext).load(item.getUrl()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new SimpleTarget<Bitmap>(AppUtils.getScreenWidth() / 2, AppUtils.getScreenHeight() / 2) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            int width = resource.getWidth();
                            int height = resource.getHeight();
                            int realHeight = (AppUtils.getScreenWidth() / 2) * height / width;
                            mMap.put(item.get_id(), realHeight);
                            ViewGroup.LayoutParams lp = imageView.getLayoutParams();
                            lp.height = realHeight;
                            imageView.setLayoutParams(lp);
//
                        }
                    });
        } else {
            ViewGroup.LayoutParams lp = imageView.getLayoutParams();
            lp.height = mMap.get(item.get_id());
            imageView.setLayoutParams(lp);

        }

        Glide.with(mContext)
                .load(item.getUrl())
//                .placeholder(colrSrc[helper.getAdapterPosition() % 3])
                .dontAnimate()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

    }
}
