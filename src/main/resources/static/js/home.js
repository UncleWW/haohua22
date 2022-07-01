$(document).ready(function() {
	
	$('.voucherManager').click(function() {
		window.location.href = $.contextPath() + "/voucher";
		});

	$('.voucherListManager').click(function() {
		window.location.href = $.contextPath() + "/voucherList";
		});

	$('.paymentHistoryManager').click(function() {
		window.location.href = $.contextPath() + "/paymentHistory";
		});

	$('.moniManager').click(function() {
		window.location.href = $.contextPath() + "/entity";
		});
		

	// 1.初始化Table
	var oTable = new TableInit();
	//oTable.Init();
		
	});

var TableInit = function() {
	var oTableInit = new Object();

	oTableInit.Init = function() {
		$('#tb-onlineInterfacelist').bootstrapTable({
			ajax: function(params) {
				params.success(changeLine(resultAjax('/queryHomePage', params.data)));
			},
//			url : $.contextPath() + '/user/queryUserPage', // 请求后台的URL（*）
//			method : 'get', // 请求方式（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true, // 是否显示行间隔色
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : false, // 是否显示分页（*）
			sortable : false, // 是否启用排序
			sortOrder : "asc", // 排序方式
			//queryParams : oTableInit.queryParams,// 传递参数（*）
//			sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
//			pageNumber : 1, // 初始化加载第一页，默认第一页
//			pageSize : 5, // 每页的记录行数（*）
//			pageList : [5, 10 ], // 可供选择的每页的行数（*）
//			paginationLoop: false,
			//search : true, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
//			strictSearch : true,
			showColumns : false, // 是否显示所有的列
			//showRefresh : true, // 是否显示刷新按钮
			minimumCountColumns : 2, // 最少允许的列数
			showHeader : false, // 是否展示头
			clickToSelect : true, // 是否启用点击选中行
			// height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId : "ID", // 每一行的唯一标识，一般为主键列
			showToggle : false, // 是否显示详细视图和列表视图的切换按钮
			cardView : false, // 是否显示详细视图
			//checkboxHeader: true,
			detailView : false, // 是否显示父子表
			columns : [ {
				field : 'interfaceProjectCode0',
				halign: 'left',
				align: 'center',
				formatter:function(value, row, index){
					return "<a href='" + row.interfaceProjectAddress0 + "' target='_blank'>" + row.interfaceProjectCode0 + "</a>";
					}
			},{
				field : 'interfaceProjectCode1',
				halign: 'left',
				align: 'center',
				formatter:function(value, row, index){
					return "<a href='" + row.interfaceProjectAddress1 + "' target='_blank'>" + row.interfaceProjectCode1 + "</a>";
					}
			},{
				field : 'interfaceProjectCode2',
				halign: 'left',
				align: 'center',
				formatter:function(value, row, index){
					return "<a href='" + row.interfaceProjectAddress2 + "' target='_blank'>" + row.interfaceProjectCode2 + "</a>";
					}
			},{
				field : 'interfaceProjectCode3',
				halign: 'left',
				align: 'center',
				formatter:function(value, row, index){
					return "<a href='" + row.interfaceProjectAddress3 + "' target='_blank'>" + row.interfaceProjectCode3 + "</a>";
					}
			}],
		});
	};
	function gridTitle(value){
		return value ? '<span title="' + value + '" >' + value + '</span>' : '';
	}
	return oTableInit;
};		

function changeLine(list) {
	
	var listlink = new Array();
	var count = Math.ceil(list.length / 4);
	var j = 0;     
	for(var i = 0; i < count; i++){
		var line = '{';
		for(var k = 0; k < 4; k++) {
			line += '"interfaceProjectCode' + k + '":"' + (undefined == list[j] ? '' : list[j].interfaceProjectCode)
				  + '","interfaceProjectAddress' + k + '":"' + (undefined == list[j] ? '' : list[j].interfaceProjectAddress) + '",'
			j++;
		}
		line = line.substring(0, line.length - 1);
		line += '}';
		listlink.push(JSON.parse(line));
	}
	return listlink;
}

