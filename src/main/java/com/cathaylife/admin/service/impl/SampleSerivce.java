package com.cathaylife.admin.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cathaylife.admin.persist.SampleDao;
import com.cathaylife.admin.persist.domain.SampleDomain;
import com.cathaylife.admin.service.ISample;

@Service
public class SampleSerivce implements ISample {
	@Resource
	private SampleDao samplePersist; 
	
	
	@Override
	public String getSampleCode(Integer id) {
		SampleDomain sampleDomain = samplePersist.getDomain(id);
		return sampleDomain.getName();
	}

}
