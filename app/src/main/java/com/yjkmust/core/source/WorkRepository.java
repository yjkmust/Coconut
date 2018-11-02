package com.yjkmust.core.source;

import com.yjkmust.config.Constants;
import com.yjkmust.core.data.WorksListVo;
import com.yjkmust.lemon.base.AbsRepository;
import com.yjkmust.lemon.event.StateConstants;
import com.yjkmust.lemon.http.RxSchedulers;
import com.yjkmust.network.RxSubscriber;

import io.reactivex.Flowable;

import static com.yjkmust.lemon.util.Tutil.checkNotNull;

public class WorkRepository extends BaseRepository {

    public void loadWorkData(String corrected, String rn) {
       addDisposable(apiService.getWorkData(corrected,rn)
       .compose(RxSchedulers.<WorksListVo>io_main())
       .subscribe(new RxSubscriber<WorksListVo>() {
           @Override
           protected void onNoNetWork() {
               super.onNoNetWork();
           }

           @Override
           public void onSuccess(WorksListVo worksListVo) {
               sendData(Constants.EVENT_KEY_WORK_LIST, worksListVo);
           }

           @Override
           public void onFailure(String msg) {
               showPageState(Constants.EVENT_KEY_WORK_LIST_STATE, StateConstants.ERROR_STATE);


           }

       }));


    }

}
