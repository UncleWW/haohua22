$(document).ready(function() {
    // 1.初始化Table
    var oTable = new TableInit();
    oTable.init();

    //初始化下拉框
    initSelect();
    bootstrapValidator();
    //绑定回车查询事件
    $(document).keyup(function (e) {//捕获文档对象的按键弹起事件
        if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了
            $('#tb_paymentHistory').bootstrapTable('refresh');
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
    //收款日期，默认今天
    $('#payDate').datetimepicker({
        minView: "month",
        language:  'zh-CN',
        weekStart: 0,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        forceParse: 0,
        format:'yyyy-mm-dd',
        orientation:"bottom-left",
        initialDate:new Date()
    });
    $('#payDate').datetimepicker('setDate', new Date());
});
//查询按钮
$("#payment-query-btn").click(function(){
    $('#tb_paymentHistory').bootstrapTable('refresh');
});

var TableInit = function() {
    var paymentHistory = new Object();
    paymentHistory.init = function() {
        $('#tb_paymentHistory').bootstrapTable({
            ajax: function(params) {
                params.success(resultAjax('/paymentHistory/queryAllCustomerPaymentHistoryPage', params.data));
            },
            //url : $.contextPath() + '/onlineInterface/getonlineInterfaceListPage', // 请求后台的URL（*）
            //method : 'get',                               // 请求方式（*）
            //toolbar : '#toolbar',                         // 工具按钮用哪个容器
            striped : true,                               // 是否显示行间隔色
            cache : false,                                // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination : true,                            // 是否显示分页（*）
            sortable : false,                             // 是否启用排序
            sortOrder : "desc",                           // 排序方式
            queryParams : paymentHistory.queryParams,// 传递参数（*）
            sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
            pageNumber : 1, // 初始化加载第一页，默认第一页
            pageSize : 5, // 每页的记录行数（*）
            pageList : [ 5, 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
            paginationLoop: false,
            //search : true, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            //strictSearch : true,
            showColumns : true, // 是否显示所有的列
            //showRefresh : true, // 是否显示刷新按钮
            //minimumCountColumns : 2, // 最少允许的列数
            clickToSelect : true, // 是否启用点击选中行
            // height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId : "customerName", // 每一行的唯一标识，一般为主键列
            showToggle : true, // 是否显示详细视图和列表视图的切换按钮
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
                field : 'totalAmount',
                title : '销售总额（元）',
                halign: 'center',
                align: 'right',
                formatter:function(value){ return formatMoney(value);}
            },{
                field : 'totalPayAmount',
                title : '累计付款（元）',
                halign: 'center',
                align: 'right',
                formatter:function(value){ return formatMoney(value);}
            },{
                field : 'totaldebtAmount',
                title : '累计欠款（元）',
                halign: 'center',
                align: 'right',
                formatter:function(value){ return dealRedColor(formatMoney(value));}
            },{
                field : '',
                title : '操作',
                halign: 'center',
                align: 'center',
                events: operateEventsFather,
                formatter : operateFormatterFather
            }],
            onLoadSuccess: function (data) {
                var customerName =$("#txt_search_customerName").val();
                var payDateBegin = $("#search_start_time").val();
                var payDateEnd = $("#search_end_time").val();
                if((customerName != "" && customerName !=null) || (payDateBegin != "" && payDateBegin !=null)
                    || (payDateEnd != "" && payDateEnd !=null)){
                    for(var i=0;i<data.rows.length;i++){
                        $('#tb_paymentHistory').bootstrapTable('expandRow', i);
                    }
                }
                return true;
            },
            onExpandRow: function (index, row, $detail) {
                paymentHistory.initSub(index, row, $detail);
            }
        });
    };
    function operateFormatterFather(value, row, index) {
        return  "<a role='button' class='optionEditFather'>新增</a>";
    }
    window.operateEventsFather ={
        'click .optionEditFather':function(e,value, row, index){
            $("#myModalLabel").html("新增付款记录");
            $("#customerSelect").val(row.customerId);
            $("#payAmountId").val("");
            $("#payeeId").val($("#fragment_loginUserName").html());
            $("#remarkId").val("");
            $('#payment_saveBtn').attr("onclick","addPayment()");
            $("#myModal").modal();
        }
    };
    function dealRedColor(value){
        return value ? '<span title="' + value + '" style="color: red" >' + value + '</span>' : '';
    }
    paymentHistory.queryParams = function(params) {

        var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit, // 页面大小
            offset: params.offset, // 页码
            customerName: $("#txt_search_customerName").val(),
            payDateBegin : $("#search_start_time").val(),//startTime
            payDateEnd: $("#search_end_time").val()//endTime

        };
        return temp;
    };
    paymentHistory.initSub = function (index, row, $detail) {

        var currTable = $detail.html('<table></table>').find('table');
        currTable.queryParams= function(params) {
            var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit, // 页面大小
                offset: params.offset, // 页码
                customerId: row.customerId,
                payDateBegin : $("#search_start_time").val(),//startTime
                payDateEnd: $("#search_end_time").val()//endTime
            };
            return temp;
        };
        $(currTable).bootstrapTable({
            ajax: function(params) {
                params.success(resultAjax('/paymentHistory/queryPayHistoryByCustomerIdPage', params.data));
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
                return { classes: "danger" }
            },
            columns : [ {
                field : 'index',
                title : '序号',
                halign: 'center',
                align: 'center',
                formatter: function(value, row, index) { return index + 1; }
            },{
                field : 'payAmount',
                title : '金额（元）',
                halign: 'center',
                align: 'right',
                formatter:function(value){ return formatMoney(value);}
            },{
                field : 'payDate',
                title : '收款日期',
                halign: 'center',
                align: 'center',
            },{
				field : 'payee',
				title : '收款人',
				halign: 'center',
				align: 'center',
			},{
                field : 'createdTime',
                title : '创建时间',
                halign: 'center',
                align: 'center',
            },{
                field : 'modifiedTime',
                title : '修改时间',
                halign: 'center',
                align: 'center',
            },{
                field : 'remark',
                title : '备注',
                halign: 'center',
                align: 'center',
            },{
                field : '',
                title : '操作',
                halign: 'center',
                align: 'center',
                visible : visible(),
                events: operateEvents,
                formatter : operateFormatter
            }]
        });
    }

    var formatMoney = function (value, type) {
        if (value == null || value == '') {
            return '';
        }
        if (type == null || type == '') {
            type  = 2;
        }
        return value.toFixed(type).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
    };
    function visible(){
        var loginUser = $("#fragment_loginUserName").html();
        if("admin" == loginUser){
            return true;
        }else {
            return false;
        }
    }
    function operateFormatter(value, row, index) {
        return  "<a role='button' class='optionEdit'>修改</a>";
    }
    window.operateEvents ={
        'click .optionEdit':function(e,value, row, index){
            $("#myModalLabel").html("修改付款记录");
            $("#updatePhId").val(row.phId);
            $("#customerSelect").val(row.customerId);
            $("#payAmountId").val(row.payAmount);
            $("#payeeId").val(row.payee);
            $("#payDateId").val(row.payDate);
            //$('#payDate').datetimepicker('setDate', row.payDate);
            $("#remarkId").val(row.remark);
            $('#payment_saveBtn').attr("onclick","updatePayment()");
            $("#myModal").modal();
        }
    };
    return paymentHistory;
};

//初始化客户下拉框
function initSelect(){
    var customers = resultAjax('/paymentHistory/queryAllCustomer');
    for(var i in customers){
        $("#customerSelect").append('<option value="'+customers[i].customerId+'" >'+customers[i].customerName+'</option>');
    }
}
$("#payment-add-btn").click(function () {
    $("#myModalLabel").html("新增付款记录");
    //收款人赋值
    $("#payeeId").val($("#fragment_loginUserName").html());
    //清空上次赋值
    $("#payAmountId").val("");
    $("#remarkId").val("");
    //保存事件
    $('#payment_saveBtn').attr("onclick","addPayment()");
    $('#myModal').modal({backdrop:false});
})
//新增雇员  信息
function addPayment() {
    var bootstrapValidator = $("#paymentForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        paramsAjax('/paymentHistory/addPayment', saveParams(), function() {
            $('#myModal').modal('hide')
            $('#tb_paymentHistory').bootstrapTable('refresh');
        });
    } else {
        return;
    }
};

//修改雇员  信息
function updatePayment() {
    var bootstrapValidator = $("#paymentForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        paramsAjax('/paymentHistory/updatePayment', saveParams(), function() {
            $('#myModal').modal('hide')
            $('#tb_paymentHistory').bootstrapTable('refresh');
        });
    } else {
        return;
    }
};

var saveParams = function() {
    var params = {
        //phid 修改时有效
        phId:$("#updatePhId").val().trim(),
        customerId:$("#customerSelect").val().trim(),
        payAmount: $("#payAmountId").val().trim(),
        payDate: $("#payDateId").val().trim(),
        payee: $("#payeeId").val().trim(),
        remark: $("#remarkId").val().trim()
    }
    return JSON.stringify(params);
}

//approveRole-modal验证销毁重构
$('#myModal').on('hidden.bs.modal', function() {
    $("#paymentForm").data('bootstrapValidator').destroy();
    $('#paymentForm').data('bootstrapValidator', null);
    bootstrapValidator();

});

function bootstrapValidator() {
    $('#paymentForm').bootstrapValidator({
        message : 'This value is not valid',
        group : '.rowGroup',
        feedbackIcons : {
            valid : 'glyphicon glyphicon-ok',
            invalid : 'glyphicon glyphicon-remove',
            validating : 'glyphicon glyphicon-refresh'
        },
        fields : {
            customerId: {
                validators: {
                    notEmpty: {
                        message: '请选择客户'
                    },
                }
            },
            payAmount: {
                validators: {
                    notEmpty: {
                        message: '请填写正确的金额'
                    },
                    regexp: {
                        regexp: /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/,
                        message: '请填写正确的金额'
                    }
                }
            },
            remark: {
                validators: {
                    stringLength: {
                        max: 10,
                        message: '最多输入10个汉字'
                    }
                }
            }
        }
    });
}