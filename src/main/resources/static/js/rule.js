var roleNodes="";
var resNodes="";
var resTree;//树  定义全局树
var roleTree;//全局树

var urls = {
			queryRoleTree:   '/rule/queryRoleTree',	
			queryResTree:    '/rule/queryResTree',
				saveRule:    '/rule/saveRule',
			queryRuleByRoleCode:  $.contextPath() + '/rule/queryRuleByRoleCode'
		};
var roleSetting = {
		data: {
			simpleData: {
				enable: true
			}
		},
	    callback:{
	    	onClick:clickRoleTree
	    },
		view:{
			fontCss:setFontClass
		}
};
var resSetting = {
		data: {
			simpleData: {
				enable: true
			}
		},
	    check:{
	    	enable: true,
			chkStyle: "checkbox",
			chkboxType: { "Y": "ps", "N": "s" }
	    },
		view:{
			fontCss:setFontClass
		}
	};
function setFontClass(treeId,treeNode){
	return treeNode.status == "0" ? {color:"#CCCCCC"} : {};
}
//点击树节点  触发事件    查询角色所拥有的资源
function clickRoleTree(event,treeId,treeNode){//不用alert 没写成通用的
   var treeObject = $.fn.zTree.getZTreeObj("resTree");
   //treeObject.cancelSelectedNode();
   treeObject.checkAllNodes(false);
   var roleCode = treeNode.id;
   $.ajax({
	      cache: true,
	      type: "POST",
	      url:urls.queryRuleByRoleCode,
	      data:{"roleCode":roleCode},
	      async: false,
	      dataType: 'json',
	      error: function(data) {
	    	var responseText = data.responseText;
			if(isContains(responseText,"您没有权限访问该页面")){
				commonAlert('您的权限不足，请联系管理员','error');
			}else{
				commonAlert('登录超时','error');
			}
	      },
	      success: function(data) {
	    	  if ('success' == data.resultFlag) {
					result = data.result;
					for (var i = 0; i < result.length; i++) {
			    		  var node = treeObject.getNodeByParam("id",result[i]);
			    		  if(node!=null){
			    			  treeObject.checkNode(node,true);
			    		  }
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

function queryRoleTree() {
	roleNodes = resultAjax(urls.queryRoleTree, "");	
}

function queryResTree() {
	resNodes = resultAjax(urls.queryResTree, "")	;
}
//初始化 
$(document).ready(function(){
	queryResTree();
	queryRoleTree();
	roleTree = $.fn.zTree.init($("#roleTree"), roleSetting,roleNodes);
	resTree  = $.fn.zTree.init($("#resTree"), resSetting, resNodes);
	//绑定回车查询事件
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	 var p1 = $('#roleIdExpand').val();
        	 var p2 = $('#resIdExpand').val();
        	 if(p1 != "" && p2 == ""){
        		 roleIdExpand();
        	 }else if(p1 == "" && p2 != ""){
        		 resIdExpand();
        	 }else if(p1!="" && p1!=""){
        		 selectAlert('您想查询哪个？',"角色","资源",function(){roleIdExpand();},function(){resIdExpand();});
        	 }
	    }  
	}); 
	$('.btn-primary').css("padding","1px");
});
//查找  定位  角色树节点
function roleIdExpand(){
	var treeObj = $.fn.zTree.getZTreeObj("roleTree");
	//定位节点之前先清除所有选中状态
	treeObj.cancelSelectedNode();
	var roleName = $("#roleIdExpand").val();
	var roleNodes = treeObj.getNodesByParamFuzzy("name",roleName);
	if(roleNodes!=null && roleNodes.length>1){
		$('#roleModal').modal('show');
	}else if(roleNodes.length==1){
		treeObj.selectNode(roleNodes[0],true);
		//资源勾选
		clickRoleTree(null,null,roleNodes[0]);
	}else{
		commonAlert("没有找到",'warning'); 
	}
}
//查找  定位  资源树节点
function resIdExpand(){
	var treeObj = $.fn.zTree.getZTreeObj("resTree");
	//定位节点之前先清除所有选中状态
	treeObj.cancelSelectedNode();
	var resName = $("#resIdExpand").val();
	var resNodes = treeObj.getNodesByParamFuzzy("name",resName);
	if(resNodes!=null && resNodes.length>1){
		$('#resModal').modal('show');
		var height = $('#resModal .fixed-table-container').height();
		if(height>=500){
			$('#resModal .fixed-table-container').css('height', '500px');
		}
	}else if(resNodes.length==1){
		treeObj.selectNode(resNodes[0],true);
	}else{
		commonAlert("没有找到",'warning'); 
	}
}
//角色弹出框  点击列展开事件
searchExpandRole = function(row,$element){
	var treeObj = $.fn.zTree.getZTreeObj("roleTree");
	var roleId = row.id
	var roleNode = treeObj.getNodeByParam("id",roleId);
	treeObj.selectNode(roleNode,true);
	clickRoleTree(null,null,roleNode);
	$('#roleModal').modal('hide');
}
//资源弹出框  点击列展开事件
searchExpandRes = function(row,$element){
	var treeObj = $.fn.zTree.getZTreeObj("resTree");
	var resId = row.id
	var resNode = treeObj.getNodeByParam("id",resId);
	treeObj.selectNode(resNode,true);
	$('#resModal').modal('hide');
}
/**
 * 弹出模态页  当查找到多个选项是弹出供选择    wangwei  2017/09/06------------角色选择    role------
 */
$('#roleModal').on('show.bs.modal', function (event) { 
	var modal = $(this)  
	modal.find('.modal-title').text("选择角色节点");  
	var treeObj = $.fn.zTree.getZTreeObj("roleTree");
	var roleName = $("#roleIdExpand").val();
	var roleNodes = treeObj.getNodesByParamFuzzy("name",roleName);
	$('#roleSelectCondition').bootstrapTable('destroy');
			$('#roleSelectCondition').bootstrapTable({
				data:roleNodes,
				striped : true, // 是否显示行间隔色
				showColumns : false, // 是否显示内容下拉列
				minimumCountColumns : 1, // 最少允许的列数
				//height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
				uniqueId : "ID", // 每一行的唯一标识，一般为主键列
				checkbox:false,//隐藏复选框
				onClickRow:searchExpandRole,// 设置   点击行事件
				columns : [  {
					field : 'id',
					title : '角色编码',
					formatter:function(value){return gridTitle(value)}
				}, {
					field : 'name',
					title : '角色名',
					formatter:function(value){return gridTitle(value)}
				}, {
					field : 'pId',
					title : '父节点',
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
/**
 * 弹出模态页  当查找到多个选项是弹出供选择    wangwei  2017/09/06------------资源选择    Resource------
 */
$('#resModal').on('show.bs.modal', function (event) { 
	var modal = $(this)  
	modal.find('.modal-title').text("选择资源节点");  
	var treeObj = $.fn.zTree.getZTreeObj("resTree");
	var resName = $("#resIdExpand").val();
	var resNodes = treeObj.getNodesByParamFuzzy("name",resName);
	$('#resSelectCondition').bootstrapTable('destroy');
			$('#resSelectCondition').bootstrapTable({
				data:resNodes,
				toolbar : '#toolbar', // 工具按钮用哪个容器
				striped : true, // 是否显示行间隔色
				showColumns : false, // 是否显示内容下拉列
				minimumCountColumns : 1, // 最少允许的列数
				clickToSelect : true, // 是否启用点击选中行
			    //height : 501, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
				uniqueId : "ID", // 每一行的唯一标识，一般为主键列
				cardView : false, // 是否显示详细视图
				detailView : false, // 是否显示父子表
				checkbox:false,//隐藏复选框
				silentSort:false, //设置为 false 将在点击分页按钮时，自动记住排序项。仅在 sidePagination设置为 server时生效.
				onClickRow:searchExpandRes,//设置点击行事件
				columns : [{
					field : 'id',
					title : '资源编码',
					formatter:function(value){return gridTitle(value)}
				}, {
					field : 'name',
					title : '资源名',
					formatter:function(value){return gridTitle(value)}
				}, {
					field : 'pId',
					title : '父节点',
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
//---------------------------jia   zai shu   wan   cheng ---------------树加载完成  ---------
function saveRule(){
	var nodes = roleTree.getSelectedNodes();
	if(nodes.length<1){
		commonAlert("没有选中节点",'error'); 
		return false;
	}
	var resCodes = new Array();
	var roleNode = nodes[0];
	if(roleNode.id=='ROOT_ROLE'){
		commonAlert("根节点不允许操作",'error'); 
		return false;
	}
	var parentRoleCode = roleNode.pId;
	var nodes = resTree.getCheckedNodes(true);
	for(var i=0;i<nodes.length;i++){
		resCodes.push(nodes[i].id);
	}	
	alertAjax(urls.saveRule, {"roleCode":roleNode.id,"parentRoleCode":parentRoleCode,"resCodes":resCodes},function(){});	
}