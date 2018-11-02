package com.yjkmust.network;



import com.yjkmust.config.URL;
import com.yjkmust.core.data.WorksListVo;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiService {
    @POST(URL.WORK_LIST)
    @FormUrlEncoded
    Flowable<WorksListVo> getWorkData(@Field("corrected") String corrected, @Field("rn") String rn);




}
