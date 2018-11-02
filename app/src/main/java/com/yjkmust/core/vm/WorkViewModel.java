package com.yjkmust.core.vm;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.yjkmust.core.data.WorksListVo;
import com.yjkmust.core.source.WorkRepository;
import com.yjkmust.lemon.base.AbsViewModel;

public class WorkViewModel extends AbsViewModel<WorkRepository> {
    private MutableLiveData<WorksListVo> mWorkMoreData;

    public WorkViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<WorksListVo> getWorkMoreData() {
        if (mWorkMoreData == null) {
            mWorkMoreData = new MutableLiveData<>();
        }
        return mWorkMoreData;
    }
    public void getWorkListData() {

        mRepository.loadWorkData("80","20");
    }

}
