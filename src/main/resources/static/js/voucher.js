$(document).ready(function() {

    //绑定回车查询事件
    $(document).keyup(function (e) {//捕获文档对象的按键弹起事件
        if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了
            queryVoucherById();
        }
    });
    //初始化下拉框
    initSelect();


    //默认查询展示最近一张凭证
    queryLastVoucher();

    //查询累计欠款金额
    initAccumulateDebt();
});

function initAccumulateDebt(){
    var customerId = $(".voucher_title_div #customerSelect").val();
    var accumulateDebt = resultAjax("/voucher/queryAccumulateDebtById", {customerId:customerId});
    $("#debtMoney").attr("value",formatMoney(accumulateDebt));
    $("#debtMoney").val(formatMoney(accumulateDebt));
    //金钱转中文  欠款
    var debtChinese = Arabia_to_Chinese(accumulateDebt);
    $("#debtChinese").attr("value",debtChinese);
    $("#debtChinese").val(debtChinese);
}

//查询按钮
$("#search_voucher").click(function(){
    queryVoucherById();
});
//根据voucherId查询凭证
function queryVoucherById(){
    var voucherId = $("#formSearch #txt_search_voucherId").val();
    var voucherVo = resultAjax("/voucher/queryVoucherById", {voucherId:voucherId});
    if(voucherVo.voucherId==null){
        commonAlert("没有找到此凭证","warning");
        return;
    }
    //赋值之前清空之前效果
    addNewVoucher();

    //赋值初始化
    initVoucher(voucherVo);
    resetNum();
};
//查询前一张，后一张凭证
function changeVoucher(type){
    var voucherId = $(".voucher_title_div .invoiceCode").find('span').eq(0).html();
    var voucherVo = resultAjax("/voucher/queryAroundByVoucherId", {voucherId:voucherId,type:type});
    if(voucherVo.voucherId==null){
        if(type==0)commonAlert("这已经是第一张凭证了","warning");
        if(type==1)commonAlert("这已经是最后一张凭证了","warning");
        return;
    }
    //赋值之前清空之前效果
    addNewVoucher();

    //赋值初始化
    initVoucher(voucherVo);
    resetNum();
}
//初始化查询最后一张凭证
function queryLastVoucher(){
    //如果voucherId有值，说明是默认不查询最近一张凭证了   而是根据voucherId查询
    var voucherId = $("#formSearch #txt_search_voucherId").val();
    if(voucherId){
        queryVoucherById();
    }else{//如果voucherId为空  默认查询最近一个凭证
        var voucherVo = resultAjax("/voucher/queryLastVoucher", "");
        initVoucher(voucherVo);
    }
}

//初始化客户下拉框
function initSelect(){
    var customers = resultAjax('/voucher/queryAllCustomer');
    for(var i in customers){
        $("#customerSelect").append('<option value="'+customers[i].customerId+'" >'+customers[i].customerName+'</option>');
    }
    $("#customerSelect option:nth-child(0)").attr("selected","true")
}

//当用户改变下拉框值得时候手动选中
$(".voucher_title_div #customerSelect").change(function(){
    var value = $(".voucher_title_div #customerSelect").val();
    $(".voucher_title_div #customerSelect").attr("value",value);
    $("#customerSelect option").attr("checked",false)
    $("#customerSelect option[value='"+value+"']").attr("checked",true)

    //查询累计欠款金额
    initAccumulateDebt();
});

//凭证赋值初始化
function initVoucher(voucherVo){
    $(".voucher_title_div .invoiceCode").find('span').eq(0).html(voucherVo.voucherId);
    $(".voucher_title_div .invoiceDate").find('span').eq(0).html(voucherVo.voucherDate);
    $(".voucher_title_div #customerSelect").val(voucherVo.customerId);
    $(".voucher_title_div #customerSelect").attr("value",voucherVo.customerId);
    $("#customerSelect option[value='"+voucherVo.customerId+"']").attr("checked",true);
    //付款   欠款 制单人 备注  初始化
    $("#voucherMaker").val(voucherVo.voucherMaker);
    $("#remarkTextarea").val(voucherVo.remark);

    var sons = voucherVo.sonList;
    var num = sons.length;
    //初始化表格行数
    initRows(num);
    for(var i in sons){
        $("#tb_voucher .voucher_row").eq(i).find('input').eq(0).attr("value",sons[i].goodsId);
        $("#tb_voucher .voucher_row").eq(i).find('input').eq(0).val(sons[i].goodsId);
        $("#tb_voucher .voucher_row").eq(i).find('input').eq(1).attr("value",sons[i].goodsName);
        $("#tb_voucher .voucher_row").eq(i).find('input').eq(1).val(sons[i].goodsName);
        $("#tb_voucher .voucher_row").eq(i).find('input').eq(2).attr("value",sons[i].goodsFormat);
        $("#tb_voucher .voucher_row").eq(i).find('input').eq(2).val(sons[i].goodsFormat);
        $("#tb_voucher .voucher_row").eq(i).find('input').eq(3).attr("value",sons[i].goodsUnit);
        $("#tb_voucher .voucher_row").eq(i).find('input').eq(3).val(sons[i].goodsUnit);
        $("#tb_voucher .voucher_row").eq(i).find('input').eq(4).attr("value",sons[i].goodsNumber);
        $("#tb_voucher .voucher_row").eq(i).find('input').eq(4).val(sons[i].goodsNumber);
        $("#tb_voucher .voucher_row").eq(i).find('input').eq(5).attr("value",sons[i].unitPrice).blur();
        $("#tb_voucher .voucher_row").eq(i).find('input').eq(5).val(sons[i].unitPrice).blur();
        $("#tb_voucher .voucher_row").eq(i).find('input').eq(6).attr("value",sons[i].totalPrice);
        $("#tb_voucher .voucher_row").eq(i).find('input').eq(6).val(sons[i].totalPrice);
        $("#tb_voucher .voucher_row").eq(i).find('input').eq(7).attr("value",sons[i].remark);
        $("#tb_voucher .voucher_row").eq(i).find('input').eq(7).val(sons[i].remark);
    }

    //查询累计欠款金额
    initAccumulateDebt();
}

//点击新增按钮 --- 新增凭证
$("#add_voucher").on("click",function(){
    addNewVoucher();
    var voucherId = resultAjax("/voucher/getNewMaxVoucherId");
    $(".voucher_title_div .invoiceCode").find('span').eq(0).html(voucherId);
    $(".voucher_title_div .invoiceDate").find('span').eq(0).html(getNowDay());
    //初始化表格行数
    initRows(5);
    //序号
    resetNum();
    //查询累计欠款金额
    initAccumulateDebt();
});
//清空之前的赋值效果
function addNewVoucher(){
    //新增时清空之前内容
    $("#tb_voucher").find('input').removeAttr("value");
    $("#tb_voucher").find('input').val("");
    $("#remarkTextarea").val("");
    var tr = $(".voucher").find(".voucher_row").eq(0);
    $(".voucher").find(".voucher_row").remove();
    $(".voucher .total").before(tr.clone());
}
//保存凭证
$("#save_voucher").on("click",function(){
    var trRows = $("#tb_voucher .voucher_row").clone();
    if( trRows!=null && trRows.length>0 ){
        var voucherId = $(".voucher_title_div .invoiceCode").find('span').eq(0).html();
        var voucherDate = $(".voucher_title_div .invoiceDate").find('span').eq(0).html();
        var customerId = $(".voucher_title_div #customerSelect").val();
        var amount = ($("#totalAmount").val()+"").split(",").join("");
        //var payAmount = ($("#payMoney").val()+"").split(",").join("");
        //var debtAmount = ($("#debtMoney").val()+"").split(",").join("");
        var voucherMaker = $("#voucherMaker").val();
        var remark = $("#remarkTextarea").val();
        if(!isNotNull(customerId)){
            commonAlert("请选择客户","warning");
            return;
        }
        var voucherVo ={};
        voucherVo.voucherId = voucherId;
        voucherVo.voucherDate = voucherDate;
        voucherVo.customerId = customerId;
        voucherVo.amount = amount;
        //voucherVo.payAmount = payAmount;
        //voucherVo.debtAmount = debtAmount;
        voucherVo.voucherMaker = voucherMaker;
        voucherVo.remark = remark;
        var voucherSons=new Array();
        for(var i=0;i<trRows.length;i++){
            var trRow=trRows[i];
            var son={};
            son.goodsId = $(trRow).find('input').eq(0).val();
            son.goodsName = $(trRow).find('input').eq(1).val();
            son.goodsFormat = $(trRow).find('input').eq(2).val();
            son.goodsUnit = $(trRow).find('input').eq(3).val();
            son.goodsNumber = $(trRow).find('input').eq(4).val();
            son.remark = $(trRow).find('input').eq(7).val();
            if(isNotNull(son.goodsName)){
                var unitPrice = $(trRow).find('input').eq(5).val();
                var totalPrice = $(trRow).find('input').eq(6).val();
                if(!isNotNull(unitPrice)){
                    unitPrice = "0"
                }
                if(!isNotNull(totalPrice)){
                    totalPrice = "0"
                }
                son.unitPrice = unitPrice.split(",").join("");
                son.totalPrice = totalPrice.split(",").join("");
                voucherSons.push(son);
            }
        }
        if(voucherSons.length<1){
            commonAlert("没有要保存的数据","warning");
            return;
        }
        voucherVo.sonList = voucherSons;
        paramsAjax('/voucher/voucherSaveOrUpdate',JSON.stringify(voucherVo),function(){

        });
    }
});

//重置序号
function resetNum(){
    var length = $(".voucher").find(".voucher_row").length;
    for(var i=0;i<length;i++){
        $(".voucher").find(".voucher_row").eq(i).find('input').eq(0).attr("value",i+1);
        $(".voucher").find(".voucher_row").eq(i).find('input').eq(0).val(i+1);
    }
}
//初始化表格行数
function initRows(n){
    var tr = $(".voucher").find(".voucher_row").eq(0);
    for(var i=0;i<n;i++){
        $(".voucher .total").before(tr.clone());
    }
};

//插入一行
$(".voucher").on("click",".po-add a",function(){
    var tr = $(this).parents(".voucher_row");
    tr.after(tr.clone());
//重置序号
    resetNum();
    //随便找个input触发一下点击事件，
    $("#tb_voucher .voucher_row").eq(0).find('input').eq(6).blur();
});

//清空或删除一行
$(".voucher").on("click",".po-delete a",function(){
    var tr = $(this).parents(".voucher_row");
    tr.find("textarea,input").val("").keyup().blur();
    tr.remove();
    if($(".voucher .voucher_row").length==3){
        $(".voucher .total").before(tr.clone());
    }
//重置序号
    resetNum();
    //随便找个input触发一下点击事件，
    $("#tb_voucher .voucher_row").eq(0).find('input').eq(6).blur();
});

//改变input框的value值
function changeValue(d){
    var value = $(d).val();
    $(d).attr("value",value);
    $(d).val(value);
    var moneyClass = $(d).attr("class");
    //如果是数量，输入其他字符时清空
    if(moneyClass == "integerNum"){
        dealGoodNum(value,d);
    }
    //如果是金钱列，处理成金钱格式
    if(moneyClass == "money"){
        //转换格式
        dealValueToShow(value,d);
    }
        //横向计算（数量*单价）
        caculateX(d);
        //金额汇总
        var length = $(".voucher").find(".voucher_row").length;
        var totalAmount = 0;
        for(var i=0;i<length;i++){
            var amount = $(".voucher").find(".voucher_row").eq(i).find('input').eq(6).val();
            if (amount == "" || amount == null) {
                amount = "0" ;
            }
            amount = amount.split(",").join("");
            totalAmount += parseFloat(amount)
        }
        //总额赋值
        $("#totalAmount").attr("value",formatMoney(totalAmount));
        $("#totalAmount").val(formatMoney(totalAmount));
        //计算总金额   付款   欠款  数额
        //dealPayDebat();
        //钱转汉字
        var chinese = Arabia_to_Chinese(totalAmount);
        $("#totalChinese").attr("value",chinese);
        $("#totalChinese").val(chinese);

};

//把欠款  和 付款单元格的输入格式处理成逗号形式
/*function changePay(d){
    var value = $(d).val();
    dealValueToShow(value,d);
    dealPayDebat();
}*/
//计算总额   付款  欠款 之间的关系
/*function dealPayDebat(){
    var totalAmount = $("#totalAmount").val().split(",").join("");
    var payMoney = $("#payMoney").val().split(",").join("");
    var debtMoey = parseFloat(totalAmount-payMoney);
    $("#debtMoney").val(formatMoney(debtMoey));
    //金钱转中文 付款
    var payChinese = Arabia_to_Chinese(payMoney);
    $("#payChinese").attr("value",payChinese);
    $("#payChinese").val(payChinese);
    //金钱转中文  欠款
    var debtChinese = Arabia_to_Chinese(debtMoey);
    $("#debtChinese").attr("value",debtChinese);
    $("#debtChinese").val(debtChinese);

}*/
//把用户输入或者赋值的  转换成逗号的显示状态显示
function dealValueToShow(value,d){
    value = value + "";
    value = value.split(",").join("");
    if (value != "" && value != null) {
        var re = /^\d+(\.\d+)?$/;//非负浮点数（正浮点数 + 0）
        if (!re.test(value)) {
            $(d).attr("value","");
            $(d).val("");
        } else {
            var showMoney = formatMoney(value);
            if(showMoney.length >13){
                $(d).attr("value","");
                $(d).val("");
                return;
            }
            $(d).attr("value",showMoney);
            $(d).val(showMoney);
        }
    }
}
//如果是数量，输入其他字符时清空
function dealGoodNum(value,d){
    if (value != "" && value != null) {
        var re = /^\d+(\.\d+)?$/;//非负浮点数（正浮点数 + 0）
        if (!re.test(value)) {
            $(d).attr("value",0);
            $(d).val(0);
        }
    }
    //横向计算（数量*单价）
    caculateX(d);
}
//横向计算（数量*单价）
function caculateX(d){
    var tr = $(d).parent().parent();
    var goodsNumber = $(tr).find('input').eq(4).val();
    var unitPrice = $(tr).find('input').eq(5).val();
    var totalPrice = "0";
    if( isNotNull(unitPrice)){
        unitPrice = unitPrice.split(",").join("");
        totalPrice = Number(goodsNumber)*parseFloat(unitPrice)
        $(tr).find('input').eq(6).attr("value",formatMoney(totalPrice));
        $(tr).find('input').eq(6).val(formatMoney(totalPrice));
    }

}
//打印
$("#print_voucher").on("click",function() {
   // selectAlert("是否打印欠款和收款", "打印", "不打印", function () {beforePrintDeaal(true)}, function () {beforePrintDeaal(false);});
    beforePrintDeaal();
});
//打印
function beforePrintDeaal(){
    //打印之后需要刷新页面，记录当前的凭证号
    var voucherId = $(".voucher_title_div .invoiceCode").find('span').eq(0).html();
    //打印时把客户名称下拉框换成span标签
    var options = $("#customerSelect option:selected");//获取当前选择项.
    var text = options.text();//获取当前选择项的文本.
    var span = document.createElement("span");
    span.innerHTML = text;
    span.id = "print_customer_name";
    var select = document.getElementsByTagName("select");
    select[0].parentNode.replaceChild(span,select[0]);

    //把金钱的input替换掉，吧制单人替换掉
    var voucherMaker = $(".total #voucherMaker").val();
    $(".total .voucherMaker").html("制单人："+voucherMaker);
    $(".total #voucherMaker").remove();

   /* var voucher_pay = $(".voucher_pay #payMoney").val();
    $(".voucher_pay span").html("收款："+voucher_pay);
    $(".voucher_pay #payMoney").remove();*/

    var voucher_noPay = $(".voucher_noPay #debtMoney").val();
    $(".voucher_noPay span").html("累计欠款："+voucher_noPay);
    $(".voucher_noPay #debtMoney").remove();

    //备注
    var remark = $("#remarkTextarea").val();
    remark = remark? remark:"备注：";
    var p = document.createElement("p");
    p.innerHTML = remark;
    var textArea = document.getElementById("remarkTextarea");
    textArea.parentNode.replaceChild(p,textArea);
    //打印
   /* if(!flag){
        var foramrtChinese = "<p class='emptymoney'>万&nbsp;&nbsp;&nbsp;仟&nbsp;&nbsp;&nbsp;佰&nbsp;&nbsp;&nbsp;拾&nbsp;&nbsp;&nbsp;元</p>"
        $(".voucher_pay span").html("收款：");
        $("#payChinese").replaceWith(foramrtChinese);
        $(".voucher_noPay span").html("累计欠款：");
        $("#debtChinese").replaceWith(foramrtChinese);
    }*/
    var html = $("#printArea").html();
    printHtml(html);
    //刷新页面
    window.location.href="/voucher?voucherId="+voucherId;
};

function printHtml(html) {
    document.body.innerHTML = html;
    window.print();
}

//钱转汉字
function Arabia_to_Chinese(Num) {
    Num='￥'+Num;
    var i;
    for (i = Num.length - 1; i >= 0; i--) {
        Num = Num.replace(",", "")//替换tomoney()中的“,”
        Num = Num.replace(" ", "")//替换tomoney()中的空格
    }
    Num = Num.replace("￥", "")//替换掉可能出现的￥字符
    if (isNaN(Num)) { //验证输入的字符是否为数字
        alert("请检查小写金额是否正确");
        return;
    }
    //---字符处理完毕，开始转换，转换采用前后两部分分别转换---//
    var  part = Num.split(".");
    var  newchar = "";
    //小数点前进行转化
    for (i = part[0].length - 1; i >= 0; i--) {
        if (part[0].length > 10) { alert("位数过大，无法计算"); return ""; } //若数量超过拾亿单位，提示
        var  tmpnewchar = ""
        var perchar = part[0].charAt(i);
        switch (perchar) {
            case "0": tmpnewchar = "零" + tmpnewchar; break;
            case "1": tmpnewchar = "壹" + tmpnewchar; break;
            case "2": tmpnewchar = "贰" + tmpnewchar; break;
            case "3": tmpnewchar = "叁" + tmpnewchar; break;
            case "4": tmpnewchar = "肆" + tmpnewchar; break;
            case "5": tmpnewchar = "伍" + tmpnewchar; break;
            case "6": tmpnewchar = "陆" + tmpnewchar; break;
            case "7": tmpnewchar = "柒" + tmpnewchar; break;
            case "8": tmpnewchar = "捌" + tmpnewchar; break;
            case "9": tmpnewchar = "玖" + tmpnewchar; break;
        }
        switch (part[0].length - i - 1) {
            case 0: tmpnewchar = tmpnewchar + "元"; break;
            case 1: if (perchar != 0) tmpnewchar = tmpnewchar + "拾"; break;
            case 2: if (perchar != 0) tmpnewchar = tmpnewchar + "佰"; break;
            case 3: if (perchar != 0) tmpnewchar = tmpnewchar + "仟"; break;
            case 4: tmpnewchar = tmpnewchar + "万"; break;
            case 5: if (perchar != 0) tmpnewchar = tmpnewchar + "拾"; break;
            case 6: if (perchar != 0) tmpnewchar = tmpnewchar + "佰"; break;
            case 7: if (perchar != 0) tmpnewchar = tmpnewchar + "仟"; break;
            case 8: tmpnewchar = tmpnewchar + "亿"; break;
            case 9: tmpnewchar = tmpnewchar + "拾"; break;
        }
        newchar = tmpnewchar + newchar;
    }
    //小数点之后进行转化
    if (Num.indexOf(".") != -1) {
        if (part[1].length > 2) {
            alert("小数点之后只能保留两位,系统将自动截段");
            part[1] = part[1].substr(0, 2)
        }
        for (i = 0; i < part[1].length; i++) {
            tmpnewchar = ""
            perchar = part[1].charAt(i)
            switch (perchar) {
                case "0": tmpnewchar = "零" + tmpnewchar; break;
                case "1": tmpnewchar = "壹" + tmpnewchar; break;
                case "2": tmpnewchar = "贰" + tmpnewchar; break;
                case "3": tmpnewchar = "叁" + tmpnewchar; break;
                case "4": tmpnewchar = "肆" + tmpnewchar; break;
                case "5": tmpnewchar = "伍" + tmpnewchar; break;
                case "6": tmpnewchar = "陆" + tmpnewchar; break;
                case "7": tmpnewchar = "柒" + tmpnewchar; break;
                case "8": tmpnewchar = "捌" + tmpnewchar; break;
                case "9": tmpnewchar = "玖" + tmpnewchar; break;
            }
            if (i == 0) tmpnewchar = tmpnewchar + "角";
            if (i == 1) tmpnewchar = tmpnewchar + "分";
            newchar = newchar + tmpnewchar;
        }
    }
    //替换所有无用汉字
    while (newchar.search("零零") != -1)
        newchar = newchar.replace("零零", "零");
    newchar = newchar.replace("零亿", "亿");
    newchar = newchar.replace("亿万", "亿");
    newchar = newchar.replace("零万", "万");
    newchar = newchar.replace("零元", "元");
    newchar = newchar.replace("零角", "");
    newchar = newchar.replace("零分", "");
    if (newchar.charAt(newchar.length - 1) == "元" || newchar.charAt(newchar.length - 1) == "角")
        newchar = newchar + "整"
    //  document.write(newchar);

    $('#Arabia_to_Chinese').html(newchar)
    return newchar;
}