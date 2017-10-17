//树状图方法
(function () {
    var zTreeObj = "";
    var setting = {
        treeId: "treeDom",//zTree 的唯一标识，初始化后，等于 用户定义的 zTree 容器的 id 属性值。
        check: {
            enable: false,//设置 zTree 的节点上是否显示 checkbox / radio
            chkboxType: {"Y": "", "N": ""}//勾选 checkbox 对于父子节点的关联关系
        },
        data: {
            simpleData: {
                enable: true,//使用简单数据模式
                idKey: "id",//节点数据中保存唯一标识的属性名称
                pIdKey: "pId",//节点数据中保存其父节点唯一标识的属性名称
                rootPId: 0//用于修正根节点父节点数据，即 pIdKey 指定的属性值
            }
        },
        edit: {
            enable: false,//不支持编辑
            showRenameBtn: false,//取消可编辑按钮
            showRemoveBtn: false//取消删除按钮
        },
        view: {
            fontCss: {
                size: "20px"
            }
        },
        callback: {
            //单击事件
            onClick: function (event, treeId, treeNode) {
                console.log(treeNode);
                $("#areaSelect").children().css("background-color","#eee")
                // $("#" + treeNode.tId).css("background", "#000");//当前节点的样式
                var zTree = cr_area.zTree("treeDom");
                zTree.expandNode(treeNode, true, true, true);//展开当前节点
                var node = zTree.getNodeByTId(treeNode.tId);//获取到当前节点的json数据 [object object]
                var resourceid = node.id;//当前选中的资源的id
                var resname = node.name;//当前选中的资源的名称
                var reslevel = node.level;//当前选中的资源的等级
                var json={"resourceid":resourceid,"resname":resname,"reslevel":reslevel};
                // json.resourceid = resourceid;
                // json.resname = resname;
                // json.reslevel = reslevel;
                cr.setCache("curNode",JSON.stringify(json),true);//把当前节点存进缓存
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
        }
    };
    $(document).ready(function () {
//		var url = HEADER + "location/check_LocationListByJson.do";
        cr_area.treeInit();//初始化
    });
    return cr_area = {
        treeInit: function () {
            var url = HEADER + "resources/queryAllResources.do";
            $.get(url, function (resourcesList) {
                if (!!resourcesList) {
                    var zNodes = [];
                    var dirstryName = [];
                    resourcesList = resourcesList.result;
                    for (var i = 0; i < resourcesList.length; i++) {
                        zNodes.push({
                            "id": resourcesList[i].id,
                            "pId": resourcesList[i].parentid,
                            "name": resourcesList[i].resourcename,
                            "level": resourcesList[i].level
                        });
                        dirstryName.push({
                            "id": resourcesList[i].id,
                            "name": resourcesList[i].resourcename,
                            // "url": resourcesList[i].url
                        });
                    }
                    ;
                    zTreeObj = $.fn.zTree.init($("#treeDom"), setting, zNodes);//将获取到的数据初始化显示在treeDom容器内
                    cr_area.getNodeById();
                    cr_area.mohuchaxun(dirstryName);//模糊查询
                }
                ;
            });
        },
        //获取树状图对象
        zTree: function (dom) {
            return $.fn.zTree.getZTreeObj(dom);//有了这个方法，用户不再需要自己设定全局变量来保存 zTree 初始化后得到的对象了，而且在所有回调函数中全都会返回 treeId 属性，用户可以随时使用此方法获取需要进行操作的 zTree 对象
        },
        getNodeById: function () {
            var zTree = this.zTree("treeDom");
            if (cr_cache.getCache("areaIdDom", true)) {//如果缓存了dom对象
                var doms = cr_cache.getCache("areaIdDom", true).split(",");
                var node1 = "";
                for (var i = 0; i < doms.length; i++) {
                    //获取树节点
                    node1 = zTree.getNodeByTId(doms[i]);
                    //选中缓存中已有的区域
                    zTree.checkNode(node1, true, true);
                    //展开树节点
//	   				zTree.expandNode(node1.getParentNode(), true, false,true);
                }
                ;
            }
        },
        //搜索
        getValue: function (dom) {
            var $this = this;
            var zTree = this.zTree("treeDom");//获取树状图对象
            //根据搜索内容获取树节点
            var tId = zTreeObj.getNodesByParam("name", dom.value, null)[0];
            if (tId) {
                var node1 = zTree.getNodeByTId(tId.tId);//通过此节点的节点id获取到其json数据
                //展开树节点
                if (node1.level == "0") {//如果是最大的父节点（第一级）
                    zTree.expandNode(node1, true, false, true);//展开自己
                } else {
                    zTree.expandNode(node1.getParentNode(), true, false, true);//如果不是第一级，就展开其父节点
                    zTree.expandNode(node1, true, false, true);//并展开自己
                    // cr_area.treeInit();
                }
                ;
                // //选中区域
                // zTree.checkNode(node1, true, true);
                // //获取选中小区节点
                // var nodes = zTreeObj.getCheckedNodes(true);
                $this.loacationArea(tId, node1);
            }
        },
        //根据输入的小区名称定为他的位置上
        loacationArea: function (treeDom) {
            var Dom = $("#" + treeDom.tId);//当前节点
            Dom.children("a").eq(0).children().css("background-color", "#FFD700");//高亮
            // var treeId =treeDom.tId;//当前节点
            // cr_area.showResourceList(treeId);//加载出右边的数据
        },
        //模糊查询
        //{"id":id,"name":name}
        mohuchaxun: function (dirstryName) {
            var url = HEADER + "resources/queryAllResources.do";
            $.get(url, function (data) {
                var resource = data.result;
                var arr = [];
                for (var i = 0; i < resource.length; i++) {
                    arr.push({"id": resource[i].id, "name": resource[i].name});//要在这个数组里面遍历匹配
                }
                ;
                $('#areaInput').typeahead({//让id=areaInput里输入的内容与下面的
                    source: dirstryName
                });
            });
        },
        // showResourceList: function (dom) {
        //     // alert("showResource"+dom);
        //     // var node = zTree.getNodeByTId(treeNode.tId);
        //     // cr_area.treeInit();
        //     // var resourceid = node.id;//当前选中的资源的id
        //     // var resourceid = $("#" + param).attr();//当前节点;//当前选中的资源的id
        //     $.ajax({
        //         type: "get",
        //         url: HEADER + "resources/queryAllResourcesByMenuid.do?page=1&pageSize=15&resourceid=" + resourceid,
        //         success: function (data) {
        //             if (!!data) {
        //                 var jsonArr = data.result;
        //                 var len = jsonArr.length;
        //                 var html = "";
        //                 for (var i = 0; i < len; i++) {
        //                     html += "<tr data-id='" + jsonArr[i].id + "'>" +
        //                         "	<td>" + (jsonArr[i].number == null ? "" : jsonArr[i].number) + "</td>" +
        //                         "	<td>" + (jsonArr[i].resourcename == null ? "" : jsonArr[i].resourcename) + "</td>" +
        //                         "	<td>" + (jsonArr[i].url == null ? "" : jsonArr[i].url) + "</td>" +
        //                         "	<td>" + resname + "</td>" +
        //                         "	<td>" + (jsonArr[i].level == null ? "" : jsonArr[i].level) + "</td>" +
        //                         "	<td><span onclick='openEditResource(this);' class='operate'>编辑</span>/<span onclick='deleteResourceMsg(this);' class='operate'>删除</span></td>" +
        //                         "</tr>";
        //                 }
        //                 $("#resourcesMsg").html(html); //将获取到的数据显示在表格的tbody中
        //                 $("#resourcesMsg").attr({
        //                     "data-id": resourceid,
        //                     "data-resname": resname,
        //                     "data-level": reslevel
        //                 });
        //             }
        //         }
        //     });
        // },
        //treeNodeId1 一定要为数组
        expandNode1: function (treeNodeId1) {
            var zTree = cr_area.zTree("treeDom");
            if (treeNodeId1.length > 0) {
// 				var doms = treeNodeId1.split(",");
                var node1 = "";
                for (var i = 0; i < treeNodeId1.length; i++) {
//	   				console.log(treeNodeId1[i]);
                    //获取树节点
//	   				node1 = zTree.getNodeByTId(doms[i]);
                    node1 = zTree.getNodeByParam("id", treeNodeId1[i], null);
                    //选中缓存中已有的区域
                    zTree.checkNode(node1, true, true);
                    //展开树节点
                    zTree.expandNode(node1.getParentNode(), true, false, true);
                }
                ;
            }
        }
    }
        ;
})(window);