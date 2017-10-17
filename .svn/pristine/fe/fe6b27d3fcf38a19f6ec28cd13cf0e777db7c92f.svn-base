$(function () {
    cr_item.initTime($("#repairsAndFailuresBeginTime"), "start"); //修理和报故障时间选择器的时间初始化为本位第一天到今天
    cr_item.initTime($("#repairsAndFailuresEndTime"));
    storageInformation.pageType("preAllPage"); //获取仓储库存列表
    repairsAndFailures.pageType("preAllPage"); //获取修理和报故障记录列表
    // $("#saveStockDownloadModel").prop("href", HEADER + "exportStock.do?downExcel=1"); //保存库存记录模板
    $("#StockInformationExport").prop("href", HEADER + "stock/exportStock.do"); //导出所有
    showTime();
    showTime1();
});

//选择日期
function showTime() {
    $(".time-select").datetimepicker({
        language: 'en',
        format: "yyyy-mm-dd",
        minView: "month",
        todayBtn: 1, //点击显示今天时间
        autoclose: 1, //选择日期后关闭
        todayHighlight: 1, //当前天数高亮
        startView: 3, //开始的显示年、还是月、或日、时、分、秒
        forceParse: 0, //默认是那一天
        minuteStep: 1
    });
}
//选择日期
function showTime1(){
    $(".time-select1").datetimepicker({
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
}

//领料用途选择改变时自动刷新下面的成品规格型号和供应商
function getEndprodSizeAndProv(dom) {
    var gid = $(dom).val();
    // alert(gid);
    var url = HEADER + "goods/queryGoodsList.do?page=1&pageSize=1&gid=" + gid;
    $.ajax({
        type: "get",
        url: url,
        async: true,
        success: function (data) {
            if (!!data) {
                var jsonArr = data.result;
                var html1 = "";
                var html2 = "";
                for (var i = 0; i < jsonArr.length; i++) {
                    html1 += jsonArr[i].providername;//供应商
                    html2 += jsonArr[i].gspec;//规格型号
                }
                $("#endproductSize1").html(html2);
                $("#endproductProvider1").html(html1);
            }
        }
    });
}


//获取到当前登录人和部门
function getLoginPerson(type) {
    var url = HEADER + "user/check_getPrincipalUser.do";
    $.ajax({
        type: "get",
        url: url,
        async: true,
        success: function (data) {
            if (!!data) {
                var jsonArr = data.result;
                var html1 = "";
                var html2 = "";
                //for (var i = 0; i <  jsonArr.length; i++) {
                    html1 = jsonArr.uname;//用户名
                    html2 = jsonArr.deptname;//部门名
                    console.log(html1+"///"+html2);
                //}
                if (type = "use") {
                    $("#User").html(html1);
                    $("#UseDepart").html(html2);
                } else if (type = "material") {
                    // console.log(html1+"///"+html2);
                    $("#materialOutstock").attr("loginPerson", html1);
                }
            }
        }
    });
}

//搜索方法（）
function search(type) {
    var value = "";
    if (type == "仓储库存") {
        value = $("#inventorySearch").val();
        storageInformation.searchMsg(value);
    } else if (type == "修理和报故障") {
        value = $("#repairsAndFailuresSearch").val();
        repairsAndFailures.searchMsg(value);
    }
}

// //点击导出
// function inventoryInformationExport() {
// 	//当前选中的订单列
// 	var trDom = $("#outinboundDetailsMsg").find("input[name=order]:checked").parents("tr");
// 	if(!!trDom.children().eq(0).html()) {
// 		var id = trDom.attr("data-id");
// 		var gtid = "";
// 		var findkey = "";
// 		$("#inventoryInformationExport").prop("href", HEADER + "stock/exportStock.do?gtid="+ gtid +"&findkey=" + findkey); //导出
// 	} else {
// 		cr_dialogBox.alert(true, "请选择仓储库存列表列!");
// 	}
// }
/*----------------------------打开编辑窗口---------------------------*/

//打开预警设置
function openWarningSet() {
    $("#warningSet").modal("show");
    warningSetting.pageType("preAllPage");
}

function openGoodsInModdle(dom, type) {
    //当前选中的订单列
    var trDom = $("#storageInformationMsg").find("input[name=order]:checked").parents("tr");
    if (!!trDom.children().eq(0).html()) {
        var name = trDom.children().eq(1).html(); //物品名称
        var sopertype = trDom.children().eq(2).html(); //物品类型
        var itemSize = trDom.children().eq(3).html(); //规格型号
        var unit = trDom.children().eq(4).html(); //单位
        var provider = trDom.children().eq(5).html(); //供应商
        var stockedNum = trDom.children().eq(8).html(); //库存数量
        var normalNum = trDom.children().eq(9).html(); //正常数量
        var errorNum = trDom.children().eq(10).html();//故障数量
        var aNum = trDom.children().eq(11).html();//审批数量
        var endproductProvider = ""; //成品供应商
        var endproductSize = ""; //成品规格型号
        var id = trDom.attr("data-id");
        var gid = trDom.attr("data-gid"); //物品编号
        if (type == "repair") {
            $("#repair").modal("show"); //打开修理模态框
            $("#repairsGoodsName").html(name).attr({"data-gid": gid, "data-id": id});//物品编号、id、故障数量
            $("#repairsGoodsSize").html(itemSize); //规格型号
            $("#repairsGoodsProvider").html(provider); //供应商
            $("#repairsGoodsUnit").html(unit); //单位
            $("#repairsErrorGoodsNumber").html(errorNum); //故障数量
            $("#repairsGoodsNum").val(""); //报修理数量
        } else if (type == "subError") {
            $("#subError").modal("show"); //打开报故障模态框
            $("#errorGoodsName").html(name).attr({"data-gid": gid, "data-id": id});
            $("#errorGoodsSize").html(itemSize); //规格型号
            $("#errorGoodsProvider").html(provider); //供应商
            $("#errorGoodsUnit").html(unit); //单位
            $("#errorGoodsStacked").html(stockedNum); //库存数量
            $("#errorGoodsNum").val(""); //报故障数量
        } else if (type == "useOutStock") {
            $("#useOutStock").modal("show"); //打开领用出库模态框
            $("#useOutGoodsName").html(name).attr({"data-gid": gid, "data-id": id});
            $("#useOutGoodsSize").html(itemSize); //规格型号
            $("#useOutGoodsProvider").html(provider); //供应商
            $("#useOutGoodsType").html(sopertype); //物品类型
            $("#useOutGoodsUnit").html(unit); //单位
            $("#errorGoodsStacked").html(stockedNum); //库存数量
            $("#useOutGoodsNormalNumber").html(normalNum); //正常数量
            $("#useOutuseNumber").val("").attr("data-anum",aNum); //领用数量（将审核数量传过去）
            getLoginPerson('use');//获取到当前登录人及其部门
            cr_item.getAllChecker($("#useOutStockChecker")); //给下拉赋值审核人
            cr_item.getAllManager($("#useOutStockManager")); //给下拉赋值仓库管理员
            cr_item.getAllUser($("#useOutStockCSPerson"));//给下拉框赋值抄送人
        } else if (type == "materialOutstock") {
            if(sopertype == "成品"){
                cr_dialogBox.alert(true, "成品不能进行此操作！");
            }else {
                $("#materialOutstock").modal("show"); //打开领料出库模态框
                $("#materialGoodsName").html(name).attr({"data-gid": gid, "data-id": id}); //物品名称
                getLoginPerson('material');//获取到当前登录人及其部门
                $("#materialGoodsType").html(sopertype); //物品类型
                $("#materialGoodsSize").html(itemSize); //规格型号
                $("#materialGoodsProvider").html(provider); //供应商
                $("#materialGoodsUnit").html(unit); //单位
                $("#materialGoodsNormalNumber").html(normalNum); //正常数量
                $("#endproductSize1").html("");//成品规格型号
                $("#endproductProvider1").html("");//成品供应商
                $("#materialuseNumber").val("").attr("data-anum",aNum);//领料数量（将审核数量传过去）
                cr_item.getAllEndProduct($("#materialPurpose")); //显示领料用途(所有成品)
                cr_item.getAllChecker($("#materialStockChecker")); //给下拉赋值审核人
                cr_item.getAllManager($("#materialStockManager")); //给下拉赋值仓库管理员
                cr_item.getAllUser($("#materialStockCSPerson"));//给下拉框赋值抄送人
            }
        } else if (type == "endproductInstock") {
            if (sopertype == "成品") {//（物品类型为成品的才能打开这个模态框）
                $("#endproductInstock").modal("show"); //打开成品入库模态框
                $("#endproductName").html(name).attr({"data-gid": gid, "data-id": id, "data-num": normalNum}); //物品名称
                $("#endproductType").html(sopertype); //物品类型
                $("#endproductSize2").html(itemSize); //规格型号
                $("#endproductProvider2").html(provider); //供应商
                $("#endproductUnit").html(unit); //单位
                $("#endproductInstock").val(""); //领用数量
                cr_item.getAllManager($("#ckmanager")); //给下拉赋值仓库管理员
            } else {
                cr_dialogBox.alert(true, "只有成品才能进行此操作！");
            }
        }
    } else {
        cr_dialogBox.alert(true, "请选择采购列表列!");
    }
}

/*----------------------------打开编辑窗口结束---------------------------*/

/*============================提交编辑开始==============================*/


//提交修理、报故障信息
function submitREMsg(type) {
    var url = HEADER + "stock/addFaultInfo.do";
    var operateNumber = $("#" + type + "GoodsNum").val(); //修理数量或者报故障数量
    var id = $("#" + type + "GoodsName").attr("data-id"); //获取data-id
    var stackedNum = $("#" + type + "GoodsStacked").html(); //库存数量
    // var errorNum = $("#repairsGoodsName").html();//故障数量
    var typeName = "";
    var ftype = "";
    if (type == "repairs") {
        ftype = "02";
        typeName = "修理";
        var errorNum = $("#repairsErrorGoodsNumber").html();//故障数量
        console.log("操作数量：" + operateNumber + "，故障数量：" + errorNum);
        if (operateNumber * 1 <= errorNum * 1) {//修理数量小于故障数量
            $.ajax({
                type: "get",
                url: url,
                data: {
                    "gid": id,
                    "ftype": ftype,
                    "fnum": operateNumber
                },
                success: function (data) {
                    cr_dialogBox.alert(true, "确定要将这些设备置为正常吗？");
                    cr_item.showTip(data.msg, function () {
                        if (type == "repairs") {
                            $("#repair").modal("hide");
                        } else if (type == "error") {
                            $("#subError").modal("hide");
                        }
                        storageInformation.pageType(storageInformation.page);
                        repairsAndFailures.pageType(repairsAndFailures.page);
                    });
                }
            });
        } else {
            cr_dialogBox.alert(true, "修理数量不能大于故障数量!");
        }
    } else if (type == "error") {
        ftype = "01";
        typeName = "故障";
        if (stackedNum * 1 >= operateNumber * 1){
            $.ajax({
                type: "get",
                url: url,
                data: {
                    "gid": id,
                    "ftype": ftype,
                    "fnum": operateNumber
                },
                success: function (data) {
                    cr_dialogBox.alert(true, "确定要将这些设备置为正常吗？");
                    cr_item.showTip(data.msg, function () {//返回值方法
                        if (type == "repairs") {
                            $("#repair").modal("hide");
                        } else if (type == "error") {
                            $("#subError").modal("hide");
                        }
                        storageInformation.pageType(storageInformation.page);
                        repairsAndFailures.pageType(repairsAndFailures.page);
                    });
                }
            });
        }else{
            cr_dialogBox.alert(true,  "故障数量不能大于库存数量!");
        }
    }
}


//提交编辑领用出库信息
function submitUseOutstock(dom) {
    var url = HEADER + "stock/outStockBefore.do";
    var useNumber = $("#useOutuseNumber").val(); //获取领用数量
    var anum =  $("#useOutuseNumber").attr("data-anum");//审核数量
    var gid = $("#useOutGoodsName").attr("data-id"); //获取物品名称里的物品编号
    var usePerson = $("#User").val(); //领用人
    var useDepart = $("#UseDepart").val(); //领用部门
    var Auditor = $("#useOutStockChecker").find("option:selected").attr("data-name");//获取审核人
    var Manager = $("#useOutStockManager").find("option:selected").attr("data-name");//获取仓管员
    var csPerson = $("#useOutStockCSPerson").find("option:selected").attr("data-name");//抄送人
    var normalNumber = $("#useOutGoodsNormalNumber").html(); //获取正常数量
    var couldNum =normalNumber*1-anum*1;//可操作数量
    console.log("领用出库正常数量："+normalNumber);
    // console.log(Auditor + "----" + Manager + "-----" + csPerson);
    if (useNumber != "" || (useNumber != 0)) {
        if (couldNum * 1 >= useNumber * 1) {
            $.ajax({
                type: "post",
                url: url,
                data: {
                    "gid": gid,//物品ID
                    "snum": useNumber,//领用数量
                    "sopertype": "02",//02出库
                    "stype1": "04",//领用出库
                    "sperson": usePerson,//领用人
                    "csuser": csPerson,//抄送人
                    "auditperson": Manager,//仓库审核
                    "auditperson1": Auditor//领导审核
                },
                success: function (data) {
                       cr_item.showTip(data.msg, function () {
                        $("#useOutStock").modal("hide");
                        storageInformation.pageType(storageInformation.page);
                        repairsAndFailures.pageType(repairsAndFailures.page);
                    });
                }
            });
        } else {
            cr_dialogBox.alert(true, "领用数量不能大于正常数量-审批数量!");
        }
    } else {
        cr_dialogBox.alert(true, "请输入领用数量!");
    }
}

//提交编辑领料出库信息
function submitMaterialOutstock(dom) {
    var url = HEADER + "stock/outStockBefore.do";
    var useNumber = $("#materialuseNumber").val(); //获取领料数量的值
    var anum =  $("#materialuseNumber").attr("data-anum");//审核数量
    var gid = $("#materialGoodsName").attr("data-id"); //获取物品名称里的物品编号
    var Auditor = $("#materialStockChecker").find("option:selected").attr("data-name"); //获取审核人
    var Manager = $("#materialStockManager").find("option:selected").attr("data-name");//获取仓管员
    var usePurpose = $("#materialPurpose").find("option:selected").val();//获取领料用途
    var fgspec = $("#endproductSize1").val();//获取成品规格型号
    var csPerson = $("#materialStockCSPerson").find("option:selected").attr("data-name");//抄送人
    var normalNumber = $("#materialGoodsNormalNumber").text(); //获取正常数量
    var couldNum =normalNumber*1-anum*1;//可操作数量
    var usePerson = $("#materialOutstock").attr("loginPerson");
    console.log("领料出库正常数量："+normalNumber);
    console.log(Auditor + "----" + Manager + "-----" + csPerson);
    // alert(usePerson);
    if (useNumber != "" || (useNumber != 0)) {
        if (couldNum * 1 >= useNumber * 1) {
            $.ajax({
                type: "post",
                url: url,
                data: {
                    "gid": gid,//物品编号
                    "snum": useNumber,//领料数量
                    "sopertype": "02",//出库
                    "stype1": "03",//领料出库
                    "sperson": usePerson,//领用人
                    "csuser": csPerson,//抄送人
                    "auditperson": Manager,//仓管员
                    "auditperson1": Auditor,//审核人
                    "museto": usePurpose,//领料用途
                    "fgspec": fgspec
                },
                success: function (data) {
                         cr_item.showTip(data.msg, function () {
                        $("#materialOutstock").modal("hide");
                        storageInformation.pageType(storageInformation.page);
                        repairsAndFailures.pageType(repairsAndFailures.page);
                    });
                }
            });
        } else {
            // alert(normalNumber+"<"+useNumber);
            cr_dialogBox.alert(true, "领料数量不能大于正常数量-审批数量!");
        }
    } else {
        cr_dialogBox.alert(true, "请输入领料数量!");
    }
}

//提交编辑成品入库信息
function submitEndProductInStock() {
    var url = HEADER + "stock/addStock.do";
    var instockNumber = $("#endproductInStock2").val(); //获取入库数量的值
    var normalNumber = $("#endproductName").attr("data-num");
    var id = $("#endproductName").attr("data-id"); //获取物品名称里的物品编号
    var usePerson = $("#User").val(); //领用人
    var useDepart = $("#UseDepart").val(); //领用部门
    var CPRktime = $("#CPRktime").val();//入库时间
    var ckmanager=$("#ckmanager").find("option:selected").attr("data-name");//仓管员
    console.log("成品入库正常数量："+normalNumber);
    if (instockNumber != "") {
            $.ajax({
                type: "post",
                url: url,
                data: {
                    "gid": id,
                    "snum": instockNumber,
                    "sopertype": "01", //操作类型（01入库）
                    "stype": "03",
                    "sperson": usePerson,
                    "sdate":CPRktime,
                    "ternoverp": useDepart,
                    "auditperson":ckmanager
                },
                success: function (data) {
                    cr_item.showTip(data.msg, function () {
                        $("#endproductInstock").modal("hide");
                        storageInformation.pageType(storageInformation.page); //刷新仓储库存页面     *****是否要刷新出入库记录，怎么刷新*****
                    });
                }
            });
    } else {
        cr_dialogBox.alert(true, "请输入入库数量!");
    }
}

//提交预警设置物品信息
function submitWarningSetInfo() {
    var url = HEADER + "stock/setHqWarning.do";
    var trDom = $("#warningSettingMsg").children();
    var jsonArr = [];
    for (var i = 0; i < trDom.length; i++) {
        jsonArr.push({
            "id": $(trDom[i]).attr("data-id"),
            "swnum": $(trDom[i]).children().eq(6).children().eq(0).val()
        });
    }
    $.ajax({
        type: "post",
        url: url,
        data: {
            "jsonData": JSON.stringify(jsonArr)
        },
        success: function (data) {
            cr_item.showTip(data.msg, function () {
                storageInformation.pageType(storageInformation.page);
                $("#warningSet").modal("hide");
            });
        }
    });
}

/*============================提交编辑结束==============================*/

//导入库存盘点信息
function doUpload(dom) {
    var formData = new FormData($("#uploadForm")[0]);
    var reg = new RegExp("xl");
    var fileUrl = cr_item.getObjectURL(dom.files[0]);
    var fileName = $(dom).val().split(".")[1];
    var url = HEADER + "putinStock.do";
    if (fileUrl != "") {
        if (reg.exec(fileName)) {
            $.ajax({
                url: url,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    cr_dialogBox.alert(true, "上传成功！");
                    console.log(JSON.stringify(data));
                },
                error: function (data) {
                    cr_dialogBox.alert(true, "上传失败!");
                }
            });
        } else {
            cr_dialogBox.alert(true, "文件格式不正确！");
        }
    }
}

//获取仓储库存（仓储库存）列表
var storageInformation = {
    page: 1,
    nextPage: 1,
    prePage: 1,
    lastPage: 1,
    param: "",
    pageType: function (type) {
        loadingModdle.showModdle();
        var pages = 0;
        var pagesize=5;
        var $this = this;
        var sopertype = $("#itemType").val(); //物品类型
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
        };
        $.ajax({
            type: "get",
            url: HEADER + "stock/getStockGoodsList.do?page=" + pages + "&pageSize="+pagesize+"&findkey=" + $this.param + "&gtid=" + sopertype,
            success: function (data) {
                $("#inventoryInformationExport").attr("href", HEADER + "check/exportStock.do?findkey=" + $this.param);
                if (!!data) {
                    loadingModdle.closeModdle();
                    var jsonArr = data.result;
                    var len = jsonArr.length;
                    var gType = "";
                    var html = "";
                    var className = "";
                    for (var i = 0; i < jsonArr.length; i++) {
                        switch (jsonArr[i].gtid) {
                            case "01":
                                gType = "主料";
                                break;
                            case "02":
                                gType = "辅料";
                                break;
                            case "03":
                                gType = "成品";
                                break;
                            default:
                                gType = "";
                                break;
                        }
                        try {
                            if (jsonArr[i].warnnum * 1 > jsonArr[i].storagenum) {
                                className = "colorRed";
                            } else {
                                className = "";
                            }
                        } catch (e) {
                            className = "";
                        }
                        //序号+物品名+物品类型+规格型号+单位+供应商+历史出库+历史入库+库存+正常数量+故障数量+审批数量
                        html += "<tr class='" + className + "' data-id='" + jsonArr[i].id + "' data-gid='" + jsonArr[i].gid + "'>" +
                            "	<td style='width: 100px;'><label><input type='radio' name='order'/>" + cr_item.getXuhao(i, pages,pagesize) + "</label></td>" +
                            "	<td>" + (jsonArr[i].gname == null ? "" : jsonArr[i].gname) + "</td>" +
                            "	<td>" + gType + "</td>" +
                            "	<td>" + (jsonArr[i].gspec == null ? "" : jsonArr[i].gspec) + "</td>" +
                            "	<td>" + (jsonArr[i].unit == null ? "" : jsonArr[i].unit) + "</td>" +
                            "	<td>" + (jsonArr[i].provider == null ? "" : jsonArr[i].provider) + "</td>" +
                            "	<td>" + (jsonArr[i].hisoutsum == null ? "" : jsonArr[i].hisoutsum) + "</td>" +
                            "	<td>" + (jsonArr[i].hisinsum == null ? "" : jsonArr[i].hisinsum) + "</td>" +
                            "	<td>" + (jsonArr[i].storagenum == null ? "" : jsonArr[i].storagenum) + "</td>" +
                            "	<td>" + (jsonArr[i].nsum == null ? "" : jsonArr[i].nsum) + "</td>" +
                            "	<td>" + (jsonArr[i].faultnum == null ? "" : jsonArr[i].faultnum) + "</td>" +
                            "	<td>" + (jsonArr[i].anum == null ? "" : jsonArr[i].anum) + "</td>" +
                            "</tr>";
                    }
                    $("#storageInformationMsg").html(html);
                    $this.changePage(data.pageNo, data.prePage, data.nextPage, data.totalPageBeans);
                    checkAllBtns.check("storageInformationBtns", storageInformation, "storageInformation");
                    $("#storageInformationNumber").html(($this.page == 0 ? 1 : $this.page) + "/" + ($this.lastPage == 0 ? 1 : $this.lastPage));
                    $("#storageInformationTotal").html(data.totalCount);
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

//查询预警设置列表
var warningSetting = {
    page: 1,
    nextPage: 1,
    prePage: 1,
    lastPage: 1,
    param: "",
    pageType: function (type) {
        loadingModdle.showModdle();
        var pages = 0;
        var $this = this;
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
            url: HEADER + "stock/getStockGoodsList.do?page=" + $this.page + "&pageSize=5&findkey=" + $this.param,
            success: function (data) {
                if (!!data) {
                    loadingModdle.closeModdle();
                    var jsonArr = data.result;
                    var len = jsonArr.length;
                    var itemType = "";
                    var html = "";
                    for (var i = 0; i < jsonArr.length; i++) {
                        switch (jsonArr[i].gtid) {
                            case "01":
                                itemType = "主料";
                                break;
                            case "02":
                                itemType = "辅料";
                                break;
                            case "03":
                                itemType = "成品";
                                break;
                        }
                        //物品编号|	物品名称|	物品类型|	规格型号|	供应商|	单位|	预警数量
                        html += "<tr data-id='" + jsonArr[i].id + "'>" +
                            "	<td>" + (jsonArr[i].gid == null ? "" : jsonArr[i].gid) + "</td>" +
                            "	<td>" + (jsonArr[i].gname == null ? "" : jsonArr[i].gname) + "</td>" +
                            "	<td>" + itemType + "</td>" +
                            "	<td>" + (jsonArr[i].gspec == null ? "" : jsonArr[i].gspec) + "</td>" +
                            "	<td>" + (jsonArr[i].provider == null ? "" : jsonArr[i].provider) + "</td>" +
                            "	<td>" + (jsonArr[i].unit == null ? "" : jsonArr[i].unit) + "</td>" +
                            "	<td><input type='text' style='width: 50px;' value='" + (jsonArr[i].swnum == null ? "0" : jsonArr[i].swnum) + "' placeholder='请输入' onchange='cr_normal.checkInteger(this);'/></td>" +
                            "</tr>";
                    }
                    $("#warningSettingMsg").html(html);
                    $this.changePage(data.pageNo, data.prePage, data.nextPage, data.totalPageBeans);
                    checkAllBtns.check("warningSettingBtns", warningSetting, "warningSetting");
                    $("#warningSettingNumber").html(($this.page == 0 ? 1 : $this.page) + "/" + ($this.lastPage == 0 ? 1 : $this.lastPage));
                    $("#warningSettingTotal").html(data.totalCount);
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

//获取修理和报故障记录
var repairsAndFailures = {
    page: 1,
    nextPage: 1,
    prePage: 1,
    lastPage: 1,
    param: "",
    pageType: function (type) {
        loadingModdle.showModdle();
        var pages = 0;
        var pagesize=5;
        var $this = this;
        var begintime = $("#repairsAndFailuresBeginTime").val() + " 00:00:00";
        var endtime = $("#repairsAndFailuresEndTime").val() + " 23:59:59";
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
            url: HEADER + "stock/getFlautInfo.do?page=" + pages + "&pageSize="+pagesize+"&findkey=" + $this.param + "&begintime=" + begintime + "&endtime=" + endtime,
            success: function (data) {
                if (!!data) {
                    loadingModdle.closeModdle();
                    var jsonArr = data.result;
                    var len = jsonArr.length;
                    var gType = "";
                    var fType = "";
                    var html = "";
                    for (var i = 0; i < jsonArr.length; i++) {
                        switch (jsonArr[i].gtid) {
                            case "01":
                                gType = "主料";
                                break;
                            case "02":
                                gType = "辅料";
                                break;
                            default:
                                gType = "";
                                break;
                        }
                        switch (jsonArr[i].ftype) {
                            case "01":
                                fType = "报故障";
                                break;
                            case "02":
                                fType = "修理";
                                break;
                            default:
                                fType = "";
                                break;
                        }
                        //序号|	物品名称|	物品类型|	规格型号|	单位|	供应商|	操作类型|	数量|	操作时间|	经办人
                        html += "<tr>" +
                            "	<td>" + cr_item.getXuhao(i, pages,pagesize) + "</td>" +
                            //								"	<td>"+(jsonArr[i].fid==null?"":jsonArr[i].fid)+"</td>"+
                            "	<td>" + (jsonArr[i].gname == null ? "" : jsonArr[i].gname) + "</td>" +
                            "	<td>" + gType + "</td>" +
                            "	<td>" + (jsonArr[i].gspec == null ? "" : jsonArr[i].gspec) + "</td>" +
                            "	<td>" + (jsonArr[i].unit == null ? "" : jsonArr[i].unit) + "</td>" +
                            "	<td>" + (jsonArr[i].provider == null ? "" : jsonArr[i].provider) + "</td>" +
                            "	<td>" + fType + "</td>" +
                            "	<td>" + (jsonArr[i].fnum == null ? "" : jsonArr[i].fnum) + "</td>" +
                            "	<td>" + (jsonArr[i].fdate == null ? "" : new Date(jsonArr[i].fdate).format("yyyy-MM-dd hh:mm:ss")) + "</td>" +
                            "	<td>" + (jsonArr[i].fperson == null ? "" : jsonArr[i].fperson) + "</td>" +
                            "</tr>";
                    }
                    $("#repairsAndFailuresMsg").html(html);
                    $this.changePage(data.pageNo, data.prePage, data.nextPage, data.totalPageBeans);
                    checkAllBtns.check("repairsAndFailuresBtns1", repairsAndFailures, "repairsAndFailures");
                    $("#repairsAndFailuresNumber").html(($this.page == 0 ? 1 : $this.page) + "/" + ($this.lastPage == 0 ? 1 : $this.lastPage));
                    $("#repairsAndFailuresTotal").html(data.totalCount);
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

function rechange(){
  var type1=$("#reStockType").val();
    var trDom = $("#storageInformationMsg").find("input[name=order]:checked").parents("tr");
    var gtype=trDom.children().eq(2).html();//物品类型
  if((type1=="01" || type1=="02") && gtype!="成品"){
      $("#outaudit").addClass("hidden");
  }else if(type1=="03"  && gtype=="成品"){
      $("#outaudit").removeClass("hidden");
  }else{
      cr_dialogBox.alert(true,"不符条件，不能选择");
  }
return type1;
}


//打开退货窗口
function openGoodsOutModdle(dom, type) {
    //当前选中的订单列
    var trDom = $("#storageInformationMsg").find("input[name=order]:checked").parents("tr");
    if (!!trDom.children().eq(0).html()) {
        var name = trDom.children().eq(1).html(); //物品名称
        var sopertype = trDom.children().eq(2).html(); //物品类型
        var itemSize = trDom.children().eq(3).html(); //规格型号
        var unit = trDom.children().eq(4).html(); //单位
        var provider = trDom.children().eq(5).html(); //供应商
        var stockedNum = trDom.children().eq(8).html(); //库存数量
        var normalNum = trDom.children().eq(9).html(); //正常数量
        var errorNum = trDom.children().eq(10).html();//故障数量
        var aNum = trDom.children().eq(11).html();//审批数量
        var endproductProvider = ""; //成品供应商
        var endproductSize = ""; //成品规格型号
        var gid = trDom.attr("data-gid");
        var id = trDom.attr("data-id");
        var gtype=trDom.children().eq(2).html();//物品类型
        $("#outaudit").addClass("hidden");
        if("成品"==gtype){
            $("#reStockType").find("option[value='03']").attr("selected","selected");
            $("#outaudit").removeClass("hidden");

        }else{
            $("#outaudit").addClass("hidden");
        }
            $("#reGoodsName").html(name).attr({"data-gid": gid, "data-id": id});
            $("#reGoodsSize").html(itemSize); //规格型号
            $("#reGoodsProvider").html(provider); //供应商
            $("#reGoodsType").html(sopertype); //物品类型
            $("#reGoodsUnit").html(unit); //单位
            $("#errorGoodsStacked").html(stockedNum); //库存数量
            $("#reGoodsNormalNumber").html(normalNum); //正常数量
            $("#reuseNumber").val("").attr("data-anum",aNum); //退货数量（将审核数量传过去）
            getLoginPerson('use');//获取到当前登录人及其部门
            cr_item.getAllChecker($("#reStockChecker")); //给下拉赋值审核人
            cr_item.getAllManager($("#reStockManager")); //给下拉赋值仓库管理员
            cr_item.getAllUser($("#reStockCSPerson"));//给下拉框赋值抄送人

        $("#returnGoodsOutStock").modal("show");

    } else {
        cr_dialogBox.alert(true, "请选择采购列表列!");
    }
}

//提交退货窗口信息
function submitRestock() {
    var trDom = $("#storageInformationMsg").find("input[name=order]:checked").parents("tr");
    var id = trDom.attr("data-id");
    var type1=rechange();
    var strtype="";
    var sopertype="";
    var url="";
    if(type1=="01"){
        strtype="stype";
        sopertype="01";
        type1="04";
        url=HEADER+"stock/addStock.do?"
    }else if(type1=="02"){
        strtype="stype";
        sopertype="01";
        type1="05";
        url=HEADER+"stock/addStock.do?"
    }else if(type1=="03"){
        strtype="stype1";
        sopertype="02";
        type1="05";
        url=HEADER+"stock/outStockBefore.do?"
    }
    var snum=$("#reuseNumber").val();//退货数量
    var errorNum = trDom.children().eq(10).html();//故障数量
    var gid = trDom.attr("data-gid"); //物品编号
    var ckmanager=$("#reStockManager").find("option:selected").attr("data-name");//仓管员
    var checker=$("#reStockChecker").find("option:selected").attr("data-name");//审核人
    var csperson=$("#reStockCSPerson").find("option:selected").attr("data-name");
    var state=$("#reGoodsState").val();
    if(state!="00"){
      if(snum>errorNum){
          cr_dialogBox.alert(true,"退货故障物品数量有误！");
          return;
      }
    }
    $.ajax({
        url:url+strtype+"="+type1+"&sopertype="+sopertype,
        type:"post",
        async:false,
        data:{
            "snum":snum,
            "gid":id,
            "auditperson":ckmanager,
            "auditperson1":checker,
            "csuser":csperson,
            "sthstate": state
        },
        success:function (data) {
            if(data.msg=="1" || data.msg==1){
                cr_dialogBox.alert(true,"操作成功！");
                $("#returnGoodsOutStock").modal("hide");
                storageInformation.pageType(storageInformation.page);
            }else{
                cr_dialogBox.alert(true,"操作失败！");
            }
        }

    });
}