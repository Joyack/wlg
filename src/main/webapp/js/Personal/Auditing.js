$(function () {
    var visitedArr = $('.status>li a');
    for (var i = 0; i < visitedArr.length; i++) {
        $(visitedArr[i]).click(function () {
            $(this).addClass("visited").parent('li').siblings().children('a').removeClass("visited")
        })
    }

    showTime();
    cr_item.initTime($("#beginTime"), "start"); //修理和报故障时间选择器的时间初始化为本位第一天到今天
    cr_item.initTime($("#endTime"));
    // 弹窗
    $(".audit-plus").click(function () {
        $(".audit-popup").addClass("popup").siblings().css("opacity", "0.5");
    });
    $(".btn").click(function () {
        $(".audit-popup").removeClass("popup").siblings().css("opacity", 1);
    });

    //审核表格接口对接
    itemManagement.searchMsg('&atype=1');


});
// function getTableData() {
//     $.get("http://172.16.31.204:8081/warehousing/audit/getAuditListByPage.do",
//         {page: 1, pageSize: 5},
//         function (data) {
//             let html = template('auditTem', data);
//             $("#auditing-tbody").html(html);
//             //点击详情弹出窗口
//             let detailBtn = $(".detail-btn");
//             for (let i = 0; i < detailBtn.length; i++) {
//                 $(detailBtn[i]).click(function () {
//                     $('.audit-detail').addClass('popup').siblings().css('opacity', '0.5');
//
//                 });
//             }
//         });
// }

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
//选择上面的tab页按钮
function load1(type){
    if(type=="1"){
        itemManagement.searchMsg('&atype=1');
    }else if(type=="2"){
        itemManagement.searchMsg('&atype=2');
    }else if(type=="3"){
        itemManagement.searchMsg('&atype=3');
    }
}

var itemManagement = {
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
            url: HEADER + "audit/getAuditListByPage.do?page=" + pages + "&pageSize=5&findkey=" + this.param,
            success: function (data) {
                if (!!data) {
                    loadingModdle.closeModdle();
                    var jsonArr = data.result;
                    var len = jsonArr.length;
                    var itemType = "";
                    for (var i = 0; i < len; i++) {
                        switch (jsonArr[i].auditstatus) {
                            case "00":
                                if(jsonArr[i].title.indexOf("入库")>0){
                                    data.result[i].auditstatus = "入库完成";
                                }else {
                                    data.result[i].auditstatus = "出库完成";
                                }
                                break;
                            case "01":
                                data.result[i].auditstatus = "审核中";
                                break;
                            case "02":
                                data.result[i].auditstatus = "审核通过（仓库）";

                                break;
                            case "03":
                                data.result[i].auditstatus = "审核未过（仓库）";
                                break;
                            case "04":
                                data.result[i].auditstatus = "撤销";

                                break;
                            case "05":
                                data.result[i].auditstatus = "审核通过（领导）";

                                break;
                            case "06":
                                data.result[i].auditstatus = "审核未过（领导）";
                                break;
                        }

                    }

                    var html = template('auditTem', data);
                    $("#auditing-tbody").html(html);
                    //点击详情弹出窗口
                    var detailBtn = $(".detail-btn #detail");
                    for (var i = 0; i < detailBtn.length; i++) {
                        $(detailBtn[i]).click(function () {
                            $('.audit-detail').addClass('popup').siblings().css('opacity', '0.5');
                            $("#dddd").css('opacity', '1');
                            $('.audit-detail').css("display", "#337ab7");

                        });
                    }
                    $("#itemManagementMsg").html(html); //将获取到的数据显示在表格的tbody中
                    $this.changePage(data.pageNo, data.prePage, data.nextPage, data.totalPageBeans); //改变变量的值
                    checkAllBtns.check("itemManagementBtns", itemManagement, "itemManagement"); //检查分页按钮是否可用，不可用就显示为禁用标志
                    $("#itemManagementNumber").html(($this.page == 0 ? 1 : $this.page) + "/" + ($this.lastPage == 0 ? 1 : $this.lastPage));
                    $("#itemManagementTotal").html(data.totalCount);
                    // } else {
                    //  loadingModdle.closeModdle();
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

//获取详情信息
function getDetail(reviewType, workType, dom) {
    $('.audit-detail').css("display", "block");
    var url = HEADER + "audit/getAuditDetail.do";
    var aid = $(dom).parents("tr").children().eq(0).html();
    $.ajax({
        type: "get",
        url: url,
        data: {
            "auditid": aid,
            "page": 1,
            "pageSize": 10
        },
        success: function (data) {
            switch (reviewType) {//审核状态
                case "出库完成":
                    reviewType = '00';
                    break;
                case "入库完成":
                    reviewType = '00';
                    break;
                case "审核中":
                    reviewType = '01';
                    break;
                case "审核通过（仓库）":
                    reviewType = '02';
                    break;
                case "审核未通过（仓库）":
                    reviewType = '03';
                    break;
                case "撤销":
                    reviewType = '04';
                    break;
                case "审核通过（领导）":
                    reviewType = '05';
                    break;
                case "审核未通过（领导）":
                    reviewType = '06';
                    break;
                default:
                    $("#reviewerType").html("");
                    break;

            }
            //显示详情表格信息
            showDetailTable(data.result[0], reviewType, workType);

        }
        // }
    });
}

//显示详情获取到的表格信息
function showDetailTable(jsonArr, reviewType, workType) {
    var options = "";
    var title = jsonArr.title;
    var content = jsonArr.content;
    var sendname = jsonArr.subpername;//申请人
    var senduser = jsonArr.subperson;
    //var createtime = new Date(jsonArr.createtime.time).format("yyyy-MM-dd hh:mm");//申请时间
    var createtime = jsonArr.createtime;//申请时间
    var auditpername = jsonArr.auditpername;//仓库审核人
    var auditperson = jsonArr.auditperson;
    var auditpername1 = jsonArr.auditpername1;//领导审核人
    var auditperson1 = jsonArr.auditperson1;
    var ptype = jsonArr.audittype;//帖子类型
    var aid = jsonArr.id;
    var sid = jsonArr.sid;


    $("#detail-table").attr("data-auditid", aid);
    $("#detail-table").attr("data-subperson",senduser);
    $("#detail-table").attr("data-sid", sid);
    $("#detail-table").html($("#detailTem").html());
    $("#detailTitle").html(title);
    $("#applicantOption").val(options);
    $("#applicant").html(sendname);
    $("#applicantTime").html(createtime);
    $("#reviewer").html(auditpername);
    $("#reviewer1").html(auditpername1);
    $("#content").html(content);
    $("#reviewerType").attr("data-auditstatus", reviewType);
    switch (reviewType) {//审核状态
        case "1":
            $("#reviewerType").html("审核通过");
            break;
        case "00":
            if(jsonArr.title.indexOf("入库")>0){
                $("#reviewerType").html("入库完成");
            }else {
                $("#reviewerType").html("出库完成");
            }

            break;
        case "01":
            $("#reviewerType").html("审核中");
            break;
        case "02":
            $("#reviewerType").html("审核通过（仓库）");
            break;
        case "03":
            $("#reviewerType").html("审核未通过（仓库）");
            break;
        case "04":
            $("#reviewerType").html("撤销");
            break;
        case "05":
            $("#reviewerType").html("审核通过（领导）");
            break;
        case "06":
            $("#reviewerType").html("审核未通过（领导）");
            break;
        default:
            $("#reviewerType").html("");
            break;

    }

    $.ajax({
        url: HEADER + "user/check_getPrincipalUser.do",
        type: "get",
        success: function (d) {
            var jsondata = d.result;
            if (reviewType == "01") {//审核中
                if(ptype=="21" || ptype=="22"|| ptype=="23"|| ptype=="24"|| ptype=="25"){
                    if (auditperson == jsondata.username) {//登录人=仓库审核人

                        $("#communityContent").css("display", "block");
//							$("#container").css("left","10%");
//							$("#content1").css("margin-left","11.5%");
                        $("#reviewerBtn").addClass("hidden");
                        $("#submitUserBtn").addClass("hidden");
                        $("#submitUserBtn1").addClass("hidden");
                        //$("#applicantOption").attr("disabled", "disabled");
                        $("#audit").addClass("hidden");

                        $("#sureInStock").removeClass("hidden");
                        $("#errInStock").removeClass("hidden");

                    } else {
                        $("#reviewerBtn").addClass("hidden");
                        $("#submitUserBtn").addClass("hidden");
                        $("#submitUserBtn1").addClass("hidden");
                        $("#applicantOption").attr("disabled", "disabled");
                        $("#communityContent").css("display", "none");
                        $("#sureInStock").addClass("hidden");
                        $("#errInStock").addClass("hidden");
                    }
                }else {
                    if (auditperson == jsondata.username) {//登录人=仓库审核人

                        $("#communityContent").css("display", "block");
//							$("#container").css("left","10%");
//							$("#content1").css("margin-left","11.5%");
                        $("#reviewerBtn").removeClass("hidden");
                        $("#submitUserBtn").addClass("hidden");
                        $("#submitUserBtn1").addClass("hidden");
                        $("#applicantOption").removeAttr("disabled", "disabled");
                        $("#audit").removeClass("hidden");
                        $("#sureInStock").addClass("hidden");
                        $("#errInStock").addClass("hidden");
                    } else {
                        $("#reviewerBtn").addClass("hidden");
                        $("#submitUserBtn").addClass("hidden");
                        $("#submitUserBtn1").addClass("hidden");
                        $("#applicantOption").attr("disabled", "disabled");
                        $("#communityContent").css("display", "none");
                        $("#sureInStock").addClass("hidden");
                        $("#errInStock").addClass("hidden");
                    }
                }
            } else if (reviewType == '02') {//仓库审核通过  领导审核
                if (auditperson1 == jsondata.username) {//登录人=领导审核人
                    $("#submitUserBtn").addClass("hidden");
                    $("#submitUserBtn1").addClass("hidden");
                    // communitySendMsg.pageType("preAllPage");
                    $("#communityContent").css("display", "block");
                    $("#reviewerBtn").removeClass("hidden");
                    $("#applicantOption").removeAttr("disabled", "disabled");
                    $("#audit").removeClass("hidden");
                    $("#sureInStock").addClass("hidden");
                    $("#errInStock").addClass("hidden");
                } else {
                    $("#reviewerBtn").addClass("hidden");
                    $("#submitUserBtn").addClass("hidden");
                    $("#submitUserBtn1").addClass("hidden");
                    $("#applicantOption").attr("disabled", "disabled");
                    $("#communityContent").css("display", "none");
                    $("#sureInStock").addClass("hidden");
                    $("#errInStock").addClass("hidden");
                }
            } else if (reviewType == '05') {//领导审核通过
                if(ptype=='02'){//判断是显示发货还是退货
                    if (senduser == jsondata.username) {//申请人发货
                        $("#reviewerBtn").addClass("hidden");
                        $("#applicantOption").attr("disabled", "disabled");
                        $("#communityContent").css("display", "none");
                        $("#submitUserBtn1").removeClass("hidden");
                        $("#submitUserBtn").removeClass("hidden");
                        $("#returnGoods").removeClass("hidden");
                        $("#sureInStock").addClass("hidden");
                        $("#errInStock").addClass("hidden");
                    } else {
                        $("#reviewerBtn").addClass("hidden");
                        $("#applicantOption").attr("disabled", "disabled");
                        $("#communityContent").css("display", "none");
                        $("#submitUserBtn1").addClass("hidden");
                        $("#returnGoods").addClass("hidden");
                        $("#sureInStock").addClass("hidden");
                        $("#errInStock").addClass("hidden");
                    }
                }else {
                    if (senduser == jsondata.username) {//申请人发货
                        $("#reviewerBtn").addClass("hidden");
                        $("#applicantOption").attr("disabled", "disabled");
                        $("#communityContent").css("display", "none");
                        $("#submitUserBtn1").removeClass("hidden");
                        $("#submitUserBtn").removeClass("hidden");
                        $("#send").removeClass("hidden");
                        $("#sureInStock").addClass("hidden");
                        $("#errInStock").addClass("hidden");
                    } else {
                        $("#reviewerBtn").addClass("hidden");
                        $("#applicantOption").attr("disabled", "disabled");
                        $("#communityContent").css("display", "none");
                        $("#submitUserBtn1").addClass("hidden");
                        $("#submitUserBtn").addClass("hidden");
                        $("#sureInStock").addClass("hidden");
                        $("#errInStock").addClass("hidden");
                    }
                }
            } else {//审核不通过
                $("#reviewerBtn").addClass("hidden");
                $("#submitUserBtn").addClass("hidden");
                $("#submitUserBtn1").addClass("hidden");
                $("#applicantOption").attr("disabled", "disabled");
                $("#sureInStock").addClass("hidden");
                $("#errInStock").addClass("hidden");
            }
        }

    });
}

//判断审核通不通过
function submitReviewerMsg(dom, type) {
    var loginuser=null;
    $.ajax({
        url: HEADER + "user/check_getPrincipalUser.do",
        type: "get",
        async:false,
        success: function (d) {
            loginuser = d.result;
        }
    });
    var url = "";
    var content = $("#applicantOption").val();
    var auditid = $("#detail-table").attr("data-auditid");
    var subperson = $(dom).parents("tr").children().eq(2).html();
    var auditstatus = $("#reviewerType").attr("data-auditstatus");
    url = HEADER + "audit/updateAudit.do";
    if (type == "success") {

        switch (auditstatus) {
            case '01':
                auditstatus = '02';
                break;
            case '02':
                auditstatus = '05';
                break;
        }
    } else if (type == "fail") {
        switch (auditstatus) {
            case '01':
                auditstatus = '03';
                break;
            case '03':
                auditstatus = '06';
                break;
        }
    } else if (type == 'cancel') {
        if(loginuser.username==subperson){
            auditstatus=$(dom).parents("tr").children("td").eq(6).html();
            if (auditstatus != '审核中') {
                cr_dialogBox.alert(true, "当前状态，不能撤销！");
                return;
            } else if (auditstatus == '撤销') {
                cr_dialogBox.alert(true, "已撤销！");
                return;
            }
        }else{
            cr_dialogBox.alert(true,"没有权限，不能撤销！");
            return;
        }

        auditid = $(dom).parents("tr").children().eq(0).html();
        auditstatus = '04';
    }else if(type=="sureInStock"){//确认收货
        auditstatus="00";
        url=HEADER+"stock/sureInStock.do";
    }else if(type=="errInStock"){
        auditstatus="20";//入库异常
        url=HEADER+"stock/errInStock.do";
    }
    cr_dialogBox.confirm(true,"确认要操作吗？",function(flag){
        if(flag){
            $.ajax({
                type: "post",
                url: url,
                data: {
                    "auditid": auditid,
                    "agree": content,
                    "type": type,
                    "auditstatus": auditstatus
                },
                success: function (data) {
                    if (data.msg == "1" || data.msg == 1) {
                        cr_dialogBox.confirm(true, "操作完成！");
                        back();
                        itemManagement.pageType("preAllPage");

                    } else {
                        cr_item.showTip(data.msg, function () {

                        });
                    }
                }
            });
        }
    });
}

//提交人发货
function delivery(str) {
    //var url = HEADER + "stock/deStock.do";

    var sid = $("#detail-table").attr("data-sid");
    var type = "发货";
    if(str=='send'){
        type="发货";
    }else if(str=="th"){
        type="退货";
    }
    var numFlag = true;
    //获取发货数量
    var jsonArr = "";
    var gname = "";
    url = HEADER + "stock/outPutStock.do";

    if (numFlag) {

        $.ajax({
            type: "post",
            url: url,
            data: {
                "id": sid
            },
            success: function (data) {
                if (!!data.status) {
                    if (data.status == "1" || data.status == 1) {
                        cr_dialogBox.confirm(true, "操作完成！");
                        back();
                        itemManagement.pageType("preAllPage")
                    } else {
                        cr_item.showTip(data.status, function () {
                            back();
                            itemManagement.pageType("preAllPage")
                        });
                    }
                } else if (!!data.msg) {
                    if (data.msg == "1" || data.msg == 1) {
                        cr_dialogBox.confirm(true, "操作完成！");
                        back();
                        itemManagement.pageType("preAllPage")

                    } else {
                        cr_item.showTip(data.msg, function () {
                            //  back();
                            itemManagement.pageType("preAllPage")
                        });
                    }
                }
            }
        });
        // }
        //  });
    } else {
        cr_dialogBox.alert(true, "库存数量少于发货数量，请检查" + gname + "!");
    }
}

function search() {
    var findkey = $("#findkey").val();
    var begintime = $("#beginTime").val();
    var endtime = $("#endTime").val();
    var param = findkey + "&begintime=" + begintime + "&endtime=" + endtime;
    itemManagement.searchMsg(param);
}

function back() {
    $('.audit-detail').addClass('popup').siblings().css('opacity', '1');
    $('.audit-detail').css("display", "none");
}

//根据aid获取数量信息
function getNumberDetail() {
    var aid = $("#setValue").attr("aid").split(",");
    var url = HEADER + "getStockList.do";//获取出入库记录
    var url1 = HEADER + "getStockGoodsList.do";//查看库存记录
    var arr = [];
    for (var i = 0; i < aid.length; i++) {
        $.ajax({
            type: "get",
            url: url,
            async: false,
            data: {
                "sno": aid[i],
                "page": 1,
                "pageSize": 1
            },
            success: function (data) {
                if (!!data) {
                    var jsonArr = data.list[0];
                    $.ajax({
                        type: "get",
                        url: url1,
                        async: false,
                        data: {
                            "gid": jsonArr.gno,
                            "page": 1,
                            "pageSize": 10
                        },
                        success: function (msg) {
                            arr.push({
                                "gno": jsonArr.gno,
                                "gname": jsonArr.gname,
                                "snum": jsonArr.snum,
                                "total": msg.list[0].nsum
                            });
                        }
                    });
                }
            }
        });
    }
    return arr;
}