package com.twolz.qiyi.dc.retrofit;

import com.twolz.qiyi.dc.dto.rokyinfo.EbikeListProtos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by liuwei
 * date 2017-07-04
 */
public interface ReceivedCacheService {


    @GET("SpiritServiceApp/v1/receivedCache/ebikes")
    Call<EbikeListProtos.EbikeList> receivedCache(@Query("maxCount") String maxCount);
}
