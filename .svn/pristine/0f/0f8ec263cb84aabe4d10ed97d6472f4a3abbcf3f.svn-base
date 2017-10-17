$(function() {
    cr_item.initTime($("#inventoryStartTime"), "start");
    cr_item.initTime($("#inventoryEndTime"));
    inventory.pageType("preAllPage"); //获取仓储库存列表
    $("#saveStockDownloadModel").prop("href", HEADER + "check/downLoadCheckModel.do?downExcel=1"); //下载库存记录模板
    showTime(); //日期选择
});

//日期选择
function showTime() {
    $("#inventoryStartTime").datetimepicker({
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
    $("#inventoryEndTime").datetimepicker({
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
};

//检查开始时间
function checkStartTime(dom) {
    var startTime = new Date(dom.value).getTime();
    var nowTime = new Date();
    if((nowTime.getTime() - startTime) < 0) {
        cr_dialogBox.alert(true, "开始时间不能大于当前时间！");
        $(dom).val(meterDate.initTime(nowTime));
    }
};


//检查结束时间
function checkEndTime(dom) {
    var endTime = new Date($(dom).val()).getTime();
    var startTime = new Date($(dom).parent().parent().prev().children().eq(1).find("input").val()).getTime();
    var date = new Date();
    var year = date.getFullYear();
    var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1);
    var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
    var hour = date.getHours() > 9 ? date.getHours() : "0" + date.getHours();
    var minute = date.getMinutes() > 9 ? date.getMinutes() : "0" + date.getMinutes();
    if((endTime - startTime) < 0) {
        cr_dialogBox.alert(true, "结束时间不能少于起始时间!");
        //		showThisMonth();
        $(dom).val(year + "-" + month + "-" + day + " " + hour + ":" + minute);
    };
    if((endTime - date.getTime()) > 0) {
        cr_dialogBox.alert(true, "结束时间不能超过当前时间!");
        $(dom).val(year + "-" + month + "-" + day + " " + hour + ":" + minute);
    };
};

//搜索
function search() {
    var value = $("#inventoryPerson").val();
    inventory.searchMsg(value);
}

//打开详情页面
function openDetails(dom) {
    $("#inventoryDetails").modal("show");
    var ckdate = $(dom).attr("data-time"); //获取盘点时间
    $("#inventoryDetails").attr("data-time", ckdate);
    $("#PDtime").html(ckdate); //显示盘点时间
    var ckperson = $(dom).attr("data-person"); //获取盘点人
    $("#PDperson").html(ckperson); //显示盘点人
    var batchno = $(dom).attr("data-batchno");

    $("#inventoryDetails").attr("data-batchno", batchno);
    inventoryDetails.pageType("preAllPage"); //获取盘点详情列表
    $("#ExportinventoryDetail").prop("href", HEADER + "check/exportCkStock.do?batchno=" + batchno); //导出盘点详情
}

//打开导入原因窗口
function openReasonModdle(dom) {
    $("#reasonEntry").modal("show");
    $("#reasonEntry").attr("data-trId", $(dom).attr("data-trId"));
    $("#reasonText").val("");
}

//提交原因
function submitReasonMsg() {
    var id = $("#reasonEntry").attr("data-trId");
    var value = $("#reasonText").val();
    if(value != "") {
        $("#" + id).attr("data-text", value);
        $("#reasonEntry").modal("hide");
        cr_dialogBox.alert(true, "录入原因成功，请点击录入盘点结果!");
    } else {
        cr_dialogBox.alert(true, "请输入原因!");
    }
}

//录入盘点结果到数据库上
function enterResults() {
    var trDoms = $("#Msg").children();
    var jsonData = [];
    var url = HEADER + "check/putinCkStockData.do";
    cr_dialogBox.confirm(true, "确定要录入盘点结果吗？", function(flag) {
        if(flag) {
            if(trDoms.length > 0) {
                for(var i = 0; i < trDoms.length; i++) {
                    jsonData.push({
                        "物品ID": !!$(trDoms[i]).attr("data-id") ? $(trDoms[i]).attr("data-id") : "",
                        "物品名称": !!$(trDoms[i]).children().eq(1).html() ? $(trDoms[i]).children().eq(1).html() : "0",
                        "物品类型": !!$(trDoms[i]).children().eq(2).html() ? $(trDoms[i]).children().eq(2).html() : "0",
                        "规格型号": !!$(trDoms[i]).children().eq(3).html() ? $(trDoms[i]).children().eq(3).html() : "0",
                        "供应商": !!$(trDoms[i]).children().eq(4).html() ? $(trDoms[i]).children().eq(4).html() : "0",
                        "单位": !!$(trDoms[i]).children().eq(5).html() ? $(trDoms[i]).children().eq(5).html() : "0",
                        "盘点正常数量": !!$(trDoms[i]).children().eq(6).html() ? $(trDoms[i]).children().eq(6).html() : "0",
                        "正常数量": !!$(trDoms[i]).children().eq(7).html() ? $(trDoms[i]).children().eq(7).html() : "0",
                        "盘点故障数量": !!$(trDoms[i]).children().eq(8).html() ? $(trDoms[i]).children().eq(8).html() : "0",
                        "故障数量": !!$(trDoms[i]).children().eq(9).html() ? $(trDoms[i]).children().eq(9).html() : "0",
                        "盘点库存数量": !!$(trDoms[i]).children().eq(10).html() ? $(trDoms[i]).children().eq(10).html() : "0",
                        "库存数量": !!$(trDoms[i]).children().eq(11).html() ? $(trDoms[i]).children().eq(11).html() : "0",
                        "盈亏": !!$(trDoms[i]).children().eq(12).html() ? $(trDoms[i]).children().eq(12).html() : "0",
                        "原因": !!$(trDoms[i]).attr("data-text") ? $(trDoms[i]).attr("data-text") : ""
                    });
                }
                $.ajax({
                    type: "post",
                    url: url,
                    data: {
                        "jsonData": JSON.stringify(jsonData)
                    },
                    success: function(data) {
                        cr_item.showTip(data.msg, function() {
                            cr_dialogBox.alert(true, "录入盘点结果成功!");
                            stockTakingInfo.pageType(stockTakingInfo.page);
                            $("#reasonEntry").modal("hide");
                        });
                    }
                });
            }
        }
    });
}

//导入库存盘点信息
function doUpload(dom) {
    var formData = new FormData($("#uploadForm")[0]);
    var reg = new RegExp("xl");
    var fileUrl = cr_item.getObjectURL(dom.files[0]);
    var fileName = $(dom).val().split(".")[1];
    var url = HEADER + "check/putinStock.do";
    if(fileUrl != "") {
        if(reg.exec(fileName)) {
            $.ajax({
                url: url,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function(data) {
                    var html = "";
                    var iType = "";
                    if(data.length > 0) {
                        cr_dialogBox.alert(true, "上传成功！");
                        for(var i = 0; i < data.length; i++) {
                            html += "<tr id='reasonList" + i + "' data-id='" + data[i].物品ID + "'>" +
                                "	<td>" + i + "</td>" +
                                "	<td>" + (data[i].物品名称 == "" ? "" : data[i].物品名称) + "</td>" +
                                "	<td>" + (data[i].物品类型 == "" ? "" : data[i].物品类型) + "</td>" +
                                "	<td>" + (data[i].规格型号 == "" ? "" : data[i].规格型号) + "</td>" +
                                "	<td>" + (data[i].供应商 == "" ? "" : data[i].供应商) + "</td>" +
                                "	<td>" + (data[i].单位 == "" ? "" : data[i].单位) + "</td>" +
                                "	<td>" + (data[i].盘点正常数量 == "" ? "0" : data[i].盘点正常数量) + "</td>" +
                                "	<td>" + (data[i].正常数量 == "" ? "0" : data[i].正常数量) + "</td>" +
                                "	<td>" + (data[i].盘点故障数量 == "" ? "0" : data[i].盘点故障数量) + "</td>" +
                                "	<td>" + (data[i].故障数量 == "" ? "0" : data[i].故障数量) + "</td>" +
                                "	<td>" + (data[i].盘点库存数量 == "" ? "0" : data[i].盘点库存数量) + "</td>" +
                                "	<td>" + (data[i].库存数量 == "" ? "0" : data[i].库存数量) + "</td>" +
                                "	<td>" + (data[i].盘点库存数量 - data[i].库存数量) + "</td>" +
                                "	<td><span data-trId='reasonList" + i + "' onclick='openReasonModdle(this);'>录入</span></td>" +
                                "</tr>";
                        }
                        $("#Msg").html(html);
                        $("#importResults").modal("show");
                        //重新刷新库存盘点数据
                        inventory.pageType("preAllpage");
                    }
                },
                error: function(data) {
                    cr_dialogBox.alert(true, "上传失败!");
                }
            });
        } else {
            cr_dialogBox.alert(true, "文件格式不正确！");
        }
    }
}

//获取盘点记录列表
var inventory = {
    page: 1,
    nextPage: 1,
    prePage: 1,
    lastPage: 1,
    param: "",
    pageType: function(type) {
        loadingModdle.showModdle();
        var pages = 0;
        var pagesize=10;
        var $this = this;
        var startTime = $("#inventoryStartTime").val() + " 00:00:00";
        var endTime = $("#inventoryEndTime").val() + " 23:59:59";
        if(type == "preAllPage") {
            pages = 1;
        } else if(type == "prePage") {
            pages = this.prePage;
        } else if(type == "nextPage") {
            pages = this.nextPage;
        } else if(type == "nextAllPage") {
            pages = this.lastPage;
        } else if(typeof(type) == "number") {
            pages = type;
        };
        $.ajax({
            type: "get",
            url: HEADER + "check/getCkResultList.do?page=" + pages + "&pageSize="+pagesize+"&begintime=" + startTime + "&endtime=" + endTime + "&checkperson=" + $this.param,
            success: function(data) {
                if(!!data) {
                    loadingModdle.closeModdle();
                    var jsonArr = data.result;
                    var len = jsonArr.length;
                    var itemType = "";
                    var html = "";
                    for(var i = 0; i < jsonArr.length; i++) {
                       // alert("批次号："+jsonArr[i].batchno);
                        switch(jsonArr[i].gtid) {
                            case "01":
                                itemType = "主料";
                                break;
                            case "02":
                                itemType = "辅料";
                                break;
                        }
                        html += "<tr>" +
                            "	<td>" + cr_item.getXuhao(i,pages,pagesize) + "</td>" +
                            "	<td>" + (jsonArr[i].ckdate == null ? "" : new Date(jsonArr[i].ckdate).format("yyyy-MM-dd hh:mm:ss")) + "</td>" +
                            "	<td>" + (jsonArr[i].ckperson == null ? "" : jsonArr[i].ckperson) + "</td>" +
                            "	<td>" + (jsonArr[i].yk == null ? "" : jsonArr[i].yk) + "</td>" +
                            "	<td><span data-batchno='" + (jsonArr[i].batchno == null ? "" : jsonArr[i].batchno) + "' data-person='" + jsonArr[i].ckperson + "' data-time='" + (jsonArr[i].ckdate == null ? "" : jsonArr[i].ckdate)+"' onclick='openDetails(this);'>查看详情</span></td>" +
                            "</tr>";
                    }
                    $("#inventoryMsg").html(html);
                    $this.changePage(data.pageNo, data.prepage, data.nextPage, data.totalPageBeans);
                    checkAllBtns.check("inventoryBtns", inventory, "inventory");
                    $("#inventoryNumber").html(($this.page == 0 ? 1 : $this.page) + "/" + ($this.lastPage == 0 ? 1 : $this.lastPage));
                    $("#inventoryTotal").html(data.totalCount);
                } else {
                    loadingModdle.closeModdle();
                }
            },
            error: function() {
                loadingModdle.closeModdle();
            }
        });
    },
    //改变变量的值
    changePage: function(page, prePage, nextPage, lastPage) {
        this.page = page;
        this.nextPage = nextPage;
        this.prePage = prePage;
        this.lastPage = lastPage;
    },
    //搜索功能
    searchMsg: function(param) {
        var $this = this;
        $this.param = param;
        $this.pageType($this.page);
    }
};

//查看盘点详情
var inventoryDetails = {
    page: 1,
    nextPage: 1,
    prePage: 1,
    lastPage: 1,
    param:"",
    pageType: function(type) {
        loadingModdle.showModdle();
        var pages = 0;
        var $this = this;
        var ckdate = $("#inventoryDetails").attr("data-time");
        var batchno = $("#inventoryDetails").attr("data-batchno");
        // alert(batchno);
        if(type == "preAllPage") {
            pages = 1;
        } else if(type == "prePage") {
            pages = this.prePage;
        } else if(type == "nextPage") {
            pages = this.nextPage;
        } else if(type == "nextAllPage") {
            pages = this.lastPage;
        } else if(typeof(type) == "number") {
            pages = type;
        };
        var startTime = $("#inventoryStartTime").val(); //开始时间
        var endTime = $("#inventoryEndTime").val(); //结束时间
        $.ajax({
            type: "get",
            url: HEADER + "check/getCkDetail.do?page=" + pages + "&pageSize=5&batchno=" + batchno,
            success: function(data) {
                if(!!data) {
                    loadingModdle.closeModdle();
                    var jsonArr = data.result;
                    var len = jsonArr.length;
                    var itemType = "";
                    var html = "";
                    for(var i = 0; i < jsonArr.length; i++) {
                        switch(jsonArr[i].gtid) {
                            case "01":
                                itemType = "主料";
                                break;
                            case "02":
                                itemType = "辅料";
                                break;
                        }
                        html += "<tr>" +
                            "<td>" + cr_item.getXuhao(i, pages) + "</td>" +
                            "<td>" + (jsonArr[i].gname == null ? "" : jsonArr[i].gname) + "</td>" +
                            "<td>" + itemType + "</td>" +
                            "<td>" + (jsonArr[i].gspec == null ? "" : jsonArr[i].gspec) + "</td>" +
                            "<td>" + (jsonArr[i].unit == null ? "" : jsonArr[i].unit) + "</td>" +
                            "<td>" + (jsonArr[i].proname == null ? "" : jsonArr[i].proname) + "</td>" +
                            "<td>" + (!jsonArr[i].cknum ? "0" : jsonArr[i].cknum) + "</td>" +
                            "<td>" + "盘点数量" + "</td>" +
                            "<td>" + (!jsonArr[i].yk ? "0" : jsonArr[i].yk) + "</td>" +
                            "<td>" + (jsonArr[i].ckreason == null ? "" : jsonArr[i].ckreason) + "</td>" +
                            "</tr>";
                    }
                    $("#inventoryDetailsMsg").html(html);
                    $this.changePage(data.pageNo, data.prePage, data.nextPage, data.totalPageBeans);
                    checkAllBtns.check("inventoryDetailsBtns", inventoryDetails, "inventoryDetails");
                    $("#inventoryDetailsNumber").html(($this.page == 0 ? 1 : $this.page) + "/" + ($this.lastPage == 0 ? 1 : $this.lastPage));
                    $("#inventoryDetailsTotal").html(data.totalCount);
                } else {
                    loadingModdle.closeModdle();
                }
            },
            error: function() {
                loadingModdle.closeModdle();
            }
        });
    },
    //改变变量的值
    changePage: function(page, prePage, nextPage, lastPage) {
        this.page = page;
        this.nextPage = nextPage;
        this.prePage = prePage;
        this.lastPage = lastPage;
    },
    //搜索功能
    searchMsg: function(param) {
        var $this = this;
        $this.param = param;
        $this.pageType($this.page);
    }
}