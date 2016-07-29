/**
 * 
 */
package com.lvmama.vst.hhs.common.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.lvmama.vst.hhs.common.exception.StampBizException;
import com.lvmama.vst.hhs.common.exception.StampErrorCodes;

/**
 * @author baolm
 *
 */
@Aspect
@Order(2)
@Component
public class HhsRespHandlerAspect {

	private static final Logger logger = LoggerFactory.getLogger(HhsRespHandlerAspect.class);

	private static final String SET_CODE_METHOD = "setCode";
	private static final String SET_MSG_METHOD = "setMsg";

	@Around("within(@org.springframework.stereotype.Service *) && @annotation(handler)")
	public Object handlerResp(ProceedingJoinPoint jp, HhsRespHandler handler) {

		Object[] args = jp.getArgs();

		String argsJson = new Gson().toJson(args);

		Signature signature = jp.getSignature();

//		logger.info("call {}, args {}, by handler {}", signature, argsJson, handler);

		Class<?> returnType = ((MethodSignature) signature).getReturnType();

//		logger.info("this:{}, target:{}, method:{}", jp.getThis(), jp.getTarget(), ((MethodSignature) signature).getMethod().getName());

		Object result = null;

		long start = System.currentTimeMillis();
		try {

			result = jp.proceed();
			// set result code success.
			setProperty(result, SET_CODE_METHOD, StampErrorCodes.SUCCESS);

		} catch (StampBizException e) { // handle business error.

			result = getInstance(returnType);
			
			// ignore not extends BaseResponse
			boolean isBaseResp = result instanceof BaseResponse;
			if(!isBaseResp)
				throw e;
			
			setProperty(result, SET_CODE_METHOD, e.getErrorCode());
			setProperty(result, SET_MSG_METHOD, e.getErrorMsg());

		} catch (Throwable e) {

			logger.error("#monitor: args=" + argsJson, e); // TODO monitor hear

			result = getInstance(returnType);
			
			// ignore not extends BaseResponse
			boolean isBaseResp = result instanceof BaseResponse;
			if(!isBaseResp)
				throw new RuntimeException(e);
			
			setProperty(result, SET_CODE_METHOD, StampErrorCodes.E_SYS_9999);
			setProperty(result, SET_MSG_METHOD, e.getMessage());
		}

		logger.info("call {}, args {}, result {}, use time {} ms", signature, argsJson, result.toString(),
				(System.currentTimeMillis() - start));
		return result;
	}

	private void setProperty(Object obj, String propName, Object propValue) {
		if (obj == null)
			return;
		if (obj instanceof BaseResponse) {
			try {
				Method method = obj.getClass().getMethod(propName, String.class);
				method.invoke(obj, propValue);
			} catch (NoSuchMethodException e) {
				logger.error("obj=" + obj, e);
			} catch (SecurityException e) {
				logger.error("obj=" + obj, e);
			} catch (IllegalAccessException e) {
				logger.error("obj=" + obj, e);
			} catch (IllegalArgumentException e) {
				logger.error("obj=" + obj, e);
			} catch (InvocationTargetException e) {
				logger.error("obj=" + obj, e);
			}
		}
	}

	private Object getInstance(Class<?> targetClass) {
		Object obj = null;
		try {
			obj = targetClass.newInstance();
		} catch (InstantiationException e) {
			logger.error("targetClass=" + targetClass, e);
		} catch (IllegalAccessException e) {
			logger.error("targetClass=" + targetClass, e);
		}
		return obj;
	}

}
