<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<meta name="menu" content="blog"/>

<head>
  <link rel="stylesheet" type="text/css" href="${ctx}/assets/js/jquery.tagsinput/jquery.tagsinput.css"/>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
</head>

<div class="am-cf am-padding">
  <div class="am-fl am-cf"><a href="${ctx}/blog/"> <strong class="am-text-primary am-text-lg">博客</strong></a> /
    <small>新建博客</small>
  </div>
</div>

<hr/>

<div class="am-g">
  <div class="am-u-sm-12 am-u-md-10">
    <form class="am-form" action="${ctx}/blog/create" method="post">

      <div class="am-form-group">
        <label for="blog-title">标题</label>
        <input type="text" id="blog-title" name="title" required/>
      </div>

      <div class="am-form-group">
        <label for="blog-summary">摘要</label>
        <textarea class="" name="summary" rows="4" id="blog-summary"></textarea>
      </div>

      <div class="am-form-group">
        <label for="blog-categoryTitle">分类</label>
        <select id="blog-categoryTitle" name="categoryTitle">
          <c:forEach items="${categories}" var="category">
            <option value="${category.title}" <c:if test="${category.title eq blog.category.title}">selected</c:if>>${category.title}</option>
          </c:forEach>
        </select>
      </div>

      <div class="am-form-group">
        <label for="blog-tagTitles">标签</label>
        <input type="text" id="blog-tagTitles" name="tagTitles" required/>
      </div>

      <div class="am-form-group">
        <label for="blog-content">内容</label>
        <script id="blog-content" name="content" type="text/plain"></script>
      </div>

      <p><button type="submit" class="am-btn am-btn-primary">提交</button></p>
    </form>
  </div>
</div>

<content tag="script">
  <script type="text/javascript" src="${ctx}/assets/js/ueditor/ueditor.config.js"></script>
  <script type="text/javascript" src="${ctx}/assets/js/ueditor/ueditor.all.min.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <script type="text/javascript" src="${ctx}/assets/js/jquery.tagsinput/jquery.tagsinput.js"></script>
  <script>
    $(document).ready(function () {
      var ue = UE.getEditor('blog-content', {
        toolbars: [[
          'fullscreen',
          'source',
          '|',
          'bold',
          'italic',
          'underline',
          'blockquote',
          '|',
          'forecolor',
          'backcolor',
          'insertorderedlist',
          'insertunorderedlist',
          '|',
          'paragraph',
          'fontfamily',
          'fontsize',
          '|',
          'justifyleft',
          'justifycenter',
          'justifyright',
          '|',
          'link',
          'unlink',
          '|',
          'insertimage',
          'emotion',
          //'insertvideo',
          //'music',
          'insertcode',
          'horizontal',
          'inserttable',
          '|',
          'help'
        ]], labelMap: {
          'anchor': '', 'undo': ''
        },
        'fontsize': [10, 11, 12, 14, 16, 18, 20, 24],
        contextMenu: [],
        zIndex: '100',
        autoHeightEnabled: true,
        autoFloatEnabled: true,
        initialFrameHeight: 320
      });
      $('#blog-tagTitles').tagsInput({
        'autocomplete_url': '${ctx}/tag/add-blog-tags',
        'autocomplete': {selectFirst: true, width: '100px', autoFill: true},
        'height': '39px',
        'width': '100%',
        'defaultText': '添加标签'
      });
    });
  </script>
</content>