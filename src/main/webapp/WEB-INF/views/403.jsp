<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
			
			<!-- Layout container -->
			<div class="layout-container">
				<!-- Layout content -->
        		<div class="layout-content">
        			<!-- Content -->
         			<div class="container-fluid flex-grow-1 container-p-y">
         				<jsp:useBean id="now" class="java.util.Date"/>    
         				<h4 class="font-weight-bold py-3 mb-4">
			              <spring:message code="heading" />
			              <div class="text-muted text-tiny mt-1"><small class="font-weight-normal"><fmt:formatDate value="${now}" dateStyle="long"/></small></div>
			            </h4>
			            
			            <h1><strong>403</strong></h1>
						<h3><spring:message code="denied" /></h3>
						<p>
							 <spring:message code="deniedmessage" />
						</p>
						
						<a href="<spring:url value="/" htmlEscape="true "/>" class="sidenav-link">
                  			<div><spring:message code="home" /></div>
                		</a>
                		
                		<a href="javascript:history.back()" class="sidenav-link"><div><spring:message code="back" /></div></a>
			            
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
</body>
</html>
