package com.twolz.qiyi.domain.mapper;

import com.twolz.qiyi.domain.model.TipInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TipInfoMapper extends Mapper<TipInfo> {

    List<TipInfo> selectBySearch(@Param("search")String search);

    TipInfo selectByTipCode(@Param("tipCode")String tipCode);
}