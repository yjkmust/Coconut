package com.yjkmust.lemon.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListActivity<T extends AbsViewModel,V>  extends AbsLifecycleActivity<T> {
    protected SmartRefreshLayout smartRefreshLayout;
    protected RecyclerView.Adapter adapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected String lastId = null;
    protected boolean isLoadMore = false;
    protected boolean isLoading = true;
    protected boolean isRefresh = false;
    protected boolean firstLoad = true;
    protected List<V> listOld = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = createAdater(listOld);
        layoutManager = createLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                lastId = null;
                isRefresh = true;
                isLoadMore = false;
                getNetData();
            }
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                isLoadMore = true;
                getMoreNetData();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_list;
    }


    protected void setData(List<V> collection) {
        if (isLoadMore) {
            onLoadMoreSuccess(collection);
        } else {
            onRefreshSuccess(collection);
        }

    }

    protected void onRefreshSuccess(List<V> collection) {
        listOld.clear();
        listOld.addAll(collection);
        smartRefreshLayout.finishRefresh();
        adapter.notifyDataSetChanged();
        if (collection.size() < 20) {
            smartRefreshLayout.finishLoadMoreWithNoMoreData();
        } else {
//            mRecyclerView.loadMoreComplete(collection,false);
        }
        isRefresh = false;
    }

    protected void onLoadMoreSuccess(List<V> collection) {
        isLoading = true;
        isLoadMore = false;
        listOld.addAll(collection);
        if (collection.size() < 20) {
            smartRefreshLayout.finishLoadMoreWithNoMoreData();
        } else {
//            mRecyclerView.loadMoreComplete(collection,false);
        }
        smartRefreshLayout.finishLoadMore();
        adapter.notifyDataSetChanged();
    }



    protected abstract RecyclerView.LayoutManager createLayoutManager() ;

    protected abstract RecyclerView.Adapter createAdater(List<V> list);
}
