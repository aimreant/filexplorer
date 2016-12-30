<%--
  Created by IntelliJ IDEA.
  User: aimreant
  Date: 4/8/16
  Time: 8:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<layout:override name="modal-name">
    Create Level
</layout:override>

<layout:override name="modal-body">
    <form id="modalForm" action="/admin/settings/level" method="post">
        <div class="box-body">
            <div class="form-group">
                <label for="level">Level</label>
                <input type="level" id="level" name="level" class="form-control" placeholder="Enter level">
            </div>
            <div class="form-group">
                <label for="space">Space</label>
                <input type="space" id="space" name="space" class="form-control" placeholder="Enter space (B)">
            </div>

        </div>
    </form>
</layout:override>


<layout:override name="content">

    <div class="box">
        <div class="box-header">
            <h3 class="box-title">Users Manage</h3>

            <div class="box-tools">
                <button type="button"
                        class="btn btn-info btn-flat btn-sm pull-right" data-toggle="modal" data-target="#addModal">New Level</button>
            </div>
        </div>


        <!-- /.box-header -->
        <div class="box-body table-responsive no-padding">
            <table class="table table-hover">

                <tbody><tr>
                    <th>Level</th>
                    <th>Space</th>
                    <th>Operation</th>
                </tr>

                <c:forEach items="${levelEntityList}" var="level">
                    <tr>
                        <td>${level.id}</td>
                        <td>${level.spaceAllow}</td>
                        <td>
                            <a class="btn btn-warning btn-flat btn-xs">Detail</a>
                            <a class="btn btn-danger btn-flat btn-xs">Delete</a>
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
<%@ include file="../../modal.jsp"%>


