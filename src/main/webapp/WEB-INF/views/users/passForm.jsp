<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="default-style">
<head>
  <jsp:include page="../fragments/headTag.jsp" />
</head>
<body>
	<div class="page-loader">
    	<div class="bg-primary"></div>
  	</div>
  	<!-- urls -->
    <spring:url value="/users/chgPass/" var="saveUserUrl"></spring:url>
 	<spring:url value="/users/profile" var="usuarioUrl"></spring:url>
 	<spring:url value="/logout" var="salirUrl"></spring:url>	
	<!-- Layout wrapper -->
	<div class="layout-wrapper layout-2">
		<div class="layout-inner">
			<jsp:include page="../fragments/sideNav.jsp" />
			<!-- Layout container -->
			<div class="layout-container">
				<!-- Layout navbar -->
				<nav class="layout-navbar navbar navbar-expand-lg align-items-lg-center bg-white container-p-x" id="layout-navbar">
					<jsp:include page="../fragments/navHeader.jsp" />
					<jsp:include page="../fragments/navHeader2.jsp" />
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
			            <div class="card mb-4" id="newuser-element">
			              <h6 class="card-header with-elements">
			              	<div class="card-title-elements">
			              	</div>
			                <div class="card-header-title"><spring:message code="changepass" /></div>
			              </h6>
			              <div class="row no-gutters row-bordered">
			                <div class="col-md-12 col-lg-12 col-xl-12">
			                  <div class="card-body demo-vertical-spacing">
			                  		<div class="row">

				                    <div class="col-md-8">
				                      <form action="#" autocomplete="off" id="add-user-form">       
				                      	
				                      	<div class="form-group">
				                          <div>
				                            <label>
				                              <spring:message code="Pattern.password.format" />
				                            </label>
				                          </div>
				                        </div>               
					                    
										<div class="form-group">
					                      <div class="input-group">
					                      	<div class="input-group-prepend">
							                  <span class="input-group-text"><i class="fa fa-asterisk"></i></span>
							                </div>
					                        <input type="password" autocomplete="new-password" id="password" name="password" class="form-control" placeholder="<spring:message code="login.password" />">
					                      </div>
					                    </div> 
					                    <div class="form-group">
					                      <div class="input-group">
					                        <div class="input-group-prepend">
							                  <span class="input-group-text"><i class="fa fa-key"></i></span>
							                </div>
					                        <input type="password" autocomplete="new-password" id="confirm_password" name="confirm_password" class="form-control" placeholder="<spring:message code="password.repeat" />">
					                      </div>
					                    </div>

				                        <div class="form-group">
				                          <button type="submit" class="btn btn-primary" id="guardar"><i class="fa fa-save"></i>&nbsp;<spring:message code="save" /></button>
										  <a href="${fn:escapeXml(usuarioUrl)}" class="btn btn-danger"><i class="fa fa-undo"></i>&nbsp;<spring:message code="cancel" /></a>
				                        </div>
				                      </form>
				                    </div>
				                  </div>
			                  
			                  </div>
			                </div>
			
			              </div>
			            </div>
         			</div>
         			<!-- / Content -->
         			<jsp:include page="../fragments/navFooter.jsp" />
        		</div>
        		<!-- Layout content -->
			</div>
			<!-- / Layout container -->
    	</div>
    </div>  	
    <!-- / Layout wrapper -->
	
  	<!-- Bootstrap and necessary plugins -->
  	<jsp:include page="../fragments/corePlugins.jsp" />
  	<jsp:include page="../fragments/bodyUtils.jsp" />
  	
  	<!-- Lenguaje -->
	<c:choose>
	<c:when test="${cookie.eSampleManagerLang.value == null}">
		<c:set var="lenguaje" value="en"/>
	</c:when>
	<c:otherwise>
		<c:set var="lenguaje" value="${cookie.eSampleManagerLang.value}"/>
	</c:otherwise>
	</c:choose>

  <!-- Custom scripts required by this view -->
  <spring:url value="/resources/js/views/User.js" var="processUser" />
  <script src="${processUser}"></script>
  
  <spring:url value="/resources/vendor/libs/validate/messages_{language}.js" var="validateLang">
	<spring:param name="language" value="${lenguaje}" />
  </spring:url>
  <script src="${validateLang}"></script>
  

  
  <!-- Mensajes -->

  	<c:set var="seleccionar"><spring:message code="select" /></c:set>
  	<c:set var="successmessage"><spring:message code="process.success" /></c:set>
  	<c:set var="errormessage"><spring:message code="process.errors" /></c:set>
  	<c:set var="waitmessage"><spring:message code="process.wait" /></c:set>

	<script>
	jQuery(document).ready(function() {
		var parametros = {saveUserUrl: "${saveUserUrl}", successmessage: "${successmessage}",
				errormessage: "${errormessage}",waitmessage: "${waitmessage}",
				usuarioUrl: "${salirUrl}" ,seleccionar: "${seleccionar}" 
		};
		ProcessUser.init(parametros);
	});
	</script>
</body>
</html>
