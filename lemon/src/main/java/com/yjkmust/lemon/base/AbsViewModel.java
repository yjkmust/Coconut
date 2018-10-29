package com.yjkmust.lemon.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.yjkmust.lemon.util.Tutil;


public class AbsViewModel<T extends AbsRepository> extends AndroidViewModel {


    public T mRepository;

    public AbsViewModel(@NonNull Application application) {
        super(application);
        mRepository = Tutil.getNewInstance(this, 0);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRepository != null) {
            mRepository.unDisposable();
        }
    }

}
