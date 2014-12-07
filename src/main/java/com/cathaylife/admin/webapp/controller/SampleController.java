package com.cathaylife.admin.webapp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cathaylife.admin.service.ISample;

@Controller
public class SampleController {
	@Resource
	private ISample sample;
	
	@RequestMapping(value="sample", method=RequestMethod.GET)
	public String sample(){
		return "sample";
	}
	
	@RequestMapping(value="doSample", method=RequestMethod.POST)
	public String doSample(HttpServletRequest req){
		String id = req.getParameter("id");
		String code = sample.getSampleCode(Integer.valueOf(id));
		System.out.println(code);
		return "sample";
	}
	
}
