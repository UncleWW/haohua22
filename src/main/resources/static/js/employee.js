$(document).ready(function() {
    // 1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    // 2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

    bootstrapValidator();
    $('.form_datetime').datetimepicker({
        language:  'zh-CN',
        format:"yyyy-mm-dd",
        minView: 2,  //日期时间选择器所能够提供的最精确的时间选择视图。
        weekStart: 0, //一周从哪一天开始。0（星期日）到6（星期六）
        todayBtn:  "linked", //如果此值为true 或 "linked"，则在日期时间选择器组件的底部显示一个 "Today" 按钮用以选择当前日期。
		// 如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: true, //当选择一个日期之后是否立即关闭此日期时间选择器。
        todayHighlight: true, //如果为true, 高亮当前日期。
        keyboardNavigation:true,//是否允许通过方向键改变日期。
        startView: 2,//0-4   0小时1天2月3年
        pickerPosition:'bottom-left',
        forceParse: 0/*,
        showMeridian: 1*/
    });
    //绑定回车查询事件
    $(document).keyup(function (e) {//捕获文档对象的按键弹起事件
        if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了
            $('#tb_departments').bootstrapTable('refresh');
        }
    });
});

//approveRole-modal验证销毁重构
$('#userDetail-modal').on('hidden.bs.modal', function() {
    $("#UserForm").data('bootstrapValidator').destroy();
    $('#UserForm').data('bootstrapValidator', null);
    bootstrapValidator();

});
$('#add_employee').click(function(){
    $("#userDetailModalLabel").html("新增雇员信息");
    $("#add_employeeName").val("");
    $("#add_employeePhone").val("")
    $("#add_sex").val("1");
    $("#add_jobName").val("");
    $("#add_input_hireDate").val("");
    $("#add_input_leaveDate").val("");
    $("#add_emergencyPhone").val("");
    $("#add_address").val("");
    $("#add_remark").val("");
    $("#add_status").val("1");
    $("#add_employeeName").removeAttr("readonly");
    $("#add_employeePhone").removeAttr("readonly");
    $('#userSaveOrUpdate').attr("data-whatever","add");
    $('#userSaveOrUpdate').attr("onclick","addUser()");
    $('#userDetail-modal').modal();
});

function bootstrapValidator() {
    $('#UserForm').bootstrapValidator({
        message : 'This value is not valid',
        group : '.rowGroup',
        feedbackIcons : {
            valid : 'glyphicon glyphicon-ok',
            invalid : 'glyphicon glyphicon-remove',
            validating : 'glyphicon glyphicon-refresh'
        },
        fields : {
            employeeName: {
                validators: {
                    notEmpty: {
                        message: '请填写姓名'
                    },
                }
            },
            employeePhone: {
                validators: {
                    notEmpty: {
                        message: '请填写手机号'
                    },
                    /*stringLength: {
                        min: 11,
                        max: 11,
                        message: '请输入11位手机号码'
                    },*/
                    regexp: {
                        regexp: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/,
                        message: '请输入正确的手机号码'
                    }
                }
            },
            sex: {
                validators: {
                    notEmpty: {
                        message: '请填选择性别'
                    },
                }
            },
            hireDate: {
                validators: {
                    date: {
                        format: 'YYYY-MM-DD',
                        message: '日期格式不正确'
                    }
                }
            },
            leaveDate: {
                validators: {
                    date: {
                        format: 'YYYY-MM-DD',
                        message: '日期格式不正确'
                    }
                }
            },
            emergencyPhone: {
                validators: {
                    regexp: {
                        regexp: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/,
                        message: '请输入正确的手机号码'
                    }
                }
            },
            status: {
                validators: {
                    notEmpty: {
                        message: '请选择状态'
                    },
                }
            }
        }
    });
}

var TableInit = function() {
	var oTableInit = new Object();

	oTableInit.Init = function() {
		$('#tb_departments').bootstrapTable({
			ajax: function(params) {
				params.success(resultAjax('/employee/queryEmployeePage', params.data));
			},
//			url : $.contextPath() + '/user/queryUserPage', // 请求后台的URL（*）
//			method : 'get', // 请求方式（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true, // 是否显示行间隔色
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, // 是否显示分页（*）
			sortable : false, // 是否启用排序
			sortOrder : "asc", // 排序方式
			queryParams : oTableInit.queryParams,// 传递参数（*）
			sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 5, // 每页的记录行数（*）
			pageList : [ 5, 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
			paginationLoop: false,
			//search : true, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
			strictSearch : true,
			showColumns : true, // 是否显示所有的列
			//showRefresh : true, // 是否显示刷新按钮
			minimumCountColumns : 2, // 最少允许的列数
			clickToSelect : true, // 是否启用点击选中行
			// height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId : "ID", // 每一行的唯一标识，一般为主键列
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			cardView : false, // 是否显示详细视图
			//checkboxHeader: true,
			detailView : false, // 是否显示父子表
			columns : [ {
				field : 'index',
				title : '序号',
				halign: 'center',
				align: 'center',
				formatter: function(value, row, index) { return index + 1; }
			}, {
				field : 'employeeName',
				title : '姓名',
				halign: 'center',
				align: 'center',
				formatter:function(value){return gridTitle(value)}
			},  {
				field : 'employeePhone',
				title : '手机号',
				formatter:function(value){return gridTitle(value)}
			}, {
				field : 'sex',
				title : '性别',
				halign: 'center',
				align: 'center',
				formatter:function(value){if (value == "1"){ return "男"; }
				else if (value =="0") { return "女"; } }
			} , {
                field : 'jobName',
                title : '职称',
                halign: 'center',
                align: 'center',
                formatter:function(value){return gridTitle(value)}
            } ,{
                field : 'hireDate',
                title : '入职日期',
                halign: 'center',
                align: 'center',
                formatter:function(value){return gridTitle(value)}
            } ,{
				field : 'leaveDate',
				title : '离职日期',
				halign: 'center',
                visible:false,
				align: 'center',
                formatter:function(value){if (value != "" && value != null){ return value; }
                else{ return '<span class="enabledFont">至今</span>'; } }
			},{
                field : 'address',
                title : '住址',
                halign: 'center',
                align: 'center',
                formatter:function(value){return gridTitle(value)}
            }, {
                field : 'status',
                title : '状态',
                halign: 'center',
                align: 'center',
                formatter:function(value){if (value == "1"){ return '<span class="enabledFont">在职</span>'; }
                else if (value =="0") { return '<span class="disabledFont">离职</span>'; } }
            },{
                field : 'emergencyPhone',
                title : '紧急联系人',
                visible:false,
                halign: 'center',
                align: 'center',
                formatter:function(value){return gridTitle(value)}
            },{
                field : 'modifier',
                title : '修改人',
                visible:false,
                halign: 'center',
                align: 'center',
                formatter:function(value){return gridTitle(value)}
            },{
                field : 'modifiedTime',
                title : '更新时间',
                halign: 'center',
                visible:false,
                align: 'center',
            },{
				field : '',
				title : '编辑',
				halign : 'center',
				align : 'center',
				events : operateEvents,
				formatter : operateFormatter
			}]
		});
	};
	function gridTitle(value){
		return value ? '<span title="' + value + '" >' + value + '</span>' : '';
	}
	
	function operateFormatter(value, row, index) {
		return [ "<a role='button' class='optionEdit'>编辑</a>" ];
	}
	
	window.operateEvents = {
			'click .optionEdit' : function(e, value, row, index) {
				$("#userDetailModalLabel").html("编辑雇员信息");
				//	$(".enabled").attr("disabled", true);
                $("#add_employeeName").val(row.employeeName);
                $("#add_employeePhone").val(row.employeePhone)
                $("#add_sex").val(row.sex);
                $("#add_jobName").val(row.jobName);
                $("#add_input_hireDate").val(row.hireDate);
                $("#add_input_leaveDate").val(row.leaveDate);
                $("#add_emergencyPhone").val(row.emergencyPhone);
                $("#add_address").val(row.address);
                $("#add_remark").val(row.remark);
                $("#add_status").val(row.status);

                $("#add_employeeName").attr("readonly","readonly");
                $("#add_employeePhone").attr("readonly","readonly");
                $('#userSaveOrUpdate').attr("data-whatever","edit");
                $('#userSaveOrUpdate').attr("onclick","editUser()");
                $('#userDetail-modal').modal();
			}
		};
	
	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			employeePhone:$("#txt_search_employeePhone").val(),
			employeeName:$("#txt_search_employeeName").val(),
			status:$("#txt_search_status").val(),
		};
		console.log(temp);
		return temp;
	};
	return oTableInit;
};

var ButtonInit = function() {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function() {
        $('#txt_search_status').select2( {
            multiple: false,
            minimumResultsForSearch: -1,
            containerCssClass: 'form-control',
            data: [{id: '', text: '不限'},{id: '1', text: '在职'},{id: '0', text: '离职'}]
        });

        //初始化页面上面的按钮事件
        $('#employee-query-btn').click(function() {
            $('#tb_departments').bootstrapTable('refresh');
        });

    }
    return oInit;
};
//新增雇员  信息
addUser = function() {
    var bootstrapValidator = $("#UserForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        paramsAjax('/employee/employeeSave', saveParams(), function() {
            $("#userDetail-modal").modal('hide');
            $('#tb_departments').bootstrapTable('refresh');
        });
    } else {
        return;
    }
};
//编辑雇员  信息
editUser = function() {
    var bootstrapValidator = $("#UserForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        paramsAjax('/employee/employeeUpdate', saveParams(), function() {
            $("#userDetail-modal").modal('hide');
            $('#tb_departments').bootstrapTable('refresh');
        });
    } else {
        return;
    }
};
var saveParams = function() {
    var params = {
    	employeeName: $("#add_employeeName").val().trim(),
        employeePhone: $("#add_employeePhone").val().trim(),
        sex: $("#add_sex").val().trim(),
        jobName: $("#add_jobName").val().trim(),
        hireDate: $("#add_input_hireDate").val().trim(),
        leaveDate: $("#add_input_leaveDate").val().trim(),
        emergencyPhone: $("#add_emergencyPhone").val().trim(),
        address: $("#add_address").val().trim(),
        remark: $("#add_remark").val().trim(),
        status: $("#add_status").val().trim()
    }
    return JSON.stringify(params);
}

