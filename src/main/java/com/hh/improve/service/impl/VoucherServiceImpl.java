package com.hh.improve.service.impl;

import com.github.pagehelper.PageHelper;
import com.hh.improve.dao.IBaseDao;
import com.hh.improve.dao.IPaymentHistoryDao;
import com.hh.improve.dao.IVoucherDao;
import com.hh.improve.dao.IVoucherSonDao;
import com.hh.improve.entity.Voucher;
import com.hh.improve.entity.VoucherSon;
import com.hh.improve.entity.vo.VoucherSearch;
import com.hh.improve.entity.vo.VoucherVo;
import com.hh.improve.service.IVoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VoucherServiceImpl extends BaseServiceImpl<Voucher> implements IVoucherService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(VoucherServiceImpl.class);
	
	@Autowired
	private IVoucherDao voucherDao;

	@Autowired
	private IVoucherSonDao voucherSonDao;

	@Autowired
	private IPaymentHistoryDao paymentHistoryDao;

	@Override
	protected IBaseDao<Voucher> getBaseDao() {
		return voucherDao;
	}


	@Override
	public VoucherVo queryLastVoucher(){
		String voucherId = voucherDao.getMaxVoucherId();
		return queryByVoucherId(voucherId);
	}

	@Override
	public VoucherVo queryVoucherById(String voucherId){
		return queryByVoucherId(voucherId);
	}

	@Override
	public VoucherVo queryAroundByVoucherId(String voucherId,int type){
		int num = Integer.parseInt(voucherId);
		if(type==0){
			num--;
		}else if(type==1){
			num++;
		}
		voucherId = dealVoucherId(num);
		return queryByVoucherId(voucherId);
	}

	@Override
	public void voucherSaveOrUpdate(VoucherVo voucherVo){


		String voucherId = voucherVo.getVoucherId();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("voucherId",voucherId);
		List<Voucher> list = voucherDao.queryByCondition(condition);
		List<VoucherSon> voucherSons = new ArrayList<VoucherSon>();
		voucherSons = voucherVo.getSonList();
		//????????????
		if( list!=null && list.size()>0 ){
			//??????
			Voucher voucher = new Voucher();
			BeanUtils.copyProperties(voucherVo,voucher);
			voucher.setVoucherId(voucherId);
			voucherDao.update(voucher);
			voucherSonDao.deleteByVoucherId(voucherId);
		}else{
			//??????
			voucherId = getNewMaxVoucherId();
			Voucher voucher = new Voucher();
			BeanUtils.copyProperties(voucherVo,voucher);
			voucher.setVoucherId(voucherId);
			voucherDao.insert(voucher);
		}
		//????????????
		for (VoucherSon voucherSon:voucherSons) {
			voucherSon.setVoucherId(voucherId);
		}
		voucherSonDao.batchInsert(voucherSons);
	}

	@Override
	public String getNewMaxVoucherId(){
		String maxId = voucherDao.getMaxVoucherId();
		String voucherId = "";
		if( maxId == null || maxId.equals("")){
			maxId = "0";
		}
		int num = Integer.parseInt(maxId);
		int newId = num + 1;
		voucherId = dealVoucherId(newId);
		return voucherId;
	}

	String dealVoucherId(int id){
		String voucherId = "";
		String str = id+"";
		int n = str.length();
		for(int i = 0; i<8-n;i++){
			voucherId+="0";
		}
		return voucherId+str;

	}

	public VoucherVo queryByVoucherId(String voucherId){
		VoucherVo voucherVo = new VoucherVo();
		//?????????????????????
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("voucherId",voucherId);
		List<Voucher> vouchers = voucherDao.queryByCondition(condition);
		if(vouchers!=null && vouchers.size()>0){
			Voucher voucher = vouchers.get(0);
			BeanUtils.copyProperties(voucher,voucherVo);
		}
		//?????????????????????
		List<VoucherSon> voucherSons = voucherSonDao.queryByCondition(condition);
		voucherVo.setSonList(voucherSons);
		return voucherVo;
	}

	@Override
	public List<VoucherVo> queryVoucherListPage(VoucherSearch voucherSearch, int offset, int limit){
		PageHelper.offsetPage(offset, limit);
		// ??????????????????????????????
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("customerName", voucherSearch.getCustomerName());
		condition.put("voucherDate_begin", voucherSearch.getVoucherDate_begin());
		condition.put("voucherDate_end", voucherSearch.getVoucherDate_end());
		return voucherDao.queryVoucherListPage(condition);
	}

	@Override
	public List<VoucherVo> queryVoucherSonListPage(VoucherSearch voucherSearch, int offset, int limit){
		PageHelper.offsetPage(offset, limit);
		// ??????????????????????????????
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("customerName", voucherSearch.getCustomerName());
		condition.put("voucherId", voucherSearch.getVoucherId());
		condition.put("customerId", voucherSearch.getCustomerId());
		condition.put("voucherDate_begin", voucherSearch.getVoucherDate_begin());
		condition.put("voucherDate_end", voucherSearch.getVoucherDate_end());
		return voucherDao.queryVoucherSonListPage(condition);
	}

	@Override
	public BigDecimal queryAccumulateDebtById(String customerId){
		//?????? customerId ????????????????????????
		BigDecimal accumulateAmount = voucherDao.countAmountByCustomerId(customerId);
		if(accumulateAmount==null){
			accumulateAmount = new BigDecimal(0);
		}
		//?????? customerId ???????????????????????????
		BigDecimal accumulatePay =  paymentHistoryDao.countPayHistoryByCustomerId(customerId);
		if(accumulatePay==null){
			accumulatePay = new BigDecimal(0);
		}
		BigDecimal accumulateDebt = accumulateAmount.subtract(accumulatePay);
		return accumulateDebt;
	}

}
