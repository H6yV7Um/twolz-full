package com.twolz.qiyi.api.web.controller;

import com.twolz.qiyi.common.core.ResultDO;
import com.twolz.qiyi.common.exception.BizException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

/**
 * 全局异常消息处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

//	@Autowired
//	private TipCacheService tipCacheService;

	@ResponseBody
    @ExceptionHandler(value = BizException.class)
    public ResultDO<?> jsonErrorHandler(HttpServletRequest req, BizException e) throws Exception {
    	ResultDO<String> rs = new ResultDO<>();
    	String resultCode=e.getErrorCode();
		String errorMessage=e.getErrorMsg();
		//非动态消息
		if(!e.isDynamic()){

			errorMessage="";//tipCacheService.getTipMsg(resultCode);

			if(null != e.getErrors() && e.getErrors().length > 0){
				MessageFormat messageFormat = new MessageFormat(errorMessage);
				errorMessage = messageFormat.format(e.getErrors());
			}
		}

    	rs.setErrorMessage(errorMessage);
    	rs.setResultCode(resultCode);
    	rs.setModule(null);
    	rs.setSuccess(false);
        return rs;
    }

}
