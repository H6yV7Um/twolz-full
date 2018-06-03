package com.twolz.qiyi.dc.retrofit;

import com.twolz.qiyi.common.dto.EBikeDeviceDto;
import com.twolz.qiyi.common.dto.EBikeRsDO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by liuwei
 * date 2017-07-03
 */
public interface LatestEbikesService {


    @GET("SpiritServiceApp/v1/receivedCache/latestEbikes")
    Call<EBikeRsDO<EBikeDeviceDto>> latestEbikes(@Query("ueSn") String ueSn);

}
