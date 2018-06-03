package com.twolz.qiyi.common.dto;


import lombok.Data;

/**
 * 
 * @author guocf
 * 电单车调用结果DTO
 * @param <T>
 */
@Data
public class EBikeRsDO<T> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer state;
	
	private String message;

	private T result;

	private T data;


}
