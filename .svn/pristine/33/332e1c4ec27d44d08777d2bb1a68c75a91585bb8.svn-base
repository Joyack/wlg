var tabArr = null;
var id = "";
$(function () {
    //导出表格
    $('#export').click(function () {
        let excelId = $('.tab-pane').filter('.active').children('table').attr('id');
        let excelName = $('#list').children().filter('.active').find('a').text();
        $(this).attr('download', excelName + '.xls');
        return ExcellentExport.excel(this, excelId, 'Sheet Name Here');
    });


    if (id === '') {
        id = "rmInventory";
    }
    $.ajax({
        url: HEADER + "report/getDateList.do?flag=1",
        type: "GET",
        async: false,
        success: function (data) {
            var jsonarr = data.result;
            var html = "";
            for (var i = 0; i < jsonarr.length; i++) {
                html += "<option value='" + jsonarr[i].datetime + "'>" + jsonarr[i].datetime + "</option>";
            }
            $("#selectMonth").html(html);
            $("#selectMonth option:first").prop("selected", "selected");
        }
    });

    itemManagement.pageType("preAllPage");

    //初始时间
    let today = new Date();
    let firstYear = today.getFullYear();
    let firstMonth = today.getMonth() + 1;
    if (firstMonth < 10) {
        firstMonth = '0' + firstMonth;
    }
    let firstTime = firstYear + '-' + firstMonth;
    $('.first').attr('value', firstTime).text(firstTime);
});


function test(dom) {
    var liDoms = $("#list").children();
    for (var i = 0; i < liDoms.length; i++) {
        $(liDoms[i]).css("background", "#fff");
        $(liDoms[i]).children().css("background", "#fff");

    }
    $(dom).children().css("background", "#AFD8F4");
    $(dom).css("background", "#AFD8F4");
    id = $(dom).children().attr("href").split('#')[1];

    itemManagement.pageType("preAllPage");
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

        var urlHeader = HEADER + 'report/';
        //拼接url
        var url = urlHeader + id + '.do?';
        var datetime = $("#selectMonth").val();


        $.ajax({
            type: "get",
            url: url + "page=" + pages + "&pageSize=10&datetime=" + datetime,
            success: function (data) {
                if (!!data) {

                    loadingModdle.closeModdle();
                    var jsonArr = data.result;
                    var len = jsonArr.length;
                    var itemType = "";
                    var html = "";
                    for (var i = 0; i < len; i++) {
                        switch (jsonArr[i].gtid) {
                            case "01":
                                jsonArr[i].gtid = "主料";
                                break;
                            case "02":
                                jsonArr[i].gtid = "辅料";
                                break;
                            case "03":
                                jsonArr[i].gtid = "成品";
                                break;
                        }
                    }
                    //     html += "<tr data-id='" + jsonArr[i].id + "'>" +
                    //         "	<td>" + (jsonArr[i].gid == null ? "" : jsonArr[i].gid) + "</td>" +
                    //         "	<td>" + (jsonArr[i].gname == null ? "" : jsonArr[i].gname) + "</td>" +
                    //         "	<td>" + itemType + "</td>" +
                    //         "	<td>" + ((jsonArr[i].gspec == ""||null) ? "——" : jsonArr[i].gspec) + "</td>" +
                    //         "	<td>" + (jsonArr[i].providername == null ? "" : jsonArr[i].providername) + "</td>" +
                    //         "	<td>" + (jsonArr[i].unit == null ? "" : jsonArr[i].unit) + "</td>" +
                    //         "	<td><span onclick='showEdit(this);'>编辑</span>/<span onclick='deleteMsg(this);'>删除</span></td>" +
                    //         "</tr>";
                    // }
                    html = template(id + 'Tem', data);
                    $('#' + id + '-table').html(html);


                    $("#itemManagementMsg").html(html); //将获取到的数据显示在表格的tbody中
                    $this.changePage(data.pageNo, data.prePage, data.nextPage, data.totalPageBeans); //改变变量的值
                    checkAllBtns.check("itemManagementBtns", itemManagement, "itemManagement"); //检查分页按钮是否可用，不可用就显示为禁用标志
                    $("#itemManagementNumber").html(($this.page == 0 ? 1 : $this.page) + "/" + ($this.lastPage == 0 ? 1 : $this.lastPage));
                    $("#itemManagementTotal").html(data.totalCount);
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
