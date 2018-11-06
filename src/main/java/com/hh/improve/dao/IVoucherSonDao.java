package com.hh.improve.dao;


import com.hh.improve.entity.VoucherSon;

import java.util.List;

public interface IVoucherSonDao extends IBaseDao<VoucherSon> {
    /**
     * 批量插入商品详情
     * @param voucherSons
     */
    void batchInsert(List<VoucherSon> voucherSons);

    /**
     * 根据凭证号删除凭证下的商品
     * @param voucherId
     */
    void deleteByVoucherId(String voucherId);

}
