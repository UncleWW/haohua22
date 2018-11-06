package com.hh.improve.controller;

import com.github.pagehelper.PageInfo;
import com.hh.improve.common.constants.WebConstants;
import com.hh.improve.common.exception.BusinessException;
import com.hh.improve.common.pojo.ErrorInfo;
import com.hh.improve.common.pojo.PageResult;
import com.hh.improve.common.util.PageUtils;
import com.hh.improve.entity.Salary;
import com.hh.improve.entity.dto.BaseDTO;
import com.hh.improve.entity.vo.SalaryVo;
import com.hh.improve.interceptor.annotation.NoRepeatRequest;
import com.hh.improve.service.ISalaryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/salary")
public class SalaryController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(SalaryController.class);
	@Autowired
	private ISalaryService salaryService;
	
	@RequestMapping("")
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("parentUri", "/salaryManager");
		model.addAttribute("uri", "/salary");
		return super.commonModel(model, response);
	}
	
	protected String getPageUri() {
		return "views/salaryManager";
	}

	@ResponseBody
	@RequestMapping("/querySalaryPage")
	public BaseDTO querySalaryPage(SalaryVo salaryVo, @RequestParam(defaultValue = WebConstants.DEFAULT_PAGE_OFFSET) int offset,
							 @RequestParam(defaultValue = "10") int limit) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		PageResult<SalaryVo> pageResult = null;
		try {
			if (CollectionUtils.isEmpty(errors)) {
				List<SalaryVo> salaryList = salaryService.getSalaryPageList(salaryVo, offset, limit);
				if (CollectionUtils.isEmpty(salaryList)) {
					int defaultOffset = Integer.valueOf(WebConstants.DEFAULT_PAGE_OFFSET).intValue();
					if (offset > defaultOffset) {
						salaryList = salaryService.getSalaryPageList(salaryVo, defaultOffset, limit);
					}
				}
				// get page info
				pageResult = PageUtils.tranferPageInfo(new PageInfo<SalaryVo>(salaryList));
			}
		} catch (Exception e) {
			LOGGER.error("SalaryController.querySalaryPage Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		if (null == pageResult) {
			pageResult = new PageResult<SalaryVo> ();
		}
		return tranferBaseDTO(errors, pageResult);
	}

	@ResponseBody
	@RequestMapping("/getSalaryHistoryPageList")
	public BaseDTO getSalaryHistoryPageList(SalaryVo salaryVo, @RequestParam(defaultValue = WebConstants.DEFAULT_PAGE_OFFSET) int offset,
							 @RequestParam(defaultValue = "10") int limit) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		PageResult<SalaryVo> pageResult = null;
		try {
			if (CollectionUtils.isEmpty(errors)) {
				List<SalaryVo> salaryList = salaryService.getSalaryHistoryPageList(salaryVo, offset, limit);
				if (CollectionUtils.isEmpty(salaryList)) {
					int defaultOffset = Integer.valueOf(WebConstants.DEFAULT_PAGE_OFFSET).intValue();
					if (offset > defaultOffset) {
						salaryList = salaryService.getSalaryHistoryPageList(salaryVo, defaultOffset, limit);
					}
				}
				// get page info
				pageResult = PageUtils.tranferPageInfo(new PageInfo<SalaryVo>(salaryList));
			}
		} catch (Exception e) {
			LOGGER.error("SalaryController.querySalaryPage Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		if (null == pageResult) {
			pageResult = new PageResult<SalaryVo> ();
		}
		return tranferBaseDTO(errors, pageResult);
	}


	@ResponseBody
	@RequestMapping("/saveSalary")
	@NoRepeatRequest(2000)
	public BaseDTO saveSalary(@RequestBody Salary salary) {
         Subject subject = SecurityUtils.getSubject();
         String userPhone = getUserPhone();
		List<ErrorInfo> errors = ckechSalary(salary);
		int result = 0;
		try {
			if (CollectionUtils.isEmpty(errors)){
				//如果上次的调薪还没生效，就又调薪了，则直接把上次调薪删掉
                   Map<String, Object> condition = new HashMap<String, Object>();
                   condition.put("employeePhone",salary.getEmployeePhone());
                   condition.put("version","1");
                   List<Salary> salarylist = salaryService.getList(condition);
                   if(salarylist!=null && salarylist.size()>0){
                       String endDate = salaryService.dealEndDate(salary.getStatus());
                       String startDate = salarylist.get(0).getStartDate();
                       if(endDate!=null && startDate!=null ){
                            int start = Integer.parseInt(startDate.replaceAll("-",""));
                            int end = Integer.parseInt(endDate.replaceAll("-",""));
                            if(end<start){
                                Salary a = new Salary();
                                a.setEmployeePhone(salary.getEmployeePhone());
                                a.setVersion("1");
                                salaryService.delete(a);
                            }
                       }
                   }
			    //第一步，把当前员工的工资置为历史工资，且给他一个结束时间
                   String endDate = salaryService.dealEndDate(salary.getStatus());
			    Salary updateSalary = new Salary();
                   updateSalary.setEmployeePhone(salary.getEmployeePhone());
                   updateSalary.setVersion("1");
                   updateSalary.setEndDate(endDate);
                   updateSalary.setCreater(userPhone);
                   updateSalary.setModifier(userPhone);
                   salaryService.update(updateSalary);
                   //第二步，将新的工资插入的记录表中
                   String startDate = salaryService.dealStartDate(salary.getStatus());
                   salary.setStartDate(startDate);
                   salary.setVersion("1");
                   salary.setModifier(userPhone);
                   salaryService.create(salary);
			}
		} catch (BusinessException e) {
			LOGGER.error("SalaryController.salarySave BusinessException: ", e);
			ErrorInfo errorInfo = new ErrorInfo(e.getErrorCode(), e.getErrorMsg());
			errors = Arrays.asList(errorInfo);
		} catch (Exception e) {
			LOGGER.error("SalaryController.salarySave BusinessException: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, result);
	}

	List<ErrorInfo> ckechSalary(Salary salary){
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		if( salary.getEmployeePhone()==null || salary.getEmployeePhone().equals("") ){
			ErrorInfo errorInfo =  new ErrorInfo("Code","手机号为空");
			errors = Arrays.asList(errorInfo);
		}
		return errors;
	}

     public static void main(String args[]){
	    int effectMonth = 1;
         SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM");
         Calendar rightNow = Calendar.getInstance();
         rightNow.setTime(new Date());
         if(effectMonth==0){//次月生效
             rightNow.add(Calendar.MONTH,-1);//日期加1个月
         }
         Date dt1=rightNow.getTime();
         String reStr = sdf.format(dt1);
         System.out.println(reStr);
     }

}
