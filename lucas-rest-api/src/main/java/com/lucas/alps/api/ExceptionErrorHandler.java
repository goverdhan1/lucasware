/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.alps.api;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.exception.Level;
import com.lucas.exception.LucasBusinessException;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.util.HttpStatusCodes;
import com.lucas.services.util.UniqueKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import javax.inject.Inject;
/**
 * This class deals with exception 
 * @author Anand
 *
 */

@ControllerAdvice
public class ExceptionErrorHandler { 

	private static final Logger logger = LoggerFactory.getLogger(ExceptionErrorHandler.class);
	private final UniqueKeyService uniqueKeyService;
	private final static String FAILURE = "failure";
	private final MessageSource messageSource;
	
    @Inject
	public ExceptionErrorHandler(UniqueKeyService uniqueKeyService, MessageSource messageSource) {
		this.uniqueKeyService = uniqueKeyService;
		this.messageSource = messageSource;
	}
    
    @ExceptionHandler( {  MissingServletRequestParameterException.class } )
    public @ResponseBody ApiResponse<MissingServletRequestParameterException> getMissingServletRequestParameterException(final MissingServletRequestParameterException e) {

    	logger.debug(" ExceptionErrorHandler [ getMissingServletRequestParameterException ] ");

    	ApiResponse<MissingServletRequestParameterException> apiResponse = new ApiResponse<MissingServletRequestParameterException>();
		apiResponse.setUniqueKey(uniqueKeyService.generateUniqueId());
		apiResponse.setCode(HttpStatusCodes.ERROR_400);
    	apiResponse.setMessage(e.getMessage());
    	apiResponse.setStatus(FAILURE);
    	
    	logError(uniqueKeyService.generateUniqueId(), e);

	    return apiResponse;
    }
    
	@ExceptionHandler( { HttpRequestMethodNotSupportedException.class } )
	public @ResponseBody ApiResponse<HttpRequestMethodNotSupportedException> getHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {

		logger.debug(" ExceptionErrorHandler [ getHttpRequestMethodNotSupportedException ] ");

		ApiResponse<HttpRequestMethodNotSupportedException> apiResponse = new ApiResponse<HttpRequestMethodNotSupportedException>();
		apiResponse.setUniqueKey(uniqueKeyService.generateUniqueId());
		apiResponse.setCode(HttpStatusCodes.ERROR_405);
    	apiResponse.setMessage(e.getMessage());
    	apiResponse.setStatus(FAILURE);

		logError(uniqueKeyService.generateUniqueId(), e);
		
		return apiResponse;
	}
    
	@ExceptionHandler( { ConversionNotSupportedException.class } )
	public @ResponseBody ApiResponse<ConversionNotSupportedException> getConversionNotSupportedException(final ConversionNotSupportedException e) {

		logger.debug(" ExceptionErrorHandler [ getConversionNotSupportedException ] ");

		ApiResponse<ConversionNotSupportedException> apiResponse = new ApiResponse<ConversionNotSupportedException>();
		apiResponse.setUniqueKey(uniqueKeyService.generateUniqueId());
		apiResponse.setCode(HttpStatusCodes.ERROR_500);
    	apiResponse.setMessage(e.getMessage());
    	apiResponse.setStatus(FAILURE);
    	
		logError(uniqueKeyService.generateUniqueId(), e);
		
		return apiResponse;
	}
	
	
	
	@ExceptionHandler( { NoSuchRequestHandlingMethodException.class } )
	public @ResponseBody ApiResponse<NoSuchRequestHandlingMethodException> getNoSuchRequestHandlingMethodException(final NoSuchRequestHandlingMethodException e) {

		logger.debug(" ExceptionErrorHandler [ getNoSuchRequestHandlingMethodException ] ");

		ApiResponse<NoSuchRequestHandlingMethodException> apiResponse = new ApiResponse<NoSuchRequestHandlingMethodException>();
		apiResponse.setUniqueKey(uniqueKeyService.generateUniqueId());
		apiResponse.setCode(HttpStatusCodes.ERROR_404);
    	apiResponse.setMessage(e.getMessage());
    	apiResponse.setStatus(FAILURE);
    	
		logError(uniqueKeyService.generateUniqueId(), e);
		
		return apiResponse;
	}
	
	@ExceptionHandler( { TypeMismatchException.class } )
	public @ResponseBody ApiResponse<TypeMismatchException> getTypeMismatchException(final TypeMismatchException e) {

		logger.debug(" ExceptionErrorHandler [ getTypeMismatchException ] ");

		ApiResponse<TypeMismatchException> apiResponse = new ApiResponse<TypeMismatchException>();
		apiResponse.setUniqueKey(uniqueKeyService.generateUniqueId());
		apiResponse.setCode(HttpStatusCodes.ERROR_400);
    	apiResponse.setMessage(e.getMessage());
    	apiResponse.setStatus(FAILURE);
    	
		logError(uniqueKeyService.generateUniqueId(), e);
		
		return apiResponse;
	}
	
	@ExceptionHandler( { HttpMediaTypeNotAcceptableException.class } )
	public @ResponseBody ApiResponse<HttpMediaTypeNotAcceptableException> getHttpMediaTypeNotAcceptableException(final HttpMediaTypeNotAcceptableException e) {

		logger.debug(" ExceptionErrorHandler [ getHttpMediaTypeNotAcceptableException ] ");

		ApiResponse<HttpMediaTypeNotAcceptableException> apiResponse = new ApiResponse<HttpMediaTypeNotAcceptableException>();
		apiResponse.setUniqueKey(uniqueKeyService.generateUniqueId());
		apiResponse.setCode(HttpStatusCodes.ERROR_406);
    	apiResponse.setMessage(e.getMessage());
    	apiResponse.setStatus(FAILURE);
    	
		logError(uniqueKeyService.generateUniqueId(), e);
		
		return apiResponse;
	}
	
	@ExceptionHandler( { HttpMediaTypeNotSupportedException.class } )
	public @ResponseBody ApiResponse<HttpMediaTypeNotSupportedException> getHttpMediaTypeNotSupportedException(final HttpMediaTypeNotSupportedException e) {

		logger.debug(" ExceptionErrorHandler [ getHttpMediaTypeNotSupportedException ] ");

		ApiResponse<HttpMediaTypeNotSupportedException> apiResponse = new ApiResponse<HttpMediaTypeNotSupportedException>();
		apiResponse.setUniqueKey(uniqueKeyService.generateUniqueId());
		apiResponse.setCode(HttpStatusCodes.ERROR_415);
    	apiResponse.setMessage(e.getMessage());
    	apiResponse.setStatus(FAILURE);
    	
		logError(uniqueKeyService.generateUniqueId(), e);
		
		return apiResponse;
	}
	
	@ExceptionHandler( { HttpMessageNotReadableException.class } )
	public @ResponseBody ApiResponse<HttpMessageNotReadableException> getHttpMessageNotReadableException(final HttpMessageNotReadableException e) {

		logger.debug(" ExceptionErrorHandler [ getHttpMessageNotReadableException ] ");

		ApiResponse<HttpMessageNotReadableException> apiResponse = new ApiResponse<HttpMessageNotReadableException>();
		apiResponse.setUniqueKey(uniqueKeyService.generateUniqueId());
		apiResponse.setCode(HttpStatusCodes.ERROR_400);
    	apiResponse.setMessage(e.getMessage());
    	apiResponse.setStatus(FAILURE);
    	apiResponse.setExplicitDismissal(Boolean.TRUE);
    	apiResponse.setLevel(Level.ERROR);    	
		logError(uniqueKeyService.generateUniqueId(), e);
		
		return apiResponse;
	}
	
	@ExceptionHandler( { HttpMessageNotWritableException.class } )
	public @ResponseBody ApiResponse<HttpMessageNotWritableException> getHttpMessageNotWritableException(final HttpMessageNotWritableException e) {

		logger.debug(" ExceptionErrorHandler [ getHttpMessageNotWritableException ] ");

		ApiResponse<HttpMessageNotWritableException> apiResponse = new ApiResponse<HttpMessageNotWritableException>();
		apiResponse.setUniqueKey(uniqueKeyService.generateUniqueId());
		apiResponse.setCode(HttpStatusCodes.ERROR_500);
    	apiResponse.setMessage(e.getMessage());
    	apiResponse.setStatus(FAILURE);
    	
		logError(uniqueKeyService.generateUniqueId(), e);
		
		return apiResponse;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public @ResponseBody ApiResponse<Object> getMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
		logger.debug(" ExceptionErrorHandler [ Internal Server Error ] ");
		ApiResponse<Object> apiResponse = new ApiResponse<Object>();
		apiResponse.setStatus(FAILURE);
		apiResponse.setCode(HttpStatusCodes.ERROR_500);
		apiResponse.setMessage(messageSource.getMessage("1093",
				new Object[]{e.getBindingResult().getFieldError().getField()}, LocaleContextHolder.getLocale()));
		apiResponse.setExplicitDismissal(Boolean.TRUE);
		apiResponse.setLevel(Level.ERROR);

		String uniqueKey = uniqueKeyService.generateUniqueId();
		apiResponse.setUniqueKey(uniqueKey);
		logError(uniqueKey, e);
		return apiResponse;
	}

    @ExceptionHandler( { LucasRuntimeException.class } )   
    public @ResponseBody ApiResponse<Object> exceptionHandler(final LucasRuntimeException e) {
    	logger.debug(" ExceptionErrorHandler [ Internal Server Error ] ");
    	ApiResponse<Object> apiResponse = new ApiResponse<Object>();
    	apiResponse.setStatus(FAILURE);
    	apiResponse.setCode(HttpStatusCodes.ERROR_500);
    	apiResponse.setMessage(e.getMessage());
    	apiResponse.setExplicitDismissal(Boolean.TRUE);
    	apiResponse.setLevel(Level.ERROR);
    	
    	String uniqueKey = uniqueKeyService.generateUniqueId();
    	apiResponse.setUniqueKey(uniqueKey);
    	logError(uniqueKey, e);
        
        return apiResponse;
    }	
 
	/*
	 * 
	 */
    
    @ExceptionHandler(LucasBusinessException.class)   
    public @ResponseBody ApiResponse<Object> exceptionHandler(final LucasBusinessException e) {

    	logger.debug(" ExceptionErrorHandler [ LucasBusinessException ] ");
    	ApiResponse<Object> apiResponse = new ApiResponse<Object>();
    	apiResponse.setStatus(FAILURE);
    	apiResponse.setCode(e.getCodeId());
    	apiResponse.setMessage(e.getMessage());
    	apiResponse.setExplicitDismissal(e.isExplicitDismissal());
    	apiResponse.setLevel(e.getLevel());
        
        return apiResponse;
    }
	
    @ExceptionHandler   
    public @ResponseBody ApiResponse<Object> exceptionHandler(final Exception e) {
    	
    	logger.debug(" ExceptionErrorHandler [ Internal Server Error ] ");
    	ApiResponse<Object> apiResponse = new ApiResponse<Object>();
    	apiResponse.setStatus(FAILURE);
    	apiResponse.setCode(HttpStatusCodes.ERROR_500);
    	apiResponse.setMessage(e.getMessage());
    	apiResponse.setExplicitDismissal(Boolean.TRUE);
    	apiResponse.setLevel(Level.ERROR);
    	
    	String uniqueKey = uniqueKeyService.generateUniqueId();
    	apiResponse.setUniqueKey(uniqueKey);
    	logError(uniqueKey, e);
        
        return apiResponse;
    }
    
    /**
     * To log the error stack trace
     * @param uniqueKey
     * @param e
     */
    private void logError(final String uniqueKey, final Exception e) {
    	
		logger.error(String.format(" Unique Key:{} %s\n" ,uniqueKey), e);
    }
    
    
}
