package com.yjkmust.lemon.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.yjkmust.lemon.event.LiveBus;
import com.yjkmust.lemon.event.StateConstants;
import com.yjkmust.lemon.util.Tutil;

import java.util.ArrayList;
import java.util.List;


public abstract class AbsLifecycleActivity<T extends AbsViewModel> extends BaseActivity {

    protected T mViewModel;

    protected Object mStateEventKey;

    protected String mStateEventTag;

    private List<Object> events = new ArrayList<>();

    public AbsLifecycleActivity() {

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mViewModel = VMProviders(this, (Class<T>) Tutil.getInstance(this, 0));
        if (null != mViewModel) {
            dataObserver();
            mStateEventKey = getStateEventKey();
            mStateEventTag = getStateEventTag();
            events.add(new StringBuilder((String) mStateEventKey).append(mStateEventTag).toString());
            LiveBus.getDefault().subscribe(mStateEventKey, mStateEventTag).observe(this, observer);
        }
    }
    protected String getStateEventTag() {
        return "";
    }



    protected abstract Object getStateEventKey();


    protected <T extends ViewModel> T VMProviders(AppCompatActivity fragment, @NonNull Class modelClass) {
        return (T) ViewModelProviders.of(fragment).get(modelClass);

    }

    protected void dataObserver() {

    }

    protected <T> MutableLiveData<T> registerObserver(Object eventKey, Class<T> tClass) {

        return registerObserver(eventKey, null, tClass);
    }

    protected <T> MutableLiveData<T> registerObserver(Object eventKey, String tag, Class<T> tClass) {
        String event;
        if (TextUtils.isEmpty(tag)) {
            event = (String) eventKey;
        } else {
            event = eventKey + tag;
        }
        events.add(event);
        return LiveBus.getDefault().subscribe(eventKey, tag, tClass);
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




    /**
     * 监听页面状态
     */
    protected Observer observer = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String state) {
            if (!TextUtils.isEmpty(state)) {
                if (StateConstants.ERROR_STATE.equals(state)) {
                    showError();
                } else if (StateConstants.EMPTY_STATE.equals(state)) {
                    showEmpty();
                } else if (StateConstants.LOADING_STATE.equals(state)) {
                    showLoading();
                } else if (StateConstants.SUCCESS_STATE.equals(state)) {
                    showSuccess();
                }
            }
        }
    };


}
