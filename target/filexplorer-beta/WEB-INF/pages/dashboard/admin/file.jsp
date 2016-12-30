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

    <div class="box">
        <div class="box-header">
            <h3 class="box-title">Files</h3>

            <div class="box-tools">
                <div class="input-group input-group-sm" style="width: 150px;">
                    <input type="text" name="table_search" class="form-control pull-right" placeholder="Search">

                    <div class="input-group-btn">
                        <button type="submit" class="btn btn-default"><i class="fa fa-search"></i></button>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.box-header -->
        <div class="box-body table-responsive no-padding">
            <table class="table table-hover">

                <tbody><tr>
                    <th>Filehash</th>
                    <th>Size</th>
                    <th>Date</th>
                    <th>Links</th>
                    <th>Operation</th>
                </tr>

                <c:forEach items="${fileEntityList}" var="file">
                    <tr>
                        <td>${file.hash}</td>
                        <td>${file.size}</td>
                        <td>${file.createdAt}</td>
                        <td>${file.linksById.size()}</td>
                        <td>
                            <a class="btn btn-warning btn-flat btn-xs" href="/admin/files/${file.id}">Detail</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
        </div>
        <!-- /.box-body -->
    </div>

</layout:override>

<%@ include file="../../master.jsp"%>


