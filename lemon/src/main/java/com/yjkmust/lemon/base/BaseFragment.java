package com.yjkmust.lemon.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yjkmust.lemon.R;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;


/**
 * @author：tqzhang on 18/3/12 19:25
 */
public abstract class BaseFragment extends Fragment {
    private View rootView;

    protected FragmentActivity activity;

    protected StatusLayoutManager statusLayoutManager;

    protected RecyclerView recyclerView;

    protected boolean mIsFirstVisible = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        rootView = inflater.inflate(getLayoutResId(), null, false);
        statusLayoutManager = new StatusLayoutManager.Builder(recyclerView)

                // 设置默认加载中布局的提示文本
                .setDefaultLoadingText("l拼命加载中...")

                // 设置默认空数据布局的提示文本
                .setDefaultEmptyText("空白了，哈哈哈哈")
                // 设置默认空数据布局的图片
                .setDefaultEmptyImg(R.drawable.empty_server)
                // 设置默认空数据布局重试按钮的文本
                .setDefaultEmptyClickViewText("retry")
                // 设置默认空数据布局重试按钮的文本颜色
                .setDefaultEmptyClickViewTextColor(getResources().getColor(R.color.colorAccent))
                // 设置默认空数据布局重试按钮是否显示
                .setDefaultEmptyClickViewVisible(false)

                // 设置默认出错布局的提示文本
                .setDefaultErrorText(R.string.app_name)
                // 设置默认出错布局的图片
                .setDefaultErrorImg(R.drawable.empty_network)
                // 设置默认出错布局重试按钮的文本
                .setDefaultErrorClickViewText("重试一波")
                // 设置默认出错布局重试按钮的文本颜色
                .setDefaultErrorClickViewTextColor(getResources().getColor(R.color.colorPrimaryDark))
                // 设置默认出错布局重试按钮是否显示
                .setDefaultErrorClickViewVisible(true)

                // 设置默认布局背景，包括加载中、空数据和出错布局
                .setDefaultLayoutsBackgroundColor(Color.WHITE)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        onEmptyClick();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        onErrorClick();

                    }

                    @Override
                    public void onCustomerChildClick(View view) {
                        onCustomerClick();

                    }
                })
                .build();
        initView(state);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean isVis = isHidden() || getUserVisibleHint();
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

    /**
     *
     */
    protected abstract void onStateRefresh();


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

    @SuppressWarnings("unchecked")
    protected <T extends View> T getViewById(int id) {
        return (T) rootView.findViewById(id);
    }


}
