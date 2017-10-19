var goodslist = null;
var getGoodListURL = HEADER + "goods/queryGoodsList.do?page=1&pageSize=10000"
$(function () {
    //	cr_item.getItemType($("#addGoodsType"));
    purManagement.pageType("preAllPage");
    // cr_normal.showTime();
    getReturnType();
    showTime();
})


//显示时间选择
function showTime(){
    $(".time-select").datetimepicker({
        language:  'en',
        weekStart: 0,//一个星期的开始
        todayBtn:  1,//点击显示今天时间
        autoclose: 1,//选择日期后关闭
        todayHighlight: 1,//当前天数高亮
        startView: 2,//开始的显示年、还是月、或日、时、分、秒
        forceParse: 0,//默认是那一天
        showMeridian: 0,//十二小时显示还是二十四小时显示
        minuteStep:1
    });
};

//获取物品列表
function getAllGoods() {
    $.ajax({
        type: "get",
        url: getGoodListURL,
        async: false,
        success: function (data) {
            goodslist = data.result;
            if (!!data) {
                var jsonArr = data.result;
                var jsonMsg = {};
                var html = "<option value=''>--请选择--</option>";
                for (var i = 0; i < jsonArr.length; i++) {
                    html += "<option gspec='" + jsonArr[i].gspec + "' provider='" + jsonArr[i].providername + "' unit='" + jsonArr[i].unit + "' value='" + jsonArr[i].id + "'>" + jsonArr[i].gname + "</option>";
                }
                $("#addGoodsType").html(html);
            }
        }
    });
}

//删除采购管理列表信息
function deletePurManageMsg(dom) {
    var stackedNum = $(dom).parents("tr").children().eq(8).children().eq(0).html();
    if (stackedNum == "0") {
        cr_dialogBox.confirm(true, "确认要删除吗?", function (flag) {
            if (flag) {
                var url = HEADER + "purchase/deletePur.do";
                var id = $(dom).parents("tr").attr("data-cid");
                $.ajax({
                    type: "post",
                    url: url,
                    data: {
                        "id": id
                    },
                    success: function (data) {
                        cr_item.showTip(data.status, function () {
                            purManagement.pageType(purManagement.page);
                        });
                    }
                });
            }
        });
    } else {
        cr_dialogBox.alert(true, "物品已有入库数，不能删除!");
    }
}

//根据采购数量和单价算出采购金额
function checkPrice(dom) {
    var cgnum = $("#addNumber").val();//采购数量
    var cgprice = $("#addCgPrice").val();//采购单价
    var cgmoney = 0;//采购金额
    if (cgnum != "") {
        cgmoney = cgnum * cgprice;
        $("#addCgMoney").val(cgmoney);

    } else {
        cr_dialogBox.alert(true, "请先填写采购数量！")
    }
}

//搜索
function search() {
    var searchValue = $("#search").val();
    purManagement.searchMsg(searchValue);
}

/*----------------------------录入采购单方法------------------------*/

//检测是否输入了值
/*检测新增*/
function checkHasValue() {
    var cnum = !!$("#addNumber").val() ? $("#addNumber").val() * 1 : 0;//采购数量
    var cgprice = !!$("#addCgPrice").val() ? $("#addCgPrice").val() * 1 : 0;//采购单价
    // console.log(cnum+"=="+cgprice);
    var cgmoney = cnum * cgprice;//采购金额
    $("#addCgMoney").val(cgmoney);


/*检测编辑*/
    var update_cnum = !!$("#updateNumber").val() ? $("#updateNumber").val() * 1 : 0;//采购数量
    var update_cgprice = !!$("#updateCgPrice").val() ? $("#updateCgPrice").val() * 1 : 0;//采购单价
    // console.log(cnum+"=="+cgprice);
    var update_cgmoney = update_cnum * update_cgprice;//采购金额
    $("#updateCgMoney").val(update_cgmoney);
}

//根据选择不同物品给录入采购单框赋值
function getOtherMsg(dom) {
    var optionDom = $(dom).find("option:selected");
    var gspec = !!optionDom.attr("gspec") ? optionDom.attr("gspec") : "";
    var unit = !!optionDom.attr("unit") ? optionDom.attr("unit") : "";
    var provider = !!optionDom.attr("provider") ? optionDom.attr("provider") : "";
    var gid = $(dom).find("option:selected").val();
    $("#shoppingUnit").html(unit);
    $("#addProvider").html(provider);
    $("#addGspec").html(gspec);
}

//提交新增录入采购单
function addPurchaseList() {
    var url = HEADER + "purchase/addPurInfo.do";
    var cid = $("#enterShoppingOrderNumber").html();
    var gid = $("#addGoodsType").find("option:selected").val();
    var cdate = $("#addCdate").val();
    var cnum = $("#addNumber").val();//采购数量
    var cgprice = $("#addCgPrice").val();//采购单价
    var cgmoney = $("#addCgMoney").val();
    var cgTime = $("#CgTime").val();//采购时间
    var cgComments = $("#CgComments").val()//采购备注
    if (gid != "") {
        if (cdate != "") {
            if (cnum != "") {
                if (cgprice != "") {
                    $.ajax({
                        type: "post",
                        url: url,
                        data: {
                            "cgid": cid,//采购单号
                            "gid": gid,//物品编号
                            "cgdate": cdate,//采购日期
                            "cgnum": cnum,//采购数量
                            "instate": "01",//物品入库状态
                            "cgstatus": "01",
                            "instoragednum": 0,//已入库数量
                            "instoragenum": cnum,//未入库数量
                            "cgprice": cgprice,
                            "cgmoney": cgmoney,
                            "createtime":cgTime,//采购时间
                            "comments":cgComments//采购备注
                        },
                        success: function (data) {
                            if (data.msg == 1 || data.msg == "1") {
                                cr_dialogBox.alert(true, "提交成功!");
                                $("#enterShoppingList").modal("hide");
                                purManagement.pageType(purManagement.page);
                            } else if (data.msg == 0 || data.msg == "0") {
                                cr_dialogBox.alert(true, "提交失败!");
                            }
                        }
                    });
                } else {
                    cr_dialogBox.alert(true, "请输入采购单价!");
                }
            } else {
                cr_dialogBox.alert(true, "请输入采购数量!");
            }
        } else {
            cr_dialogBox.alert(true, "请输入时间!");
        }
    } else {
        cr_dialogBox.alert(true, "请选择物品类型!");
    }
}

//打开编辑
function openEditPurManageMsg(dom) {
    var innum= $(dom).parents("tr").children().eq(8).html();
    if(innum*1<=0){
    $("#updateShoppingList").modal("show");
    var id = $(dom).parents("tr").attr("data-id");
    var cgId =$(dom).parents("tr").attr("data-cno");
    var goodName = $(dom).parents("tr").children().eq(1).html();
    var cgType = $(dom).parents("tr").children().eq(2).html();
    var cgDan = $(dom).parents("tr").children().eq(3).html();
    var supplier = $(dom).parents("tr").children().eq(4).html();
    var cgDate = $(dom).parents("tr").children().eq(5).html();
    var cgPrice = $(dom).parents("tr").attr("data-price");
    var cgMoney = $(dom).parents("tr").attr("data-money");
    var cgNum= $(dom).parents("tr").children().eq(6).html();
    var cgComments = $(dom).parents("tr").children().eq(10).html();
    var gid = $(dom).parents("tr").children().eq(1).attr("data-gid");

    /* //给物品名称下拉框赋值
     cr_item.getItemType($("#updateGoodsType"),true);
     $("#updateGoodsType option").each(function(){
         if($(this).text() == goodName){
             $(this).prop("selected","selected");
         }
     });*/
    $("#cid").val(id);
    $("#updateGoodsType").html(goodName);
    $("#updateGoodsType").attr("gid",gid);
    $("#updateShoppingOrderNumber").html(cgId);
    $("#updateGspec").html(cgType);
    $("#updateProvider").html(supplier);
    $("#updateshoppingUnit").html(cgDan);
    $("#updateNumber").val(cgNum);
    $("#updateCgPrice").val(cgPrice);
    $("#updateCgMoney").val(cgMoney);
    $("#updateCgTime").val(cgDate);
    $("#updateCgComments").val(cgComments);
}else{
    cr_dialogBox(true,"已有入库数量,不能修改采购信息!");
}

}


//提交编辑采购清单
function updatePurchaseList() {
    var url = HEADER + "purchase/updatePurInfo.do";
    var id = $("#cid").val();
    var cid = $("#updateShoppingOrderNumber").html();
    var gid = $("#updateGoodsType").attr("gid");
    var cgType = $("#updateGspec").val();
    var updateProvider = $("#updateProvider").html();
    var cnum = $("#updateNumber").val();//采购数量
    var cgprice = $("#updateCgPrice").val();//采购单价
    var cgmoney = $("#updateCgMoney").val();
    var cgTime = $("#updateCgTime").val();//采购时间
    var cgComments = $("#updateCgComments").val()//采购备注

            if (cnum != "") {
                if (cgprice != "") {
                    $.ajax({
                        type: "post",
                        url: url,
                        data: {
                            "id": id,//采购单号
                            "gid": gid,//物品编号
                            "cgid":cid,
                            "cgnum": cnum,//采购数量
                            "cgprice": cgprice,
                            "cgmoney": cgmoney,
                            "createtime":cgTime,//采购时间

                            "cgdate":cgTime,

                            "comments":cgComments,//采购备注
                            "instate": "01",//物品入库状态
                            "cgstatus": "01",
                            "instoragednum": 0,//已入库数量
                            "instoragenum": cnum,//未入库数量
                        },
                        success: function (data) {
                            if (data.msg == 1 || data.msg == "1") {
                                cr_dialogBox.alert(true, "提交成功!");
                                $("#updateShoppingList").modal("hide");
                                purManagement.pageType(purManagement.page);
                            } else if (data.msg == 0 || data.msg == "0") {
                                cr_dialogBox.alert(true, "提交失败!");
                            }
                        }
                    });
                } else {
                    cr_dialogBox.alert(true, "请输入采购单价!");
                }
            } else {
                cr_dialogBox.alert(true, "请输入采购数量!");
            }
        }

/*----------------------------录入采购单方法结束------------------------*/

/*-----------------------入库方法---------------------------*/

//提交入库信息
function submitIncomingMsg() {
    var url = HEADER + "stock/addStock.do";
    var snum = $("#inStackedNum").val();//入库数量
    var sopertype = "01";
    var stype = $("#inStackType").val();
    var cno = $("#inGoodsName").attr("data-cid");
    var gno = $("#inGoodsName").attr("data-gid");
    var cnum = $("#inGoodsName").attr("data-cnum");
    var storagenum = $("#inStackNum").html();//待入库数量
    var RkTime = $("#RkTime").val();//入库时间
    var ckmanager=$("#ckmanager").find("option:selected").attr("data-name");//获取仓管员
    if (snum != "") {
        if (snum * 1 <= storagenum * 1) {
            $.ajax({
                type: "post",
                url: url,
                data: {
                    "cgid": cno,
                    "gid": gno,
                    "snum": snum,
                    "sopertype": sopertype,
                    "stype": stype,
                    "sdate":RkTime,//入库时间
                    "auditperson":ckmanager
                },
                success: function (data) {

                    cr_item.showTip(data.msg, function () {
                        $("#goodsInventory").modal("hide");
                        purManagement.pageType(purManagement.page);
                    });
                }
            });
        } else {
            cr_dialogBox.alert(true, "入库数量不能大于待入库数量!");
        }
    } else {
        cr_dialogBox.alert(true, "请输入入库数量!");
    }
}

//获取退货类型
function getReturnType(dom) {
    var outReturnType = $(dom).val();
    console.log("（onchange里面）退货物品状态值：" + outReturnType);
    var GZnum = $("#outGoodsName").attr("data-faultnum");//故障数量
    var KCnum = $("#outGoodsName").attr("data-stackedNum");//库存数量
    console.log("（onchange里面）故障数量:" + GZnum + "；库存数量：" + KCnum);
    var afaultnum = $("#outGoodsName").attr("data-afaultnum");//故障物品退货审核数量
    var anormalnum = $("#outGoodsName").attr("data-anormalnum");//正常物品退货审核数量
    console.log("（onchange里面）故障物品退货审核数量:" + afaultnum + "；  正常物品退货审核数量：" + anormalnum);
    var couldReturnNum = "";
    if (outReturnType == "00") {
        couldReturnNum = GZnum * 1 - afaultnum * 1;
        console.log("（onchange里面）可退故障物品数量：" + couldReturnNum);
        $("#couldReturnNum").html(couldReturnNum);//故障数量
    } else if (outReturnType == "01") {
        couldReturnNum = KCnum * 1 - anormalnum * 1;
        console.log("（onchange里面）可退正常物品值：" + couldReturnNum);
        $("#couldReturnNum").html(couldReturnNum);//正常数量
    }
}

//提交退货信息
function submitReturnMsg() {
    var url = HEADER + "stock/outStockBefore.do";
    var snum = $("#outNumber").val();
    var sopertype = "02";
    var sthstate = $("#outReturnType").val();//退货类型：00或者01
    var cid = $("#outGoodsName").attr("data-cid");
    var cno = $("#outGoodsName").attr("data-cno");
    var gno = $("#outGoodsName").attr("data-gid");
    var faultnum = $("#outGoodsName").attr("data-faultnum");
    var stackedNum = $("#outGoodsName").attr("data-stackedNum");//库存数量
    var couldReturnNum = $("#couldReturnNum").html();//可退货数量
    console.log("（提交里面）可退货数量:" + couldReturnNum);
    var manager = $("#StockManager").find("option:selected").attr("data-name");//仓管员
    var checker = $("#StockChecker").find("option:selected").attr("data-name");//审核人
    var csPerson = $("#StockCSPerson").find("option:selected").attr("data-name");//抄送人
    if (snum != "") {
        if (sthstate == '00') {//故障退货
            if (snum * 1 <= couldReturnNum * 1) {//退货数量小于可退货故障数量
                //if()
                $.ajax({
                    type: "post",
                    url: url,
                    data: {
                        "cgid": cid,
                        "gid": gno,
                        "snum": snum,
                        "sopertype": sopertype,
                        "stype1": "02",
                        "sthstate": sthstate,
                        "csUser": csPerson,//抄送人
                        "auditperson": manager,//仓管员
                        "auditperson1": checker//审核人
                    },
                    success: function (data) {
                        cr_item.showTip(data.msg, function () {
                            $("#returnGoods").modal("hide");
                            purManagement.pageType(purManagement.page);
                        });
                    }
                });
            } else {
                cr_dialogBox.alert(true, "故障物品退货数量不能大于可退货数量：" + couldReturnNum + "!");
            }
        } else if (sthstate == '01') {//正常退货
            if (snum * 1 <= couldReturnNum * 1) {//退货数量小于库存数量
                $.ajax({
                    type: "post",
                    url: url,
                    data: {
                        "cgid": cid,
                        "gid": gno,
                        "snum": snum,
                        "sopertype": sopertype,
                        "stype1": "02",
                        "sthstate": sthstate,
                        "csUser": csPerson,//抄送人
                        "auditperson": manager,//仓管员
                        "auditperson1": checker//审核人
                    },
                    success: function (data) {
                        cr_item.showTip(data.msg, function () {
                            $("#returnGoods").modal("hide");
                            purManagement.pageType(purManagement.page);
                        });
                    }
                });
            } else {
                cr_dialogBox.alert(true, "正常物品退货数量不能大于可退货数量：" + couldReturnNum + "!");
            }
        }
    } else {
        cr_dialogBox.alert(true, "请输入退货数量!");
    }
}

/*-----------------------入库方法结束---------------------------*/

/*------------------------------------窗口打开方法开始-----------------------------------------*/

//打开入库记录窗口
function openInRecordsModdle(dom) {
    var id = $(dom).parents("tr").attr("data-cid");
    $("#inventoryRecords").attr("data-cid", id);
    $("#inventoryRecords").modal("show");
    itemDetails.pageType("preAllPage");
}

//打开物品入库窗口
//入库数量不能大于采购数量
function openGoodsInModdle() {
    //当前选中的订单列
    var trDom = $("#purManagementMsg").find("input[name='order']:checked").parents("tr");
    //var id = trDom.parents("tr").children().eq(1).attr("data-gid");//id的值
    if (!!trDom.children().eq(1).html()) {
        var name = trDom.children().eq(1).html();
        var cnum = trDom.children().eq(6).html();//采购数量
        var stockNum = trDom.children().eq(9).html();//待入库数
        var order = $.trim(trDom.children().eq(0).children().eq(0).text());
        var gid = trDom.children().eq(1).attr("data-gid");
        var cno = trDom.attr("data-cno");
        var cid = trDom.attr("data-cid");
        var gno = trDom.attr("data-gno");
        cr_item.getAllManager($("#ckmanager")); //给下拉赋值仓库管理员
        $("#goodsInventory").modal("show");
        $("#inGoodsName").html(name).attr({
            "data-cid": cid,
            "data-gid": gid,
            "data-cno": cno,
            "data-gno": gno,
            "data-cnum": cnum
        });
        $("#inShouldNum").html(cnum);//采购数量
        $("#inStackNum").html(stockNum);//待入库数
        $("#inStackedNum").val("");//入库数量
        $("#inStackType").val("01");//入库类型
        $("#inOrder").val(order);
    } else {
        cr_dialogBox.alert(true, "请选择采购列表列!");
    }
}

//打开退货窗口
//已入库数位0不能打开退货窗口 退货数量不能大于-+已入库数量
function openGoodsRetrun() {

 /*   //当前选中的订单列
    var trDom = $("#purManagementMsg").find("input[name='order']:checked").parents("tr");
    var id = trDom.parents("tr").children().eq(1).attr("data-gid");
    if (!!trDom.children().eq(1).html()) {
        var name = trDom.children().eq(1).html();
        var cnum = trDom.children().eq(6).html();//采购数量
        var stockNum = trDom.children().eq(9).html();//待入库数*/


    //当前选中的订单列
    var trDom = $("#purManagementMsg").find("input[name='order']:checked").parents("tr");
    cr_item.getAllChecker($("#StockChecker")); //给下拉赋值审核人
    cr_item.getAllManager($("#StockManager")); //给下拉赋值仓库管理员
    cr_item.getAllUser($("#StockCSPerson"));//给下拉框赋值抄送人
    if (!!trDom.children().eq(1).html()) {
        var name = trDom.children().eq(1).html();
        var gspec = trDom.children().eq(2).html();
        var provider = trDom.children().eq(4).html();
        var unit = trDom.children().eq(3).html();
        var gid = trDom.children().eq(1).attr("data-gid");

        var order = $.trim(trDom.children().eq(0).text());

        var cno = trDom.attr("data-cno");
        var cid = trDom.attr("data-cid");
        var gno = trDom.attr("data-gno");
        var faultnum = trDom.attr("data-faultnum");//故障数量
        console.log("（打开退货里面）故障数量:" + faultnum);
        var stackedNum = trDom.children().eq(8).children().eq(0).html() * 1;//库存数量
        var anormalnum = trDom.attr("data-anormalnum");//审核中的正常退货数量
        var afaultnum = trDom.attr("data-afaultnum");//审核中的故障退货数量

        alert(name);//打印数据
        alert(gspec);
        alert(provider);
        alert(unit);
        alert(gid);
        alert(order);
        alert(cno);
        alert(cid);
        alert(gno);
        alert(faultnum);
        alert(stackedNum);
        alert(anormalnum);
        alert(afaultnum);
        console.log("（打开退货里面）审核中的正常退货数量：" + anormalnum + "；   审核中的故障退货数量：" + afaultnum);
        if ((stackedNum + "") != "NaN" && stackedNum > 0) {
            $("#returnGoods").modal("show");
            $("#outGoodsName").html(name).attr({
                "data-cid": cid,
                "data-gid": gid,
                "data-cno": cno,
                "data-gno": gno,
                "data-stackedNum": stackedNum,
                "data-faultnum": faultnum,
                "data-afaultnum": afaultnum,
                "data-anormalnum": anormalnum
            });
            $("#outGspec").html(gspec);
            $("#outUnit").html(unit);
            $("#outProvider").html(provider);
            $("#outOrder").val(order);
            $("#outReturnType").val("00");
            $("#couldReturnNum").html(faultnum * 1 - afaultnum * 1);
        } else {
            cr_dialogBox.alert(true, "暂没有库存，不能退货!");
        }
    } else {
        cr_dialogBox.alert(true, "请选择采购列表列!");
    }
}

//打开录入采购清单窗口
function openEnterShoppingList() {
    var url = HEADER + "purchase/getNextPurId.do";
    $.ajax({
        type: "get",
        url: url,
        async: false,
        success: function (data) {
            if (!!data) {
                $("#enterShoppingOrderNumber").html(data.nextPurId);
            }
        }
    });
    $("#addItemType").val("");//物品类型
    $("#shoppingUnit").html("");//单位
    $("#addProvider").html("");//供应商
    $("#addGspec").html("");//规格型号
    $("#enterShoppingList").modal("show");
    $("#addNumber").val("");
    $("#addCdate").val("");
    $("#addCgPrice").val("");
    $("#addCgMoney").val("");
    $("#addCgMoments").val("");
    $("#addCgComments").val("");
    getAllGoods();

}

/*------------------------------------窗口打开方法结束-----------------------------------------*/

//获取采购管理列表
var purManagement = {
    page: 1,
    nextPage: 1,
    prePage: 1,
    lastPage: 1,
    param: "",
    pageType: function (type) {
        loadingModdle.showModdle();
        var pages = 0;
        var $this = this;
        var cinstate = $("#purchType").val();
        if (type == "preAllPage") {
            pages = 1;
        } else if (type == "prePage") {
            pages = this.prePage;
        } else if (type == "nextPage") {
            pages = this.nextPage;
        } else if (type == "nextAllPage") {
            pages = this.lastPage;
        } else if (typeof(type) == "number") {
            pages = type;
        }
        ;
        $.ajax({
            type: "get",
            url: HEADER + "purchase/getPurList.do?page=" + pages + "&pageSize=10&cinstate=" + cinstate + "&findkey=" + $this.param,
            success: function (data) {
                if (!!data) {
                    loadingModdle.closeModdle();
                    var jsonArr = data.result;
                    var len = jsonArr.length;
                    var itemType = "";
                    var html = "";
                    for (var i = 0; i < jsonArr.length; i++) {
                        switch (jsonArr[i].instate) {
                            case "01":
                                itemType = "未完全入库";
                                break;
                            case "02":
                                itemType = "已完全入库";
                                break;
                            default:
                                itemType = "";
                                break;
                        }
                        html += "<tr data-cno='" + jsonArr[i].cgid + "' data-faultnum='" + jsonArr[i].faultnum + "'  data-price='" + jsonArr[i].cgprice + "'  data-money='" + jsonArr[i].cgmoney + "'  data-gno='" + jsonArr[i].gno + "' data-cid='" + jsonArr[i].cid + "' data-id='" + jsonArr[i].cid + "' data-afaultnum='" + jsonArr[i].afaultnum + "' data-anormalnum='" + jsonArr[i].anum + "'>" +
                            "	<td style='width: 160px;'><label><input type='radio' name='order'/>" + (jsonArr[i].cgid == null ? "" : jsonArr[i].cgid) + "</label></td>" +
                            "	<td data-gid='" + jsonArr[i].gid + "'>" + (jsonArr[i].gname == null ? "" : jsonArr[i].gname) + "</td>" +
                            "	<td>" + (jsonArr[i].gspec == null ? "" : jsonArr[i].gspec) + "</td>" +
                            "	<td>" + (jsonArr[i].unit == null ? "" : jsonArr[i].unit) + "</td>" +
                            "	<td>" + (jsonArr[i].proname == null ? "" : jsonArr[i].proname) + "</td>" +
                            "	<td>" + (jsonArr[i].cgdate == null ? "" : jsonArr[i].cgdate) + "</td>" +
                            "	<td>" + (jsonArr[i].cgnum == null ? "" : jsonArr[i].cgnum) + "</td>" +
                            "	<td>" + itemType + "</label></td>" +
                            "	<td>" + (jsonArr[i].storagednum == null ? "" : jsonArr[i].storagednum) + "</td>" +
                            "	<td>" + (jsonArr[i].storagenum == null ? "" : jsonArr[i].storagenum) + "</td>" +
                            "	<td>" + (jsonArr[i].comments == null ? "" : jsonArr[i].comments) + "</td>" +
                            "	<td><span onclick='openInRecordsModdle(this);'>详情</span>/<span onclick='openEditPurManageMsg(this);'>编辑</span>/<span onclick='deletePurManageMsg(this);'>删除</span></td>" +
                            "</tr>";
                    }
//html+="<tr><td>合计</td><td> </td><td> </td><td> </td><td> </td><td> </td><td>"+采购数量+"</td><td> </td><td>"+已入库数量+"</td><td>"+带入库数量+"</td><td style="position:relative;"> </td></tr>"
                    $("#purManagementMsg").html(html);
                    $this.changePage(data.pageNo, data.prePage, data.nextPage, data.totalPageBeans);
                    checkAllBtns.check("purManagementBtns", purManagement, "purManagement");
                    $("#purManagementNumber").html(($this.page == 0 ? 1 : $this.page) + "/" + ($this.lastPage == 0 ? 1 : $this.lastPage));
                    $("#purManagementTotal").html(data.totalCount);
                } else {
                    loadingModdle.closeModdle();
                }
            },
            error: function () {
                loadingModdle.closeModdle();
            }
        });
    },
    //改变变量的值
    changePage: function (page, prePage, nextPage, lastPage) {
        this.page = page;
        this.nextPage = nextPage;
        this.prePage = prePage;
        this.lastPage = lastPage;
    },
    //搜索功能
    searchMsg: function (param) {
        var $this = this;
        $this.param = param;
        $this.pageType($this.page);
    }
};

//获取详情页面列表信息
var itemDetails = {
    page: 1,
    nextPage: 1,
    prePage: 1,
    lastPage: 1,
    param: "",
    pageType: function (type) {
        loadingModdle.showModdle();
        var pages = 0;
        var pagesize=10;
        var $this = this;
        var id = $("#inventoryRecords").attr("data-cid");
        if (type == "preAllPage") {
            pages = 1;
        } else if (type == "prePage") {
            pages = this.prePage;
        } else if (type == "nextPage") {
            pages = this.nextPage;
        } else if (type == "nextAllPage") {
            pages = this.lastPage;
        } else if (typeof(type) == "number") {
            pages = type;
        }
        ;
        $.ajax({
            type: "get",
            url: HEADER + "purchase/getPurDetailById.do?page=" + pages + "&pageSize="+pagesize+"&id=" + id,
            success: function (data) {
                if (!!data) {
                    loadingModdle.closeModdle();
                    var jsonArr = data.result;
                    var stype = "";
                    var html = "";
                    var aStatus = "";
                    var type1 = "";
                    for (var i = 0; i < jsonArr.length; i++) {
                        switch (jsonArr[i].sopertype) {
                            case "01":
                                stype = "入库";
                                aStatus = "入库完成";
                                type1 = "success";
                                break;
                            case "02":
                                stype = "出库";
                                break;
                            default:
                                stype = "";
                                break;
                        }
                        switch (jsonArr[i].auditstatus) {
                            case "00":
                                aStatus = "出库完成";
                                type1 = "success";
                                break;
                            case "01":
                                aStatus = "审核中";
                                type1 = "pending";
                                break;
                            case "02":
                                aStatus = "审核通过（仓库）";
                                type1 = "success";
                                break;
                            case "03":
                                aStatus = "审核未通过（仓库）";
                                type1 = "notpass";
                                break;
                            case "04":
                                aStatus = "撤销";
                                type1 = "others";
                                break;
                            case "05":
                                aStatus = "审核通过（领导）";
                                type1 = "success";
                                break;
                            case "06":
                                aStatus = "审核未通过（领导）";
                                type1 = "notpass";
                                break;
                            default:
                                aStatus = "其他";
                                type1 = "others";
                                break;
                        }
                        html += "<tr>" +
                            "	<td>" + cr_item.getXuhao(i, pages,pagesize) + "</td>" +
                            "	<td>" + (jsonArr[i].sdate == null ? "" : jsonArr[i].sdate) + "</td>" +
                            "	<td>" + stype + "</td>" +
                            "	<td>" + (jsonArr[i].sperson == null ? "" : jsonArr[i].sperson) + "</td>" +
                            "	<td>" + (jsonArr[i].snum == null ? "" : jsonArr[i].snum) + "</td>" +
                            "	<td class='" + type1 + "Class'>" + aStatus + "</td>" +
                            "</tr>";
                    }
                    $("#itemDetailsMsg").html(html);
                    $this.changePage(data.pageNo, data.prePage, data.nextPage, data.totalPageBeans);
                    checkAllBtns.check("itemDetailsBtns", itemDetails, "itemDetails");
                    $("#itemDetailsNumber").html(($this.page == 0 ? 1 : $this.page) + "/" + ($this.lastPage == 0 ? 1 : $this.lastPage));
                    $("#itemDetailsTotal").html(data.totalCount);
                } else {
                    loadingModdle.closeModdle();
                }
            },
            error: function () {
                loadingModdle.closeModdle();
            }
        });
    },
    //改变变量的值
    changePage: function (page, prePage, nextPage, lastPage) {
        this.page = page;
        this.nextPage = nextPage;
        this.prePage = prePage;
        this.lastPage = lastPage;
    },
    //搜索功能
    searchMsg: function (param) {
        var $this = this;
        $this.param = param;
        $this.pageType($this.page);
    }
};