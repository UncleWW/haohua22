package com.hh.improve.dao;


import com.hh.improve.entity.vo.ReportVoucherVo;

import java.util.List;

public interface IReportVoucherDao extends IBaseDao<ReportVoucherVo> {

    List<String> getAllCustomer();

    List<String> getAllMonths();

    /**
     * 按月统计  每个月的总销售额
     * @return
     */
    List<ReportVoucherVo> getReportSaleGroupByMonth();

    List<ReportVoucherVo> getReportVoucherVo();
}
