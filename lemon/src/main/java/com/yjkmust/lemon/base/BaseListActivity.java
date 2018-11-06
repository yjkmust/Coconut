package com.yjkmust.lemon.base;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yjkmust.lemon.R;
import com.yjkmust.lemon.event.ItemData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseListActivity<T extends AbsViewModel,V>  extends AbsLifecycleActivity<T> {

    protected SmartRefreshLayout smartRefreshLayout;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter adapter;
    protected RecyclerView.LayoutManager layoutManager;
//    protected ItemData oldItems;
//    protected ItemData newItems;
    protected String lastId = null;
    protected boolean isLoadMore = false;
    protected boolean isLoading = true;
    protected boolean isRefresh = false;
    protected List<V> listOld = new ArrayList<>();
    protected List<V> listNew = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        oldItems = new ItemData();
//        newItems = new ItemData();
        adapter = createAdater(listOld);
        layoutManager = createLayoutManager();
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
//    protected void onRefreshSuccess(Collection<?> collection) {
//        newItems.addAll(collection);
//        oldItems.clear();
//        oldItems.addAll(newItems);
//        smartRefreshLayout.finishRefresh();
//        adapter.notifyDataSetChanged();
//        if (collection.size() < 20) {
//            smartRefreshLayout.finishLoadMoreWithNoMoreData();
//        } else {
////            mRecyclerView.loadMoreComplete(collection,false);
//        }
//        isRefresh = false;
//    }
//
//    protected void onLoadMoreSuccess(List<?> collection) {
//        isLoading = true;
//        isLoadMore = false;
//        oldItems.addAll(collection);
//        if (collection.size() < 20) {
//            smartRefreshLayout.finishLoadMoreWithNoMoreData();
//        } else {
////            mRecyclerView.loadMoreComplete(collection,false);
//        }
//        smartRefreshLayout.finishLoadMore();
//        adapter.notifyDataSetChanged();
//    }

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
