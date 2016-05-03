<%@ page language="java" pageEncoding="UTF-8" %>
<meta name="menu" content="user" />
<meta name="subMenu" content="role" />

<div class="am-cf am-padding">
  <div class="am-fl am-cf"><a href="${ctx}/server/"> <strong class="am-text-primary am-text-lg">角色</strong></a> /
    <small>修改角色</small>
  </div>
</div>

<hr/>

<div class="am-g">
  <div class="am-u-sm-12 am-u-md-8">
    <form class="am-form am-form-horizontal" action="${ctx}/server/update/${role.id}" method="post">

      <div class="am-form-group">
        <label for="server-loginName" class="am-u-sm-3 am-form-label">角色名</label>

        <div class="am-u-sm-9">
          <input type="text" id="server-loginName" name="loginName" value="${role.roleName}" required />
        </div>
      </div>

      <div class="am-form-group">
        <label for="server-loginPwd" class="am-u-sm-3 am-form-label">角色别名</label>

        <div class="am-u-sm-9">
          <input type="text" id="server-loginPwd" name="loginPwd" value="${role.aliasName}" required />
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
