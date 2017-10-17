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
    <title>perm</title>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            resList();
        });

        function subDefult(){
            var Prefix = $("#url").val();
            if(Prefix=='')return;
            $.ajax({
                type: "post",
                url: "permission/add_DefultPermission.do",
                data: {Prefix:Prefix},
                dataType: 'json',
                success: function (data) {
                    if(data.status==1){
                        alert("添加成功");
                        resList();
                    }else{
                        alert("添加失败");
                    }
                }
            });
        }


        function sub(){
            var url = $("#url").val();
            if(url=='')return;
            $.ajax({
                type: "post",
                url: "permission/add_Permission.do",
                data: {url:url},
                dataType: 'json',
                success: function (data) {
                    if(data.status==1){
                        alert("添加成功");
                        resList();
                    }else{
                        alert("添加失败");
                    }
                }
            });
        }

        function delRes(i){
            var id = $("#list input").eq(i-2).val();
            alert(id);
            $.ajax({
                type: "post",
                url: "permission/delete_Permission.do",
                data: {id:id},
                dataType: 'json',
                success: function (data) {
                    if(data.status==1){
                        alert("删除成功");
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
            var id = $("#list input").eq(i-2).val();
            $.ajax({
                type: "post",
                url: "permission/update_Permission.do",
                data: {url:url,id:id},
                dataType: 'json',
                success: function (data) {
                    if(data.status==1){
                        alert("更新成功");
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
                url: "permission/check_PermissionList.do",
                data: {},
                dataType: 'json',
                success: function (data) {
                    $("#list").empty();
                    var n = 0;
                    for(var i=0;i<data.length;i++){
                        $("#list").append("<input type='hidden' value='"+data[i].id+"' id='name"+i+"'>").append("");n++;
                        $("#list").append("<input type='text' value='"+data[i].url+"' id='name"+i+"'>").append("　　");n++;
                        $("#list").append("<input type='button' value='更新' onclick='updateRes("+n+");'/>").append("　　");
                        $("#list").append("<a href='javascript:void(0)' onclick='delRes("+n+");'>删除</a>").append("<br/><br/>");n++;
                    }
                }
            });
        }
    </script>
</head>
<body>
权限名称:<input type="text" name="url" id="url"/><br/><br/>
好了提交：<input type="button" onclick="sub();" value="提交吧" /> <input type="button" onclick="subDefult();" value="或者你可以偷懒一下" /><br/><br/>
=====================简单不失华丽的分割线=====================<br/><br/>
<div id="list"></div>
</body>
</html>
