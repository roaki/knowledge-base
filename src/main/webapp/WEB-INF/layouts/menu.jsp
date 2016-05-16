<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
  <div class="am-offcanvas-bar admin-offcanvas-bar">
    <ul class="am-list admin-sidebar-list am-nav">
      <li><a href="${ctx}/main/"><span class="am-icon-home"></span> 首页</a></li>

      <tags:hasAnyPermissions name="user:list,role:list,authority:list">
        <li>
          <a class="am-cf" data-am-collapse="{target: '#collapse-nav-user'}">
            <span class="am-icon-file"></span> 用户管理<span class="am-icon-angle-right am-fr am-margin-right"></span></a>
          <ul class="am-list am-collapse admin-sidebar-sub ${menu=='user'?'am-in':''}" id="collapse-nav-user">
            <shiro:hasPermission name="user:list">
              <li class="${subMenu=='user'?'am-active':''}"><a href="${ctx}/user/" class="am-cf"><span
                      class="am-icon-users"></span> 用户</a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="role:list">
              <li class="${subMenu=='role'?'am-active':''}"><a href="${ctx}/role/"><span class="am-icon-gear"></span>
                角色</a>
              </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="authority:list">
              <li class="${subMenu=='authority'?'am-active':''}"><a href="${ctx}/authority/"><span
                      class="am-icon-trello"></span>
                权限</a></li>
            </shiro:hasPermission>
          </ul>
        </li>
      </tags:hasAnyPermissions>

      <shiro:hasPermission name="category:list">
        <li class="${menu=='category'?'am-active':''}"><a href="${ctx}/category/"><span class="am-icon-server"></span>
          分类</a>
        </li>
      </shiro:hasPermission>
      <shiro:hasPermission name="tag:list">
        <li class="${menu=='tag'?'am-active':''}"><a href="${ctx}/tag/"><span class="am-icon-table"></span>
          标签</a>
        </li>
      </shiro:hasPermission>
      <shiro:hasPermission name="blog:list">
        <li class="${menu=='blog'?'am-active':''}"><a href="${ctx}/blog/"><span class="am-icon-table"></span>
          文章</a>
        </li>
      </shiro:hasPermission>
      <shiro:hasPermission name="collect:list">
        <li class="${menu=='collect'?'am-active':''}"><a href="${ctx}/collect/"><span class="am-icon-table"></span>
          我的收藏</a>
        </li>
      </shiro:hasPermission>
      <li><a href="${ctx}/logout"><span class="am-icon-sign-out"></span> 退出</a></li>
    </ul>


    <div class="am-panel am-panel-default admin-sidebar-panel">
      <div class="am-panel-bd">
        <p><span class="am-icon-tag"></span> wiki</p>

        <p>Welcome to the Amaze UI wiki!</p>
      </div>
    </div>
  </div>
</div>
