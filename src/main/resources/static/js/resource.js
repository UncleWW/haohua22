var resNodes="";
var roleNodes="";
var resTree;//树  定义全局树
var requestUrl;//请求后台链接    每次点击保存  或者  修改时  替换链接  
var urls = {
			initTree:  '/resource/queryResTree',	
			addRes:       $.contextPath() + '/resource/saveRes',
			editResource: $.contextPath() + '/resource/updateResource',
			delResource:  '/resource/delResource',
			
			saveRule: '/resource/saveRule'
		};
var setting = {
		showIcon: true,
		data: {
			simpleData: {
				dataFilter: filter,
				enable: true
			}
		},
    callback:{
    	onClick:clickTree
    },
	view:{
		fontCss:setFontClass,

	}
};
function setFontClass(treeId,treeNode){
	return treeNode.status == "0" ? {color:"#CCCCCC"} : {};
}
//点击树节点  触发事件     将当前节点值赋值到右侧展示
function clickTree(event,treeId,treeNode){
	$("#titleUpdate").html("资源详情"); 
	//重置校验
	resetFormWW();
	$("#addbutton").attr("disabled", false);
	$("#editbutton").attr("disabled", false);
	$("#delbutton").attr("disabled", false);
	//无效图标不可删除
	if(treeNode.status=="0"){
		$("#delbutton").attr("disabled", true);
	}else{
		$("#delbutton").attr("disabled", false);
	}
	//根节点  没有父节点  
	var parentNode = treeNode.getParentNode();
	if(parentNode==null){
		//  根节点不能修改和删除  
		$("#editbutton").attr("disabled", true);
		$("#delbutton").attr("disabled", true);
		$("#parentResName").val("无父节点");
	}else{
		$("#parentResName").val(parentNode.name);
		$("#parentResCode").val(parentNode.id);
	}
	$("#resCode").val(treeNode.id);
	$("#resName").val(treeNode.name);
	$("#resUri").val(treeNode.resUri);
	$("#resOrder").val(treeNode.resOrder);
	$(".radio").find("input[type=radio][value="+treeNode.resType+"]").prop("checked",true);
	//处理图标
	dealResIconDefault(treeNode.resIcon);
	$("#status").val(treeNode.status);
	$("#remark").val(treeNode.remark);
	//表单置为不可编辑
	initClass();
}
//后台数据  与树节点对应关系     可不写 
function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	for (var i=0, l=childNodes.length; i<l; i++) {
		childNodes[i].id = childNodes[i].resCode;
		childNodes[i].name = childNodes[i].resName;
		childNodes[i].title = childNodes[i].resName;
		childNodes[i].pId = childNodes[i].parentResCode;
		childNodes[i].isParent = true;	
		childNodes[i].icon= "./plugin/ztree-v3/3.5.29/css/zTreeStyle/img/diy/5.png";
	}
	return childNodes;
}
//初始化 
$(document).ready(function(){
	$("#addbutton").attr("disabled", false);
	$("#editbutton").attr("disabled", false);
	$("#delbutton").attr("disabled", false);
	queryResTree();
	resTree  = $.fn.zTree.init($("#resTree"), setting, resNodes);
		$('.emotion').qqFace({	
			id : 'facebox', 
			assign:'saytext', 
		});
		//表单置为不可编辑
		initClass();
		bootstrapValidator();	
		$('#buttonStyle .btn-primary').css("padding","1px");
		
});
function queryResTree() {
	resNodes = resultAjax(urls.initTree, null)	
}
//重新加载树
function reloadTree(){
		//刷新之后定位之前选中节点
	    var resCode = $("#resCode").val(); 
		queryResTree();
		resTree  = $.fn.zTree.init($("#resTree"), setting, resNodes);
		//刷新之后定位之前选中节点
		var node = resTree.getNodeByParam("id",resCode);
		resTree.selectNode(node,true);
		//模拟点击事件
		clickTree(null,null,node)
}

//保存方法
function saveFunction() {
		showMask("mask");
		var data=$('#form1').serialize();
		//序列化获得表单数据，结果为：user_id=12&user_name=John&user_age=20
		var submitData=decodeURIComponent(data,true);
		$.ajax({
            cache: true,
            type: "POST",
            url:requestUrl,
            data:submitData,
            async: false,
            dataType: 'json',
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
    				var result = data.result;
    				if(result!=null && result!=""){
                		roleNodes = result;
                		$('#roleModal').modal('show');
                	}else{
                		/**
                		 * 如果data为空说明用户只有一个角色，后台已经分配，可以直接重新加载树    
                		 * 否则就要等用户选择将资源分配到指定角色之后才可以刷新树
                		 * 
                		 * 修改时不返回数据，将直接走下面的代码，不影响业务
                		 */
                		commonAlert("保存成功！",'success'); 
                		reloadTree();
                		resetFormWW();
                		$("#addbutton").attr("disabled", false);
                		$("#editbutton").attr("disabled", false);
                		$("#delbutton").attr("disabled", false);
                		//表单置为不可编辑
                		initClass();
                	}
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
function closeModel(){
	var resCode = $("#form1 #resCode").val();
	warnAlert('关闭后系统将随机为您\n将该资源分配给您所拥有的一个角色\n您可以返回继续分配\n确认关闭么?',
			urls.saveRule, {"resCode":resCode,"roleCode":null},function(){
				reloadTree();
	    		resetFormWW();
	    		$("#addbutton").attr("disabled", false);
	    		$("#editbutton").attr("disabled", false);
	    		$("#delbutton").attr("disabled", false);
	    		//表单置为不可编辑
	    		initClass();
	    		$('#roleModal').modal('hide');
			})
}
//角色弹出框  点击选择要分配的角色    后台分配资源
searchExpandRole = function(row,$element){
	var roleCode = row.roleCode
	var resCode = $("#form1 #resCode").val();
	alertAjax(urls.saveRule, {"resCode":resCode,"roleCode":roleCode}, function(){
		reloadTree();
		resetFormWW();
		$("#addbutton").attr("disabled", false);
		$("#editbutton").attr("disabled", false);
		$("#delbutton").attr("disabled", false);
		//表单置为不可编辑
		initClass();
	}) 	
	$('#roleModal').modal('hide');
}
/**
 * 弹出模态页  当查找到多个选项是弹出供选择    wangwei  2017/09/206------------角色选择    role------
 */
$('#roleModal').on('show.bs.modal', function (event) { 
	var modal = $(this)  
	modal.find('.modal-title').text("选择要分配的角色");  
	
	$('#roleSelectCondition').bootstrapTable('destroy');
			$('#roleSelectCondition').bootstrapTable({
				data:roleNodes,
				striped : true, // 是否显示行间隔色
				showColumns : false, // 是否显示内容下拉列
				minimumCountColumns : 1, // 最少允许的列数
				// height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
				uniqueId : "ID", // 每一行的唯一标识，一般为主键列
				checkbox:false,//隐藏复选框
				onClickRow:searchExpandRole,// 设置   点击行事件
				columns : [  {
					field : 'roleCode',
					title : '角色编码',
					formatter:function(value){return gridTitle(value)}
				}, {
					field : 'roleName',
					title : '角色名',
					formatter:function(value){return gridTitle(value)}
				},{
					field : 'status',
					title : '状态',
					formatter:function(value){if (value == "1"){ return "有效"; }
					else if (value =="0") { return "无效"; } }
				}]
			});
		function gridTitle(value){
			return value ? '<span title="' + value + '" >' + value + '</span>' : '';
		}
})
//点击新增按钮    
addFunction = function() {
	$("#titleUpdate").html("新增资源菜单");  
	var nodes = resTree.getSelectedNodes();
	if(nodes.length<1){
		commonAlert("没有选中节点",'error'); 
		return false;
	}
	 var roleNode = nodes[0];
	if(roleNode.status=='0'){
	    commonAlert("无效资源，不允许操作",'error'); 
		return false;
	}
	if(roleNode.resType=='2'){
		commonAlert("按钮不能新增菜单",'error'); 
		return false;
	}
	$("#editbutton").attr("disabled", true);
	$("#delbutton").attr("disabled", true);
	
	//新增     请求方法地址
	requestUrl = urls.addRes;
	var resNode = nodes[0];
	$("#parentResName").val(resNode.name);
	$("#parentResCode").val(resNode.id);
	$("#form1 input[type='text']").each(function() {
		$(this).val("");
	});
	$("#form1 #status").val("1");
	$("#form1 #remark").val("");
	$(".radio").find("input[type=radio][value=1]").prop("checked",true);
	//处理图标
	dealResIcon(null);
	//表单可编辑
	removeClass();
}

editFunction = function() {
	$("#titleUpdate").html("修改资源信息");  
	var nodes = resTree.getSelectedNodes();
	if(nodes.length<1){
		commonAlert("没有选中节点",'error'); 
		return false;
	}
	$("#addbutton").attr("disabled", true);
	$("#delbutton").attr("disabled", true);
	//修改   请求方法地址
	requestUrl = urls.editResource;
	//表单可编辑   resCode不可编辑
	removeClass();
	$("#resCode").attr("readonly","readonly");
	//处理图标
	var icon = $("#resIcon").val();
	dealResIcon(icon);
}
//删除某个节点
delFunction = function() {
	var treeObject = $.fn.zTree.getZTreeObj("resTree");
	var nodes = treeObject.getSelectedNodes();
	if(nodes.length<1){
		commonAlert("没有选中节点",'error'); 
		return false;
	}else{
		//修改   请求方法地址
		requestUrl = urls.delResource;
		var node =nodes[0];
		warnAlert('确认删除么?',requestUrl, {"resCode":node.id,"status":"0"}, function(){reloadTree();},"删除成功！");
	}
}
//处理资源图标   展示的方法
function dealResIcon(icon){
	$("#resIcon").nextAll().remove();
	var str = '<div id="saytext" style="font-size:20px;cursor:pointer;" class="emotion"></div>';
	$("#resIcon").parent().append(str);
	$("#saytext").append('<span style="color:#00AAFF;font-size:15px;cursor:pointer;">点击选择</span>');
	$("#resIcon").val("");
	if(icon!=null && icon!=""){
		$("#resIcon").val(icon);
		$('#saytext').find("span").remove();
		$('#saytext').removeClass();
		$('#saytext').addClass(icon);
	}
	$(document).on('click','#saytext',addIcon);
}
//默认情况图标不可选
function dealResIconDefault(icon){
	$("#resIcon").nextAll().remove();
	var str = '<div id="defaultId" style="font-size:20px;cursor:pointer;"></div>';
	$("#resIcon").parent().append(str);

	$("#defaultId").append('<span style="color:gray;font-size:15px;cursor:pointer;">点击选择</span>');
	if(icon!=null && icon!=""){
		$("#resIcon").val(icon);
		$('#defaultId').find("span").remove();
		$('#defaultId').removeClass();
		$('#defaultId').addClass(icon);
	}else{
		$("#resIcon").val('');
	}
}
//处理   右侧修改表单    和底部按钮
function initClass(){
	$("#resCode").attr("disabled","disabled");
	$("#resName").attr("disabled","disabled")
	$("#resUri").attr("disabled","disabled");
	$("#resOrder").attr("disabled","disabled");
	$(".radio").find("input[type=radio]").attr("disabled","disabled");
	$("#status").attr("disabled","disabled")
	$("#remark").attr("disabled","disabled");
	$("#dealDiv").css("display", "none");
}
//处理   右侧修改表单    和底部按钮
function removeClass(){
	$("#resCode").removeAttr("disabled");
	$("#resCode").removeAttr("readonly");
	$("#resCode").css("background-color", "");
	$("#resName").removeAttr("disabled");
	$("#resUri").removeAttr("disabled");
	$("#resOrder").removeAttr("disabled");
	$(".radio").find("input[type=radio]").removeAttr("disabled");
	$("#status").removeAttr("disabled");
	$("#remark").removeAttr("disabled");

	$("#dealDiv").css("display", "")
}

//---------------------------------------表单验证------------
function bootstrapValidator(){
$('#form1')
.bootstrapValidator({
    message: 'This value is not valid',
    group:'.rowGroup',
    //submitButtons: 'button[id="saveButton"]',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        resCode: {
            message: '不能为空',
            validators: {
                notEmpty: {
                    message: '编码不能为空'
                }
            }
        },
        resName: {
            validators: {
                notEmpty: {
                    message: '菜单名称不能为空'
                }
            }
        },
        resUri: {
            validators: {
                notEmpty: {
                    message: '路径不能为空'
                }
            }
        },
        resOrder: {
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


//点击保存按钮时  判断校验是否通过
$("#saveButton").click(function(){
	//修改的时候   父资源无效，不能修改子资源
	if(requestUrl==urls.editResource){
		var treeObj = $.fn.zTree.getZTreeObj("resTree");
		var nodes = treeObj.getSelectedNodes();
		var parentCode;
		if(nodes.length>=1){
			parentNode = nodes[0].getParentNode();
			if($("#status").val() == "1" && parentNode.status=="0"){
				commonAlert("父节资源无效，当前资源不能设为有效",'error');
				return;
			}
		}
	}
	  var bootstrapValidator = $("#form1").data('bootstrapValidator');
	  bootstrapValidator.validate();
	  if(bootstrapValidator.isValid()){
		  saveFunction();
	  }else{
		  return;
	  }
});
//点击取消按钮  恢复到修改之前的样子
function resFromInvalid(){
	$("#titleUpdate").html("资源详情"); 
	var treeObj = $.fn.zTree.getZTreeObj("resTree");
	var nodes = treeObj.getSelectedNodes();
	if(nodes.length<1){
		commonAlert("没有选中节点",'error'); 
		return;
	}
	var treeNode = nodes[0];
	
	$("#resCode").val(treeNode.id);
	$("#resName").val(treeNode.name);
	$("#resUri").val(treeNode.resUri);
	$("#resOrder").val(treeNode.resOrder);
	$("input[type='radio']").attr("checked",false);
	$(".radio").find("input[type=radio][value="+treeNode.resType+"]").prop("checked",true);
	//处理图标
	dealResIconDefault(treeNode.resIcon);
	$("#status").val(treeNode.status);
	$("#remark").val(treeNode.remark);
	//重置校验
	resetFormWW();
	//表单置为不可编辑
	initClass();
	$("#addbutton").attr("disabled", false);
	$("#editbutton").attr("disabled", false);
	$("#delbutton").attr("disabled", false);
}
//重置校验
function resetFormWW(){
	$("#form1").data('bootstrapValidator').destroy();
	$('#form1').data('bootstrapValidator', null);
	bootstrapValidator();	
}