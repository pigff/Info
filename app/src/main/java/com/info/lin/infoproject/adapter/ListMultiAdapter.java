package com.info.lin.infoproject.adapter;

import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.info.lin.infoproject.R;
import com.info.lin.infoproject.data.net.MultiData;
import com.info.lin.infoproject.utils.AppUtils;
import com.info.lin.infoproject.utils.Constants;
import com.info.lin.infoproject.utils.ImgLoadUtils;
import com.info.lin.infoproject.widget.RatioImageView;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.List;

/**
 * Created by lin on 2017/2/23.
 */

public class ListMultiAdapter extends BaseMultiItemQuickAdapter<MultiData, BaseViewHolder> {

    public ListMultiAdapter(List<MultiData> data) {
        super(data);
        addItemType(MultiData.ITEM_DATA, R.layout.recycler_data_item);
        addItemType(MultiData.ITEM_IMG, R.layout.recycler_img_item);
        addItemType(MultiData.ITEM_SORT_LINE, R.layout.recycler_sort_line_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiData item) {
        switch (helper.getItemViewType()) {
            case MultiData.ITEM_DATA:
                helper.setText(R.id.tv_title_data_item, item.getGankItemBean().getDesc())
                        .setText(R.id.tv_author_data_item, item.getGankItemBean().getWho())
                        .setText(R.id.tv_date_data_item, AppUtils.getGankDate(item.getGankItemBean().getPublishedAt()))
                        .addOnClickListener(R.id.data_item_group);
                break;
            case MultiData.ITEM_SORT_LINE:
                String title = item.getName();
                helper.setText(R.id.tv_sort_line_item, title);
                TextView textView = helper.getView(R.id.tv_sort_line_item);
                switch (title) {
                    case Constants.TYPE_BENEFIT:
                        AppUtils.setTextViewLeftDrawableForHeader(textView, MaterialDesignIconic.Icon.gmi_mood);
                        break;
                    case Constants.TYPE_ANDROID:
                        AppUtils.setTextViewLeftDrawableForHeader(textView, MaterialDesignIconic.Icon.gmi_android);
                        break;
                    case Constants.TYPE_IOS:
                        AppUtils.setTextViewLeftDrawableForHeader(textView, MaterialDesignIconic.Icon.gmi_apple);
                        break;
                    case Constants.TYPE_APP:
                        AppUtils.setTextViewLeftDrawableForHeader(textView, MaterialDesignIconic.Icon.gmi_apps);
                        break;
                    case Constants.TYPE_REST_VIDEO:
                        AppUtils.setTextViewLeftDrawableForHeader(textView, MaterialDesignIconic.Icon.gmi_collection_video);
                        break;
                    case Constants.TYPE_EXPAND_RES:
                        AppUtils.setTextViewLeftDrawableForHeader(textView, FontAwesome.Icon.faw_location_arrow);
                        break;
                    default:
                        AppUtils.setTextViewLeftDrawableForHeader(textView, MaterialDesignIconic.Icon.gmi_more);
                        break;
                }
                break;
            case MultiData.ITEM_IMG:
                RatioImageView imageView = helper.getView(R.id.iv_recycler_item);
                ImgLoadUtils.loadAdapterUrl(mContext, item.getGankItemBean().getUrl(), imageView);
                break;
            default:
                break;
        }
    }

    public void updateList(List<MultiData> multiDatas) {
        if (!(multiDatas != null && !getData().containsAll(multiDatas))) {
            Toast.makeText(mContext, mContext.getString(R.string.recent_news_notice), Toast.LENGTH_SHORT).show();
        }
        setNewData(multiDatas);
    }
}
