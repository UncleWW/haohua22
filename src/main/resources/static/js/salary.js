$(document).ready(function() {
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

//approveRole-modal验证销毁重构
$('#employeeSalary-modal').on('hidden.bs.modal', function() {
    $("#salaryForm").data('bootstrapValidator').destroy();
    $('#salaryForm').data('bootstrapValidator', null);
    bootstrapValidator();

});

$("#change_salary").blur(function () {
    var str = $(this).val()+"";
    str = str.split(",").join("");
    if (str != "" && str != null) {
        var re = /^\d+(\.\d+)?$/;//非负浮点数（正浮点数 + 0）
        if (!re.test(str)) {
            $(this).val("");
            $('#salaryForm').data("bootstrapValidator").resetForm();
            $('#salaryForm').data("bootstrapValidator").validateField('salary');
        } else {
            $("#change_salary").val(formatMoney(str));
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
            salary: {
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
                params.success(resultAjax('/salary/querySalaryPage', params.data));
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
            uniqueId : "employeeName", // 每一行的唯一标识，一般为主键列
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
                field : 'employeeName',
                title : '姓名'
            },{
                field : 'salary',
                title : '薪资（元）',
                formatter:function(value){ return formatMoney(value);}
            }, {
                field : 'startDate',
                title : '生效日期'
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
                formatter : operateFormatter
            }],
            onLoadSuccess: function (data) {
                //var address=$("#txt_search_interfaceAddress").val();
                var employeeName=$("#txt_search_employeeName").val();
                var status=$("#txt_search_status").val();
                if((employeeName != "" && employeeName !=null)  || status != "" ){
                    for(var i=0;i<data.rows.length;i++){
                        $('#tb_salary').bootstrapTable('expandRow', i);
                    }
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
            $("#show_employeeName").val(row.employeeName);
            $("#show_employeePhone").val(row.employeePhone);
            $("#now_salary").val( formatMoney(row.salary));
            $("#change_salary").val("");
            $('#saveChangeSalaryId').attr("onclick","saveChangeSalary()");
            $("#employeeSalary-modal").modal();
        }
    };
    function operateFormatter(value, row, index) {
        return  "<a role='button' class='optionEdit'>调薪</a>";
    }

    onlineInterfaceListTable.initSub = function (index, row, $detail) {

        var currTable = $detail.html('<table></table>').find('table');
        currTable.queryParams= function(params) {
            var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit, // 页面大小
                offset: params.offset, // 页码
                employeePhone: row.employeePhone,
                employeeName: $("#txt_search_employeeName").val(),
                status: $("#txt_search_status").val(),
                version: "0",
            };
            return temp;
        };
        $(currTable).bootstrapTable({
            ajax: function(params) {
                params.success(resultAjax('/salary/getSalaryHistoryPageList', params.data));
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
            columns : [ {
                field : 'index',
                title : '序号',
                halign: 'center',
                align: 'center',
                formatter: function(value, row, index) { return index + 1; }
            }, {
                field : 'salary',
                title : '历史薪资(元)',
                formatter: function(value, row, index) { return formatMoney(value); }
            }, {
                field : 'startDate',
                title : '开始时间',
                halign: 'center',
                align: 'center',
            },{
				field : 'endDate',
				title : '结束时间',
				halign: 'center',
				align: 'center',
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
    var formatMoney = function (value, type) {
        if (value == null || value == '') {
            return '';
        }
        if (type == null || type == '') {
            type  = 2;
        }
        return value.toFixed(type).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
    };
    onlineInterfaceListTable.queryParams = function(params) {

        var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit, // 页面大小
            offset: params.offset, // 页码
            employeeName: $("#txt_search_employeeName").val(),
            status: $("#txt_search_status").val(),
            version: "1",//

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
        paramsAjax('/salary/saveSalary', saveParams(), function() {
            $("#employeeSalary-modal").modal('hide');
            $('#tb_salary').bootstrapTable('refresh');
        });
    } else {
        return;
    }
};
var saveParams = function() {
    var salary = $("#change_salary").val().trim();
    salary = salary.split(",").join("");
    var params = {
        employeePhone: $("#show_employeePhone").val().trim(),
        salary: salary,
        status: $("#effect_month").val().trim()//effectMonth生效月份 用status接收值，因为此字段没有在使用
    }
    return JSON.stringify(params);
}

