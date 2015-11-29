package com.lucas.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;



public class LucasBusinessException extends RuntimeException {

	private static final long serialVersionUID = -4838272870215561926L;
	private String codeId;
	private Boolean explicitDismissal;
	private Level level;
	private String message;

	
	public LucasBusinessException(String codeId, boolean explicitDismissal, Level level, Object ... objectArray){
		MessageSource messageSource = new ReloadableResourceBundleMessageSource();
		((ReloadableResourceBundleMessageSource) messageSource).setBasename("classpath:locale/notifications");
		this.message = messageSource.getMessage(codeId, objectArray, LocaleContextHolder.getLocale());
		this.codeId = codeId;
		this.explicitDismissal = explicitDismissal;
		this.level = level;
	}

	public LucasBusinessException(Set<ConstraintViolation<?>> constraintViolations) {
		this.codeId = null;
		this.explicitDismissal = Boolean.TRUE;
		this.level = Level.WARN;
		java.util.Iterator<ConstraintViolation<?>> iter = constraintViolations.iterator();
		StringBuilder Violations = new StringBuilder();
		while (iter.hasNext()) {
			ConstraintViolation<?> voilation = iter.next();
			if (voilation != null) {
				Violations.append(voilation.getPropertyPath().toString() +": " + voilation.getMessage() + ". ");
			}
		}
		this.message = Violations.toString();
		this.explicitDismissal = Boolean.TRUE;
		this.level = Level.WARN;
		this.codeId = null;
	}

	public String getCodeId() {
		return codeId;
	}

	public Boolean isExplicitDismissal() {
		return explicitDismissal;
	}

	public Level getLevel() {
		return level;
	}

	public String getMessage() {
		return message;
	}

}
