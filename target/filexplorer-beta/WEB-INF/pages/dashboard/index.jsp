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
        <a href="/">
            <i class="fa fa-dashboard"></i> <span>Dashboard</span>
        </a>
    </li>
    <li>
        <a href="/files">
            <i class="fa fa-th"></i> <span>Files</span>
        </a>
    </li>

</layout:override>

<layout:override name="content">

    <div class="row">
        <div class="col-md-12">
            <!-- The time line -->
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

                            <c:if test="${log.linkByLinkId != null}">
                                <c:if test="${formatter.isImg(log.linkByLinkId.fileByFileId)}">
                                    <div class="box">
                                        <div class="box-body table-responsive no-padding">
                                            <img src="/store/${log.linkByLinkId.fileByFileId.hash}" style="width: 100%;"/>
                                        </div>
                                        <!-- /.box-body -->
                                    </div>
                                </c:if>

                                <c:if test="${formatter.isVideo(log.linkByLinkId.fileByFileId)}">
                                    <div class="box">
                                        <div class="box-body table-responsive no-padding">
                                            <video src="/store/${log.linkByLinkId.fileByFileId.hash}" controls="controls" style="width: 100%;">
                                                您的浏览器不支持 video 标签。
                                            </video>
                                        </div>
                                        <!-- /.box-body -->
                                    </div>
                                </c:if>

                                <c:if test="${formatter.isAudio(log.linkByLinkId.fileByFileId)}">
                                    <div class="box">
                                        <div class="box-body table-responsive no-padding">
                                            <audio src="/store/${log.linkByLinkId.fileByFileId.hash}" controls="controls" style="width: 100%;">
                                                您的浏览器不支持 audio 标签。
                                            </audio>
                                        </div>
                                        <!-- /.box-body -->
                                    </div>
                                </c:if>
                            </c:if>

                            <div class="timeline-body">
                                ${log.operation}
                            </div>
                            <div class="timeline-footer">

                            </div>
                        </div>
                    </li>
                </c:forEach>

            </ul>
        </div>
        <!-- /.col -->
    </div>

</layout:override>

<%@ include file="../master.jsp"%>


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
