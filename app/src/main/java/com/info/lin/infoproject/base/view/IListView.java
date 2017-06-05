package com.info.lin.infoproject.base.view;

import java.util.List;

/**
 * Created by greedy on 2017/5/11.
 */

public interface IListView<T> extends IBaseView {

    void loadData(List<T> data);

    void loadError();
}
