package com.twolz.qiyi.domain.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.twolz.qiyi.common.core.SysCode;
import com.twolz.qiyi.common.exception.BizException;
import com.twolz.qiyi.domain.mapper.TipInfoMapper;
import com.twolz.qiyi.domain.model.TipInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * 
 * @author liuwei
 *
 */
@Service
public class TipInfoService {

    @Autowired
    private TipInfoMapper tipInfoMapper;


	public PageInfo<TipInfo> findByPage(Integer page, Integer rows, String searchVal){
		//分页
		if (!StringUtils.isEmpty(page) && !StringUtils.isEmpty(rows)) {
			PageHelper.startPage(page, rows);
		}

		List<TipInfo> list = tipInfoMapper.selectBySearch(searchVal);
		PageInfo<TipInfo> pageInfo = new PageInfo<TipInfo>(list);
		return pageInfo;
	}

	public Integer insertIt(TipInfo record) throws BizException {
		TipInfo lastInfo=this.getItByCode(record.getTipCode());
		if(lastInfo!=null){
			throw new BizException(SysCode.TIP_CODE_EXIST);
		}
		int row = tipInfoMapper.insert(record);
		if (row > 0) {
			int id=record.getId();
			return id;
		}

		throw new BizException("10001");
	}


	public boolean updateIt(TipInfo record) throws BizException {
		int row = tipInfoMapper.updateByPrimaryKey(record);
		if(row>0){
			return true;
		}
		return false;
	}

	public void deleteIt(Integer id) throws BizException {
		tipInfoMapper.deleteByPrimaryKey(id);
	}

	public TipInfo getIt(Integer id) {
		return tipInfoMapper.selectByPrimaryKey(id);
	}


	public TipInfo getItByCode(String tipCode) {
		return tipInfoMapper.selectByTipCode(tipCode);
	}

	/**
	 * 获取所有的消息提示信息
	 * @return
	 */
	public List<TipInfo> getAll() {
		List<TipInfo> list= tipInfoMapper.selectAll();
		return list;
	}

}
