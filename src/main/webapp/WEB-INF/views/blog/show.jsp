<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html class="no-js fixed-layout">
<head>
<meta charset="UTF-8">
<title>${blog.title}</title>
<meta name="decorator" content="none">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate icon" type="image/png" href="${ctx}/assets/i/favicon.png">
<link rel="stylesheet" href="${ctx}/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="${ctx}/assets/css/app.css" />

</head>
<body>
	<header class="am-topbar">
		<h1 class="am-topbar-brand">
			<a href="/">JOY</a>
		</h1>

		<button
			class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only"
			data-am-collapse="{target: '#doc-topbar-collapse'}">
			<span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span>
		</button>

		<div class="am-collapse am-topbar-collapse" id="doc-topbar-collapse">
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

			<form class="am-topbar-form am-topbar-left am-form-inline"
				role="search">
				<div class="am-form-group">
					<input type="text" class="am-form-field am-input-sm"
						placeholder="搜索文章">
				</div>
				<button type="submit" class="am-btn am-btn-default am-btn-sm">搜索</button>
			</form>

		</div>
	</header>

	<div class="am-g am-g-fixed blog-g-fixed">
		<div class="am-u-md-8">
			<article class="blog-main">
				<h3 class="am-article-title blog-title">
					<a href="${ctx}/blog/${blog.id}">${blog.title}</a>
				</h3>
				<h4 class="am-article-meta blog-meta">
					<a href="${ctx}/?criteria_EQ_creator.username=${blog.creator.username}">${blog.creator.username}</a>
					发表于
					<fmt:formatDate value="${blog.creationTime}" pattern="yyyy-MM-dd" />
					分类: <a href="${ctx}/?criteria_EQ_category.title=${blog.category.title}">${blog.category.title}</a>
					统计: ${blog.readNum}阅/
					
					<span id="voteNum">${blog.voteNum}</span><a id="vote" data="${blog.id}" href="javascript:void(0);">赞</a>
					/评论 
					<a id="collect" data="${blog.id}" href="javascript:void(0);">${collect}</a>
				</h4>
				<p>
					标签:
					<c:forEach items="${blog.tags}" var="tag">
						<a href="${ctx}/?criteria_EQ_tags.title=${tag.title}">${tag.title}</a> &nbsp;
          			</c:forEach>
				</p>

				<div class="am-g blog-content"
					style="padding-left: 1.5rem; padding-right: 1.5rem;">
					${blog.content}
					<hr />
					<ul class="am-comments-list">
						<li class="am-comment"><a href="#link-to-user-home"> <img
								src="http://s.amazeui.org/media/i/demos/bw-2014-06-19.jpg?imageView/1/w/96/h/96/q/80"
								alt="" class="am-comment-avatar" width="48" height="48">
						</a>
							<div class="am-comment-main">
								<header class="am-comment-hd">
									<div class="am-comment-meta">
										<a href="#link-to-user" class="am-comment-author">某人</a> 评论于
										<time datetime="2013-07-27T04:54:29-07:00"
											title="2013年7月27日 下午7:54 格林尼治标准时间+0800">2014-7-12
											15:30</time>
									</div>
								</header>
								<div class="am-comment-bd">
									<p>《永远的蝴蝶》一文，还吸收散文特长，多采用第一人称，淡化情节，体现一种思想寄托和艺术追求。</p>
								</div>
							</div></li>
					</ul>
				</div>
			</article>
		</div>

		<div class="am-u-md-4 blog-sidebar">
			<div class="am-panel-group">
				<section class="am-panel am-panel-default">
					<div class="am-panel-hd">关于我</div>
					<div class="am-panel-bd">
						<p>前所未有的中文云端字型服务，让您在 web
							平台上自由使用高品质中文字体，跨平台、可搜寻，而且超美。云端字型是我们的事业，推广字型学知识是我们的志业。从字体出发，关心设计与我们的生活，justfont
							blog 正是為此而生。</p>
						<a class="am-btn am-btn-success am-btn-sm" href="#">查看更多 →</a>
					</div>
				</section>
				<section class="am-panel am-panel-default">
					<div class="am-panel-hd">文章目录</div>
					<ul class="am-list blog-list">
						<li><a href="#">Google fonts 的字體（sans-serif 篇）</a></li>
						<li><a href="#">[but]服貿最前線？－再訪桃園機場</a></li>
						<li><a href="#">到日星鑄字行學字型</a></li>
						<li><a href="#">glyph font vs. 漢字（上）</a></li>
						<li><a href="#">浙江民間書刻體上線</a></li>
						<li><a href="#">[極短篇] Android v.s iOS，誰的字體好讀？</a></li>
					</ul>
				</section>

				<section class="am-panel am-panel-default">
					<div class="am-panel-hd">团队成员</div>
					<div class="am-panel-bd">
						<ul class="am-avg-sm-4 blog-team">
							<li><img class="am-thumbnail"
								src="http://img4.duitang.com/uploads/blog/201406/15/20140615230220_F5LiM.thumb.224_0.jpeg"
								alt="" /></li>
							<li><img class="am-thumbnail"
								src="http://img4.duitang.com/uploads/blog/201406/15/20140615230220_F5LiM.thumb.224_0.jpeg"
								alt="" /></li>
							<li><img class="am-thumbnail"
								src="http://img4.duitang.com/uploads/blog/201406/15/20140615230220_F5LiM.thumb.224_0.jpeg"
								alt="" /></li>
							<li><img class="am-thumbnail"
								src="http://img4.duitang.com/uploads/blog/201406/15/20140615230220_F5LiM.thumb.224_0.jpeg"
								alt="" /></li>
							<li><img class="am-thumbnail"
								src="http://img4.duitang.com/uploads/blog/201406/15/20140615230159_kjTmC.thumb.224_0.jpeg"
								alt="" /></li>
							<li><img class="am-thumbnail"
								src="http://img4.duitang.com/uploads/blog/201406/15/20140615230220_F5LiM.thumb.224_0.jpeg"
								alt="" /></li>
							<li><img class="am-thumbnail"
								src="http://img4.duitang.com/uploads/blog/201406/15/20140615230220_F5LiM.thumb.224_0.jpeg"
								alt="" /></li>
							<li><img class="am-thumbnail"
								src="http://img4.duitang.com/uploads/blog/201406/15/20140615230159_kjTmC.thumb.224_0.jpeg"
								alt="" /></li>
						</ul>
					</div>
				</section>
			</div>
		</div>

	</div>

	<footer class="blog-footer">
		<p>
			blog template<br /> <small>© Copyright XXX. by the AmazeUI
				Team.</small>
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

<script type="text/javascript">

	$(document).ready(function() {
		$("#collect").click(function() {
		var blogId = $(this).attr("data");
		console.log($("#collect").text());
		if ($("#collect").text() == "收藏") {
			$.get("${ctx}/collect/create?blogId=" + blogId, function(msg) {
				if(msg == "success"){
					$("#collect").text("取消收藏");
				}
			});
		}else {
			$.get("${ctx}/collect/delete?blogId=" + blogId, function(msg) {
				if(msg == "success"){
					$("#collect").text("收藏");
				}
			});
		}
		});
		
		$("#vote").click(function() {
			var blogId = $(this).attr("data");
			$.get("${ctx}/vote/create?blogId=" + blogId, function(msg) {
				if (msg == "success") {
					$("#voteNum").html(parseInt($("#voteNum").html()) + 1);
				} else {
					alert("您已赞过此博文！");
				}
			});
		});
		
	});

</script>
</body>
</html>
