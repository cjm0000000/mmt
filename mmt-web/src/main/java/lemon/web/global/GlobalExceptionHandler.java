package lemon.web.global;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.github.cjm0000000.mmt.web.WebException;

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
	public ModelAndView handleMMTException(WebException ex) {
		return new ModelAndView("error","msg",ex.getMessage());
	}
	
	/**
	 * 处理无权限异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	public ModelAndView handleAccessDeniedException(AccessDeniedException e){
		return new ModelAndView("forbidden","msg",e.getMessage());
	}
	
	/**
	 * 处理空指针异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler
	public ModelAndView handleNullPointerException(NullPointerException ex) {
		return new ModelAndView("error","msg",ex.getMessage());
	}
}
