<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="default-style">
<head>
  <jsp:include page="../../fragments/headTag.jsp" />
</head>
<body>
	<div class="page-loader">
    	<div class="bg-primary"></div>
  	</div>
  	<!-- urls -->
    <spring:url value="/admin/racks/saveEntity/" var="saveUrl"></spring:url>
    <spring:url value="/admin/racks/" var="listUrl"></spring:url>	
	<!-- Layout wrapper -->
	<div class="layout-wrapper layout-2">
		<div class="layout-inner">
			<jsp:include page="../../fragments/sideNav.jsp" />
			<!-- Layout container -->
			<div class="layout-container">
				<!-- Layout navbar -->
				<nav class="layout-navbar navbar navbar-expand-lg align-items-lg-center bg-white container-p-x" id="layout-navbar">
					<jsp:include page="../../fragments/navHeader.jsp" />
					<jsp:include page="../../fragments/navHeader2.jsp" />
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
			            <div class="card mb-4" id="edit-element">
			              <h6 class="card-header with-elements">
			              	<div class="card-title-elements">
			              	</div>
			                <div class="card-header-title"><spring:message code="racks" /></div>
			              </h6>
			              <div class="row no-gutters row-bordered">
			                <div class="col-md-12 col-lg-12 col-xl-12">
			                  <div class="card-body demo-vertical-spacing">
			                  		<div class="row">

				                    <div class="col-md-8">
				                      <form action="#" autocomplete="off" id="edit-form">
				                      	<div class="form-group">
					                      <div class="input-group">
					                      	<div class="input-group-prepend">
							                  <span class="input-group-text"><i class="fa fa-key"></i></span>
							                </div>
					                        <input type="text" id="systemId" name="systemId" readonly value="${entidad.systemId}"  
					                        		data-toggle="tooltip" data-placement="right" title="<spring:message code="systemId" />" 
					                        				class="form-control" placeholder="<spring:message code="systemId" />">
					                      </div>
					                    </div>                      
										<div class="form-group">
					                      <div class="input-group">
					                      	<div class="input-group-prepend">
							                  <span class="input-group-text"><i class="fa fa-key"></i></span>
							                </div>
					                        <input type="text" id="id" name="id" value="${entidad.id}"  
					                        	data-toggle="tooltip" data-placement="right" title="<spring:message code="id" />" 
					                        		class="form-control" placeholder="<spring:message code="id" />">
					                      </div>
					                    </div>	
					                    <div class="form-group">
					                      <div class="input-group">
					                      	<div class="input-group-prepend">
							                  <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
							                </div>
					                        <input type="text" id="name" name="name" value="${entidad.name}"  
					                        	data-toggle="tooltip" data-placement="right" title="<spring:message code="name" />" 
					                        		class="form-control" placeholder="<spring:message code="name" />">
					                      </div>
					                    </div>
					                    <div class="form-group">
						                	<select class="select2-control form-control" id="equip" name="equip" style="width: 100%" data-allow-clear="false">
						                		<option value=""></option>
					                        	<c:forEach items="${equips}" var="equip">
													<c:choose> 
														<c:when test="${equip.systemId eq entidad.equip.systemId}">
															<option selected value="${equip.systemId}"><spring:message code="${equip.name}" /></option>
														</c:when>
														<c:otherwise>
															<option value="${equip.systemId}"><spring:message code="${equip.name}" /></option>
														</c:otherwise>
													</c:choose> 
												</c:forEach>
						                	</select>
					                    </div>
					                    
					                    <div class="form-group">
					                      <div class="input-group">
					                      	<div class="input-group-prepend">
							                  <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
							                </div>
					                        <input type="text" id="obs" name="obs" value="${entidad.obs}"  
					                        	data-toggle="tooltip" data-placement="right" title="<spring:message code="obs" />" 
					                        		class="form-control" placeholder="<spring:message code="obs" />">
					                      </div>
					                    </div>
       
				                        <div class="form-group">
				                          <button type="submit" class="btn rounded-pill btn-outline-primary" 
				                          		data-toggle="tooltip" data-placement="bottom" title="<spring:message code="saveRecord" />" 
				                          			id="guardar"><i class="ion ion-md-save"></i>     <spring:message code="save" /></button>
										  <a href="${fn:escapeXml(listUrl)}" class="btn rounded-pill btn-outline-danger"
										  			data-toggle="tooltip" data-placement="bottom" title="<spring:message code="cancelRecord" />">
										  				<i class="ion ion-md-close-circle"></i>     <spring:message code="cancel" /></a>
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
         			<jsp:include page="../../fragments/navFooter.jsp" />
        		</div>
        		<!-- Layout content -->
			</div>
			<!-- / Layout container -->
    	</div>
    </div>  	
    <!-- / Layout wrapper -->
	
  	<!-- Bootstrap and necessary plugins -->
  	<jsp:include page="../../fragments/corePlugins.jsp" />
  	<jsp:include page="../../fragments/bodyUtils.jsp" />
  	
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
  <spring:url value="/resources/js/views/Entity.js" var="processEntity" />
  <script src="${processEntity}"></script>
  
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
	    	$("li.admin").addClass("open");
	    	$("li.admin").addClass("active");
	    	$("li.racks").addClass("active");
	    });
	</script>
	
	<script>
	jQuery(document).ready(function() {
		var parametros = {saveUrl: "${saveUrl}", successmessage: "${successmessage}",
				errormessage: "${errormessage}",waitmessage: "${waitmessage}",
				listUrl: "${listUrl}" ,seleccionar: "${seleccionar}" 
		};
		ProcessEntity.init(parametros);
	});
	if ($('html').attr('dir') === 'rtl') {
	    $('.tooltip-demo [data-placement=right]').attr('data-placement', 'left').addClass('rtled');
	    $('.tooltip-demo [data-placement=left]:not(.rtled)').attr('data-placement', 'right').addClass('rtled');
  	}
  	$('[data-toggle="tooltip"]').tooltip();
	</script>
</body>
</html>
