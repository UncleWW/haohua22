$(document).ready(function() {
    // 1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    // 2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

    bootstrapValidator();

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
    $("#userDetailModalLabel").html("新增客户信息");
    $("#modal_customerId").val("");
    $("#modal_customerName").val("");
    $("#modal_customerPhone").val("")
    $("#modal_address").val("");
    $("#modal_remark").val("");
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
            customerName: {
                validators: {
                    notEmpty: {
                        message: '请填写姓名'
                    },
                }
            },
            customerPhone: {
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
            }
        }
    });
}

var TableInit = function() {
	var oTableInit = new Object();

	oTableInit.Init = function() {
		$('#tb_departments').bootstrapTable({
			ajax: function(params) {
				params.success(resultAjax('/customer/queryCustomerPage', params.data));
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
				field : 'customerName',
				title : '姓名',
				halign: 'center',
				align: 'center',
				formatter:function(value){return gridTitle(value)}
			},  {
				field : 'customerPhone',
				title : '手机号',
				formatter:function(value){return gridTitle(value)}
			}, {
                field : 'address',
                title : '地址',
                halign: 'center',
                align: 'center',
                formatter:function(value){return gridTitle(value)}
            },{
                field : 'remark',
                title : '备注',
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
				$("#userDetailModalLabel").html("编辑客户信息");
                $("#modal_customerId").val(row.customerId);
                $("#modal_customerName").val(row.customerName);
                $("#modal_customerPhone").val(row.customerPhone)
                $("#modal_address").val(row.address);
                $("#modal_remark").val(row.remark);

                $('#userSaveOrUpdate').attr("data-whatever","edit");
                $('#userSaveOrUpdate').attr("onclick","editUser()");
                $('#userDetail-modal').modal();
			}
		};
	
	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
            customerName:$("#txt_search_customerName").val(),
            customerPhone:$("#txt_search_customerPhone").val(),
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
        paramsAjax('/customer/customerSave', saveParams(), function() {
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
        paramsAjax('/customer/customerUpdate', saveParams(), function() {
            $("#userDetail-modal").modal('hide');
            $('#tb_departments').bootstrapTable('refresh');
        });
    } else {
        return;
    }
};
var saveParams = function() {
    var params = {
        customerId:$("#modal_customerId").val().trim(),
        customerName: $("#modal_customerName").val().trim(),
        customerPhone: $("#modal_customerPhone").val().trim(),
        address: $("#modal_address").val().trim(),
        remark: $("#modal_remark").val().trim()
    }
    return JSON.stringify(params);
}

