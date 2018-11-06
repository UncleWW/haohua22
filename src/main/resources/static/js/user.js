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
$('#add_user').click(function(){
    $("#userDetailModalLabel").html("新增用户信息");
    $("#add_userName").val("");
    $("#add_userPhone").val("")
    $("#add_password").val("")
    $("#add_sex").val("1");
    $("#add_status").val("1");
    $("#add_userName").removeAttr("readonly");
    $("#add_userPhone").removeAttr("readonly");
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
            userName : {
                validators : {
                    notEmpty : {
                        message : '请填写姓名'
                    },
                }
            },
			userPhone : {
                validators : {
                    notEmpty : {
                        message : '请填写手机号'
                    },
                    /*stringLength: {
                        min: 11,
                        max: 11,
                        message: '请输入11位手机号码'
                    },*/
                    regexp: {
                        regexp: /^1[3|5|8]{1}[0-9]{9}$/,
                        message: '请输入正确的手机号码'
                    }
                }
            },password : {
                validators : {
                    notEmpty : {
                        message : '请填写密码'
                    }
                }
            },
			sex : {
                validators : {
                    notEmpty : {
                        message : '请填选择性别'
                    },
                }
            },
            status : {
                validators : {
                    notEmpty : {
                        message : '请填选择状态'
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
				params.success(resultAjax('/user/queryUserPage', params.data));
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
				field : 'userName',
				title : '姓名',
				formatter:function(value){return gridTitle(value)}
			}, {
                field : 'userPhone',
                title : '手机号',
                halign: 'center',
                align: 'center',
                formatter:function(value){return gridTitle(value)}
            },{
				field : 'sex',
				title : '性别',
				halign: 'center',
				align: 'center',
				formatter:function(value){if (value == "1"){ return "男"; }
				else if (value =="0") { return "女"; } }
			} , {
				field : 'status',
				title : '状态',
				halign: 'center',
				align: 'center',
				formatter:function(value){if (value == "1"){ return '<span class="enabledFont">有效</span>'; }
				else if (value =="0") { return '<span class="disabledFont">无效</span>'; } }
			}, {
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
				$("#userDetailModalLabel").html("编辑用户信息");
                $("#add_userName").val(row.userName);
				$("#add_userPhone").val(row.userPhone);
				$("#add_password").val(row.password);
				$("#add_sex").val(row.sex);
				$("#add_status").val(row.status);
                $("#add_userName").attr("readonly","readonly");
                $("#add_userPhone").attr("readonly","readonly");
                $('#userSaveOrUpdate').attr("data-whatever","edit");
                $('#userSaveOrUpdate').attr("onclick","editUser()");
                $('#userDetail-modal').modal();
			}
		};
	
	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			userPhone:$("#txt_search_userPhone").val(),
			userName:$("#txt_search_userName").val(),
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
			data: [{id: '', text: '不限'},{id: '1', text: '有效'},{id: '0', text: '无效'}]
		});

		//初始化页面上面的按钮事件
		$('#userDetail-query-btn').click(function() {
			$('#tb_departments').bootstrapTable('refresh');
		});

	}
	return oInit;
};
//新增角色  信息
addUser = function() {
    var bootstrapValidator = $("#UserForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        paramsAjax('/user/userSave', saveParams(), function() {
            $("#userDetail-modal").modal('hide');
            $('#tb_departments').bootstrapTable('refresh');
        });
    } else {
        return;
    }
};
//新增角色  信息
editUser = function() {
    var bootstrapValidator = $("#UserForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        paramsAjax('/user/userUpdate', saveParams(), function() {
            $("#userDetail-modal").modal('hide');
            $('#tb_departments').bootstrapTable('refresh');
        });
    } else {
        return;
    }
};
var saveParams = function() {
	var params = {
        userName: $("#add_userName").val().trim(),
        userPhone: $("#add_userPhone").val().trim(),
        password: $("#add_password").val().trim(),
        sex: $("#add_sex").val().trim(),
        status: $("#add_status").val().trim()
		}
		return JSON.stringify(params);
	
	}