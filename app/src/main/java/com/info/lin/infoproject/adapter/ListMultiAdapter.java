package com.info.lin.infoproject.adapter;

import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.info.lin.infoproject.R;
import com.info.lin.infoproject.data.net.MultiData;

import java.util.List;

/**
 * Created by lin on 2017/2/23.
 */

public class ListMultiAdapter extends BaseMultiItemQuickAdapter<MultiData, BaseViewHolder> {

    public ListMultiAdapter(List<MultiData> data) {
        super(data);
        addItemType(MultiData.ITEM_DATA, R.layout.recycler_item_data);
        addItemType(MultiData.ITEM_IMG, R.layout.recycler_item_img);
        addItemType(MultiData.ITEM_SORT_LINE, R.layout.recycler_item_sort_line);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiData item) {
        switch (helper.getItemViewType()) {
            case MultiData.ITEM_DATA:

                break;
            case MultiData.ITEM_SORT_LINE:

                break;
            case MultiData.ITEM_IMG :

                break;
            default:
                break;
        }
    }

    public void updateList(List<MultiData> multiDatas) {
        if (multiDatas != null && !getData().containsAll(multiDatas)) {
            setNewData(multiDatas);
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.recent_news_notice), Toast.LENGTH_SHORT).show();
        }
    }
}
