package com.yjkmust.core.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.yjkmust.coconut.R;
import com.yjkmust.config.Constants;
import com.yjkmust.core.data.WorksListVo;
import com.yjkmust.core.vm.WorkViewModel;
import com.yjkmust.lemon.base.AbsLifecycleActivity;
import com.yjkmust.lemon.base.BaseListActivity;

import java.util.List;

public class TestActivity extends BaseListActivity<WorkViewModel> {

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,TestActivity.class);
        return intent;

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        smartRefreshLayout = findViewById(R.id.smart_refresh);
        recyclerView = findViewById(R.id.recycler_view);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }
    @Override
    protected void getNetData() {
        super.getNetData();
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
        return null;
    }

    @Override
    protected RecyclerView.Adapter createAdater(List<Object> list) {
        return null;
    }


    @Override
    protected Object getStateEventKey() {
        return "123";
    }
}
