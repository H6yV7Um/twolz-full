package com.twolz.qiyi.dc.retrofit;

import com.twolz.qiyi.dc.dto.rokyinfo.DevicesMsg;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;


/**
 * Created by liuwei
 * date 2017-07-03
 */
public interface RokyInfoService {

    @FormUrlEncoded
    @POST("SpiritServiceApp/v1/send/singleEbike")
    Call<ResponseBody> singleEbike(@Field("ueSn") String ueSn, @Field("data") String data);

    @GET("SpiritServiceApp/v1/devices")
    Call<DevicesMsg> devices(@Query("ue_sn_array") String ueSnArray);


    @GET("SpiritServiceApp/stock/ccus")
    Call<DevicesMsg> ccus(@Query("maxId") String maxId);

}
