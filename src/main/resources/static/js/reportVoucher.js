$(document).ready(function() {
    initData();
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
});

// 指定图表的配置项和数据
var option = {
    title: {
        text: '销售报表'
    },
    tooltip: {},
    legend: {
        data:[]
    },
    xAxis: {
        data: []
    },
    yAxis: {
        type: 'value',
        axisLabel:{
            formatter:'{value}(元)'
        }
    },
    series: [],
    tooltip : {
        trigger: 'axis',
        formatter:function(params)
        {
            var relVal = params[0].name;
            for (var i = 0, l = params.length; i < l; i++) {
                relVal += '<br/>名称：' + params[i].seriesName + ',销售额：' + params[i].value+"元";
            }
            return relVal;
        }
    },
};

function initData(){
    $.ajax({
        cache: true,
        type: "GET",
        url: $.contextPath() + '/reportVoucher/getTestData',
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
                structureChart(data);
                var yMax = 500;
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
    function structureChart(data){
        //表legend列头信息
        option.legend.data=data.names;
        //X轴坐标
        option.xAxis.data=data.months;
        //数据
        var serie=[];
        for( var i=0;i < data.names.length;i++){
            var item={
                name: data.names[i],
                type: 'bar',
                data: data.data[i],
                itemStyle: {
                    normal: {
                        label: {
                            show: true, //开启显示
                            position: 'top', //在上方显示
                            textStyle: { //数值样式
                                color: 'black',
                                fontSize: 16
                            }
                        }
                    }
                }
            }
            serie.push(item);
        };
        option.series = serie;
    }
}