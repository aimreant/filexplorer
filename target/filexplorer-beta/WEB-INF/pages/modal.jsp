<%--
  Created by IntelliJ IDEA.
  User: aimreant
  Date: 11/28/16
  Time: 5:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" uri="http://www.dreamlu.net/tags/jsp-layout.tld" %>

<!-- 弹出层插件 -->
<div class="modal fade" id="addModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">
                    <layout:block name="modal-name"></layout:block>
                </h4>
            </div>
            <div class="modal-body">
                <layout:block name="modal-body"></layout:block>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary" form="modalForm">Submit</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
