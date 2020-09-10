<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="default-style">

<head>
  <jsp:include page="fragments/headTag.jsp" />  
</head>

<body>
  <div class="page-loader">
    <div class="bg-primary"></div>
  </div>
  <!-- Content -->
  <div class="authentication-wrapper authentication-1 px-4">
    <div class="authentication-inner py-5">
      <!-- Logo -->
      <div class="d-flex justify-content-center align-items-center">
        <div class="ui-w-70">
        	<spring:url value="/resources/img/logo.png" var="logo" />
			<img src="${logo}" alt="<spring:message code="login" />" />
        </div>
      </div>
      <!-- / Logo -->
      <!-- Form -->
      <form class="login-form my-5" action="j_spring_security_check" method="post">
        <div class="form-group">
          <label class="form-label"><spring:message code="login.username" /></label>
          <input type="text" name="j_username" class="form-control">
        </div>	
		<c:if test="${not empty error}">
			<div class="alert-danger">
				<c:choose>
					<c:when test="${SPRING_SECURITY_LAST_EXCEPTION.message=='Bad credentials'}">
						<spring:message  code="login.badCredentials" />
					</c:when>
					<c:when test="${SPRING_SECURITY_LAST_EXCEPTION.message=='User account is locked'}">
						<spring:message  code="login.accountLocked" />
					</c:when>
					<c:when test="${SPRING_SECURITY_LAST_EXCEPTION.message=='User account has expired'}">
						<spring:message  code="login.accountExpired" />
					</c:when>
					<c:when test="${SPRING_SECURITY_LAST_EXCEPTION.message=='User credentials have expired'}">
						<spring:message  code="login.credentialsExpired" />
					</c:when>
					<c:when test="${SPRING_SECURITY_LAST_EXCEPTION.message=='User is disabled'}">
						<spring:message  code="login.userDisabled" />
					</c:when>
					<c:when test="${SPRING_SECURITY_LAST_EXCEPTION.message=='Maximum sessions of 1 for this principal exceeded'}">
						<spring:message  code="login.maxSessionsOut" />
					</c:when>
					<c:otherwise>
						${SPRING_SECURITY_LAST_EXCEPTION.message}
					</c:otherwise>
				</c:choose>
			</div>
		</c:if>
        <div class="form-group">
          <label class="form-label d-flex justify-content-between align-items-end">
            <div><spring:message code="login.password" /></div>
            <a href="#" class="d-block small"></a>
          </label>
          <input type="password" name="j_password" class="form-control">
        </div>
        <div class="d-flex justify-content-between align-items-center m-0">
          <label class="custom-control custom-checkbox m-0">
            
          </label>
          <button type="submit" class="btn btn-primary"><spring:message code="login" /></button>
        </div>
        <div class="d-flex  align-items-left m-0">
        	<a href="?lang=en" class="d-block small actEng"> English </a>&nbsp&nbsp|&nbsp&nbsp<a href="?lang=es" class="d-block small actEsp"> Español </a>&nbsp&nbsp|&nbsp&nbsp<a href="javascript:cargarAyuda()" class="d-block small"><i class="ion ion-md-help"></i></a>
        </div>
      </form>
      <!-- / Form -->
		
      <div class="text-center text-muted">
        
      </div>

    </div>
  </div>

  <!-- / Content -->
  <!-- Bootstrap and necessary plugins -->
  <jsp:include page="fragments/corePlugins.jsp" />
  
  <!-- Lenguaje -->
	<c:choose>
	<c:when test="${cookie.eSampleManagerLang.value == null}">
		<c:set var="lenguaje" value="en"/>
	</c:when>
	<c:otherwise>
		<c:set var="lenguaje" value="${cookie.eSampleManagerLang.value}"/>
	</c:otherwise>
	</c:choose>
	
	<spring:url value="/resources/help/login_{language}.html" var="helpUrl">
  		<spring:param name="language" value="${lenguaje}" />
  	</spring:url>
  
  
  <script>
	$(".actEng").click(function(){ 
		var d = new Date();
	    d.setTime(d.getTime() + (365*24*60*60*1000));
	    var expires = "expires="+d.toUTCString();
		document.cookie="eSampleManagerLang=en;"+expires+"; path=/samplemanager/";
		location.reload();
        });
	$(".actEsp").click(function(){ 
		var d = new Date();
		d.setTime(d.getTime() + (365*24*60*60*1000));
	    var expires = "expires="+d.toUTCString();
		document.cookie="eSampleManagerLang=es;"+expires+"; path=/samplemanager/";
		location.reload();
        });
	function cargarAyuda(){ 
		window.open("${helpUrl}");
	}
	
	var e=null;
	e=localStorage.getItem("themeSettingsTheme");
	
	if(e==null){
		localStorage.setItem("themeSettingsTheme","theme-shadow");
	}
  </script>
  </body>
</html>