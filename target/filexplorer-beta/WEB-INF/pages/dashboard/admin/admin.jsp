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

<layout:override name="modal-name">
    Create Admin
</layout:override>

<layout:override name="modal-body">
    <form id="modalForm" action="/admin/settings/admins" method="post">
        <div class="box-body">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="username" id="username" name="username" class="form-control" placeholder="Enter username">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" class="form-control" placeholder="Password">
            </div>
        </div>
    </form>
</layout:override>


<layout:override name="content">

    <div class="col-md-9">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">Admins Manage</h3>

                <div class="box-tools">
                    <button type="button"
                            class="btn btn-info btn-flat btn-sm pull-right" data-toggle="modal" data-target="#addModal">New Admin</button>
                </div>
            </div>


            <!-- /.box-header -->
            <div class="box-body table-responsive no-padding">
                <table class="table table-hover">

                    <tbody><tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Create time</th>
                        <th>Operation</th>
                    </tr>

                    <c:forEach items="${adminEntityList}" var="admin">
                        <tr>
                            <td>${admin.id}</td>
                            <td>${admin.username}</td>
                            <td>${admin.createdAt}</td>
                            <td>
                                <c:if test="${!deleted}">
                                    <form action="/admin/settings/admins/${admin.id}" method="post" style="display: inline;">
                                        <input type="hidden" name="_method" value="delete"/>
                                        <button type="submit" class="btn btn-danger btn-flat btn-xs">Delete</button>
                                    </form>
                                </c:if>
                                <c:if test="${deleted}">
                                    <form action="/admin/settings/admins/${admin.id}?deleted=1" method="post" style="display: inline;">
                                        <input type="hidden" name="_method" value="put"/>
                                        <button type="submit" class="btn btn-info btn-flat btn-xs">Restore</button>
                                    </form>
                                </c:if>

                                <form action="/admin/settings/admins/${admin.id}?pre=1" method="post" style="display: inline;">
                                    <input type="hidden" name="_method" value="delete"/>
                                    <button type="submit" class="btn btn-warning btn-flat btn-xs">Delete Forever</button>
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
        <div class="box box-solid">
            <div class="box-header with-border">
                <h3 class="box-title">Labels</h3>

                <div class="box-tools">
                    <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                    </button>
                </div>
            </div>
            <div class="box-body no-padding" style="display: block;">
                <ul class="nav nav-pills nav-stacked">
                    <c:if test="${deleted}">
                        <li><a href="/admin/settings/admins"><i class="fa fa-circle-o text-light-blue"></i> In use </a></li>
                        <li><a href="/admin/settings/admins?deleted=1"><i class="fa fa-circle text-yellow"></i> Deleted </a></li>
                    </c:if>
                    <c:if test="${!deleted}">
                        <li><a href="/admin/settings/admins"><i class="fa fa-circle text-light-blue"></i> In use </a></li>
                        <li><a href="/admin/settings/admins?deleted=1"><i class="fa fa-circle-o text-yellow"></i> Deleted </a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>

</layout:override>

<%@ include file="../../master.jsp"%>
<%@ include file="../../modal.jsp"%>


