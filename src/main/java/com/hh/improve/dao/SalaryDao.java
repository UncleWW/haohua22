package com.hh.improve.dao;


import com.hh.improve.entity.Salary;
import com.hh.improve.entity.vo.SalaryVo;

import java.util.List;
import java.util.Map;

public interface SalaryDao extends IBaseDao<Salary>{
    /**
     * 根据条件查询数据
     *
     * @author 011336wangwei3
     * @param condition
     * @return List<T>
     */
     List<SalaryVo> getSalaryPageList(Map<String, Object> condition);

    /**
     * 根据条件查询详细历史数据
     *
     * @author 011336wangwei3
     * @param condition
     * @return List<T>
     */
     List<SalaryVo> getSalaryHistoryPageList(Map<String, Object> condition);

    /**
     * 物理删除
     * @param salary
     * @return
     */
     int delete(Salary salary);
}
