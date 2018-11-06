package com.hh.improve.service.impl;

import com.github.pagehelper.PageHelper;
import com.hh.improve.dao.IBaseDao;
import com.hh.improve.dao.IVoucherSonDao;
import com.hh.improve.entity.Customer;
import com.hh.improve.entity.VoucherSon;
import com.hh.improve.service.IVoucherSonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherSonServiceImpl extends BaseServiceImpl<VoucherSon> implements IVoucherSonService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(VoucherSonServiceImpl.class);
	
	@Autowired
	private IVoucherSonDao voucherSonDao;
	
	@Override
	protected IBaseDao<VoucherSon> getBaseDao() {
		return voucherSonDao;
	}


}
