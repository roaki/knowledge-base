<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<meta name="menu" content="tag" />

<div class="am-cf am-padding">
  <div class="am-fl am-cf"><a href="${ctx}/tag/"> <strong class="am-text-primary am-text-lg">标签</strong></a> /
    <small>新建标签</small>
  </div>
</div>

<hr/>

<div class="am-g">
  <div class="am-u-sm-12 am-u-md-8">
    <form class="am-form am-form-horizontal" action="${ctx}/tag/create" method="post">
      <div class="am-form-group">
        <label for="title" class="am-u-sm-3 am-form-label">名称</label>
        <div class="am-u-sm-9">
          <input type="text" id="title" name="title" value="${tag.title}" required />
        </div>
      </div>

      <div class="am-form-group">
        <div class="am-u-sm-9 am-u-sm-push-3">
          <button type="submit" class="am-btn am-btn-primary">保存</button>
        </div>
      </div>
    </form>
  </div>
</div>