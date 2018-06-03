package com.twolz.qiyi.web.service;

import com.twolz.qiyi.domain.mapper.OperatLogMapper;
import com.twolz.qiyi.domain.model.OperatLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuwei
 * @date 2018-06-03
 */
@Service
public class OperatLogService {

    @Autowired
    OperatLogMapper operatLogMapper;

    public int insertSelective(OperatLog record) {
        return operatLogMapper.insertSelective(record);
    }
}
