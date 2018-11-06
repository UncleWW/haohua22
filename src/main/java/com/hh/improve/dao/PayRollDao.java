package com.hh.improve.dao;


import com.hh.improve.entity.PayRoll;
import com.hh.improve.entity.vo.PayRollVo;

import java.util.List;
import java.util.Map;

public interface PayRollDao extends IBaseDao<PayRoll>{
    /**
     * 分页展示工资发放记录
     * @param condition
     * @return
     */
    List<PayRollVo> getPayRollVoPageList(Map<String, Object> condition);

    /**
     * 分页展示History工资发放记录
     * @param condition
     * @return
     */
    List<PayRollVo> queryHistoryPayRollVoPage(Map<String, Object> condition);
}
