$(document).ready(function() {
    // 1.初始化Table
    var oTable = new TableInit();
    oTable.init();
    // 2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

    //绑定回车查询事件
    $(document).keyup(function (e) {//捕获文档对象的按键弹起事件
        if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了
            $('#tb_voucher').bootstrapTable('refresh');
        }
    });

    $('.form_datetime').datetimepicker({
        minView: "month",
        language:  'zh-CN',
        weekStart: 0,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        forceParse: 0,
        format:'yyyy-mm-dd',
        orientation:"bottom-left"
        /*showMeridian: 1*/
    });
});


var TableInit = function() {
    var voucherListTable = new Object();
    voucherListTable.init = function() {
        $('#tb_voucher').bootstrapTable({
            ajax: function(params) {
                params.success(resultAjax('/voucherList/queryVoucherListPage', params.data));
            },
            //url : $.contextPath() + '/onlineInterface/getonlineInterfaceListPage', // 请求后台的URL（*）
            //method : 'get',                               // 请求方式（*）
            //toolbar : '#toolbar',                         // 工具按钮用哪个容器
            striped : true,                               // 是否显示行间隔色
            cache : false,                                // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination : true,                            // 是否显示分页（*）
            sortable : false,                             // 是否启用排序
            sortOrder : "desc",                           // 排序方式
            queryParams : voucherListTable.queryParams,// 传递参数（*）
            sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
            pageNumber : 1, // 初始化加载第一页，默认第一页
            pageSize : 5, // 每页的记录行数（*）
            pageList : [ 5, 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
            paginationLoop: false,
            //search : true, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            //strictSearch : true,
            //showColumns : true, // 是否显示所有的列
            //showRefresh : true, // 是否显示刷新按钮
            //minimumCountColumns : 2, // 最少允许的列数
            clickToSelect : true, // 是否启用点击选中行
            // height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId : "customerName", // 每一行的唯一标识，一般为主键列
            showToggle : false, // 是否显示详细视图和列表视图的切换按钮
            cardView : false, // 是否显示详细视图
            //checkboxHeader: true,
            detailView : true, // 是否显示父子表
            columns : [ {
                field : 'index',
                title : '序号',
                halign: 'center',
                align: 'center',
                formatter: function(value, row, index) { return index + 1; }
            },{
                field : 'customerName',
                title : '客户名称',
                halign: 'center',
                align: 'center',
            },{
                field : 'amount',
                title : '总额（元）',
                halign: 'center',
                align: 'right',
                formatter:function(value){ return formatMoney(value);}
            },{
                field : 'voucherNnm',
                title : '数量（单）',
                halign: 'center',
                align: 'center',
                formatter:function(value){ return gridTitle(value);}
            }],
            onLoadSuccess: function (data) {
                //var address=$("#txt_search_interfaceAddress").val();
                var customerName =$("#txt_search_customerName").val();
                var voucherDate_begin = $("#search_start_time").val();
                var voucherDate_end = $("#search_end_time").val();
                if((customerName != "" && customerName !=null) || (voucherDate_begin != "" && voucherDate_begin !=null)  || (voucherDate_end != "" && voucherDate_end !=null)){
                    for(var i=0;i<data.rows.length;i++){
                        $('#tb_voucher').bootstrapTable('expandRow', i);
                    }
                }
                return true;
            },
            onExpandRow: function (index, row, $detail) {
                voucherListTable.initSub(index, row, $detail);
            }
        });
    };

    function dealRedColor(value){
        return value ? '<span title="' + value + '" style="color: red" >' + value + '</span>' : '';
    }
    voucherListTable.queryParams = function(params) {

        var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit, // 页面大小
            offset: params.offset, // 页码
            customerName: $("#txt_search_customerName").val(),
            voucherDate_begin : $("#search_start_time").val(),//startTime
            voucherDate_end: $("#search_end_time").val()//endTime

        };
        return temp;
    };
    voucherListTable.initSub = function (index, row, $detail) {

        var currTable = $detail.html('<table></table>').find('table');
        currTable.queryParams= function(params) {
            var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit, // 页面大小
                offset: params.offset, // 页码
                voucherId: $("#txt_search_voucherId").val(),
                customerId: row.customerId,
                customerName: $("#txt_search_customerName").val(),
                voucherDate_begin : $("#search_start_time").val(),//startTime
                voucherDate_end: $("#search_end_time").val()//endTime
            };
            return temp;
        };
        $(currTable).bootstrapTable({
            ajax: function(params) {
                params.success(resultAjax('/voucherList/queryVoucherSonListPage', params.data));
            },
            /*url : $.contextPath() + '/onlineInterface/getOnlineInterfaceListDetail', // 请求后台的URL（*）
            method : 'get',  */                             // 请求方式（*）
            //toolbar : '#toolbar',                         // 工具按钮用哪个容器
            striped : true,                               // 是否显示行间隔色
            cache : false,                                // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination : true,                            // 是否显示分页（*）
            //sortable : false,                             // 是否启用排序
            //sortOrder : "desc",                           // 排序方式
            //queryParams : {interfaceProjectAddress: row.interfaceProjectAddress,onlineInterfaceAddress: $("#txt_search_interfaceAddress").val().trim(),onlineInterfaceCode: $("#txt_search_interfaceCode").val().trim(),interfaceName: $("#txt_search_interfaceName").val().trim()},
            queryParams:currTable.queryParams,
            sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
            pageNumber : 1, // 初始化加载第一页，默认第一页
            pageSize : 5, // 每页的记录行数（*）
            pageList : [ 5, 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
            paginationLoop: false,
            //search : true, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            //strictSearch : true,
            showColumns : false, // 是否显示所有的列
            //showRefresh : true, // 是否显示刷新按钮
            //minimumCountColumns : 2, // 最少允许的列数
            clickToSelect : true, // 是否启用点击选中行
            // height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            //uniqueId : "onlineInterfaceAddress", // 每一行的唯一标识，一般为主键列
            showToggle : false, // 是否显示详细视图和列表视图的切换按钮
            cardView : false, // 是否显示详细视图
            //checkboxHeader: true,
            detailView : false, // 是否显示父子表
            rowStyle: function (row, index) {
                //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
                return { classes: "warning" }
            },
            columns : [ {
                field : 'index',
                title : '序号',
                halign: 'center',
                align: 'center',
                formatter: function(value, row, index) { return index + 1; }
            }, {
                field : 'voucherId',
                title : '凭证号',
                halign: 'center',
                align: 'center',
                formatter: function(value, row, index) { return gridTitle(value); }
            }, {
                field : 'customerName',
                title : '客户名',
                halign: 'center',
                align: 'center',
                formatter: function(value, row, index) { return gridTitle(value); }
                },{
                field : 'amount',
                title : '总额（元）',
                halign: 'center',
                align: 'right',
                formatter:function(value){ return formatMoney(value);}
            },{
                field : 'voucherDate',
                title : '发票日期',
                halign: 'center',
                align: 'center',
            },{
				field : 'voucherMaker',
				title : '制单人',
				halign: 'center',
				align: 'center',
			},{
                field : '',
                title : '操作',
                halign: 'center',
                align: 'center',
                events: operateEvents,
                formatter : operateFormatter
            }]
        });
        //$(currTable).bootstrapTable('hideColumn', 'interfaceCode');
    }

    function gridTitle(value){
        return value ? '<span title="' + value + '" >' + value + '</span>' : '';
    }
    function operateFormatter(value, row, index) {
        return  "<a role='button' class='optionEdit'>查看</a>";
    }
    window.operateEvents ={
        'click .optionEdit':function(e,value, row, index){
          // commonAlert("跳转到凭证详情"+row.voucherId,"warning");
           window.location.href="/voucher?voucherId="+row.voucherId;
        }
    };
    var formatMoney = function (value, type) {
        if (value == null || value == '') {
            return '';
        }
        if (type == null || type == '') {
            type  = 2;
        }
        return value.toFixed(type).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
    };

    return voucherListTable;
};

var ButtonInit = function() {
    var oInit = new Object();

    oInit.Init = function() {
        //初始化页面上面的按钮事件
        $('#voucher-query-btn').click(function() {
            $('#tb_voucher').bootstrapTable('refresh');
        });

    }
    return oInit;
};


