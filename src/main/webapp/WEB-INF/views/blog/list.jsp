<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<meta name="menu" content="blog" />

<div class="am-cf am-padding">
  <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">博客</strong> /
    <small>列表</small>
  </div>
</div>

<c:if test="${not empty msg}">
  <div class="am-g"><p class="am-text-success am-text-center">${msg}</p></div>
</c:if>

<div class="am-g">
  <form action="${ctx}/blog/" class="search">

    <div class="am-u-sm-12 am-u-md-3">
      <div class="am-btn-toolbar">
        <div class="am-btn-group am-btn-group-xs">
          <a type="button" class="am-btn am-btn-default" href="${ctx}/blog/new"><span class="am-icon-plus"></span>
            新增</a>
        </div>
      </div>
    </div>

    <div class="am-u-sm-12 am-u-md-3">
      <div class="am-input-group am-input-group-sm">
        <input type="text" name="criteria_LIKE_ip" value="${param.criteria_LIKE_ip}"
               class="am-form-field">
            <span class="am-input-group-btn">
              <button class="am-btn am-btn-default" type="submit">搜索</button>
            </span>
      </div>
    </div>

  </form>
</div>

<div class="am-g">
  <div class="am-u-sm-12">
    <form class="am-form">
      <table class="am-table am-table-striped am-table-hover table-main">
        <thead>
        <tr>
          <th class="table-check"><input type="checkbox"/></th>
          <th class="table-id">id</th>
          <th class="table-title">博客名称</th>
          <th class="table-author am-hide-sm-only">创建日期</th>
          <th class="table-date am-hide-sm-only">修改日期</th>
          <th class="table-set">操作</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${blogs.content}" var="blog" varStatus="status">
          <tr>
            <td><input type="checkbox"/></td>
            <td>${status.index + 1}</td>
            <td><a href="${ctx}/blog/${blog.id}">${blog.title}</a></td>
            <td class="am-hide-sm-only">
              <fmt:formatDate value="${blog.creationTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td class="am-hide-sm-only">
              <fmt:formatDate value="${blog.modifiedTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>
              <div class="am-btn-toolbar">
                <div class="am-btn-group am-btn-group-xs">
                  <a class="am-btn am-btn-default am-btn-xs am-text-secondary" href="${ctx}/blog/edit/${blog.id}"><span
                          class="am-icon-pencil-square-o"></span> 编辑
                  </a>
                  <a class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only delete-model"
                     data-href="${ctx}/blog/delete/${blog.id}" href="javascript:void(0);"><span
                          class="am-icon-trash-o"></span> 删除
                  </a>
                </div>
              </div>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
      <tags:page page="${blogs}" paginationSize="5"/>
    </form>
  </div>
</div>
