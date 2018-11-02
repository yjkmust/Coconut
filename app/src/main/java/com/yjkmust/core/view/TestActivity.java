package com.yjkmust.core.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.yjkmust.coconut.R;
import com.yjkmust.core.vm.WorkViewModel;
import com.yjkmust.lemon.base.BaseListActivity;

import java.util.List;

public class TestActivity extends BaseListActivity<WorkViewModel> {

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,TestActivity.class);
        return intent;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
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
        return null;
    }
}
