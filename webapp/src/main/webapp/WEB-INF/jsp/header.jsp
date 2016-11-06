<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<link href="https://fonts.googleapis.com/css?family=Maitree" rel="stylesheet"> 
	<link rel="stylesheet" href="<c:url value= "/resources/css/style.css" /> ">
	<link rel="icon" href="<c:url value= "/resources/images/icon.png" />" type="image/x-icon">
	<link rel="shortcut icon" href="<c:url value= "/resources/images/icon.png" />" type="image/x-icon">
</head>

<body class="back-image">

        <nav class="navbar navbar-default" role="navigation">
    	  <div class="container">
		    <!-- Brand and toggle get grouped for better mobile display -->
		    <div class="navbar-header">
		      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-brand-centered">
		        <span class="sr-only">Toggle navigation</span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		      </button>
		      <div class="navbar-brand navbar-brand-centered"><a href="<c:url value="/"/>">PawnsAtWar</a></div>
		    </div>

		    <!-- Collect the nav links, forms, and other content for toggling -->
		    <div class="collapse navbar-collapse" id="navbar-brand-centered">
		      <ul class="nav navbar-nav">
		        <li><a href="<c:url value="/map"/>"><spring:message code="header.map"/></a></li>
		        <li><a href="<c:url value="/armies"/>"><spring:message code="header.army"/></a></li>
		        <li><a href="<c:url value="/commerce"/>"><spring:message code="header.commerce"/></a></li>
		      </ul>
		      <ul class="nav navbar-nav navbar-right">
				  <li><a href="<c:url value="/messages"/>"><spring:message code="header.messages"/></a></li>
		        <li><a href="<c:url value="/logout"/>"><spring:message code="header.logout"/></a></li>
		      </ul>
		    </div><!-- /.navbar-collapse -->
		  </div><!-- /.container-fluid -->
		</nav>
