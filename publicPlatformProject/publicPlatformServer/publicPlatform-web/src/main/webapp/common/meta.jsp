<!DOCTYPE HTML>
<%@ page isELIgnored="false" %> 
<%@ page language="java"  pageEncoding="utf-8"%>
<%@page import="org.springframework.context.annotation.Import"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="javax.servlet.http.*" %>
<c:set var="ctx" value="/alipay/hc${pageContext.request.contextPath}"/>

<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
response.setDateHeader("Expires",-10);
%>
