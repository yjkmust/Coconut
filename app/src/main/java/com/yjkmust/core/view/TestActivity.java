package com.yjkmust.core.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yjkmust.coconut.R;
import com.yjkmust.config.Constants;
import com.yjkmust.core.data.WorksListVo;
import com.yjkmust.core.vm.WorkViewModel;
import com.yjkmust.lemon.base.AbsLifecycleActivity;
import com.yjkmust.lemon.base.BaseListActivity;
import com.yjkmust.lemon.view.PagingLayout.PageLayout;

import java.util.List;

public class TestActivity extends BaseListActivity<WorkViewModel,WorksListVo.Works> {

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,TestActivity.class);
        return intent;

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        smartRefreshLayout = findViewById(R.id.smart_refresh);
        recyclerView = findViewById(R.id.recycler_view);
        rootView = findViewById(R.id.ll_root);
        customView = LayoutInflater.from(this)
                .inflate(R.layout.activity_test, null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        mViewModel.getWorkListData();
    }

    @Override
    protected void getNetData() {
        mViewModel.getWorkListData();
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver(Constants.EVENT_KEY_WORK_LIST, WorksListVo.class).observe(this, worksListVo -> {
            if (worksListVo != null) {
                lastId = worksListVo.data.content.get(worksListVo.data.content.size() - 1).tid;
                setData(worksListVo.data.content);
            }

        });
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getApplicationContext());
    }

    @Override
    protected RecyclerView.Adapter createAdater(List<WorksListVo.Works> list) {
        return new TestAdapter(list);
    }


    @Override
    protected Object getStateEventKey() {
        return "123";
    }

    class TestAdapter extends BaseQuickAdapter<WorksListVo.Works,BaseViewHolder>{

        public TestAdapter(@Nullable List<WorksListVo.Works> data) {
            super(R.layout.layout_test_item,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, WorksListVo.Works item) {
            Glide.with(mContext).load(item.avatar).into((ImageView) helper.getView(R.id.iv_test));
            helper.setText(R.id.txt_test,item.content);
        }
    }

}
