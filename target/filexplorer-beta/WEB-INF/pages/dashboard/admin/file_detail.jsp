<%--
  Created by IntelliJ IDEA.
  User: aimreant
  Date: 4/8/16
  Time: 8:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" uri="http://www.dreamlu.net/tags/jsp-layout.tld" %>

<layout:override name="sidebar">

    <li class="header">MAIN NAVIGATION</li>
    <li class="treeview">
        <a href="/admin/">
            <i class="fa fa-dashboard"></i> <span>Dashboard</span>
        </a>
    </li>
    <li>
        <a href="/admin/files">
            <i class="fa fa-th"></i> <span>Files</span>
        </a>
    </li>

    <li>
        <a href="/admin/users">
            <i class="fa fa-th"></i> <span>Users Manage</span>
        </a>
    </li>

    <li class="treeview">
        <a href="/admin/settings">
            <i class="fa fa-pie-chart"></i>
            <span>Setting</span>
            <i class="fa fa-angle-left pull-right"></i>
        </a>
        <ul class="treeview-menu">
            <li><a href="/admin/settings/level"><i class="fa fa-circle-o"></i>Level Setting</a></li>
            <li><a href="/admin/settings/admins"><i class="fa fa-circle-o"></i>Admins Manage</a></li>
        </ul>
    </li>

</layout:override>


<layout:override name="content">

    <div class="col-md-9">
        <c:if test="${isImg}">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">Preview for ${fileEntity.hash}</h3>
                </div>

                <!-- /.box-header -->
                <div class="box-body table-responsive no-padding">
                    <img src="/store/${fileEntity.hash}" style="width: 100%;"/>
                </div>
                <!-- /.box-body -->
            </div>
        </c:if>

        <c:if test="${isVideo}">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">Preview for ${fileEntity.hash}</h3>
                </div>

                <!-- /.box-header -->
                <div class="box-body table-responsive no-padding">
                    <video src="/store/${fileEntity.hash}" controls="controls" style="width: 100%;">
                        您的浏览器不支持 video 标签。
                    </video>
                </div>
                <!-- /.box-body -->
            </div>
        </c:if>

        <c:if test="${isAudio}">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">Preview for ${fileEntity.hash}</h3>
                </div>

                <!-- /.box-header -->
                <div class="box-body table-responsive no-padding">
                    <audio src="/store/${fileEntity.hash}" controls="controls" style="width: 100%;">
                        您的浏览器不支持 audio 标签。
                    </audio>
                </div>
                <!-- /.box-body -->
            </div>
        </c:if>

        <div class="box">
            <div class="box-header">
                <h3 class="box-title">Links to file ${fileEntity.hash}</h3>
            </div>


            <!-- /.box-header -->
            <div class="box-body table-responsive no-padding">
                <table class="table table-hover">

                    <tbody><tr>
                        <th>Link ID</th>
                        <th>Filename</th>
                        <th>Owner</th>
                        <th>Date</th>
                        <th>Operation</th>
                    </tr>

                    <c:forEach items="${linkEntityList}" var="link">
                        <tr>
                            <td>${link.id}</td>
                            <td>${link.filename}</td>
                            <td>
                                <a href="/admin/users/${link.userByUserId.id}">
                                    ${link.userByUserId.username}
                                </a>
                            </td>
                            <td>${link.createdAt}</td>
                            <td>
                                <form action="#" method="post">
                                    <input type="hidden" name="_method" value="delete"/>
                                    <a class="btn btn-warning btn-flat btn-xs disabled" href="#">Detail</a>
                                    <button type="submit" class="btn btn-danger btn-flat btn-xs disabled">Delete</button>
                                </form>

                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>
            </div>
            <!-- /.box-body -->
        </div>
    </div>

    <div class="col-md-3">
        <div class="box box-primary">
            <div class="box-body box-profile">

                <h3 class="profile-username text-center">File</h3>


                <ul class="list-group list-group-unbordered">
                    <li class="list-group-item">
                        <b>Size</b> <a class="pull-right">${fileEntity.size}B</a>
                    </li>
                    <li class="list-group-item">
                        <b>Date</b> <a class="pull-right">${fileEntity.createdAt}</a>
                    </li>
                    <li class="list-group-item">
                        <b>Links</b> <a class="pull-right">${linkEntityList.size()}</a>
                    </li>
                </ul>


                <form action="#" method="post" >
                    <input type="hidden" name="_method" value="delete"/>
                    <button type="submit" class="btn btn-danger btn-primary btn-block disabled">Delete</button>
                </form>

            </div>
            <!-- /.box-body -->
        </div>
    </div>


</layout:override>

<%@ include file="../../master.jsp"%>


