<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="default-style">
<head>
  <jsp:include page="fragments/headTag.jsp" />
</head>
<body>
	<div class="page-loader">
    	<div class="bg-primary"></div>
  	</div>
  	
	<!-- Layout wrapper -->
	<div class="layout-wrapper layout-2">
		<div class="layout-inner">
			<jsp:include page="fragments/sideNav.jsp" />
			<!-- Layout container -->
			<div class="layout-container">
				<!-- Layout navbar -->
				<nav class="layout-navbar navbar navbar-expand-lg align-items-lg-center bg-white container-p-x" id="layout-navbar">
					<jsp:include page="fragments/navHeader.jsp" />
					<jsp:include page="fragments/navHeader2.jsp" />
				</nav>
				<!-- / Layout navbar -->
				<!-- Layout content -->
        		<div class="layout-content">
        			<!-- Content -->
         			<div class="container-fluid flex-grow-1 container-p-y">
         				<jsp:useBean id="now" class="java.util.Date"/>    
         				<h4 class="font-weight-bold py-3 mb-4">
			              <spring:message code="heading" />
			              <div class="text-muted text-tiny mt-1"><small class="font-weight-normal"><fmt:formatDate value="${now}" dateStyle="long"/></small></div>
			            </h4>
			            <spring:url value="/capture/specimens/newEntity/" var="newEntity"/>
			            <div class="demo-vertical-spacing">
			              <ul class="nav nav-pills nav-fill">
			                <li class="nav-item">
			                  <a class="nav-link active" href="${fn:escapeXml(newEntity)}"><spring:message code="add" /> <spring:message code="specimen" />
			                    <span class="badge badge-primary"></span>
			                  </a>
			                </li>
			              </ul>
			            
			            </div>
			            
         			</div>
         			<!-- / Content -->
         			<jsp:include page="fragments/navFooter.jsp" />
        		</div>
        		<!-- Layout content -->
			</div>
			<!-- / Layout container -->
    	</div>
    </div>  	
    <!-- / Layout wrapper -->
	
  	<!-- Bootstrap and necessary plugins -->
  	<jsp:include page="fragments/corePlugins.jsp" />
  	<jsp:include page="fragments/bodyUtils.jsp" />
  	
  	<!-- Lenguaje -->
	<c:choose>
	<c:when test="${cookie.eSampleManagerLang.value == null}">
		<c:set var="lenguaje" value="en"/>
	</c:when>
	<c:otherwise>
		<c:set var="lenguaje" value="${cookie.eSampleManagerLang.value}"/>
	</c:otherwise>
	</c:choose>

	<spring:url value="/resources/help/home.html" var="helpUrl"/>
	<script>
		jQuery(document).ready(function() {
	    	$("li.home").addClass("open");
	    	$("li.home").addClass("active");
	    	$("li.home1").addClass("active");
	    });
		function cargarAyuda(){ 
			window.open("${helpUrl}");
		}
	</script>
</body>
</html>
