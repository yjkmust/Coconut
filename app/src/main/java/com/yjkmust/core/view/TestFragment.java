package com.yjkmust.core.view;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yjkmust.coconut.R;
import com.yjkmust.config.Constants;
import com.yjkmust.core.data.WorksListVo;
import com.yjkmust.core.vm.WorkViewModel;
import com.yjkmust.lemon.base.BaseListFragment;

import java.util.List;

public class TestFragment extends BaseListFragment<WorkViewModel,WorksListVo.Works> {
    public static TestFragment newIntent(){
        return new TestFragment();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        mViewModel.getWorkListData();

    }

    @Override
    protected void getNetData() {
        super.getNetData();
        mViewModel.getWorkListData();
    }

    @Override
    protected void getMoreNetData() {
        super.getMoreNetData();
        mViewModel.getWorkMoreData();
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
        return new LinearLayoutManager(getActivity());
    }

    @Override
    protected RecyclerView.Adapter createAdater(List<WorksListVo.Works> list) {
        return new TestAdapter(list);
    }

    @Override
    protected Object getStateEventKey() {
        return "123";
    }

    @Override
    public int getContentResId() {
        return 0;
    }
    class TestAdapter extends BaseQuickAdapter<WorksListVo.Works,BaseViewHolder> {

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
