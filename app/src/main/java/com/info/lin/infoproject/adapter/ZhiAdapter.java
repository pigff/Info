package com.info.lin.infoproject.adapter;

import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.info.lin.infoproject.R;
import com.info.lin.infoproject.data.net.DailyStory;
import com.info.lin.infoproject.utils.ImgLoadUtils;

import java.util.List;

/**
 * Created by greedy on 2017/8/5.
 */

public class ZhiAdapter extends BaseQuickAdapter<DailyStory, BaseViewHolder> {
    public ZhiAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DailyStory item) {
        helper.setText(R.id.tv_title_zhi_card, item.getTitle())
                .addOnClickListener(R.id.zhi_card_group);
        ImageView imageView = helper.getView(R.id.iv_zhi_card);
        ImgLoadUtils.loadUrl(mContext, item.getImages().get(0), imageView);
    }

    public void updateList(List<DailyStory> dailyStories) {
        if (!(dailyStories != null && !getData().containsAll(dailyStories))) {
            Toast.makeText(mContext, mContext.getString(R.string.recent_news_notice), Toast.LENGTH_SHORT).show();
        }
        setNewData(dailyStories);
    }
}
