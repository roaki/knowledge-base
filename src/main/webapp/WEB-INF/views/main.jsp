<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html class="no-js fixed-layout">
<head>
    <meta charset="UTF-8">
    <title>博客页面</title>
    <meta name="decorator" content="none">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="alternate icon" type="image/png" href="${ctx}/assets/i/favicon.png">
    <link rel="stylesheet" href="${ctx}/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/app.css"/>
</head>
<body>
<header class="am-topbar">
    <h1 class="am-topbar-brand">
        <a href="/">JOY</a>
    </h1>

    <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only"
            data-am-collapse="{target: '#doc-topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span
            class="am-icon-bars"></span></button>

    <div class="am-collapse am-topbar-collapse" id="doc-topbar-collapse">
        <ul class="am-nav am-nav-pills am-topbar-nav">
            <li class="am-active"><a href="/">首页</a></li>
            <%--<li class="am-dropdown" data-am-dropdown>
              <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
                Java <span class="am-icon-caret-down"></span>
              </a>
              <ul class="am-dropdown-content">
                <li><a href="#">多线程</a></li>
                <li><a href="#">IO</a></li>
                <li><a href="#">Jvm</a></li>
                <li><a href="#">Servlet</a></li>
              </ul>
            </li>
            <li><a href="#">Spring</a></li>--%>



            <li class="am-dropdown" data-am-dropdown>
                <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">click me
                    <span class="am-icon-caret-down"></span></a>
                <ul class="am-dropdown-content">
                    <li><a href="${ctx}/blog/">所有文章</a></li>
                    <li><a href="${ctx}/collect/">我的收藏</a></li>
                </ul>
            </li>

        </ul>

        <div class="am-collapse am-topbar-collapse">
        <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right">
            <li class="am-dropdown" data-am-dropdown>
                <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
                    <span class="am-icon-user"></span>
                    <shiro:principal />
                    <span class="am-icon-caret-down"></span>
                </a>
                <ul class="am-dropdown-content">
                    <li><a href="#"><span class="am-icon-user"></span> 资料</a></li>
                    <li><a href="#"><span class="am-icon-cog"></span> 设置</a></li>
                    <li><a href="${ctx}/logout"><span
                            class="am-icon-power-off"></span> 退出</a></li>
                </ul></li>
        </ul>

        <form class="am-topbar-form am-topbar-right am-form-inline" role="search">
            <div class="am-form-group">
                <input type="text" class="am-form-field am-input-sm" placeholder="搜索">
            </div>
            <button type="submit" class="am-btn am-btn-default am-btn-sm">搜索</button>
        </form>
        </div>


    </div>
</header>

<div class="am-g am-g-fixed blog-g-fixed">
    <div class="am-u-md-8">
        <c:forEach items="${blogs.content}" var="blog">
            <article class="blog-main">
                <h3 class="am-article-title blog-title">
                    <a href="${ctx}/blog/${blog.id}">${blog.title}</a>
                </h3>
                <h4 class="am-article-meta blog-meta"><a
                        href="${ctx}/?criteria_EQ_creator.username=${blog.creator.username}">${blog.creator.username}</a>
                    发表于
                    <fmt:formatDate value="${blog.creationTime}" pattern="yyyy-MM-dd"/>
                    分类: <a href="${ctx}/?criteria_EQ_category.title=${blog.category.title}">${blog.category.title}</a>
                    统计: ${blog.readNum}阅/${blog.voteNum}赞/评论
                </h4>

                <p>标签:
                    <c:forEach items="${blog.tags}" var="tag">
                        <a href="${ctx}/?criteria_EQ_tags.title=${tag.title}">${tag.title}</a> &nbsp;
                    </c:forEach>
                </p>

                <div class="am-g blog-content" style="padding-left: 1.5rem;padding-right: 1.5rem;">
                        ${blog.summary}
                </div>
            </article>
            <hr class="am-article-divider blog-hr">
        </c:forEach>

        <tags:page page="${blogs}" paginationSize="5" showTitle="false"/>
    </div>

    <div class="am-u-md-4 blog-sidebar">
        <div class="am-panel-group">
            <section class="am-panel am-panel-default">
                <div class="am-panel-hd">分类</div>
                <div class="am-panel-bd">
                    <p><c:forEach items="${categories}" var="category"><a
                            href="${ctx}/?criteria_EQ_category.title=${category.title}">${category.title}</a>&nbsp;&nbsp;</c:forEach>
                    </p>
                    <a class="am-btn am-btn-success am-btn-sm" href="#">更多</a>
                </div>
            </section>
            <section class="am-panel am-panel-default">
                <div class="am-panel-hd">标签</div>
                <div class="am-panel-bd">
                    <p><c:forEach items="${tags}" var="tag"><a
                            href="${ctx}/?criteria_EQ_tags.title=${tag.title}">${tag.title}</a>&nbsp;&nbsp;</c:forEach>
                    </p>
                    <a class="am-btn am-btn-success am-btn-sm" href="#">更多</a>
                </div>
            </section>
            <section class="am-panel am-panel-default">
                <div class="am-panel-hd">最新文章</div>
                <ul class="am-list blog-list">
                    <c:forEach items="${lastBlogs.content}" var="blog">
                        <li><a href="${ctx}/blog/${blog.id}">${blog.title}</a></li>
                    </c:forEach>
                </ul>
            </section>

            <section class="am-panel am-panel-default">
                <div class="am-panel-hd">最热文章</div>
                <ul class="am-list blog-list">
                    <c:forEach items="${hotBlogs.content}" var="blog">
                        <li><a href="${ctx}/blog/${blog.id}">${blog.title}</a></li>
                    </c:forEach>
                </ul>

            </section>
        </div>
    </div>

</div>

<footer class="blog-footer">
    <p>
        <small>© Copyright joywifi. by the AmazeUI Team.</small>
    </p>
</footer>

<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="${ctx}/assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="${ctx}/assets/js/jquery.min.js"></script>
<!--<![endif]-->
<script src="${ctx}/assets/js/amazeui.min.js"></script>

</body>
</html>
