package lemon.web;

import lemon.web.base.MMTException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * MMT 全局异常处理机
 * @author lemon
 * @version 1.0
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * 处理业务异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler
	public ModelAndView handleMMTException(MMTException ex) {
		return new ModelAndView("error","msg",ex.getMessage());
	}
}
