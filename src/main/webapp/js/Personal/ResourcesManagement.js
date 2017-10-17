$(function () {
//获取宽高
//     var height = $(window).height();//浏览器当前可视部分高度
    $("#areaSelect").height(888);
    $("#treeDom").height(800);
})

//打开新增
function openAddResource() {
    // var content = $("#resourcesMsg").html();
    var resName = $("#resourcesMsg").attr("data-resname");//获取主菜单的名字
    var resLevel = ($("#resourcesMsg").attr("data-level"))*1+2;
    //判断右侧有没有数据
        if(resName == undefined){
            cr_dialogBox.alert(true, "请选择左侧资源！");
        }else{
            $("#addResourceModal").modal("show");
            $("#addParentMenu").val(resName);
            $("#addLevel").val(resLevel);
        }
}

//打开编辑
function openEditResource(dom) {
    $("#editResourceModal").modal("show");
    var id = $(dom).parents("tr").attr("data-id");
    var editResourceNumber = $(dom).parents("tr").children().eq(0).html();//顺序编号
    var editResourceName = $(dom).parents("tr").children().eq(1).html();//资源名称
    var editUrl = $(dom).parents("tr").children().eq(2).html();//资源URL
    var editParentMenu = $(dom).parents("tr").children().eq(3).html();//父菜单
    var editLevel = $(dom).parents("tr").children().eq(4).html();//等级
    $("#editResourceNumber").val(editResourceNumber);//顺序编号
    $("#editUrl").val(editUrl);//资源URL
    $("#editParentMenu").val(editParentMenu);//父菜单
    $("#editLevel").val(editLevel);//等级
    $("#editResourceName").val(editResourceName).attr("data-id", id);//资源名称
}
//刷新资源信息
function normalLoad(){
    var curMode = cr.getCache("curNode",true);
    var resourceid = JSON.parse(curMode).resourceid;
    var resname = JSON.parse(curMode).resname;
    var reslevel = JSON.parse(curMode).reslevel;
    console.log("当前节点："+curMode);//curMode=null
    $.ajax({
        type: "get",
        url: HEADER + "resources/queryAllResourcesByMenuid.do?page=1&pageSize=20&resourceid=" + resourceid,
        success: function (data) {
            if (!!data) {
                var jsonArr = data.result;
                var len = jsonArr.length;
                var html = "";
                for (var i = 0; i < len; i++) {
                    html += "<tr data-id='" + jsonArr[i].id + "'>" +
                        "	<td>" + (jsonArr[i].number == null ? "" : jsonArr[i].number) + "</td>" +
                        "	<td>" + (jsonArr[i].resourcename == null ? "" : jsonArr[i].resourcename) + "</td>" +
                        "	<td>" + (jsonArr[i].url == null ? "" : jsonArr[i].url) + "</td>" +
                        "	<td>" + resname + "</td>" +
                        "	<td>" + (jsonArr[i].level == null ? "" : jsonArr[i].level) + "</td>" +
                        "	<td><span onclick='openEditResource(this);' class='operate'>编辑</span>/<span onclick='deleteResourceMsg(this);' class='operate'>删除</span></td>" +
                        "</tr>";
                }
                $("#resourcesMsg").html(html); //将获取到的数据显示在表格的tbody中
                $("#resourcesMsg").attr({
                    "data-id": resourceid,
                    "data-resname": resname,
                    "data-level": reslevel
                });
            }
        }
    });
}

//提交新增
function submitAddResource() {
    var addnumber = $("#addResourceNumber").val(); //顺序编号
    var addresourcename = $("#addResourceName").val(); //资源名称
    var addurl = $("#addUrl").val(); //url
    var addlevel = $("#addLevel").val();//等级
    var addparentid = $("#resourcesMsg").attr("data-id");//获取到当前选中的资源的id作为新增子菜单的parentid
    // alert(addparentid == "2c909f4c5d54ca86015d54caf6140001");
    cr_dialogBox.confirm(true, "确定新增该数据？", function (flag) {
        if (flag) {
            $.ajax({
                type: "get",
                url: HEADER + "resources/add_Resources.do",
                async: true,
                data: {
                    "resourcename": addresourcename,
                    "url": addurl,
                    "parentid": addparentid,
                    "number": addnumber,
                    "level": addlevel
                },
                success: function (data) {
                    if (data.status == 1 || data.status == "1") {
                        cr_dialogBox.alert(true, "新增成功!");
                        $("#addResourceModal").modal("hide");
                        normalLoad();
                    } else if (data.status == 0 || data.status == "0") {
                        cr_dialogBox.alert(true, "新增失败!");
                    }
                }
            });
        }
    });
}

//提交编辑
function submitEditResource(dom) {
    var id = $("#editResourceName").attr("data-id");//id
    var editnumber = $("#editResourceNumber").val(); //顺序编号
    var editresourcename = $("#editResourceName").val(); //资源名称
    var editurl = $("#editUrl").val(); //url
    var editlevel = $("#editLevel").val();//等级
    var editparentid = $("#resourcesMsg").attr("data-id");//获取到当前选中的资源的id作为新增子菜单的parentid
    cr_dialogBox.confirm(true, "确定修改？", function (flag) {
        if (flag) {
            $.ajax({
                type: "post",
                url: HEADER + "resources/update_Resources.do",
                async: true,
                data: {
                    "id": id,
                    "resourcename": editresourcename,
                    "url": editurl,
                    "parentid": editparentid,
                    "number": editnumber,
                    "level": editlevel
                },
                success: function (data) {
                    if (data.status == 1 || data.status == "1") {
                        cr_dialogBox.alert(true, "修改成功!");
                        $("#editResourceModal").modal("hide");
                        normalLoad();
                    } else if (data.status == 0 || data.status == "0") {
                        cr_dialogBox.alert(true, "修改失败!");
                        $("#editResourceModal").modal("hide");
                    }
                }
            });
        }
    });
}

//删除角色信息
function deleteResourceMsg(dom) {
    var id = $(dom).parents("tr").attr("data-id");
    var url = HEADER + "resources/delete_Resources.do";
    cr_dialogBox.confirm(true, "确定删除？", function (flag) {
        if (flag) {
            $.ajax({
                type: "post",
                url: url,
                async: true,
                data: {
                    "id": id
                },
                success: function (data) {
                    if (data.status == 1 || data.status == "1") {
                        cr_dialogBox.alert(true, "删除成功！");
                        normalLoad();
                    } else if (data.status == 0 || data.status == "0") {
                        cr_dialogBox.alert(true, "删除失败！");
                    }
                }
            });
        }
    });
}
