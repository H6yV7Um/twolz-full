package com.twolz.qiyi.dc.mongo;

import com.twolz.qiyi.rmi.dto.TestDto;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by liuwei
 * date 2017-06-06
 */
public interface TestMsgRepository extends MongoRepository<TestDto, String> {

}
