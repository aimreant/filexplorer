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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
    Modify User
</layout:override>

<layout:override name="modal-body">
    <form id="modalForm" action="/admin/users/${userEntity.id}" method="post">
        <input type="hidden" name="_method" value="put"/>
        <div class="box-body">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="username" id="username" name="username" class="form-control" placeholder="Enter username" value="${userEntity.username}" disabled>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" class="form-control" placeholder="Password" disabled>
            </div>
            <div class="form-group">
                <label for="level">Level</label>
                <select class="form-control" id="level" name="level">
                    <c:forEach items="${levelEntityList}" var="level">
                        <option>${level.id} (${level.spaceAllow}B)</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </form>
</layout:override>


<layout:override name="content">

    <div class="col-md-9">

        <ul class="timeline">

            <li>
                <i class="fa fa-comments bg-yellow"></i>

                <div class="timeline-item">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <i class="fa fa-bar-chart-o"></i>

                            <h3 class="box-title">User Operation History</h3>

                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                </button>
                            </div>
                        </div>
                        <div class="box-body">
                            <div id="area-chart" style="height: 338px; padding: 0px; position: relative;" class="full-width-chart"><canvas class="flot-base" style="direction: ltr; position: absolute; left: 0px; top: 0px; width: 742px; height: 338px;" width="1484" height="676"></canvas><canvas class="flot-overlay" style="direction: ltr; position: absolute; left: 0px; top: 0px; width: 742px; height: 338px;" width="1484" height="676"></canvas></div>
                        </div>
                        <!-- /.box-body-->
                    </div>
                </div>
            </li>

            <c:forEach items="${logEntityList}" var="log">
                <!-- Main Example -->
                <li>
                    <i class="fa fa-envelope bg-blue"></i>

                    <div class="timeline-item">
                        <span class="time"><i class="fa fa-clock-o"></i> ${log.createdAt} </span>

                        <h3 class="timeline-header"><a href="#"> ${log.userByUserId.username} </a> Operation</h3>

                        <div class="timeline-body">
                                ${log.operation}
                        </div>
                        <div class="timeline-footer">
                            <c:if test="${log.linkByLinkId != null}">
                                <a class="btn btn-warning btn-flat btn-xs" href="/admin/files/${log.linkByLinkId.fileByFileId.id}">View File</a>
                            </c:if>
                        </div>
                    </div>
                </li>
            </c:forEach>

        </ul>
    </div>

    <div class="col-md-3">
        <div class="box box-primary">
            <div class="box-body box-profile">
                <img class="profile-user-img img-responsive img-circle" src="/img/user4-128x128.jpg" alt="User profile picture">

                <h3 class="profile-username text-center">${userEntity.username}</h3>

                <p class="text-muted text-center">
                    <c:if test="${deleted}">
                        User deleted at ${userEntity.deletedAt}
                    </c:if>
                    <c:if test="${!deleted}">
                        User in use.
                    </c:if>
                </p>

                <ul class="list-group list-group-unbordered">
                    <li class="list-group-item">
                        <b>Level</b> <a class="pull-right">${userEntity.levelByLevelId.id}</a>
                    </li>
                    <li class="list-group-item">
                        <b>Space</b>
                        <a class="pull-right">
                            <fmt:formatNumber value="${userEntity.spaceUsage/userEntity.levelByLevelId.spaceAllow}" type="percent" />
                        </a>
                    </li>
                    <li class="list-group-item">
                        <b>Files</b> <a class="pull-right">${linkCounts}</a>
                    </li>
                </ul>


                <c:if test="${!deleted}">
                    <form action="/admin/users/${userEntity.id}" method="post" >
                        <input type="hidden" name="_method" value="delete"/>
                        <a href="#" data-toggle="modal" data-target="#addModal" class="btn btn-primary btn-block"><b>Modify</b></a>
                        <button type="submit" class="btn btn-danger btn-primary btn-block">Delete</button>
                    </form>
                </c:if>
                <c:if test="${deleted}">
                    <form action="/admin/users/${userEntity.id}?deleted=1" method="post">
                        <input type="hidden" name="_method" value="put"/>
                        <a href="#" data-toggle="modal" data-target="#addModal" class="btn btn-primary btn-block"><b>Modify</b></a>
                        <button type="submit" class="btn btn-info btn-primary btn-block">Restore</button>
                    </form>
                </c:if>


                <form action="/admin/users/${userEntity.id}?pre=1" method="post" style="margin-top: 5px;">
                    <input type="hidden" name="_method" value="delete"/>
                    <button type="submit" class="btn btn-warning btn-primary btn-block">Delete forever</button>
                </form>
            </div>
            <!-- /.box-body -->
        </div>
    </div>


</layout:override>

<%@ include file="../../master.jsp"%>
<%@ include file="../../modal.jsp"%>


<script>
    $(function () {
//        var areaData = [[2, 88.0], [3, 93.3], [4, 102.0], [5, 108.5], [6, 115.7], [7, 115.6],
//            [8, 124.6], [9, 130.3], [10, 134.3], [11, 141.4], [12, 146.5], [13, 151.7], [14, 159.9],
//            [15, 165.4], [16, 167.8], [17, 168.7], [18, 169.5], [19, 168.0]];

        var areaData = ${stat} ;
        $.plot("#area-chart", [areaData], {
            grid: {
                borderWidth: 0
            },
            series: {
                shadowSize: 0, // Drawing is faster without shadows
                color: "#00c0ef"
            },
            lines: {
                fill: true //Converts the line chart to area chart
            },
            yaxis: {
                show: false
            },
            xaxis: {
                show: false
            }
        });
    });

</script>


