/**
 * 
 */
package cn.YouWei.STD.robot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator
 *
 */
@Controller
public class GeneralController {
	private static final Logger logger=LoggerFactory.getLogger(GeneralController.class);
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
public ModelAndView index_jsp(){
	ModelAndView modelAndView = new ModelAndView("index");
	return modelAndView;
}
}
