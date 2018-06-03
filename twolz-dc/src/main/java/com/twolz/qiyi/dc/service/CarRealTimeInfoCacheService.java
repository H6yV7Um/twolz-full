package com.twolz.qiyi.dc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twolz.qiyi.common.constant.CarConst;
import com.twolz.qiyi.common.dto.CarRealTimeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author liuwei
 * 车辆实时信息缓存
 */
@Slf4j
@Service
public class CarRealTimeInfoCacheService {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public CarRealTimeDto getCar(String boxNo){
		return getCarCache(boxNo,CarConst.CAR_INFO);
	}

	public CarRealTimeDto getLastCar(String boxNo){
		return getCarCache(boxNo,CarConst.LAST_CAR_INFO);
	}

	public CarRealTimeDto getCarCache(String boxNo,String prefix){
		String key=boxNo;
		ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
		String body=valueOperations.get(prefix+key);
		if(body==null || body.isEmpty()){
			return null;
		}
		CarRealTimeDto userObj=null;
		ObjectMapper objectMapper=new ObjectMapper();
		try {
			if(body!=null &&!body.isEmpty()){
				userObj= objectMapper.readValue(body, CarRealTimeDto.class);
			}
		} catch (Exception e) {
			log.error("解析JSON，jsonInfo:"+body,e);
			userObj = null;
		}
		return userObj;
	}

	
	/**
     * 保存键值对数据到Cache
     */
    public void putObject(String id,Object userObj){
    	ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
		try {
			String body=objectMapper.writeValueAsString(userObj);
			valueOperations.set(CarConst.CAR_INFO+id,body,15, TimeUnit.MINUTES);
		} catch (JsonProcessingException e) {
			log.error("对象转换JSON串失败",e);
		}
	}
    
	/**
	 * 保存键值对数据到Cache-Forever
	 */
	public void putLastObject(String id,Object userObj){
		ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
		try {
			String body=objectMapper.writeValueAsString(userObj);
			valueOperations.set(CarConst.LAST_CAR_INFO+id,body,15);
		} catch (JsonProcessingException e) {
			log.error("对象转换JSON串失败",e);
		}
	}
    
}
