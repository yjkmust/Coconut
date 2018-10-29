package com.yjkmust.lemon.base;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.yjkmust.lemon.util.Tutil;


public abstract class AbsLifecycleActivity<T extends AbsViewModel> extends BaseActivity {

    protected T mViewModel;

    public AbsLifecycleActivity() {

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mViewModel = VMProviders(this, (Class<T>) Tutil.getInstance(this, 0));
        dataObserver();
    }


    protected <T extends ViewModel> T VMProviders(AppCompatActivity fragment, @NonNull Class modelClass) {
        return (T) ViewModelProviders.of(fragment).get(modelClass);

    }

    protected void dataObserver() {

    }

    @Override
    protected void onStateRefresh() {
        showLoading();
    }

    protected void showError() {
        statusLayoutManager.showEmptyLayout();
    }
    protected void showEmpty(){
        statusLayoutManager.showEmptyLayout();
    }


    protected void showSuccess() {
        statusLayoutManager.showSuccessLayout();
    }

    protected void showLoading() {
        statusLayoutManager.showLoadingLayout();
    }




}
