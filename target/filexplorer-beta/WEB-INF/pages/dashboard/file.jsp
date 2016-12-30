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

<layout:override name="modal-name">
    Upload File
</layout:override>

<layout:override name="modal-body">
    <form method="post" action="/files?dir=${currentDir}" enctype="multipart/form-data" id="modalForm">
        <div class="box-body">
            <div class="form-group">
                <label for="file">File input</label>
                <input type="file" id="file" name="file">

            </div>
        </div>
    </form>
</layout:override>

<layout:override name="modal2-name">
    New Directory
</layout:override>

<layout:override name="modal2-body">
    <form method="post" action="/directory?dir=${currentDir}" enctype="multipart/form-data" id="modalForm2">
        <div class="box-body">
            <div class="form-group">
                <div class="form-group">
                    <label for="name">Directory name</label>
                    <input type="name" id="name" name="name" class="form-control" placeholder="Enter name for directory">
                </div>
            </div>
        </div>
    </form>
</layout:override>

<layout:override name="content">

    <div class="col-md-9">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">Files</h3>

                <div class="box-tools">
                    <c:if test="${!deleted}">
                        <button type="button"
                                class="btn btn-info btn-flat btn-sm pull-right" data-toggle="modal" data-target="#addModal2">New Directory</button>
                        <button type="button"
                                class="btn btn-info btn-flat btn-sm pull-right" data-toggle="modal" data-target="#addModal">Upload File</button>
                    </c:if>
                </div>
            </div>


            <!-- /.box-header -->
            <div class="box-body table-responsive no-padding">
                <table class="table table-hover">

                    <tbody><tr>
                        <th>Filename</th>
                        <th>Size</th>
                        <th>Date</th>
                        <th>Operation</th>
                    </tr>

                    <c:if test="${currentDirectoryEntity.name != '/' && currentDirectoryEntity.parentId.id != null}">
                        <tr>
                            <td><a href="/files?dir=${currentDirectoryEntity.parentId.id}"><i class="fa fa-fw fa-chevron-left"></i>Back</a></td>
                            <td> - </td>
                            <td> - </td>
                            <td> - </td>
                        </tr>
                    </c:if>

                    <c:forEach items="${directoryEntityList}" var="directory">
                        <tr>
                            <td>
                                <c:if test="${!deleted}">
                                    <a href="/files?dir=${directory.id}"><i class="fa fa-fw fa-files-o"></i>${directory.name}</a>
                                </c:if>
                                <c:if test="${deleted}">
                                    <i class="fa fa-fw fa-files-o"></i>${directory.name}
                                </c:if>
                            </td>
                            <td> - </td>
                            <td>${directory.createdAt}</td>
                            <td>
                                <%--<a class="btn btn-warning btn-flat btn-xs">Detail</a>--%>

                                <c:if test="${!deleted}">
                                    <!-- Rename just for not-deleted -->
                                    <form action="/directory/${directory.id}?pre=1&dir=${currentDir}" method="post" style="display: inline;">
                                        <input type="hidden" name="_method" value="put"/>
                                        <button type="submit" class="btn btn-primary btn-flat btn-xs">Rename</button>
                                    </form>
                                    <form action="/directory/${directory.id}" method="post" style="display: inline;">
                                        <input type="hidden" name="_method" value="delete"/>
                                        <button type="submit" class="btn btn-danger btn-flat btn-xs">Delete</button>
                                    </form>
                                </c:if>
                                <c:if test="${deleted}">
                                    <form action="/directory/${directory.id}?deleted=1" method="post" style="display: inline;">
                                        <input type="hidden" name="_method" value="put"/>
                                        <button type="submit" class="btn btn-info btn-flat btn-xs">Restore</button>
                                    </form>
                                    <form action="/directory/${directory.id}?forever=1" method="post" style="display: inline;">
                                        <input type="hidden" name="_method" value="delete"/>
                                        <button type="submit" class="btn btn-danger btn-flat btn-xs">Delete forever</button>
                                    </form>
                                </c:if>

                            </td>
                        </tr>
                    </c:forEach>

                    <c:forEach items="${linkEntityList}" var="link">
                        <tr>
                            <td><i class="fa fa-fw fa-file"></i>${link.filename}</td>
                            <td>${link.fileByFileId.size}B</td>
                            <td>${link.createdAt}</td>
                            <td>
                                <%--<a class="btn btn-warning btn-flat btn-xs">Detail</a>--%>

                                    <c:if test="${!deleted}">
                                        <!-- Rename just for not-deleted -->
                                        <form action="/files/${link.id}?pre=1&dir=${currentDir}" method="post" style="display: inline;">
                                            <input type="hidden" name="_method" value="put"/>
                                            <button type="submit" class="btn btn-primary btn-flat btn-xs">Rename</button>
                                        </form>
                                        <form action="/files/${link.id}" method="post" style="display: inline;">
                                            <input type="hidden" name="_method" value="delete"/>
                                            <button type="submit" class="btn btn-danger btn-flat btn-xs">Delete</button>
                                            <a class="btn btn-warning btn-flat btn-xs" href="/files/${link.id}">Download</a>
                                        </form>
                                    </c:if>
                                    <c:if test="${deleted}">
                                        <form action="/files/${link.id}?deleted=1" method="post" style="display: inline;">
                                            <input type="hidden" name="_method" value="put"/>
                                            <button type="submit" class="btn btn-info btn-flat btn-xs">Restore</button>
                                        </form>
                                        <form action="/files/${link.id}?forever=1" method="post" style="display: inline;">
                                            <input type="hidden" name="_method" value="delete"/>
                                            <button type="submit" class="btn btn-danger btn-flat btn-xs">Delete forever</button>
                                        </form>

                                        <!-- Deleted file have no download action -->
                                    </c:if>

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
                        <li><a href="/files"><i class="fa fa-circle-o text-light-blue"></i> In use </a></li>
                        <li><a href="/files?deleted=1"><i class="fa fa-circle text-yellow"></i> Deleted </a></li>
                    </c:if>
                    <c:if test="${!deleted}">
                        <li><a href="/files"><i class="fa fa-circle text-light-blue"></i> In use </a></li>
                        <li><a href="/files?deleted=1"><i class="fa fa-circle-o text-yellow"></i> Deleted </a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>



</layout:override>

<%@ include file="../master.jsp"%>
<%@ include file="../modal.jsp"%>
<%@ include file="../modal2.jsp"%>


