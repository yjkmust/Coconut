package com.yjkmust.lemon.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yjkmust.lemon.R;
import com.yjkmust.lemon.event.ItemData;

import java.util.Collection;
import java.util.List;

public abstract class BaseListFragment<T extends AbsViewModel> extends AbsLifecycleFragment<T> {
    protected SmartRefreshLayout smartRefreshLayout;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter adapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected ItemData oldItems;
    protected ItemData newItems;
    protected String lastId = null;
    protected boolean isLoadMore = true;
    protected boolean isLoading = true;
    protected boolean isRefresh = false;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        smartRefreshLayout = getViewById(R.id.smart_refresh);
        recyclerView = getViewById(R.id.recycler_view);
        oldItems = new ItemData();
        newItems = new ItemData();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                isLoadMore = true;
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                lastId = null;
                isRefresh = true;
                isLoadMore = false;
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        isLoadMore = false;
    }
    protected void setData(List<?> collection) {
        if (isLoadMore) {
            onLoadMoreSuccess(collection);
        } else {
            onRefreshSuccess(collection);
        }
    }
    protected void onRefreshSuccess(Collection<?> collection) {
        newItems.addAll(collection);
        oldItems.clear();
        oldItems.addAll(newItems);
        smartRefreshLayout.finishRefresh();
        adapter.notifyDataSetChanged();
        if (collection.size() < 20) {
          smartRefreshLayout.finishLoadMoreWithNoMoreData();
        } else {
//            mRecyclerView.loadMoreComplete(collection,false);
        }
        isRefresh = false;
    }

    protected void onLoadMoreSuccess(List<?> collection) {
        isLoading = true;
        isLoadMore = false;
        oldItems.addAll(collection);
        if (collection.size() < 20) {
            smartRefreshLayout.finishLoadMoreWithNoMoreData();
        } else {
//            mRecyclerView.loadMoreComplete(collection,false);
        }
        smartRefreshLayout.finishLoadMore();
        adapter.notifyDataSetChanged();
    }

    protected abstract RecyclerView.LayoutManager createLayoutManager();
    protected abstract RecyclerView.Adapter createAdater();

}
