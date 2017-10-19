var HEADER = "/warehousing/";
var HEADER1 = "";

Date.prototype.format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

//仓储系统相关方法
var cr_item = {
    //返回值显示
    showTip: function (msg, callback) {
        if (msg == 1 || msg == "1") {
            cr_dialogBox.alert(true, "操作成功!");
            if (callback) {
                callback();
            }
        } else if (msg == 0 || msg == "0") {
            cr_dialogBox.alert(true, "操作失败!");
        }
    },
    //给下拉框赋值单位
    getUnit: function (dom) {
        var html = "";
        var unitArr = ["个", "件", "台", "米", "公斤", "调", "箱", "卷", "套", "包", "副", "双", "只", "把", "根"];
        for (var i = 0; i < unitArr.length; i++) {
            html += "<option value='" + unitArr[i] + "'>" + unitArr[i] + "</option>"
        }
        $(dom).html(html);
    },
    //给下拉框赋值评分标准
    getScore: function (dom) {
        var html = "";
        var scoreArr = ["1分", "2分", "3分", "4分", "5分"];
        for (var i = 0; i < scoreArr.length; i++) {
            html += "<option value='" + scoreArr[i] + "'>" + scoreArr[i] + "</option>"
        }
        $(dom).html(html);
    },

    //给下拉框赋值物品类型
    getItemType: function (dom) {
        var url = HEADER + "goods/getAllGoodsName.do";
        $.ajax({
            type: "get",
            url: url,
            async: true,
            success: function (data) {
                if (!!data) {
                    var jsonArr = data.allGoods;
                    var jsonMsg = {};
                    var html = "<option value=''>--请选择--</option>";
                    for (var i = 0; i < jsonArr.length; i++) {
                        html += "<option gspec='" + jsonArr[i].gspec + "' provider='" + jsonArr[i].provider + "' unit='" + jsonArr[i].unit + "' value='" + jsonArr[i].id + "'>" + jsonArr[i].gname + "</option>";
                    }
                    $(dom).html(html);
                }
            }
        });
    },

    //获取所有用户
    getAllUser:function (dom) {
        var url = HEADER + "user/check_UserList.do?page=1&pageSize=100";
        $.ajax({
            type: "get",
            url: url,
            async: true,
            success: function (data) {
                if (!!data) {
                    var jsonArr = data.result;
                    var html = "<option value=''>--请选择--</option>";
                    var jsonMsg = [];
                    for (var i = 0; i < jsonArr.length; i++) {
                        html += "<option value='" + jsonArr[i].id + "' data-name='"+jsonArr[i].username + "'>" + jsonArr[i].uname + "</option>";
                    }
                    $(dom).html(html);
                    $(dom).selectpicker('refresh');
                    $(dom).selectpicker('show');
                }
            }
        });
    },


    //获取所有成品
    getAllEndProduct: function (dom) {
        var url = HEADER + "goods/queryGoodsList.do?page=1&pageSize=10&gtid=03";
        $.ajax({
            type: "get",
            url: url,
            async: true,
            success: function (data) {
                if (!!data) {
                    var jsonArr = data.result;
                    var html = "<option value=''>--请选择--</option>";
                    // var html1 ="";
                    // var html2 = "";
                    for (var i = 0; i < jsonArr.length; i++) {
                        html += "<option gtid='" + jsonArr[i].gtid + "' gspec='" + jsonArr[i].gspec + "' provider='" + jsonArr[i].provider + "' unit='" + jsonArr[i].unit + "' value='" + jsonArr[i].id + "'>" + jsonArr[i].gname + "</option>";
                    }
                    $(dom).html(html);
                }
            }
        });
    },

//给下拉框赋值审核人
    getAllChecker: function (dom) {
        var url = HEADER + "user/getUserByRole.do?roleid=checker&page=1&pageSize=100";
        $.ajax({
            type: "get",
            url: url,
            async: true,
            success: function (data) {
                if (!!data) {
                    var jsonArr = data.result;
                    var html = "<option value=''>--请选择--</option>";
                    for (var i = 0; i < jsonArr.length; i++) {
                        html += "<option value='" + jsonArr[i].id + "' data-name='"+jsonArr[i].username + "'>" + jsonArr[i].uname + "</option>"
                    }
                    $(dom).html(html);
                }
            }
        });
    },
//给下拉框赋值管理员
    getAllManager: function (dom) {
        var url = HEADER + "user/getUserByRole.do?roleid=ccManager&page=1&pageSize=100";
        $.ajax({
            type: "get",
            url: url,
            async: true,
            success: function (data) {
                if (!!data) {
                    var jsonArr = data.result;
                    var jsonMsg = {};
                    var html = "<option value=''>--请选择--</option>";
                    for (var i = 0; i < jsonArr.length; i++) {
                        html += "<option value='" + jsonArr[i].id + "' data-name='"+jsonArr[i].username + "'>" + jsonArr[i].uname + "</option>"
                    }
                    $(dom).html(html);
                }
            }
        });
    },


//获取所有部门
    getDepart: function (dom) {
        var url = HEADER + "dept/check_DeptList.do?page=1&pageSize=1000";
        $.ajax({
            type: "get",
            url: url,
            async: true,
            success: function (data) {
                if (!!data) {
                    var departArr = data.result;
                    var jsonMsg = {};
                    var html = "<option value=''>--请选择--</option>";
                    for (var i = 0; i < departArr.length; i++) {
                        html += "<option value='" + departArr[i].id + "'>" + departArr[i].deptname + "</option>"
                    }
                    $(dom).html(html);
                }
            }
        });
    },

//给下拉框赋值所有角色
    getRole: function (dom) {
        var url = HEADER + "role/check_RoleForPage.do?page=1&pageSize=1000";
        $.ajax({
            type: "get",
            url: url,
            async: true,
            success: function (data) {
                if (!!data) {
                    var roleArr = data.result;
                    var jsonMsg = {};
                    var html = "<option value=''>--请选择--</option>";
                    for (var i = 0; i < roleArr.length; i++) {
                        html += "<option value='" + roleArr[i].id + "'>" + roleArr[i].rname + "</option>"
                    }
                    $(dom).html(html);
                }
            }
        });
    },

//给下拉框赋值供应商
    getProviderId: function (dom,type) {
        var url = HEADER + "supplier/querySupplierList.do?page=1&pageSize=1000";
        $.ajax({
            type: "get",
            url: url,
            async: false,
            success: function (data) {
                if (!!data) {
                    var jsonArr = data.result;
                    var jsonMsg = {};
                    var html = "<option value=''>--请选择--</option>";
                    for (var i = 0; i < jsonArr.length; i++) {
                        html += "<option value='" + jsonArr[i].id + "'>" + jsonArr[i].proname + "</option>";
                        // jsonMsg.push(jsonArr[i].id );
                    }
                    $(dom).html(html);
                    if(!type) {
                        $(dom).selectpicker('refresh');
                        $(dom).selectpicker('show');
                    }
                    // $(dom).selectpicker('render');
                }
            }
        });
    },

//初始化时间输入框
    initTime: function (dom, type) {
        if (type == "start") {
            var time = new Date().format("yyyy-MM") + "-01";
            $(dom).val(time);
        } else {
            $(dom).val(new Date().format("yyyy-MM-dd"));
        }
    },

//检测时间
    checkBeginTime: function (dom) {
        var dateTime = $(dom).val();
        var nowTime = new Date().getTime();
        var selectTime = new Date(dateTime).getTime();
        if (dateTime == "") {
            cr_dialogBox.alert(true, "请选择时间！");
        } else if ((nowTime - selectTime) < 0) {
            //			cr_dialogBox.alert(true,"日期不能大于当前时间!");
            $(dom).val($this.initTime1(new Date()));
        }
        ;
    }
    ,
//检查结束时间
    checkEndTime: function (dom, startDom) {
        var dateTime = $(dom).val();
        var nowTime = new Date().getTime();
        var selectTime = new Date(dateTime).getTime();
        var startTime = new Date($("#" + startDom).val()).getTime();
        if (dateTime == "") {
            cr_dialogBox.alert(true, "请选择时间！");
        } else {
            if (selectTime > nowTime) {
                cr_dialogBox.alert(true, "结束时间不能大于当前时间!");
                $(dom).val((new Date()).format("yyyy-MM-dd"));
            } else if (selectTime < startTime) {
                cr_dialogBox.alert(true, "结束时间不能少于时间!");
                $(dom).val((new Date()).format("yyyy-MM-dd"));
            }
        }
        ;
    }
    ,
//根据页数获取序号
    getXuhao: function (num, page,pagesize) {
        if (page > 0) {
            return ((page - 1) * pagesize*1 + num * 1 + 1);
        } else {
            return 1;
        }
    }
    ,

//获取上传文件的路径
//file -- 传入上传文件的input对象
    getObjectURL: function (file) {
        var url = null;
        if (window.createObjectURL != undefined) { // basic
            url = window.createObjectURL(file);
        } else if (window.URL != undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL != undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file);
        }
        return url;
    }
}

var cr_normal = {
    mohuchaxun: function (dirstryName) {
        //		var url = HEADER+"location/check_LocationListByJson.do";
        //		$.get(url,function(data){
        //			var community = data.dirstry;
        //			var len = community.length;
        //			var arr = [];
        //			for(var i=0;i<len;i++){
        //				arr.push({"id":community[i].id,"name":community[i].name});
        //			};
        $('#areaInput').typeahead({
            source: dirstryName
        });
        //		});
    },
    //显示与隐藏回到顶部按钮
    returnTop: function (dom) {
        $("#areaSelect").scroll(function () {
            var top = $("#areaSelect").scrollTop();
            if (top >= 100) {
                $(".cr_top").fadeIn(500);
            } else {
                $(".cr_top").fadeOut(500);
            }
            ;
        });
        $("#box").scroll(function () {
            var top1 = $("body").scrollTop();
            if (top1 >= 100) {
                $(".cr_top").fadeIn(500);
            } else {
                $(".cr_top").fadeOut(500);
            }
            ;
        });
    },
    //区域选择回到顶部
    areaTop: function () {
        $("#areaSelect").animate({scrollTop: 0}, 500);
        $("body").animate({scrollTop: 0}, 500);
    },
    //判断正数
    checkInteger: function (dom) {
        var value = parseInt($(dom).val()) + "";
        // alert(value);
        if (value == "NaN") {
            cr_dialogBox.alert(true,"请输入正确的数字!");
            $(dom).val(0);
        } else {
            $(dom).val(parseInt(Math.round(value)*100)/100);
        }
        ;
    },
};
//区域选择
var area = {
    lastInputDom: null,
    regionId: [],
    times: 0,
    getHW: function getHW(height) {
        var wHeight = $(window).height();
        var divDoms = $("#box").children();
        if (height) {
            divDoms.eq(0).height(height);
            divDoms.eq(1).height(height);
        } else {
            divDoms.eq(0).height(812);
            divDoms.eq(1).height(812);
        }
        ;
        $(".cr_moddle").width($(document).width());
        $(".cr_moddle").height($(document).height());
    },

    //区域显示与隐藏
    showNext: function showNext(dom) {
        var flag = $(dom).attr("data-flag");
        if (flag == "true") {
            $(dom).removeClass("glyphicon-menu-down").addClass("glyphicon-menu-right");
            $(dom).parent().children("ul").css("display", "none");
            $(dom).attr("data-flag", "false");
        } else if (flag == "false") {
            $(dom).removeClass("glyphicon-menu-right").addClass("glyphicon-menu-down");
            $(dom).parent().children("ul").css("display", "block");
            $(dom).attr("data-flag", "true");
        }
    },

    //chexkbox-如果上级被选中，下级所有元素都要选中
    selectAllLower: function (dom) {
        //		cr_dialogBox.alert(true,$("input[type='checkbox']","#allDrowmenu").length);
        var flag = $(dom).prop("checked");
        var ulDom = $(dom).parent().next();
        if (ulDom.html() == "	") {
            $(dom).prop("checked", false);
        } else {
            var liDoms = $(dom).parent().next().children();
            if (flag) {
                for (var i = 0; i < liDoms.length; i++) {
                    $(liDoms[i]).find("label").children().prop("checked", true);
                }
                ;
            } else {
                for (var i = 0; i < liDoms.length; i++) {
                    $(liDoms[i]).find("label").children().prop("checked", false);
                }
                ;
            }
            ;
        }
    },
    //chexkbox-如果子级都没有选中，上级要去除选中状态
    clearHeighSelect: function (dom) {
        var liDoms = $(dom).parent().parent().parent().children();
        var flag = false;
        var flag1 = false;
        var inputDoms = "";
        //清空区域搜索框内容
        $("#areaSelect").val("");
        $("#areaSelect").next().next().prop("href", "javascript:void(0);");
        $("#areaSelect").next().next().addClass("hidden");
        $("#areaSelect").next().removeClass("hidden");
        for (var i = 0; i < liDoms.length; i++) {
            inputDoms = $(liDoms[i]).find("label").find("input");
            flag = flag || inputDoms.prop("checked");
        }
        ;
        if (flag) {
            //上级根据下级选项联动
            liDoms.parent().prev().find("input").prop("checked", true);
            //顶级根据所有下级联动
            $(dom).parents("li").parent("ul").parent().parent().prev().find("input").prop("checked", true);
        } else {
            liDoms.parent().prev().find("input").prop("checked", false);
        }
        ;
        //得到大区下面所有复选框状态
        var liDoms1 = $(dom).parents("li[class=menuList]").children("ul").children("li").find("input");
        for (var i = 0; i < liDoms1.length; i++) {
            flag1 = flag1 || $(liDoms1[i]).prop("checked");
        }
        ;
        if (flag1 == false) {
            $(dom).parents("li").parent("ul").parent().parent().prev().find("input").prop("checked", false);
        }

    },
    //若检测到有缓存的区域，自动展开下拉项
    showLowerList: function () {
        if (area.getAreaId()) {
            var areaIds = area.getAreaId().split(",");
            var doms = [];
            for (var i = 0; i < areaIds.length; i++) {
                doms.push($("input[data-id=" + areaIds[i] + "]"));
            }
            ;
            for (var j = 0; j < doms.length; j++) {
                //				doms[j].parent().parent().parent().css("display","block");
                doms[j].parents("ul").css("display", "block");
                doms[j].parents("ul").prev().prev().removeClass("glyphicon glyphicon-menu-right").addClass("glyphicon glyphicon-menu-down").attr("data-flag", true);

            }
        } else {
            var inputDoms = $("#allDrowmenu").find("li").find("input[type='checkbox']");
            for (var i = 0; i < inputDoms.length; i++) {
                $(inputDoms[i]).prop("checked", false);
            }
            ;
            cr_cache.removeCache("areaId", true);
        }
    },
    //将输入框的名称赋给定位按钮
    putValue1: function (dom) {
        var value = $(dom).val();
        if (value) {
            var inputDom = $("input[data-name='" + value + "']", $("#allDrowmenu"));
            var parentUl = inputDom.parents("ul");
            $(dom).next().next().prop("href", "#" + value);
            if (inputDom.prop("checked")) {
                //展开列表
                parentUl.css("display", "block");
                //该表箭头状态
                parentUl.prev().prev().attr("data-flag", "true").removeClass("glyphicon glyphicon-menu-right").addClass("glyphicon glyphicon-menu-down");
                inputDom.parent().css("color", "red");
                $("#areaSelect").next().addClass("hidden");
                $("#areaSelect").next().next().removeClass("hidden");
            }
            ;
        }
    },
    //展示当前下拉框
    showList: function (dom) {
        var value = $(dom).prev().prev().val();
        var inputDom = $("input[data-name='" + value + "']", $("#allDrowmenu"));
        inputDom.parent().parent().parent().css("display", "block");
        inputDom.parent().parent().parent().prev().prev().attr("data-flag", "true").removeClass("glyphicon glyphicon-menu-right").addClass("glyphicon glyphicon-menu-down");
    },
    //改变按钮
    changeSearch: function (dom) {
        $("#areaSelect").next().removeClass("hidden");
        $("#areaSelect").next().next().addClass("hidden");
    },
    //搜索区域
    selectArea1: function (dom) {
        var value = $("#areaSelect").val();
        if (value) {
            $(dom).prop("href", value);
            var inputDom = $("input[data-name='" + value + "']", $("#allDrowmenu"));
            var parent = inputDom.parents("ul");
            if (inputDom.prop("checked") == false) {
                if (parent && parent.attr("id") != "allDrowmenu") {
                    parent.css("display", "block");
                    parent.prev().prev().removeClass("glyphicon-menu-right").addClass("glyphicon-menu-down").attr("data-flag", "true");
                    inputDom.parent().css("color", "red");
                    parent.prev().children().eq(0).prop("checked", true);
                    inputDom.prop("checked", true);
                    showMeterList(inputDom);
                    //切换搜索按钮为定位按钮
                    $("#areaSelect").next().addClass("hidden");
                    $("#areaSelect").next().next().removeClass("hidden");

                }
                ;
            } else {
                inputDom.parent().css("color", "red");
                $("#areaSelect").next().addClass("hidden");
                $("#areaSelect").next().next().removeClass("hidden");
            }
        }
    },
    selectArea: function (dom) {
        var $this = this;
        var inputDom = "";
        if ($this.lastInputDom != null && $this.lastInputDom.html() != undefined) {
            $this.lastInputDom.css("color", "#333");
        } else if (dom.value == null) {
            $this.lastInputDom = null;
        }
        ;
        //根据id或者区域名称查找区域
        var aa = $("input[data-name='" + dom.value + "']");
        var bb = $("input[data-id=" + dom.value + "]");
        if (aa.length >= 1) {
            inputDom = aa;
            //			dom.next().prop("href",dom.value);
        } else if (bb.length >= 0) {
            inputDom = bb;
        }
        ;
        //改变字体颜色
        inputDom.parent().css("color", "red");
        if (inputDom.is(":checked") == false) {
            var parent = inputDom.parents("ul");
            if (parent && parent.attr("id") != "allDrowmenu") {
                parent.css("display", "block");
                //让他的父级选中
                parent.prev().children().eq(0).prop("checked", true);
                parent.prev().prev().removeClass("glyphicon-menu-right").addClass("glyphicon-menu-down").attr("data-flag", "true");
                //选中状态
                $this.lastInputDom = inputDom.parent();
                inputDom.prop("checked", true);
                //展示子列表
                showMeterList(inputDom);
                //切换按钮
                $("#areaSelect").next().addClass("hidden").next().removeClass("hidden");
            }
            ;
        }
    },
    //显示与隐藏回到顶部按钮
    returnTop: function (dom) {
        $(dom).scroll(function () {
            var top = $(dom).scrollTop();
            if (top >= 500) {
                $(".cr_top").fadeIn(500);
            } else {
                $(".cr_top").fadeOut(500);
            }
            ;
        });
    },
    //区域选择回到顶部
    areaTop: function () {
        $("#areaList").animate({scrollTop: 0}, 500);
    },
    //得到所有大区
    getRegion: function () {
        var $this = this;
        var len = provinceJson.length;
        var html = "";
        $this.regionId = [];
        for (var i = 0; i < len; i++) {
            html += "<li class='menuList'>" +
                "	<span style='cursor: pointer;' data-flag='false' class='glyphicon glyphicon-menu-right' onclick='area.showNext(this);'></span><label><input data-id='" + provinceJson[i].id + "' data-name='" + provinceJson[i].name + "' type='checkbox' onclick='area.selectAllLower(this);showMeterList(this);'/>" + provinceJson[i].name + "</label>" +
                "	<ul id='" + provinceJson[i].id + "' style='display:none;'>" +
                //				"	<ul id='" + provinceJson[i].id + "' style='display:none;'>" +
                "	</ul>" +
                "</li>";
            $this.regionId.push(provinceJson[i].id);
        }
        $("#allDrowmenu").html(html);
        area.getBusiness();
    },
    //检测缓存中是否有已选择的区域
    checkLocalArea: function () {
        var areaId = cr_cache.getCache("areaId", true);
        var idArr = [];
        var timer = setInterval(function () {
            if (areaId) {
                idArr = areaId.split(",");
                for (var i = 0; i < idArr.length; i++) {
                    $("input[data-id=" + idArr[i] + "]").prop("checked", true);
                    //					$("input[data-id="+idArr[i]+"]").parents("li[class='business']").children("label").children("input").attr("checked",true);
                    $("input[data-id=" + idArr[i] + "]").parents("li[class='menuList']").children("label").children("input").attr("checked", true);
                }
                ;
                showMeterList();
                clearInterval(timer);
            }
            ;
        }, 500);
        //2016/11/23 提示语句删除
        //		if(!areaId){
        //			cr_dialogBox.alert(true,"请选择区域！");
        //		};
    },
    //获取区域id值
    getAreaId: function () {
        var areaId = [];
        var inputDoms = $("li[class ='district']");
        var checkboxDoms = "";
        for (var i = 0; i < inputDoms.length; i++) {
            checkboxDoms = $("input[type='checkbox']:checked", inputDoms[i]);
            for (var j = 0; j < checkboxDoms.length; j++) {
                areaId.push($(checkboxDoms[j]).attr("data-id"));
            }
        }
        araeId = areaId.sort(function (a, b) {
            return a - b;
        });
        return araeId.toString();
    },
    getAllAreaId: function () {
        var areaId = [];
        var inputDoms = $("#allDrowmenu").find("li").find("input[type='checkbox']:checked");
        for (var i = 0; i < inputDoms.length; i++) {
            areaId.push($(inputDoms[i]).attr("data-id"));
        }
        ;
        areId = areaId.sort(function (a, b) {
            return a - b;
        });
        return areaId;
    },
    getAreaNameId: function () {
        var names = [];
        var inputDoms = $("li[class='menuList']");
        var checkboxDoms = "";
        for (var i = 0; i < inputDoms.length; i++) {
            checkboxDoms = $("input[type='checkbox']:checked", inputDoms[i]);
            for (var j = 0; j < checkboxDoms.length; j++) {
                names.push([$(checkboxDoms[j]).attr("data-name"), $(checkboxDoms[j]).attr("data-id")]);
            }
        }
        return names;
    },
    //获取所有选中区域名称
    getAllAreaName: function () {
        var names = [];
        return $("#allDrowmenu").find("li").find("input[type='checkbox']:checked").parent().text();
    },
    showChangeBox: function (name, areaId, changeCode) {
        var html = "";
        if (!!changeCode == false) {
            changeCode = "";
        }
        ;
        html = "<div id='areaChange123123' class='input-group' style='background:#fff;position: absolute;top: 25%;left: 40%;padding: 15px;'>" +
            "	<div class='row' style='margin-left: 0px;'>" +
            "		<div class='col-md-3 col-xs-4' style='width: 88px;'>" +
            "			<label>当前名称</label>" +
            "		</div>" +
            "		<div class='col-md-8 col-xs-8'>" +
            "			<span class='form-control' id='areaChangeId' data-areaId='" + areaId + "'>" + name + "</span>" +
            "		</div>" +
            "	</div><br />" +
            "	<div class='row' style='margin-left: 0px;'>" +
            "		<div class='col-md-3 col-xs-4' style='width: 88px;'>" +
            "			<label>区域名称</label>" +
            "		</div>" +
            "		<div class='col-md-8 col-xs-8'>" +
            "			<input class='form-control' type='text' name='areaChangeName' id='areaChangeName'/>" +
            "		</div>" +
            "	</div><br />" +
            "	<div class='row' style='margin-left: 0px;'>" +
            "		<div class='col-md-3 col-xs-4' style='width: 88px;'>" +
            "			<label>编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</label>" +
            "		</div>" +
            "		<div class='col-md-8 col-xs-8'>" +
            "			<input class='form-control' type='text' name='areaChangeCode' id='areaChangeCode' value='" + changeCode + "' />" +
            "		</div>" +
            "	</div><br />" +
            "	<div class='row' style='margin-left: 0px;'>" +
            "		<div class='col-md-6 col-xs-6'>" +
            "			<span class='btn btn-success' style='width: 100px;' onclick='area.saveChangeBox();'>保存</span>" +
            "		</div>" +
            "		<div class='col-md-6 col-xs-6'>" +
            "			<span class='btn btn-success' style='width: 100px;' onclick='area.cancelChangeBox();'>取消</span>" +
            "		</div>" +
            "	</div>" +
            "</div>";
        if (this.times == 0) {
            $("body").append(html);
            this.times = 1;
        } else if (this.times == 1) {
            $("#areaChange123123").remove();
            $("body").append(html);
        }
        ;
    },
    saveChangeBox: function () {
        var areaId = $("#areaChangeId").attr("data-areaId");
        var name = $("#areaChangeName").val();
        var code = $("#areaChangeCode").val();
        var url = HEADER + "updateLocationNameAndCode.do?id=" + areaId + "&name=" + name + "&code=" + code;
        $.post(url, function (data) {
            var status = data.status;
            if (status == "1") {
                $.ajax({
                    type: "get",
                    url: HEADER + "getLocationListAllToJs.do",
                    success: function (data) {
                        console.log("更新js文件成功！");
                    }
                });
                $.ajax({
                    type: "get",
                    async: false,
                    url: HEADER + "getLocations.do",
                    success: function (data) {
                        var res = eval(data);
                        provinceJson = res.parent;
                        cityJson = res.child;
                    }
                });
                cr_dialogBox.alert(true, "修改成功!");
                area.getRegion();
                area.checkLocalArea();
            } else if (status == "2") {
                cr_dialogBox.alert(true, "修改失败！");
            }
        });
    },
    cancelChangeBox: function () {
        $("#areaChange123123").remove();
        this.times = 0;
    },
    //区域右键菜单
    rightMenu: function () {
        var data = [
            [{
                text: "添加事业部",
                func: function () {
                    var $this = this;
                    var html = "";
                    var id = "";
                    if ($($this).attr("class") == "menuList") {
                        html = "<li class='menuList' data-type='0' data-id='-1'>" +
                            "	<span style='cursor: pointer;' data-flag='false' class='glyphicon glyphicon-menu-right' onclick='area.showNext(this);'></span>" +
                            "	<input style='width:100px' type='text'/><span class='glyphicon glyphicon-ok areaIcon' onclick='area.addArea(this);'></span><span class='glyphicon glyphicon-remove areaIcon' onclick='area.deleteThisList(this);'></span>" +
                            "</li>";
                        $($this).parent().append(html);
                        id = "";
                    }
                }
            },
                {
                    text: "添加小区",
                    func: function () {
                        var $this = this;
                        var html = "";
                        var id = "";
                        if ($($this).attr("class") == "menuList") {
                            id = $($this).children("ul").attr("id");
                            html = "<li class='district' data-type='1' data-id='" + id + "'>" +
                                "	<input style='width:90px' type='text'/><span style='margin-left: 10px;' class='glyphicon glyphicon-ok areaIcon' onclick='area.addArea(this);'></span><span class='glyphicon glyphicon-remove areaIcon' onclick='area.deleteThisList(this);'></span>" +
                                "</li>";
                            $($this).children("ul").append(html);
                            id = "";
                        }
                    }
                },
                {
                    text: "更改信息",
                    func: function () {
                        var areaId = $(this).children().eq(1).children().eq(0).data("id");
                        var url = HEADER + "updateLocationNameAndCodeBefore.do?id=" + areaId;
                        $.post(url, function (data) {
                            var code = data.code;
                            var name = data.name;
                            var areaId = data.id;
                            area.showChangeBox(name, areaId, code);
                        });
                    }
                }
            ],
            [{
                text: "删除事业部",
                func: function () {
                    var $this = this;
                    var id = $($this).find("label").children().eq(0).attr("data-id");
                    if (!!$($this).find("ul").has("li").length != 0) {
                        cr_dialogBox.alert(true, "抱歉，本区域下面还有子区域，不能删除！");
                        return false;
                    } else {
                        cr_dialogBox.confirm(true, "确定要删除当前事业部么？", function (flag) {
                            if (flag == true) {
                                $.ajax({
                                    type: "get",
                                    url: HEADER + "delLocation.do?id=" + id,
                                    success: function (data) {
                                        if (data.msg == "success") {
                                            $.ajax({
                                                type: "get",
                                                url: HEADER + "getLocationListAllToJs.do",
                                                success: function (data) {
                                                    console.log("更新js文件成功！");
                                                }
                                            });
                                            $.ajax({
                                                type: "get",
                                                async: false,
                                                url: HEADER + "getLocations.do",
                                                success: function (data) {
                                                    var res = eval(data);
                                                    provinceJson = res.parent;
                                                    cityJson = res.child;
                                                }
                                            });
                                            cr_dialogBox.alert(true, "删除成功!");
                                            area.getRegion();
                                            area.checkLocalArea();
                                        } else if (data.msg == "error") {
                                            cr_dialogBox.alert(true, "抱歉，删除失败!");
                                        } else if (data.msg == "1") {
                                            cr_dialogBox.alert(true, "小区下有电表,无法删除!");
                                        } else if (data.msg == "2") {
                                            cr_dialogBox.alert(true, "事业部下面有小区,无法删除!");
                                        }
                                        ;
                                    }
                                });
                            }
                        });
                    }
                }
            }]
        ];
        var data1 = [
            [{
                text: "添加小区",
                func: function () {
                    var $this = this;
                    var html = "";
                    var id = "";
                    if ($($this).attr("class") == "district") {
                        id = $($this).parent().attr("id");
                        html = "<li class='district' data-type='2' data-id='" + id + "'>" +
                            "	<input style='width:90px' type='text'/><span style='margin-left: 10px;' class='glyphicon glyphicon-ok areaIcon' onclick='area.addArea(this);'></span><span class='glyphicon glyphicon-remove areaIcon' onclick='area.deleteThisList(this);'></span>" +
                            "</li>";
                        $($this).parent().append(html);
                        id = "";
                    }
                }
            },
                {
                    text: "更改信息",
                    func: function () {
                        var areaId = $(this).children().eq(1).children().eq(0).data("id");
                        var url = HEADER + "updateLocationNameAndCodeBefore.do?id=" + areaId;
                        $.post(url, function (data) {
                            var code = data.code;
                            var name = data.name;
                            var areaId = data.id;
                            area.showChangeBox(name, areaId, code);
                        });
                    }
                }
            ],
            [{
                text: "删除小区",
                func: function () {
                    var $this = this;
                    var id = $($this).find("label").children().eq(0).attr("data-id");
                    if (!!$($this).find("ul").has("li").length != 0) {
                        cr_dialogBox.alert(true, "抱歉，本区域下面还有子区域，不能删除！");
                        return false;
                    } else {
                        cr_dialogBox.confirm(true, "确定要删除当前小区么？", function (flag) {
                            if (flag == true) {
                                $.ajax({
                                    type: "get",
                                    url: HEADER + "delLocation.do?id=" + id,
                                    success: function (data) {
                                        if (data.msg == "success") {
                                            $.ajax({
                                                type: "get",
                                                url: HEADER + "getLocationListAllToJs.do",
                                                success: function (data) {
                                                    console.log("更新js文件成功！");
                                                }
                                            });
                                            $.ajax({
                                                type: "get",
                                                async: false,
                                                url: HEADER + "getLocations.do",
                                                success: function (data) {
                                                    var res = eval(data);
                                                    provinceJson = res.parent;
                                                    cityJson = res.child;
                                                }
                                            });
                                            cr_dialogBox.alert(true, "删除成功!");
                                            area.getRegion();
                                            area.checkLocalArea();
                                        } else if (data.msg == "error") {
                                            cr_dialogBox.alert(true, "抱歉，删除失败!");
                                        } else if (data.msg == "1") {
                                            cr_dialogBox.alert(true, "小区下有电表,无法删除!");
                                        } else if (data.msg == "2") {
                                            cr_dialogBox.alert(true, "事业部下面有小区,无法删除!");
                                        }
                                        ;
                                    }
                                });
                            }
                        });
                    }
                }
            }]
        ];
        if (area.getAllAreaId()) {
            var menuList = $("#allDrowmenu").find("li[class=menuList]");
            var business = menuList.find("li[class=district]");
            for (var i = 0; i < menuList.length; i++) {
                $(menuList[i]).smartMenu(data, {name: "menuList"});
            }
            ;
            for (var i = 0; i < business.length; i++) {
                $(business[i]).smartMenu(data1, {name: "district"});
            }
            ;
        }
    },
    //添加区域
    addArea: function (dom) {
        var parentId = $(dom).parent().attr("data-id");
        var type = $(dom).parent().attr("data-type");
        var name = $(dom).prev().val();
        var flag = false;
        var jsonMsg = {};
        if (parentId == -1) {
            jsonMsg.type = type;
            jsonMsg.name = name;
            $.ajax({
                type: "get",
                async: false,
                url: HEADER + "addLocationCheckParent.do?name=" + name,
                success: function (data) {
                    if (data.msg == "0") {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
            });
        } else {
            jsonMsg.parentId = parentId
            jsonMsg.type = type;
            jsonMsg.name = name;
            $.ajax({
                type: "get",
                async: false,
                url: HEADER + "addLocationCheck.do?parentid=" + parentId + "&name=" + name,
                success: function (data) {
                    if (data.msg == "0") {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
            });
        }
        ;
        if (flag == true) {
            $.ajax({
                type: "get",
                url: HEADER + "addLocation.do",
                data: jsonMsg,
                success: function (data) {
                    if (data.msg == "success") {
                        $.ajax({
                            type: "get",
                            url: HEADER + "getLocationListAllToJs.do",
                            success: function (data) {
                                console.log("更新js文件成功！");
                            }
                        });
                        $.ajax({
                            type: "get",
                            async: false,
                            url: HEADER + "getLocations.do",
                            success: function (data) {
                                var res = eval(data);
                                provinceJson = res.parent;
                                cityJson = res.child;
                            }
                        });
                        cr_dialogBox.alert(true, "添加成功!");
                        area.getRegion();
                        area.checkLocalArea();
                    } else {
                        cr_dialogBox.alert(true, "添加失败!");
                    }
                }
            });
        } else {
            cr_dialogBox.alert(true, "已有重复的名称！");
        }
        ;
    },
    //删除添加区域的list
    deleteThisList: function (dom) {
        $(dom).parent().remove();
    }
};

//日期打印
var selectedDate = [];
var meterDate = {
    //查找所有选中日期
    getSleectDates: function () {
        var dates = [];
        var labelDoms = $("label[name='dataChecked'][class*='active']");
        for (var i = 0; i < labelDoms.length; i++) {
            dates.push($(labelDoms[i]).attr("dates"));
        }
        ;
        return dates.toLocaleString();
    },
    //打印日期
    printDate: function () {
        var html = "";
        for (var i = 1; i < 32; i++) {
            html += "<label name='dataChecked' dates='" + i + "' class='btn btn-warning allBtn'>" +
                "  	<input type='checkbox' autocomplete='off'/>" + i + "" +
                " </label>";
        }
        ;
        $("#allBtns").html(html);
        meterDate.resizeW();
    },
    //计算按钮宽高度
    resizeW: function () {
        var wWidth = $(window).width() * 0.5;
        var btns = Math.floor(wWidth / 10) - Math.floor(wWidth / 40);
        $(".allBtn").css({
            "width": btns + "px",
            "height": btns + "px",
            "line-height": (btns - (btns / 7)) + "px",
            "font-size": btns / 2 + "px",
            "border-radius": "50%",
            "opacity": "0.7",
            "margin": "0 40px 20px 0",
        });
    },
    //点击日期时间，显示不同颜色
    clickDate: function () {
        selectedDate = [];
        var datesLen = $("label[name=dataChecked]").length;
        for (var i = 0; i < datesLen; i++) {
            $("label[name=dataChecked]")[i].onclick = function () {
                //判断是否选中，颜色的转换
                if ($(this).attr("class") == "btn btn-warning allBtn") {
                    $(this).css("background", "#ff7800");
                    selectedDate["day" + $(this).attr("dates")] = $(this).attr("dates");
                } else {
                    $(this).css("background", "#eea236");
                    selectedDate["day" + $(this).attr("dates")] = "";
                }
                ;
            };
        }
        ;
        //显示所选的日期
        var html = "";
        //点击日期直接显示
        $(".allBtn").on("click", function () {
            var arr = [];
            for (var i = 1; i < 32; i++) {
                var dates = selectedDate["day" + i + ""];
                if (dates) {
                    html += (dates + "日、");
                }
                ;
            }
            ;
            var arrDays = html.split("、");
            arrDays.pop();
            html = "";
            //给选择的日期添加"、"
            for (var i = 0; i < arrDays.length; i++) {
                if (i < (arrDays.length - 1)) {
                    arrDays[i] = arrDays[i] + "、";
                }
                ;
                html += arrDays[i];
            }
            ;
            $("#getDates").html(html);
            //控制提示的上下滑动-----------------------------------------------------------------------------------------
            if ($("#getDates").html() == "" || $("#getDates").html() == null) {
                $("#selectDate").slideUp("fast");
            } else {
                $("#selectDate").slideDown("fast");
            }
            html = "";
        });
    },
    //初始化时间
    initTime: function (time) {
        var year = time.getFullYear();
        var month = (time.getMonth() + 1) > 9 ? (time.getMonth() + 1) : "0" + (time.getMonth() + 1);
        var day = time.getDate() > 9 ? time.getDate() : "0" + time.getDate();
        var hour = time.getHours() > 9 ? time.getHours() : "0" + time.getHours();
        var minute = time.getMinutes() > 9 ? time.getMinutes() : "0" + time.getMinutes();
        return year + "-" + month + "-" + day + " " + hour + ":" + minute;
    },
    initTime1: function (time) {
        var year = time.getFullYear();
        var month = (time.getMonth() + 1) > 9 ? (time.getMonth() + 1) : "0" + (time.getMonth() + 1);
        var day = time.getDate() > 9 ? time.getDate() : "0" + time.getDate();
        var hour = time.getHours() > 9 ? time.getHours() : "0" + time.getHours();
        var minute = time.getMinutes() > 9 ? time.getMinutes() : "0" + time.getMinutes();
        return year + "-" + month + "-" + day;
    }
}

var checkTab = {
    //切换tab框内容
    changeTabBg: function (dom) {
        $("input[name=search]").val("");
        //		searchMsg1($("input[name=search]"));
        var liDoms = $("#myTab").find("li");
        for (var i = 0; i < liDoms.length; i++) {
            $(liDoms[i]).children("a").css({"background": "none", "border": "none"});
        }
        ;
        if (parseInt($(dom).children("a").css("width")) < 150) {
            $(dom).children("a").css("width", "150");
        }
        ;
        $(dom).children("a").css({
            "background": "url(images/tab_bg.png) no-repeat",
            "background-size": "100%",
            //			"width":"100%",
            "height": "31px",
            "line-height": "14px",
            "text-align": "center"
        });
    },
    tab_init: function () {
        var liDoms = $("#myTab").children("li");
        $("#myTab").children("li").eq(0).children("a").css({
            "background": "url(images/tab_bg.png) no-repeat",
            "background-size": "100%",
            "width": "100%",
            "height": "31px",
            "line-height": "14px",
            "text-align": "center",
            "border": "none"
        });
        for (var i = 1; i < liDoms.length; i++) {
            if (parseInt($("#myTab").children("li").eq(i).children("a").css("width")) < 150) {
                //				console.log($("#myTab").children("li").eq(i).children("a").text()+"=="+$("#myTab").children("li").eq(i).children("a").css("width"));
                $("#myTab").children("li").eq(i).children("a").css({
                    "width": "150px",
                    "height": "31px",
                    "line-height": "14px",
                    "text-align": "center",
                    "border": "none"
                });
            } else {
                //				console.log($("#myTab").children("li").eq(i).children("a").text()+"=="+$("#myTab").children("li").eq(i).children("a").css("width"));
                $("#myTab").children("li").eq(i).children("a").css({
                    //					"width":"190px",
                    "height": "31px",
                    "line-height": "14px",
                    "text-align": "center",
                    "border": "none"
                });
            }
            ;
        }
        ;
    }
};

/*-----------------------------------图表方法------------------------------------*/
var crEcharts = {
    //实现图表
    drawEcharts: function (dom, title, legend, xAxisDate, seriesData) {
        // 路径配置
        require.config({
            paths: {
                echarts: 'dist'
            }
        });
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/bar'
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById(dom));
                option = {
                    tooltip: {
                        trigger: 'axis'
                    },
                    title: {
                        text: title.title,
                        x: title.direction
                    },
                    legend: {
                        data: legend
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {show: false},
                            dataView: {show: false, readOnly: false},
                            magicType: {show: true, type: ['line', 'bar']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    calculable: true,
                    xAxis: [{
                        type: 'category',
                        boundaryGap: false,
                        data: xAxisDate
                    }],
                    yAxis: [{
                        type: 'value'
                    }],
                    series: seriesData
                };
                // 为echarts对象加载数据
                myChart.setOption(option);
            }
        );
    },
    /*
     使用方法
     var xData = [['2013/1/24'],['2013/1/25'],['2013/1/28'],['2013/1/29']];
     var legend1 = ['MA5', 'MA10'];
     var series = [
     {
     name: 'MA5',
     type: 'line',
     data: [1,2,3,4],
     smooth: true,
     lineStyle: {
     normal: {opacity: 0.5}
     }
     },
     {
     name: 'MA10',
     type: 'line',
     data: [11,22,33,44],
     smooth: true,
     lineStyle: {
     normal: {opacity: 0.5}
     }
     }];
     drowCharts("main","车流量",xData,legend1,series);

     dom -- dom节点名称
     name -- 图表名称
     xData -- x轴坐标数据
     legend1 -- 线名称
     series -- 每一条线的数据
     lineType -- 决定是柱状图还是折线图 'bar' 'line'
     * */
    drawEcharts1: function (dom, name, xData, legend1, series) {
        var myChart = echarts.init(document.getElementById(dom));
        option = {
            title: {
                text: name,
                left: 0
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'line'
                }
            },
            toolbox: {
                show: true,
                feature: {
                    //		            magicType: {type: ['line', 'bar']},
                    restore: {},
                    saveAsImage: {}
                }
            },
            legend: {
                //线名称
                data: legend1
            },
            grid: {
                left: '10%',
                right: '10%',
                bottom: '15%'
            },
            xAxis: {
                type: 'category',
                data: xData,
                scale: true,
                boundaryGap: false,
                axisLine: {onZero: false},
                splitLine: {show: false},
                splitNumber: 20
            },
            yAxis: {
                scale: true,
                splitArea: {
                    show: true
                }
            },
            dataZoom: [{
                type: 'inside',
                start: 0,
                end: 100
            },
                {
                    show: true,
                    type: 'slider',
                    y: '90%',
                    start: 50,
                    end: 100
                }
            ],
            series: series
        };
        myChart.setOption(option);
    },
    drawEcharts2: function (dom, title, xData, legend, series) {
        var myChart = echarts.init(document.getElementById(dom));
        option = {
            title: {
                text: title,
                left: 0
            },
            legend: {
                //数组
                data: legend,
                width: "75%",
                left: "17%",
                top: "4%",
                //		        align:"left",
                padding: [0, 0, "10%", 0]
            },
            toolbox: {
                feature: {
                    dataView: {},
                    saveAsImage: {
                        pixelRatio: 2
                    }
                }
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    animation: false
                }
            },
            xAxis: {
                data: xData
            },
            yAxis: {},
            dataZoom: [{
                type: 'slider',
                show: true,
                start: 0,
                end: 100,
                handleSize: 8
            },
                {
                    type: 'inside',
                    start: 0,
                    end: 100
                },
                {
                    type: 'slider',
                    show: true,
                    yAxisIndex: 0,
                    filterMode: 'empty',
                    width: 12,
                    height: '70%',
                    handleSize: 8,
                    showDataShadow: false,
                    left: '93%'
                }
            ],
            series: series,
            animationEasing: 'elasticOut',
            animationDelayUpdate: function (idx) {
                return idx * 5;
            }
        };
        myChart.setOption(option);
    },
    drawEchartsCircle: function (dom, title, dataName, data) {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById(dom));
        // 指定图表的配置项和数据
        option = {
            title: {
                text: title,
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: dataName
            },
            series: [{
                name: '用电总量',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: data,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    }
};

/*-----------------------------------检查分页按钮--------------------------------------------------*/
var checkAllBtns = {
    check: function (btnDom, json, name) {
        //检测按钮是否可用
        var btns = $("#" + btnDom).children();
        for (var i = 0; i < btns.length; i++) {
            $(btns[i]).removeAttr("disabled");
        }
        $(btns[0]).attr("onclick", name + ".pageType('preAllPage');");
        $(btns[1]).attr("onclick", name + ".pageType('prePage');");
        $(btns[3]).attr("onclick", name + ".pageType('nextPage');");
        $(btns[4]).attr("onclick", name + ".pageType('nextAllPage');");
        if (json.lastPage <= 1) {
            for (var i = 0; i < btns.length; i++) {
                $(btns[i]).attr("disabled", "disabled").removeAttr("onclick");
            }
            ;
        }
        ;
        if (json.page <= 1) {
            $(btns[0]).attr("disabled", "disabled").removeAttr("onclick");
            $(btns[1]).attr("disabled", "disabled").removeAttr("onclick");
        } else if (json.page >= json.lastPage) {
            $(btns[3]).attr("disabled", "disabled").removeAttr("onclick");
            $(btns[4]).attr("disabled", "disabled").removeAttr("onclick");
        }
        ;
    }
};

//登录跳转到menu页面之后查找用户所在区域
var checkUserArea = {
    getUserArea: function () {
        //只允许他运行一次
        /*$.ajax({
         type:'get',
         url:HEADER+"getUserLocationJs.do",
         success:function(data){
         var oldUserName = cr_cache.getCache("users",true);
         var oldJsName = cr_cache.getCache("jsName",true);
         var username = data.username;
         var jsName = data.jsname;
         if(username == null || oldUserName != username){
         cr_cache.setCache("users",username,true);
         }else if(jsName==null || oldJsName != jsName){
         cr_cache.setCache("jsName",jsName,true);
         };
         }
         });*/
    },
    checkJsName: function () {
        //载入区域js文件
        /*var jsName = cr_cache.getCache("jsName",true);
         if(jsName){
         var jsUrl = "<script src='js/"+jsName+"' type='text/javascript' charset='utf-8'><\/script>";
         $('body').append(jsUrl);
         area.getRegion();
         }else{
         $.ajax({
         type:'get',
         url:HEADER+"getUserLocationJs.do",
         async:false,
         success:function(data){
         var jsName = data.jsname;
         var jsUrl = "<script src='js/"+jsName+"' type='text/javascript' charset='utf-8'><\/script>";
         $('body').append(jsUrl);
         area.getRegion();
         cr_cache.setCache("jsName",jsName,true);
         cr_cache.getCache("loadingWelcome","false",true);
         }
         });
         };*/
    }
};

//获取用户权限
var userRole = {
    getRoleSelect: function (dom) {
        $.ajax({
            type: 'get',
            url: HEADER + "role/queryAllRole.do",
            success: function (data) {
                var len = data.length;
                var html = "<option value=''>--请选择--</option>";
                for (var i = 0; i < len; i++) {
                    html += "<option value='" + data[i].id + "'>" + data[i].descn + "</option>"
                }
                ;
                $("#" + dom).html(html);
            }
        });
    }
};

//加载事件
var loadingModdle = {
    showModdle: function () {
        //		$("#loadingModdle").css("height","100%");
        $("#loadingModdle").removeClass("hidden");
    },
    closeModdle: function () {
        $("#loadingModdle").addClass("hidden");
    },
    //模糊查询
    mohuchaxun: function () {
        if (!!provinceJson == true) {
            var len = provinceJson.length;
            var proId = 0;
            var city = [];
            var k = 0;
            for (var i = 0; i < len; i++) {
                proId = provinceJson[i].id;
                if (cityJson[provinceJson[i].id]) {
                    for (var j = 0; j < cityJson[provinceJson[i].id].length; j++) {
                        city.push({"id": k, "name": cityJson[provinceJson[i].id][j].name});
                        k++;
                        //						console.log(cityJson[provinceJson[i].id][j].name);
                    }
                }
            }
            ;
            $('#areaSelect').typeahead({
                source: city
            });
        }
    }
};

var cr_number = {
    //保留2位小数
    getFloatStr: function (num) {
        num += '';
        num = num.replace(/[^0-9|\.]/g, ''); //清除字符串中的非数字非.字符

        if (/^0+/) //清除字符串开头的0
            num = num.replace(/^0+/, '');
        if (!/\./.test(num)) //为整数字符串在末尾添加.00
            num += '.00';
        if (/^\./.test(num)) //字符以.开头时,在开头添加0
            num = '0' + num;
        num += '00'; //在字符串末尾补零
        num = num.match(/\d+\.\d{2}/)[0];
        return num;
    },
    //经一个10进制的数变成各种进制数
    baseConverter: function (num, scale) {
        var arr = [];
        var aa = "";
        decNumber = num;
        while (decNumber > 0) {
            rem = Math.floor(decNumber % scale);
            arr.push(rem);
            decNumber = Math.floor(decNumber / scale);
        }
        for (var i = 0; i < arr.length; i++) {
            if (arr[i] == 10) {
                arr[i] = 'A'
            }
            ;
            if (arr[i] == 11) {
                arr[i] = 'B'
            }
            ;
            if (arr[i] == 12) {
                arr[i] = 'C'
            }
            ;
            if (arr[i] == 13) {
                arr[i] = 'D'
            }
            ;
            if (arr[i] == 14) {
                arr[i] = 'E'
            }
            ;
            if (arr[i] == 15) {
                arr[i] = 'F'
            }
            ;
        }
        ;
        arr.reverse();
        for (var i = 0; i < arr.length; i++) {
            aa += arr[i];
        }
        return aa;
    },
    //范围随机数
    random: function (start, end) {
        return Math.floor(Math.random() * (end - start + 1)) + start;
    }
};