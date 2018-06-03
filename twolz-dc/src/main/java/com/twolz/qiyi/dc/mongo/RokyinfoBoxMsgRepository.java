package com.twolz.qiyi.dc.mongo;

import com.twolz.qiyi.dc.dto.rokyinfo.RokyinfoBoxRptMessage;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by liuwei
 * date 2017-06-06
 */
public interface RokyinfoBoxMsgRepository extends MongoRepository<RokyinfoBoxRptMessage, String> {

    @Query("{\"ebike.ccu.SN\":?0,\"ebike.reportTime\":{$gte:?1,$lte:?2}}")
    List<RokyinfoBoxRptMessage> queryBySnAndRptTimeBetween(String sn, String startTime, String endTime, Sort sort);

    @Query("{\"ebike.ccu.SN\":?0,\"ebike.reportTime\":{$gte:?1,$lte:?2}}")
    List<RokyinfoBoxRptMessage> queryBySnAndRptTimeBetween(String sn, String startTime, String endTime);

    @Query("{\"ebike.ccu.SN\":?0,\"ebike.lbs.lon\":{$gt:0},\"ebike.lbs.lat\":{$gt:0}}")
    RokyinfoBoxRptMessage queryLastBySnAndRptTimeBetween(String sn, Sort sort);
}
