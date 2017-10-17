<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/11/23
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>res</title>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            getparent();
            resList();
        });

        function getparent(){
            $.ajax({
                type: "get",
                url: "resources/check_ResourcesList.do",
                data: {parentid: 0},
                dataType: 'json',
                success: function (data) {
                    $("#parentid").empty();
                    $("#parentid").append("<option value='0'>这是父级</option>");
                    for (var i = 0; i < data.length; i++) {
                        $("#parentid").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
                    }

                }
            });
        }

        function sub(){
            var name = $("#name").val();
            var url = $("#url").val();
            var parentid = $("#parentid").val();
            if(name==''|url==''|parentid=='')return;
            $.ajax({
                type: "post",
                url: "resources/add_Resources.do",
                data: {name:name,url:url,parentid:parentid},
                dataType: 'json',
                success: function (data) {
                    if(data.status==1){
                        alert("添加成功");
                        getparent();
                        resList();
                    }else{
                        alert("添加失败");
                    }
                }
            });
        }

        function delRes(i){
            var id = $("#list input").eq(i-3).val();
            $.ajax({
                type: "post",
                url: "resources/delete_Resources.do",
                data: {id:id},
                dataType: 'json',
                success: function (data) {
                    if(data.status==1){
                        alert("删除成功");
                        getparent();
                        resList();
                    }else if(data.status==0){
                        alert("删除失败");
                    }else if(data.status==2){
                        alert("id not in");
                    }
                }
            });
        }
        function updateRes(i){
            var url = $("#list input").eq(i-1).val();
            var name = $("#list input").eq(i-2).val();
            var id = $("#list input").eq(i-3).val();
            $.ajax({
                type: "post",
                url: "resources/update_Resources.do",
                data: {name:name,url:url,id:id},
                dataType: 'json',
                success: function (data) {
                    if(data.status==1){
                        alert("更新成功");
                        getparent();
                        resList();
                    }else if(data.status==0){
                        alert("更新失败");
                    }else if(data.status==2){
                        alert("id not in");
                    }
                }
            });

        }

        function resList(){
            $.ajax({
                type: "get",
                url: "resources/check_ResourcesList.do",
                data: {},
                dataType: 'json',
                success: function (data) {
                    $("#list").empty();
                    var n = 0;
                    for(var i=0;i<data.length;i++){
                        $("#list").append("<input type='hidden' value='"+data[i].id+"' id='name"+i+"'>");n++;
                        $("#list").append("<input type='text' value='"+data[i].name+"' id='name"+i+"'>");n++;
                        $("#list").append("　　").append("<input type='text' value='"+data[i].url+"'>").append("　　");n++;
                        $("#list").append("<input type='button' value='更新' onclick='updateRes("+n+");'/>").append("　　");
                        $("#list").append("<a href='javascript:void(0)' onclick='delRes("+n+");'>删除</a>").append("　　");n++;
                        $("#list").append(data[i].id).append("　　").append(data[i].sn).append("　　").append(data[i].parentid).append("<br/><br/>");;

                    }
                }
            });
        }
    </script>
</head>
<body>
    资源名称:<input type="text" name="name" id="name"/><br/><br/>
    资源连接:<input type="text" name="url" id="url"/><br/><br/>
    父级选择:<select id="parentid" style="width:100px;">　</select><br/><br/>
    好了提交：<input type="button" onclick="sub();" value="提交吧" /><br/><br/>

    =====================简单不失华丽的分割线=====================<br/><br/>
    <div id="list"></div>
</body>
</html>
