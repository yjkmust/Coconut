package com.yjkmust.lemon.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yjkmust.lemon.R;
import com.yjkmust.lemon.view.PagingLayout.PageLayout;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public abstract class BaseActivity extends AppCompatActivity {
    protected boolean firstLoad = true;
    protected View rootView;
    protected View customView;
    protected PageLayout mPageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏
        initStatusBar();
        initToolBar();
        //设置布局内容
        setContentView(getLayoutId());
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initStatusLayout();
        if (firstLoad){
            mPageLayout.showLoading();
            lazyLoad();
            firstLoad = false;
        }

    }



    protected void initStatusBar() {

    }
    /**
     * 初始化toolbar
     */
    protected void initToolBar() {
        //doSomething
    }
    protected void lazyLoad(){}

    protected void getNetData(){}

    protected void getMoreNetData(){}




    /**
     * 设置布局layout
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化views
     *
     * @param savedInstanceState
     */
    public abstract void initViews(Bundle savedInstanceState);



    /**
     * 显示进度条
     */
    public void showProgressBar() {
    }

    /**
     * 隐藏进度条
     */
    public void hideProgressBar() {

    }
    protected void initStatusLayout(){
        mPageLayout = new PageLayout.Builder(this)
                .initPage(rootView)
                .setCustomView(customView)
                .setEmptyDrawable(R.drawable.pic_empty)
                .setErrorDrawable(R.drawable.pic_error)
                .setOnRetryListener(new PageLayout.OnRetryClickListener() {
                    @Override
                    public void onRetry() {
                        getNetData();
                    }
                })
//                .setLoadingText("Loading")
                .create();
    }






}
