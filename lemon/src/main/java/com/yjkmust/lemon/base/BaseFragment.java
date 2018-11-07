package com.yjkmust.lemon.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yjkmust.lemon.R;
import com.yjkmust.lemon.view.PagingLayout.PageLayout;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;


/**
 * @author：tqzhang on 18/3/12 19:25
 */
public abstract class BaseFragment extends Fragment {
    protected FragmentActivity activity;
    protected View rootView;
    protected View customView;
    protected PageLayout mPageLayout;
    protected boolean firstLoad = true;

    protected boolean mIsFirstVisible = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        customView = inflater.inflate(getLayoutResId(), null, false);
        initView(state);
        return customView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean isVis = isHidden() || getUserVisibleHint();
        initStatusLayout();
        if (isVis && mIsFirstVisible) {
            lazyLoad();
            mIsFirstVisible = false;
        }

    }


    /**
     * @return
     */
    public abstract int getLayoutResId();

    public abstract int getContentResId();

    /**
     * 初始化views
     *
     * @param state
     */
    public abstract void initView(Bundle state);




    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    /**
     * 当界面可见时的操作
     */
    protected void onVisible() {
        if (mIsFirstVisible && isResumed()) {
            lazyLoad();
            mIsFirstVisible = false;
        }
    }
    protected void initStatusLayout(){
        mPageLayout = new PageLayout.Builder(getContext())
                .initPage(rootView)
                .setCustomView(customView)
                .setErrorDrawable(R.drawable.pic_error)
                .setEmptyDrawable(R.drawable.pic_empty)
                .setOnRetryListener(new PageLayout.OnRetryClickListener() {
                    @Override
                    public void onRetry() {
                        getNetData();
                    }
                })
                .create();
    }
    /**
     * 数据懒加载
     */
    protected void lazyLoad() {

    }

    /**
     * 当界面不可见时的操作
     */
    protected void onInVisible() {

    }
    protected void onEmptyClick(){}
    protected void onErrorClick(){}
    protected void onCustomerClick(){}


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.activity = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (FragmentActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }
    protected void getNetData(){}
    protected void getMoreNetData(){}

    @SuppressWarnings("unchecked")
    protected <T extends View> T getViewById(int id) {
        return (T) customView.findViewById(id);
    }


}
