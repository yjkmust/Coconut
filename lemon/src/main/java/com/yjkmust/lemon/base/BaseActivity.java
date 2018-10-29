package com.yjkmust.lemon.base;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yjkmust.lemon.R;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public abstract class BaseActivity extends AppCompatActivity {

    protected StatusLayoutManager statusLayoutManager;
    protected RecyclerView recyclerView;

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

    }
    /**
     *
     */
    protected  void onStateRefresh(){

    }


    protected void initStatusBar() {

    }



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
     * 初始化toolbar
     */
    protected void initToolBar() {
        //doSomething
    }


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
    }
    protected void onEmptyClick(){}
    protected void onErrorClick(){}
    protected void onCustomerClick(){}



}
