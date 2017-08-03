package com.info.lin.infoproject.adapter;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.info.lin.infoproject.R;

import java.util.List;

/**
 * Created by lin on 2017/2/28.
 */
public class DrawerAdapter extends BaseQuickAdapter<DrawerAdapter.Item, BaseViewHolder> {

    public DrawerAdapter(int layoutResId, List<Item> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Item item) {
        helper.setImageResource(R.id.iv_navigation_item, item.img)
                .setText(R.id.title_navigation_item, item.title)
                .addOnClickListener(R.id.group_rv_navigation_item);
    }

    public enum Item {
        PERSONAL(R.mipmap.ic_personal, R.string.personal),
        SWITCH_SKIN(R.mipmap.ic_skin, R.string.switch_skin),
        SWITCH_MODE(R.mipmap.ic_mode, R.string.switch_mode);

        public
        @DrawableRes
        int img;
        public
        @StringRes
        int title;

        Item(int img, int title) {
            this.img = img;
            this.title = title;
        }
    }
}
