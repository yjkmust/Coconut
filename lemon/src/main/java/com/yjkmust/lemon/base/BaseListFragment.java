package com.yjkmust.lemon.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yjkmust.lemon.R;
import com.yjkmust.lemon.event.ItemData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public abstract class BaseListFragment<T extends AbsViewModel,V> extends AbsLifecycleFragment<T> {
    protected SmartRefreshLayout smartRefreshLayout;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter adapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected String lastId = null;
    protected boolean isLoadMore = true;
    protected boolean isLoading = true;
    protected boolean isRefresh = false;
    protected List<V> list = new ArrayList<>();

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        smartRefreshLayout = getViewById(R.id.smart_refresh);
        recyclerView = getViewById(R.id.recycler_view);
        rootView = getViewById(R.id.ll_root);
        layoutManager = createLayoutManager();
        adapter = createAdater(list);
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
        initSmartRefreshLayout();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        isLoadMore = false;
        mPageLayout.showLoading();
    }
    protected void setData(List<V> collection) {
        if (isLoadMore) {
            onLoadMoreSuccess(collection);
            mPageLayout.hide();
        } else {
            onRefreshSuccess(collection);
            mPageLayout.hide();
        }
    }
    protected void onRefreshSuccess(List<V> collection) {
        list.clear();
        list.addAll(collection);
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
        isLoadMore = false;
        list.addAll(collection);
        if (collection.size() < 20) {
            smartRefreshLayout.finishLoadMoreWithNoMoreData();
        } else {
//            mRecyclerView.loadMoreComplete(collection,false);
        }
        smartRefreshLayout.finishLoadMore();
        adapter.notifyDataSetChanged();
    }
    protected void initSmartRefreshLayout(){
        ClassicsHeader header = (ClassicsHeader) smartRefreshLayout.getRefreshHeader();
        ClassicsFooter footer  = (ClassicsFooter) smartRefreshLayout.getRefreshFooter();
        int delta = new Random().nextInt(7 * 24 * 60 * 60 * 1000);
        header.setLastUpdateTime(new Date(System.currentTimeMillis()-delta));
        header.setTimeFormat(new SimpleDateFormat("更新于 MM-dd HH:mm", Locale.CHINA));
        header.REFRESH_HEADER_PULLING = "下拉可以刷新";
        header.REFRESH_HEADER_REFRESHING = "正在刷新...";
        header.REFRESH_HEADER_LOADING = "正在加载...";
        header.REFRESH_HEADER_RELEASE = "释放立即刷新";
        header.REFRESH_HEADER_FINISH = "刷新完成";
        header.REFRESH_HEADER_FAILED = "刷新失败";
        header.REFRESH_HEADER_UPDATE = "上次更新 M-d HH:mm";


        footer.REFRESH_FOOTER_PULLING = "上拉加载更多";
        footer.REFRESH_FOOTER_RELEASE = "释放立即加载";
        footer.REFRESH_FOOTER_LOADING = "正在加载...";
        footer.REFRESH_FOOTER_REFRESHING = "正在刷新...";
        footer.REFRESH_FOOTER_FINISH = "加载完成";
        footer.REFRESH_FOOTER_FAILED = "加载失败";
        footer.REFRESH_FOOTER_NOTHING = "没有更多数据了";
    }

    protected abstract RecyclerView.LayoutManager createLayoutManager();
    protected abstract RecyclerView.Adapter createAdater(List<V> list);

}
