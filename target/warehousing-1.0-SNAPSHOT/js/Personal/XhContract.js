var goodslist = null;
var getGoodListURL = HEADER + "goods/queryGoodsList.do?page=1&pageSize=10000"
$(function () {
    // cr_item.getItemType($("#addGoodsType"));//获取物品类型
    XhContractList.pageType("preAllPage");
    cr_item.getAllEndProduct($("#addGoodsType"));//获取所有成品
    // cr_normal.showTime1();
    cr_item.getAllChecker($("#Checker"));//审核人
    cr_item.getAllManager($("#StockManager"));//管理员
    cr_item.getAllUser($("#CSPerson"));//抄送人
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

//删除销货管理列表信息
function deleteXhContractMsg(dom) {
    var stackedNum = $(dom).parents("tr").children().eq(10).children().eq(0).html();
    if (stackedNum == "0") {
        cr_dialogBox.confirm(true, "确认要删除吗?", function (flag) {
            if (flag) {
                var url = HEADER + "xh/deleteXhContract.do";
                var id = $(dom).parents("tr").attr("data-id");
                $.ajax({
                    type: "post",
                    url: url,
                    data: {
                        "id": id
                    },
                    success: function (data) {
                        cr_item.showTip(data.status, function () {
                            XhContractList.pageType(XhContractList.page);
                        });
                    }
                });
            }
        });
    } else {
        cr_dialogBox.alert(true, "合同已有出库数，不能删除!");
    }
}

//检测是否输入了值
function checkHasValue() {
    var cnum = $("#addNumber").val();//销货数量
    var cgprice = $("#addPrice").val();//销货单价
    var cgmoney = cnum * cgprice;//销货金额
    $("#addMoney").val(cgmoney);
}

//搜索
function search() {
    var searchValue = $("#search").val();
    XhContractList.searchMsg(searchValue);
}

/*----------------------------录入销货单方法------------------------*/

//根据选择不同物品给录入销货单框赋值
function getOtherMsg(dom) {
    var optionDom = $(dom).find("option:selected");
    var gspec = !!optionDom.attr("gspec") ? optionDom.attr("gspec") : "";
    var unit = !!optionDom.attr("unit") ? optionDom.attr("unit") : "";
    var provider = !!optionDom.attr("provider") ? optionDom.attr("provider") : "";
    var gid = $(dom).find("option:selected").val();
    // alert(unit);
    $("#shoppingUnit").html(unit);
    $("#addProvider").html(provider);
    $("#addGspec").html(gspec);
    $("#addNumber").val("");
    $("#addCdate").val("");
    $("#addCgPrice").val("");
    $("#addCgMoney").val("");
}

//提交新增录入销货单
function addPurchaseList() {
    var url = HEADER + "xh/addXhContract.do";
    var cid = $("#enterShoppingOrderNumber").html();
    var gid = $("#addGoodsType").find("option:selected").val();
    var cnum = $("#addNumber").val();
    var xhprice = $("#addPrice").val();
    var xhmoney = $("#addMoney").val();
    var customer = $("#addCustomer").val();
    var XhTime = $("#XhTime").val();//销货时间
    if (gid != "") {
        if (cnum != "") {
            if (xhprice != "") {
                $.ajax({
                    type: "post",
                    url: url,
                    data: {
                        "xhid": cid,//销货单号
                        "gid": gid,//物品编号
                        "xhnum": cnum,//销货数量
                        "outstate": "01",//物品入库状态
                        "xhstatus": "01",
                        "customer": customer,
                        "outednum": 0,//已入库数量
                        "outnum": cnum,//未入库数量
                        "xhprice": xhprice,
                        "xhmoney": xhmoney,
                        "createtime":XhTime
                    },
                    success: function (data) {
                        if (data.msg == 1 || data.msg == "1") {
                            cr_dialogBox.alert(true, "提交成功!");
                            $("#enterShoppingList").modal("hide");
                            XhContractList.pageType(XhContractList.page);
                        } else if (data.msg == 0 || data.msg == "0") {
                            cr_dialogBox.alert(true, "提交失败!");
                        }
                    }
                });
            } else {
                cr_dialogBox.alert(true, "请输入销货单价!");
            }
        } else {
            cr_dialogBox.alert(true, "请输入销货数量!");
        }

    } else {
        cr_dialogBox.alert(true, "请选择物品类型!");
    }
}

/*----------------------------录入销货单方法结束------------------------*/

/*-----------------------发货出库方法---------------------------*/

//提交发货出库信息
function submitIncomingMsg() {
    var url = HEADER + "stock/outStockBefore.do";
    var snum = $("#sendnum").val();//发货出库数量
    var sopertype = "02";
    var xhid = $("#inGoodsName").attr("data-xhid");
    var gno = $("#inGoodsName").attr("data-gid");
    var outnum = $("#stocknum").html();//待出库数量
    var stoargenum = $("#inGoodsName").attr("data-storagenum");
    var aoutnum = $("#inGoodsName").attr("data-aoutnum");//出库审批数量
    var Auditor = $("#Checker").find("option:selected").attr("data-name"); //获取审核人
    var Manager = $("#StockManager").find("option:selected").attr("data-name");//获取仓管员
    var csPerson = $("#CSPerson").find("option:selected").attr("data-name");//抄送人
    var CkTime = $("#CkTime").val();//出库时间
    console.log(Auditor + "+" + Manager + "+" + csPerson);
    if (snum != "") {
        if (snum * 1 <= outnum * 1) {
            if(snum*1<=stoargenum*1-aoutnum*1){
                    $.ajax({
                        type: "post",
                        url: url,
                        data: {
                            "xhid": xhid,
                            "gid": gno,
                            "snum": snum,
                            "sopertype": sopertype,
                            "stype1": "01",
                            "csUser": csPerson,//抄送人
                            "auditperson": Manager,//仓管员
                            "auditperson1": Auditor,//审核人
                            "sdate":CkTime//出库时间
                        },
                        success: function (data) {
                            cr_item.showTip(data.msg, function () {
                                $("#goodsInventory").modal("hide");
                                XhContractList.pageType(XhContractList.page);
                            });
                        }
                    });
                }else{
                    cr_dialogBox.alert(true, "发货数量不能大于待出库数!");
            }
            }else{
                cr_dialogBox.alert(true, "发货数量不能大于库存数量-审批数量");

            }
    } else {
        cr_dialogBox.alert(true, "请输入发货数量!");
    }
}

//提交退货信息
function submitReturnMsg() {
    var url = HEADER + "stock/addStock.do";
    var snum = $("#outNumber").val();
    var sopertype = "01";
    var stype = "02";//销售退货入库
    var sthstate = $("#outReturnType").val();
    var xhid = $("#outGoodsName").attr("data-xhid");
    var gno = $("#outGoodsName").attr("data-gid");
    var stackedNum = $("#outGoodsName").attr("data-stackedNum");
    var ckmanager=$("#ckmanager").find("option:selected").attr("data-name");//获取仓管员
    if (snum != "") {
        if (snum * 1 <= stackedNum * 1) {
            $.ajax({
                type: "post",
                url: url,
                data: {
                    "xhid": xhid,
                    "gid": gno,
                    "snum": snum,
                    "sopertype": sopertype,
                    "stype": stype,
                    "sthstate": sthstate,
                    "auditperson":ckmanager

                },
                success: function (data) {
                    cr_item.showTip(data.msg, function () {
                        $("#returnGoods").modal("hide");
                        XhContractList.pageType(XhContractList.page);
                    });
                }
            });
        } else {
            cr_dialogBox.alert(true, "退货数量不能大于已入库数量!");
        }
    } else {
        cr_dialogBox.alert(true, "请输入退货数量!");
    }
}

/*-----------------------入库方法结束---------------------------*/

/*------------------------------------窗口打开方法开始-----------------------------------------*/

//打开出库库记录窗口
function openInRecordsModdle(dom) {
    var id = $(dom).parents("tr").attr("data-id");
    $("#inventoryRecords").attr("data-xhid", id);
    $("#inventoryRecords").modal("show");
    itemDetails.pageType("preAllPage");
}

//打开物品发货出库窗口
//出库数量不能大于销货数量
function openGoodsInModdle() {
    //当前选中的订单列
    var trDom = $("#purManagementMsg").find("input[name=order]:checked").parents("tr");
    if (!!trDom.children().eq(1).children().eq(0).html()) {
        var outnum = trDom.children().eq(11).children().eq(0).html();//待出库数
        if (outnum > 0) {
            var gname = trDom.children().eq(1).children().eq(0).html();//物品名
            var gspec = trDom.children().eq(2).children().eq(0).html();//规格型号
            var xhnum = trDom.children().eq(6).children().eq(0).html();//销货数量
            //var xhnum = trDom.children().eq(12).children().eq(0).html();//物品正常库存数量
            var anum = trDom.children().eq(13).children().eq(0).html();//物品审批数量
            var order = $.trim(trDom.children().eq(0).children().eq(0).text());//销货单号
            var gtype = trDom.attr("data-gtype");//物品类型
            // console.log("gtype" + gtype);
            var gno = trDom.attr("data-gid");
            var gid = trDom.children().eq(1).attr("data-gid");
            var xhid = trDom.attr("data-xhid");
            var id = trDom.attr("data-id");
            var storagenum = trDom.attr("data-storagenum");//物品正常库存数量
            var aoutnum = trDom.attr("data-aoutnum");
            var customer = trDom.children().eq(4).children().eq(0).html();
            var gtypestr = "";
            switch (gtype) {
                case "01":
                    gtypestr = "主料";
                    break;
                case "02":
                    gtypestr = "辅料";
                    break;
                case "03":
                    gtypestr = "成品";
                    break;
            }
            $("#goodsInventory").modal("show");
            $("#xhid").html(xhid);
            $("#inGoodsName").html(gname).attr({
                "data-storagenum": storagenum,
                "data-xhid": id,
                "data-gid": gid,
                "data-gno": gno,
                "data-xhnum": xhnum,
                "data-aoutnum":aoutnum
            });
            // console.log("物品类型" + gtypestr);//物品类型
            $("#outgoodstype").html(gtypestr);//物品类型
            $("#goodspec").html(gspec);//规格型号
            $("#stocknum").html(outnum);//库存数量
            $("#customer").html(customer);//销货商
            $("#inOrder").val(order);//销货单号
            $("#sendnum").val("");
            cr_item.getAllChecker($("#Checker"));//审核人
            cr_item.getAllManager($("#StockManager"));//管理员
            cr_item.getAllUser($("#CSPerson"));//抄送人
        } else {
            cr_dialogBox.alert(true, "待出库数为0，不能发货出库!");
        }
    } else {
        cr_dialogBox.alert(true, "请选择销货合同列表列!");
    }
}

//打开退货窗口
//已入库数位0不能打开退货窗口 退货数量不能大于已入库数量
function openGoodsRetrun() {
    //当前选中的订单列
    var trDom = $("#purManagementMsg").find("input[name=order]:checked").parents("tr");
    if (!!trDom.children().eq(1).children().eq(0).html()) {
        var name = trDom.children().eq(1).children().eq(0).html();
        var gspec = trDom.children().eq(2).children().eq(0).html();
        var provider = trDom.children().eq(4).children().eq(0).html();
        var unit = trDom.children().eq(3).children().eq(0).html();
        var gid = trDom.children().eq(1).attr("data-gid");
        var order = $.trim(trDom.children().eq(0).children().eq(0).text());
        var gno = trDom.attr("data-gno");
        var xhid = trDom.attr("data-id");
        var stackedNum = trDom.children().eq(10).children().eq(0).html() * 1;//已出库数量
        cr_item.getAllManager($("#ckmanager")); //给下拉赋值仓库管理员
        if ((stackedNum + "") != "NaN" && stackedNum > 0) {
            $("#returnGoods").modal("show");
            $("#outGoodsName").html(name).attr({
                "data-xhid": xhid,
                "data-gid": gid,
                "data-gno": gno,
                "data-stackedNum": stackedNum//已出库数量
            });
            $("#outGspec").html(gspec);
            $("#inxhid").html(xhid);
            $("#outUnit").html(unit);
            $("#outProvider").html(provider);
            $("#outOrder").val(order);
            $("#outReturnType").val("00");
            $("#outNumber").val("");
        } else {
            cr_dialogBox.alert(true, "该物品还未发货，不能进行退货操作!");
        }
    } else {
        cr_dialogBox.alert(true, "请选择销货列表列!");
    }
}

//打开录入销货清单窗口
function openEnterShoppingList() {
    var url = HEADER + "xh/getNextXhId.do";
    $.ajax({
        type: "get",
        url: url,
        async: false,
        success: function (data) {
            if (!!data) {
                $("#enterShoppingOrderNumber").html(data.nextXhId);
            }
        }
    });
    $("#addItemType").val("");
    $("#shoppingUnit").html("");//单位
    $("#addProvider").html("");//供应商
    $("#addGspec").html("");//物品规格型号
    $("#addCustomer").val("");//销货商
    $("#addPrice").val("");//销货单价
    $("#addNumber").val("");//销货数量
    $("#addMoney").val("");//销货金额
    $("#enterShoppingList").modal("show");
    cr_item.getAllEndProduct($("#addGoodsType"));
}

/*------------------------------------窗口打开方法结束-----------------------------------------*/

//获取销货合同列表
var XhContractList = {
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
            url: HEADER + "xh/getXhContractList.do?page=" + pages + "&pageSize=10&outstate=" + cinstate + "&findkey=" + $this.param,
            success: function (data) {
                if (!!data) {
                    loadingModdle.closeModdle();
                    var jsonArr = data.result;
                    var len = jsonArr.length;
                    var itemType = "";
                    var html = "";
                    for (var i = 0; i < jsonArr.length; i++) {
                        switch (jsonArr[i].outstate) {
                            case "01":
                                itemType = "未完全出库";
                                break;
                            case "02":
                                itemType = "已完全出库";
                                break;
                            default:
                                itemType = "";
                                break;
                        }
                        html += "<tr data-storagenum='" + jsonArr[i].storagenum + "'data-aoutnum='" + jsonArr[i].aoutnum + "' data-gno='" + jsonArr[i].gno + "' data-outnum='" + jsonArr[i].outnum + "' data-gtype='" + jsonArr[i].gtid + "'  data-xhid='" + jsonArr[i].xhid + "' data-id='" + jsonArr[i].id + "'>" +
                            "	<td style='width: 160px;'><label><input type='radio' name='order'/>" + (jsonArr[i].xhid == null ? "" : jsonArr[i].xhid) + "</label></td>" +
                            "	<td data-gid='" + jsonArr[i].gid + "'><label>" + (jsonArr[i].gname == null ? "" : jsonArr[i].gname) + "</label></td>" +
                            "	<td><label>" + (jsonArr[i].gspec == null ? "" : jsonArr[i].gspec) + "</label></td>" +
                            "	<td><label>" + (jsonArr[i].unit == null ? "" : jsonArr[i].unit) + "</label></td>" +
                            "	<td><label>" + (jsonArr[i].customer == null ? "" : jsonArr[i].customer) + "</label></td>" +
                            "	<td><label>" + (jsonArr[i].xhdate == null ? "" : jsonArr[i].xhdate) + "</label></td>" +
                            "	<td><label>" + (jsonArr[i].xhnum == null ? "" : jsonArr[i].xhnum) + "</label></td>" +
                            "	<td><label>" + (jsonArr[i].xhprice == null ? "" : jsonArr[i].xhprice) + "</label></td>" +
                            "	<td><label>" + (jsonArr[i].xhmoney == null ? "" : jsonArr[i].xhmoney) + "</label></td>" +
                            "	<td><label>" + itemType + "</label></td>" +
                            "	<td><label>" + (jsonArr[i].outednum == null ? "" : jsonArr[i].outednum) + "</label></td>" +
                            "	<td><label>" + (jsonArr[i].outnum == null ? "" : jsonArr[i].outnum) + "</label></td>" +
                            "	<td><label>" + (jsonArr[i].nnum == null ? "" : jsonArr[i].nnum) + "</label></td>" +
                            "	<td><label>" + (jsonArr[i].anum == null ? "" : jsonArr[i].anum) + "</label></td>" +
                            "	<td><label><span onclick='openInRecordsModdle(this);'>详情</span>/<span onclick='deleteXhContractMsg(this);'>删除</span></label></td>" +
                            "</tr>";
                    }
//html+="<tr><td>合计</td><td> </td><td> </td><td> </td><td> </td><td> </td><td>"+销货数量+"</td><td> </td><td>"+已入库数量+"</td><td>"+带入库数量+"</td><td style="position:relative;"> </td></tr>"
                    $("#purManagementMsg").html(html);
                    $this.changePage(data.pageNo, data.prePage, data.nextPage, data.totalPageBeans);
                    checkAllBtns.check("purManagementBtns", XhContractList, "XhContractList");
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
        var id = $("#inventoryRecords").attr("data-xhid");
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
            url: HEADER + "xh/getXhContractDetailById.do?page=" + pages + "&pageSize="+pagesize+"&id=" + id,
            success: function (data) {
                if (!!data) {
                    loadingModdle.closeModdle();
                    var jsonArr = data.result;
                    var len = jsonArr.length;
                    var stype = "";
                    var html = "";
                    var aStatus = "";
                    var type1 = "";
                    for (var i = 0; i < jsonArr.length; i++) {
                        switch (jsonArr[i].sopertype) {
                            case "01":
                                stype = "入库";
                                var aStatus = "入库完成";
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
                                aStatus  = "审核中";
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
                        console.log(jsonArr[i].sdate);
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