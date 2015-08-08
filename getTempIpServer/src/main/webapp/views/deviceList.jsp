<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta content="width=device-width,user-scalable=no" name="viewport">
<meta name="apple-mobile-web-app-capable" content="yes">
<link rel="Stylesheet" href="../css/xk.css" />
<link rel="stylesheet" href="../css/neiye.css" />
<link rel="stylesheet" href="../layer/skin/layer.css" />
<title>设备列表</title>
</head>
<body>
	<div class="ny_content">
		<div class="nr">
			<div class="wjj">
				<div class="name"><span>设备信息</span></div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr class="biaot"><td>设备ID</td><td>设备名称</td><td>设备IP</td><td>登记时间</td></tr>
					<c:choose>
						<c:when test="${empty list}">
							<tr><td colspan="4"><a>暂无设备相关的IP信息</a></td></tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="item" items="${list}" varStatus="status">
								<c:choose>
									<c:when test="${status.count%2 == 0}">
										<tr class="odd">
									</c:when>
									<c:otherwise><tr></c:otherwise>
								</c:choose>
							    	<td><a href="">${item.deviceID}</a></td>
							    	<td><a href="">${item.deviceName}</a></td>
							    	<td><a href="">${item.remoteIP}</a></td>
							    	<td><a href="">${item.timestamp}</a></td>
						    	</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</table>
			</div>
		</div>
	</div>
</body>
</html>