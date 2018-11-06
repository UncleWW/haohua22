var roleNodes="";
var roleTree;//全局树
var specialRoleCode;//根节点
var requestUrl;//请求后台链接    每次点击保存  或者  修改时  替换链接  
var urls = {
			initTree:  '/role/queryRoleTree',	
			addRole:  '/role/saveRole',
			editRole:  '/role/updateRole',
			delRole:  '/role/delRole',
				
			queryUserRole: '/role/queryUserRole',
			queryUserNoRole:  '/role/queryUserNoRole',
			
			changeUserRole:  $.contextPath() + '/role/changeUserRole'
		};
var setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
	    callback:{
	    	onClick:clickTree
	    },
		view:{
			fontCss:setFontClass
		}
};
//无效资源样式
function setFontClass(treeId,treeNode){
	return treeNode.status == '0' ? {color:"#CCCCCC"} : {};
}
//一次加载资源树
function queryRoleTree() {
	roleNodes = resultAjax(urls.initTree, "")	
}
//重新加载树
function reloadTree(flag){
	//刷新之后定位之前选中节点
	 var roleCode ;
	if(flag){//删除节点情况     定位选中的节点编码
		var nodes = roleTree.getSelectedNodes();
		if(nodes.length>0){
			roleCode = nodes[0].id;
		}
	}else{//如果是新增或者修改则到弹出框里找之前的节点编码
		roleCode = $("#roleCode").val(); 
	}
    //刷新树
	queryRoleTree();
	roleTree = $.fn.zTree.init($("#roleTree"), setting,roleNodes);
	//刷新之后定位之前选中节点
	var node = roleTree.getNodeByParam("id",roleCode);
	roleTree.selectNode(node,true);
}
/**
 * 弹出模态页  处理    wangwei  2017/08/30
 */
$('#myModal').on('show.bs.modal', function (event) { 
	//重置校验
	resetFormWW();
	//判断是否选中节点
	var nodes = roleTree.getSelectedNodes();
	if(nodes.length<1){
		commonAlert('没有选中节点！','error');
		return false;
	}
    var roleNode = nodes[0];
	//获得点击按钮信息
    var button = $(event.relatedTarget) // 触发事件的按钮  
    var recipient = button.data('whatever') // 解析出data-whatever内容  
    var modal = $(this)  
    //处理信息填充
    var parentNode = roleNode.getParentNode();
    if(recipient=='edit'){//修改
    	if(parentNode==null){
	    	commonAlert("根节点，不允许修改",'error');
			return false;
	    }
    	modal.find('.modal-title').text("修改角色信息");  
    	modal.find('.modal-body #parentRoleCode').val(parentNode.id);  
    	modal.find('.modal-body #parentRoleName').val(parentNode.name);  
    	modal.find('.modal-body #roleCode').val(roleNode.id) ; 
    	modal.find('.modal-body #roleCode').attr("readonly", "readonly");
    	modal.find('.modal-body #roleName').val(roleNode.name) ; 
    	modal.find('.modal-body #roleOrder').val(roleNode.roleOrder);  
    	modal.find('.modal-body #status').val(roleNode.status) ; 
    	modal.find('.modal-body #roleType').val(roleNode.roleType);  
    	modal.find('.modal-body #remark').val(roleNode.remark);  	
    	modal.find('#btn2').attr("onclick","editRole()");
    	
    }else if(recipient=='add'){//新增
    	if(roleNode.status=='0'){
    	    commonAlert("无效资源，不允许操作",'error');  
    		return false;
    	}
    	if(roleNode.roleType=='2'){
    	    commonAlert("不能为用户新增角色",'error');  
    		return false;
    	}
    	modal.find('.modal-title').text("新增角色信息");  
    	modal.find('.modal-body #parentRoleCode').val(roleNode.id);  
    	modal.find('.modal-body #parentRoleName').val(roleNode.name);  
    	modal.find('.modal-body #roleCode').val("");  
    	modal.find('.modal-body #roleCode').removeAttr("readonly");
    	modal.find('.modal-body #roleName').val("") ; 
    	modal.find('.modal-body #roleOrder').val("");  
    	modal.find('.modal-body #status').val("1");
    	modal.find('.modal-body #roleType').val("2") ; 
    	modal.find('.modal-body #remark').val("");  
    	modal.find('#btn2').attr("onclick","addRole()");
    	}
   
})
//修改   角色信息
editRole = function() {
	//判断 当父角色无效时   子角色不能改为有效
	var treeObj = $.fn.zTree.getZTreeObj("roleTree");
	var nodes = treeObj.getSelectedNodes();
	var parentCode;
	if(nodes.length>=1){
		parentNode = nodes[0].getParentNode();
		if($('.modal-body #status').val() == "1" && parentNode.status=="0"){
			commonAlert("父节角色无效，当前角色不能设为有效",'error');  
			return;	
		}
	}
	//保存之前校验   表单格式
	 var bootstrapValidator = $("#roleForm").data('bootstrapValidator');
	  bootstrapValidator.validate();
	  if(!bootstrapValidator.isValid()){		  
		  return;
	  }
	//保存
	requestUrl = urls.editRole;
	saveFunction();
	$('#myModal').modal('hide');
};
//新增角色  信息
addRole = function() {
	//保存之前校验   表单格式
    var bootstrapValidator = $("#roleForm").data('bootstrapValidator');
	bootstrapValidator.validate();
	if(!bootstrapValidator.isValid()){		  
		 return;
	 }
	 //保存
	requestUrl = urls.addRole;
	saveFunction();
	$('#myModal').modal('hide');
};
//删除某个节点
delRole = function() {
	var nodes = roleTree.getSelectedNodes();
	if(nodes.length<1){
		swal({
			title: '',
	        text: '没有选中节点！',
	        icon: 'error',
	        button: {
	        	text: 'OK',
	        	closeModal: true
	        },
		    timer: 3000
		})
		return false;
	}else{
		var roleNode = nodes[0];
        var parentNode = roleNode.getParentNode();
            if(parentNode==null){
                commonAlert("根节点，不允许删除",'error');
                return false;
            }
		warnAlert("确认删除么？",urls.delRole, {"roleCode":roleNode.id,"status":'0'}, function(){
			reloadTree(true);
		})	
	}
}
//保存方法
function saveFunction() {
	    //修改的时候   父角色无效，不能修改子角色
		if(requestUrl==urls.editRole){
			var treeObj = $.fn.zTree.getZTreeObj("roleTree");
			var nodes = treeObj.getSelectedNodes();
			var parentCode;
			if(nodes.length>=1){
				parentNode = nodes[0].getParentNode();
				if($("#status").val() == "1" && parentNode.status=="0"){
					commonAlert("父节角色无效，当前角色不能设为有效",'error'); 
					return;	
				}
			}
		}
		var data=$('#roleForm').serialize();
		//序列化获得表单数据，结果为：user_id=12&user_name=John&user_age=20
		var submitData=decodeURIComponent(data,true);
		alertAjax(requestUrl, submitData, function(){
			 reloadTree(false);
			 resetFormWW();
		});
}
$(document).ready(function(){
	//加载树
	queryRoleTree();
	roleTree = $.fn.zTree.init($("#roleTree"), setting,roleNodes);
	// 1.初始化Table
	var noTable = new noTableInit();
	noTable.Init();
	// 2.初始化Table
	var hasTable = new HasTableInit();
	hasTable.Init();
	bootstrapValidator();	
	//绑定回车查询事件
		$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
		    	var p1 = $('#userRoleUserPhone').val();
		    	var p2 = $('#userRoleUserName').val();

		    	var n1 = $('#userNoRoleUserPhone').val();
		    	var n2 = $('#userNoRoleUserName').val();

		    	
	        	 if(!checkNullWW(p1,p2) && checkNullWW(n1,n2)){
	        		 hasRoleSearch();
	        	 }else if(checkNullWW(p1,p2) &&  !checkNullWW(n1,n2)){
	        		 hasNoRoleSearch();
	        	 }else if(!checkNullWW(p1,p2) && !checkNullWW(n1,n2)){
	        		// selectAlert('您想查询哪个？',"未分配用户列表","已分配用户列表",function(){hasRoleSearch();},function(){hasNoRoleSearch();});
                     hasRoleSearch();
                     hasNoRoleSearch();
	        	 }
		    }  
		}); 

		$('#buttonStyle .btn-primary').css("padding","1px");
});
//点击树节点  触发事件     查询已分配    和   未分配用户列表
function clickTree(){
	$('#noRoleUser').bootstrapTable('refresh');
	$('#hasRoleUser').bootstrapTable('refresh');
}
function hasRoleSearch(){
	$('#hasRoleUser').bootstrapTable('refresh');
}
function hasNoRoleSearch(){
	$('#noRoleUser').bootstrapTable('refresh');
}
//---------------------------------------------------table---noRole-----------------------------------------
var noTableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#noRoleUser').bootstrapTable({
			ajax: function(params) {
				params.success(resultAjax(urls.queryUserNoRole, params.data));
			},
//			url : urls.queryUserNoRole, // 请求后台的URL（*）
//			method : 'get', // 请求方式（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true, // 是否显示行间隔色
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, // 是否显示分页（*）
			paginationLoop : false, // 设置为 true 启用分页条无限循环的功能。
			//onlyInfoPagination:false,//设置为 true 只显示总数据数，而不显示分页按钮。需要 pagination='True'
			pageList : [ 5  ], // 可供选择的每页的行数（*）
			paginationLoop: false,
			sortable : false, // 是否启用排序
			sortOrder : "asc", // 排序方式
			queryParams : oTableInit.queryParams,// 传递参数（*）
			sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 5, // 每页的记录行数（*）
			search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
			strictSearch : false,
			showColumns : true, // 是否显示所有的列
			showRefresh : false, // 是否显示刷新按钮
			minimumCountColumns : 1, // 最少允许的列数
			clickToSelect : true, // 是否启用点击选中行
			// height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId : "ID", // 每一行的唯一标识，一般为主键列
			showToggle : false, // 是否显示详细视图和列表视图的切换按钮
			cardView : false, // 是否显示详细视图
			detailView : false, // 是否显示父子表
			silentSort:false, //设置为 false 将在点击分页按钮时，自动记住排序项。仅在 sidePagination设置为 server时生效.
			columns : [ {
				checkbox : true
			}, {
				field : 'userPhone',
				title : '手机',
				halign: 'center',
				align: 'center',
				formatter:function(value){return gridTitle(value)}
			}, {
				field : 'userName',
				title : '姓名',
				formatter:function(value){return gridTitle(value)}
			}, {
				field : 'sex',
				title : '性别',
				formatter:function(value){return gridTitle(value)}
			}]
		});
	};
	function gridTitle(value){
		return value ? '<span title="' + value + '" >' + value + '</span>' : '';
	}
	oTableInit.queryParams = function(params) {
		var nodes = roleTree.getSelectedNodes();
		var roleCode="" ;
		if(nodes.length>0){
			roleCode = nodes[0].id;
		}
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset,// 页码
			userPhone:$('#userNoRoleUserPhone').val(),
			userName: $('#userNoRoleUserName').val(),
			//deptname:$('#userNoRoleDeptName').val(),
			roleCode:roleCode
		};
		console.log(temp);
		return temp;
	};
	return oTableInit;
};

//-------------------------------------------table---hasRole----------------------------
var HasTableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#hasRoleUser').bootstrapTable({
			ajax: function(params) {
				params.success(resultAjax(urls.queryUserRole, params.data));
			},
//			url : urls.queryUserRole, // 请求后台的URL（*）
//			method : 'get', // 请求方式（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true, // 是否显示行间隔色
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, // 是否显示分页（*）
			paginationLoop : false, // 设置为 true 启用分页条无限循环的功能。
			//onlyInfoPagination:false,//设置为 true 只显示总数据数，而不显示分页按钮。需要 pagination='True'
			pageList : [ 5], // 可供选择的每页的行数（*）
			paginationLoop: false,
			sortable : false, // 是否启用排序
			sortOrder : "asc", // 排序方式
			queryParams : oTableInit.queryParams,// 传递参数（*）
			sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 5, // 每页的记录行数（*）
			search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
			strictSearch : false,
			showColumns : true, // 是否显示所有的列
			showRefresh : false, // 是否显示刷新按钮
			minimumCountColumns : 1, // 最少允许的列数
			clickToSelect : true, // 是否启用点击选中行
			// height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId : "ID", // 每一行的唯一标识，一般为主键列
			showToggle : false, // 是否显示详细视图和列表视图的切换按钮
			cardView : false, // 是否显示详细视图
			detailView : false, // 是否显示父子表
			silentSort:false, //设置为 false 将在点击分页按钮时，自动记住排序项。仅在 sidePagination设置为 server时生效.
			columns : [ {
				checkbox : true
			}, {
                field : 'userPhone',
                title : '手机',
                halign: 'center',
                align: 'center',
                formatter:function(value){return gridTitle(value)}
            }, {
                field : 'userName',
                title : '姓名',
                formatter:function(value){return gridTitle(value)}
            }, {
                field : 'sex',
                title : '性别',
                formatter:function(value){return gridTitle(value)}
            }]
		});
	};
	function gridTitle(value){
		return value ? '<span title="' + value + '" >' + value + '</span>' : '';
	}
	oTableInit.queryParams = function(params) {
		var nodes = roleTree.getSelectedNodes();
		var roleCode="" ;
		if(nodes.length>0){
			roleCode = nodes[0].id;
		}
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, // 页面大小
				offset : params.offset,// 页码
				userPhone:$('#userRoleUserPhone').val(),
				userName: $('#userRoleUserName').val(),
				//deptname:$('#userRoleDeptName').val(),
				roleCode:roleCode
		};
		console.log(temp);
		return temp;
	};
	return oTableInit;
};
//-------------------------------------------moveUserToRole---------------------------
moveUserToRole = function() {
	var selectedNodes = $('#noRoleUser').bootstrapTable('getSelections');
	var selected = roleTree.getSelectedNodes();
	if(selected.length < 1){
        commonAlert("请先点击选择相应的角色",'warning');
        return;
    }
	if (null == selectedNodes || selectedNodes.length == 0) {
		commonAlert("请勾选用户",'warning');
		return;
	}
	var userIds = new Array();
	$(selectedNodes).each(function(i,val) {
		userIds[i] = val.userPhone;
	});
	var param = {userIds: userIds, status: '1', roleCode: roleTree.getSelectedNodes()[0].id};
	changeUserRole(param);
}
moveUserToNoRole = function() {
	var selectedNodes = $('#hasRoleUser').bootstrapTable('getSelections');
    var selected = roleTree.getSelectedNodes();
    if(selected.length < 1){
        commonAlert("请先点击选择相应的角色",'warning');
        return;
    }
    if (null == selectedNodes || selectedNodes.length == 0) {
        commonAlert("请勾选用户",'warning');
        return;
    }
	var userIds = new Array();
	$(selectedNodes).each(function(i,val) {
		userIds[i] = val.userPhone;
	});
	var param = {userIds: userIds, status: '0', roleCode: roleTree.getSelectedNodes()[0].id};
	changeUserRole(param);
}

changeUserRole = function(param) {//不想要提示框，没写成通用的
		showMask("mask");
		$.ajax({
			url : urls.changeUserRole,
			type : "POST",
			dataType : "",
			async : false,
			data : param,
			traditional:true,
			error: function(data) {
				hideMask("mask");
				var responseText = data.responseText;
				if(isContains(responseText,"您没有权限访问该页面")){
					commonAlert('您的权限不足，请联系管理员','error');
				}else{
					commonAlert('登录超时','error');
				}
		      },
		      success: function(data) {
		    	  hideMask("mask");
		    	  if ('success' == data.resultFlag) {
		    		  $('#noRoleUser').bootstrapTable('refresh');
			    	  $('#hasRoleUser').bootstrapTable('refresh');
					} else if ('fail' == data.resultFlag) {
						var errors = data.errors;
	    				if(errors!=null && errors.length>0){
	    					var a = document.createElement("div");
	    					a.classList.add('error');
	    					for(var i=0;i<errors.length;i++){
	    						var b = document.createElement("span");
	    						b.innerHTML = "错误信息: "+errors[i].errorMsg;
	    						a.appendChild(b);
	    						var br =document.createElement("br");
	    						a.appendChild(br);
	    					}
	    					contentAlert(a,'error')
	    				}else{
	    					commonAlert('出错了','error');
	    				}
						//alert(JSON.stringify(data.errors));
					}
		      }
		});
}
//---------------------------------------表单验证------------
function bootstrapValidator(){
$('#roleForm')
.bootstrapValidator({
  message: 'This value is not valid',
  group:'.rowGroup',
  //submitButtons: 'button[id="btn2"]',
  feedbackIcons: {
      valid: 'glyphicon glyphicon-ok',
      invalid: 'glyphicon glyphicon-remove',
      validating: 'glyphicon glyphicon-refresh'
  },
  fields: {
	  roleCode: {
          message: '不能为空',
          validators: {
              notEmpty: {
                  message: '编码不能为空'
              }
          }
      },
      roleName: {
          validators: {
              notEmpty: {
                  message: '菜单名称不能为空'
              }
          }
      },
      roleOrder: {
          validators: {
              regexp: {
                  regexp: /^([1-9]\d|\d)$/,
                  message: '请输入0~99'
              }
          }
      },
      remark: {
          validators: {
          	 stringLength: {
                   max: 1000,
                   message: '字符太长'
               }
          }
      }   
  }
})
}
//重置校验
function resetFormWW(){
	$("#roleForm").data('bootstrapValidator').destroy();
	$('#roleForm').data('bootstrapValidator', null);
	bootstrapValidator();	
}