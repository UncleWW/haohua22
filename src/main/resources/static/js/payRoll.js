$(document).ready(function() {

    $('.form_datetime').datetimepicker({
        language:  'zh-CN',
        format:"yyyy-mm",
        minView: 3,  //日期时间选择器所能够提供的最精确的时间选择视图。
        weekStart: 0, //一周从哪一天开始。0（星期日）到6（星期六）
        todayBtn:  "linked", //如果此值为true 或 "linked"，则在日期时间选择器组件的底部显示一个 "Today" 按钮用以选择当前日期。
        // 如果是true的话，"Today" 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        autoclose: true, //当选择一个日期之后是否立即关闭此日期时间选择器。
        todayHighlight: true, //如果为true, 高亮当前日期。
        keyboardNavigation:true,//是否允许通过方向键改变日期。
        startView: 3,//0-4   0小时1天2月3年
        pickerPosition:'bottom-left',
        initialDate: new Date(),
        forceParse: 0/*,
        showMeridian: 1*/
    });
    $('#search_input_salaryDate').val(getNowMonth());

    // 1.初始化Table
    var oTable = new TableInit();
    oTable.init();
    // 2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

    bootstrapValidator();

    //绑定回车查询事件
    $(document).keyup(function (e) {//捕获文档对象的按键弹起事件
        if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了
            $('#tb_salary').bootstrapTable('refresh');
        }
    });
});
$("#ceate_New_Month_Bill").click(function(){
    selectAlert('确认要新建月账单么',"取消","确认",null,function(){createNewMonthBill();});
});
function createNewMonthBill(){
    alertAjax('/payRoll/createNewMonthBill',  null, function () {
        $('#tb_salary').bootstrapTable('refresh'); 
    }, "新建完成")
}
//approveRole-modal验证销毁重构
$('#employeeSalary-modal').on('hidden.bs.modal', function() {
    $("#salaryForm").data('bootstrapValidator').destroy();
    $('#salaryForm').data('bootstrapValidator', null);
    bootstrapValidator();

});

$("#change_salaryPaid").blur(function () {
    var str = $(this).val();
    if (str != "" && str != null) {
        var re = /^\d+(\.\d+)?$/;//非负浮点数（正浮点数 + 0）
        if (!re.test(str)) {
            $(this).val("");
            $('#salaryForm').data("bootstrapValidator").resetForm();
            $('#salaryForm').data("bootstrapValidator").validateField('salary');
        } else {
            $("#change_salaryPaid").val(formatMoney(str));
            $('#salaryForm').data("bootstrapValidator").resetForm();
            $('#salaryForm').data("bootstrapValidator").validateField('salary');
        }
    }else{
            $('#salaryForm').data("bootstrapValidator").resetForm();
            $('#salaryForm').data("bootstrapValidator").validateField('salary');
    }
});
function bootstrapValidator() {
    $('#salaryForm').bootstrapValidator({
        message : 'This value is not valid',
        group : '.rowGroup',
        feedbackIcons : {
            valid : 'glyphicon glyphicon-ok',
            invalid : 'glyphicon glyphicon-remove',
            validating : 'glyphicon glyphicon-refresh'
        },
        fields : {
            salaryPaid: {
                validators: {
                    notEmpty: {
                        message: '请输入正确金额'
                    }
                }
            },
        }
    });
}

var TableInit = function() {
    var onlineInterfaceListTable = new Object();
    onlineInterfaceListTable.init = function() {
        $('#tb_salary').bootstrapTable({
            ajax: function(params) {
                params.success(resultAjax('/payRoll/queryPayRollVoPage', params.data));
            },
            //url : $.contextPath() + '/onlineInterface/getonlineInterfaceListPage', // 请求后台的URL（*）
            //method : 'get',                               // 请求方式（*）
            //toolbar : '#toolbar',                         // 工具按钮用哪个容器
            striped : true,                               // 是否显示行间隔色
            cache : false,                                // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination : true,                            // 是否显示分页（*）
            sortable : false,                             // 是否启用排序
            sortOrder : "desc",                           // 排序方式
            queryParams : onlineInterfaceListTable.queryParams,// 传递参数（*）
            sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
            pageNumber : 1, // 初始化加载第一页，默认第一页
            pageSize : 10, // 每页的记录行数（*）
            pageList : [ 5, 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
            paginationLoop: false,
            //search : true, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            //strictSearch : true,
            showColumns : true, // 是否显示所有的列
            //showRefresh : true, // 是否显示刷新按钮
            //minimumCountColumns : 2, // 最少允许的列数
            clickToSelect : true, // 是否启用点击选中行
            // height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId : "employeeName", // 每一行的唯一标识，一般为主键列
            //showToggle : false, // 是否显示详细视图和列表视图的切换按钮
            //cardView : false, // 是否显示详细视图
            //checkboxHeader: true,
            showColumns:false,//是否显示内容列下拉框。
            undefinedText:'-',
            detailView : true, // 是否显示父子表
            columns : [ {
                field : 'index',
                title : '序号',
                halign: 'center',
                align: 'center',
                formatter: function(value, row, index) { return index + 1; }
            },{
                field : 'employeeName',
                title : '姓名'
            },{
                field : 'salary',
                title : '当月薪资（元）',
                formatter:function(value){ return formatMoney(value);}
            }, {
                field : 'salaryPaid',
                title : '实发工资',
                formatter:function(value){ return formatMoney(value);}
            }, {
                field : 'balance',
                title : '差额(元)',
                formatter: function(value, row, index) { return formatMoney(value); },
                cellStyle:function(value,row,index){
                    if (value < 0){
                        return {css:{"color":"red"}}
                    }else if(value > 0 ){
                        return {css:{"color":"green"}}
                    }else{
                        return {};
                    }
                }
            },{
                field : 'salaryDate',
                title : '月份'
            },{
                field : 'remark',
                title : '备注'
            },{
				field : 'status',
				title : '状态',
				halign: 'center',
				align: 'center',
                formatter:function(value){if (value == "1"){ return '<span class="enabledFont">在职</span>'; }
                else if (value =="0") { return '<span class="disabledFont">离职</span>'; } }
            },{
                field : 'modifiedTime',
                title : '更新时间',
                halign: 'center',
                align: 'center',
            },{
                field : '',
                title : '操作',
                halign: 'center',
                align: 'center',
                events: operateEvents,
                formatter : function(value,row){
                    var nowDate = getNowMonth();
                    if (nowDate == row.salaryDate) {
                        return "<a role='button' class='optionEdit'>调整</a>";
                    } else {
                        return "<a role='button' style='color: #a3a3a3'>已归档</a>";
                    }
                }
            }],
            onLoadSuccess: function (data) {
                if(data.rows.length==1){
                        $('#tb_salary').bootstrapTable('expandRow', 0);
                }
                return true;
            },
            onExpandRow: function (index, row, $detail) {
                onlineInterfaceListTable.initSub(index, row, $detail);
            }
        });
    };
    function getLink(value){
        var link = value;
        var suffix = value.split("?");
        if(suffix.length<0 || suffix[1]!='wsdl'){
            link = value + "?wsdl";
        }
        return value ? '<a  target="blank" href="' +link+ '" >' + value + '</a>' : '';
    }
    window.operateEvents ={
        'click .optionEdit':function(e,value, row, index){
            $("#employeeSalaryModalLabell").html("调整薪资");
            $("#hidden_payrollId").val(row.payrollId);
            $("#show_employeeName").val(row.employeeName);
            $("#show_employeePhone").val(row.employeePhone);
            $("#now_salary").val( formatMoney(row.salary));
            $("#change_salaryPaid").val(formatMoney(row.salaryPaid));
            $("#change_remark").val("");
            $('#saveChangeSalaryId').attr("onclick","saveChangeSalary()");
            $("#employeeSalary-modal").modal();
        }
    };

    onlineInterfaceListTable.initSub = function (index, row, $detail) {

        var currTable = $detail.html('<table></table>').find('table');
        currTable.queryParams= function(params) {
            var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit, // 页面大小
                offset: params.offset, // 页码
                employeePhone: row.employeePhone,
            };
            return temp;
        };
        $(currTable).bootstrapTable({
            ajax: function(params) {
                params.success(resultAjax('/payRoll/queryHistoryPayRollVoPage', params.data));
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
            pageSize : 10, // 每页的记录行数（*）
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
            rowStyle:function(row,index){
                var coculaterMonth = $('#search_input_salaryDate').val();
                if (row.salaryDate == coculaterMonth){
                    return {css:{'color':'#a3a3a3'}}
                }else{
                    return {} ;
                }
            },
            columns : [ {
                field : 'index',
                title : '序号',
                halign: 'center',
                align: 'center',
                formatter: function(value, row, index) { return index + 1; }
            }, {
                field : 'salary',
                title : '当月薪资(元)',
                formatter: function(value, row, index) { return formatMoney(value); }
            },  {
                field : 'salaryPaid',
                title : '实际发放(元)',
                formatter: function(value, row, index) { return formatMoney(value); }
            },  {
                field : 'balance',
                title : '差额(元)',
                formatter: function(value, row, index) { return formatMoney(value); },
                cellStyle:function(value,row,index){
                    if (value < 0){
                        return {css:{"color":"red"}}
                    }else if(value > 0 ){
                        return {css:{"color":"green"}}
                    }else{
                        return {};
                    }
                }
            }, {
                field : 'remark',
                title : '备注'
            },{
                field : 'salaryDate',
                title : '月份'
            },{
				field : 'modifiedTime',
				title : '更新时间',
				halign: 'center',
				align: 'center',
			}]
        });
        $(currTable).bootstrapTable('hideColumn', 'interfaceCode');
    }

    function gridTitle(value){
        return value ? '<span title="' + value + '" >' + value + '</span>' : '';
    }

    onlineInterfaceListTable.queryParams = function(params) {

        var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit, // 页面大小
            offset: params.offset, // 页码
            salaryDate:  $('#search_input_salaryDate').val(),
            employeeName:  $('#txt_search_employeeName').val(),
        };
        return temp;
    };



    return onlineInterfaceListTable;
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
            $('#tb_salary').bootstrapTable('refresh');
        });

    }
    return oInit;
};

//保存调薪薪资
saveChangeSalary = function() {
    var bootstrapValidator = $("#salaryForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        paramsAjax('/payRoll/savePayRoll', saveParams(), function() {
            $("#employeeSalary-modal").modal('hide');
            $('#tb_salary').bootstrapTable('refresh');
        });
    } else {
        return;
    }
};
var saveParams = function() {
    var salaryPaid = $("#change_salaryPaid").val().trim();
    salaryPaid = salaryPaid.split(",").join("");
    var params = {
        payrollId: $("#hidden_payrollId").val(),
        employeePhone: $("#show_employeePhone").val().trim(),
        salaryPaid: salaryPaid,
        remark: $("#change_remark").val().trim()//effectMonth生效月份 用status接收值，因为此字段没有在使用
    }
    return JSON.stringify(params);
}

