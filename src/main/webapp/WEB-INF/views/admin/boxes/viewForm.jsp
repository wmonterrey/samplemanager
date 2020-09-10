<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="default-style">
<head>
  <jsp:include page="../../fragments/headTag.jsp" />
  <spring:url value="/resources/vendor/css/masonry.css" var="masonrycss" />
  <link rel="stylesheet" href="${masonrycss}">
</head>
<body>
	<div class="page-loader">
    	<div class="bg-primary"></div>
  	</div>
  	
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
			            <div class="card mb-4" id="viewentity-element">
			              <h6 class="card-header with-elements">
			                <div class="card-header-title"><i class="fa fa-database"></i>&nbsp;<strong><c:out value="${entidad.systemId}" /></strong></div>
			              </h6>
			              <div class="col-md-12 col-lg-12 col-xl-12">
			                <div class="card-body demo-vertical-spacing">
			                <div class="card-body table-responsive">
			                	<table id="lista_datos" class="table table-striped table-bordered" width="100%">
					                <tbody>
					                	<tr>
					                		<td><spring:message code="id" /></td>
					                		<td><c:out value="${entidad.id}" /></td>
					                	</tr>
					                	<tr>
					                		<td><spring:message code="name" /></td>
					                		<td><c:out value="${entidad.name}" /></td>
					                	</tr>
					                	<tr>
					                		<td><spring:message code="rack" /></td>
					                		<td><c:out value="${entidad.rack.name}" /></td>
					                	</tr>
					                	<tr>
					                		<td><spring:message code="rows" /></td>
					                		<td><c:out value="${entidad.rows}" /></td>
					                	</tr>
					                	<tr>
					                		<td><spring:message code="columns" /></td>
					                		<td><c:out value="${entidad.columns}" /></td>
					                	</tr>
					                	<tr>
					                		<td><spring:message code="capacity" /></td>
					                		<td><c:out value="${entidad.capacity}" /></td>
					                	</tr>
					                	<tr>
					                		<td><spring:message code="obs" /></td>
					                		<td><c:out value="${entidad.obs}" /></td>
					                	</tr>
					                	<tr>
					                		<td><spring:message code="recordDate" /></td>
					                		<td><c:out value="${entidad.recordDate}" /></td>
					                	</tr>
					                	<tr>
					                		<td><spring:message code="recordUser" /></td>
					                		<td><c:out value="${entidad.recordUser}" /></td>
					                	</tr>
					                	<tr>
					                		<td><spring:message code="recordIp" /></td>
					                		<td><c:out value="${entidad.recordIp}" /></td>
					                	</tr>
					                	<tr>
					                		<td><spring:message code="pasive" /></td>
					                		<td><c:out value="${entidad.pasive}" /></td>
					                	</tr>
					                </tbody>
			                	</table>
			                </div>
			                <div class="row justify-content-end">
			                	<spring:url value="/admin/boxes/" var="listUrl"></spring:url>	
			                	<spring:url value="/admin/boxes/editEntity/{systemId}/"
				                                        var="editUrl">
	                                <spring:param name="systemId" value="${entidad.systemId}" />
	                            </spring:url>
                         		<spring:url value="/admin/boxes/disableEntity/{systemId}/" var="disableUrl">
                              		<spring:param name="systemId" value="${entidad.systemId}" />
                         		</spring:url>
                         		<spring:url value="/admin/boxes/enableEntity/{systemId}/" var="enableUrl">
                           			<spring:param name="systemId" value="${entidad.systemId}" />
                         		</spring:url>
								<a class="btn rounded-pill btn-outline-primary" 
										data-toggle="tooltip" data-placement="bottom" title="<spring:message code="editRecord" />" 
											href="${fn:escapeXml(editUrl)}"><i class="ion ion-md-create"></i>     <spring:message code="edit" /></a>
								<c:choose>
									<c:when test="${entidad.pasive=='0'.charAt(0)}">
										<button id="disable_entity" onclick="location.href='${fn:escapeXml(disableUrl)}'" type="button" class="btn rounded-pill btn-outline-danger"
											data-toggle="tooltip" data-placement="bottom" title="<spring:message code="disableRecord" />" >
												<i class="ion ion-md-close-circle"></i>     <spring:message code="disable" /></button>
									</c:when>
									<c:otherwise>
										<button id="enable_entity" onclick="location.href='${fn:escapeXml(enableUrl)}'" type="button" class="btn rounded-pill btn-outline-primary"
											data-toggle="tooltip" data-placement="bottom" title="<spring:message code="enableRecord" />" >
												<i class="ion ion-md-checkmark"></i>     <spring:message code="enable" /></button>
									</c:otherwise>
							 	</c:choose>
							 	<a class="btn rounded-pill btn-outline-primary" 
							 		data-toggle="tooltip" data-placement="bottom" title="<spring:message code="returnRecord" />" 
							 		 href="${fn:escapeXml(listUrl)}"><i class="ion ion-md-arrow-back"></i>     <spring:message code="back" /></a>
							</div>
			                <!--/row-->	 
			                </div>
			               
			              </div>
			
			            </div>
			            
			            
			            <div class="card mb-4" id="viewentity-element-3">
			            	<h6 class="card-header with-elements">
				                <div class="card-header-title"><i class="fa fa-grip-horizontal"></i>&nbsp;<strong><spring:message code="specimens" /></strong></div>
				            </h6>
				            <div class="col-md-12 col-lg-12 col-xl-12">
				            	<div class="card-body">
						            <div class="masonry-grid">
						            	<c:forEach begin="1" end="${entidad.capacity}" varStatus="loop">
						            		<div class="masonry-grid-item">
						            			<p class="pos"><c:out value="${loop.count}"/></p>
						            			<c:forEach items="${muestras}" var="muestra">
						            				<spring:url value="/capture/specimens/{specimenId}/"
														var="specimenUrl">
														<spring:param name="specimenId" value="${muestra.specimen.systemId}" />
													</spring:url>
											    	<c:choose>
														<c:when test="${muestra.pos==loop.count}">
														    <p class="ident"><a href="${fn:escapeXml(specimenUrl)}"><c:out value="${muestra.specimen.specimenId}" /></a></p>
														    <p class="tipo"><c:out value="${muestra.specimen.specimenType}" /></a></p>
														    <p class="volumen"><c:out value="${muestra.specimen.volume}" /> <c:out value="${muestra.specimen.volUnits}" /></p>
														</c:when>
													</c:choose>
						            			</c:forEach>
						            		</div>
						            	</c:forEach>
						            </div>
					            </div>
				            </div>
			            </div>
			            
			            
			            <div class="card mb-4" id="viewentity-element-2">
			              <h6 class="card-header with-elements">
			                <div class="card-header-title"><i class="fa fa-archive"></i>&nbsp;<strong><spring:message code="audittrail" /></strong></div>
			              </h6>
			              <div class="col-md-12 col-lg-12 col-xl-12">
			                <div class="card-body table-responsive">
			                	<table id="lista_cambios" class="table table-striped table-bordered datatable" width="100%">
					                <thead>
					                	<tr>
											<th><spring:message code="entityClass" /></th>
											<th><spring:message code="entityName" /></th>
											<th><spring:message code="entityProperty" /></th>
											<th><spring:message code="entityPropertyOldValue" /></th>
											<th><spring:message code="entityPropertyNewValue" /></th>
											<th><spring:message code="modifiedBy" /></th>
											<th><spring:message code="dateModified" /></th>
					                	</tr>
					                </thead>
					                <tbody>
									<c:forEach items="${bitacora}" var="cambio">
										<tr>
											<td><spring:message code="${cambio.entityClass}" /></td>
											<td><spring:message code="${cambio.entityName}" /></td>
											<td><c:out value="${cambio.entityProperty}" /></td>
											<td><c:out value="${cambio.entityPropertyOldValue}" /></td>
											<td><c:out value="${cambio.entityPropertyNewValue}" /></td>
											<td><c:out value="${cambio.username}" /></td>
											<td><c:out value="${cambio.operationDate}" /></td>
										</tr>
									</c:forEach>
					                </tbody>
					            </table>
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
  
  <spring:url value="/resources/vendor/libs/validate/messages_{language}.js" var="validateLang">
	<spring:param name="language" value="${lenguaje}" />
  </spring:url>
  <script src="${validateLang}"></script>
  
   	<spring:url value="/resources/vendor/libs/datatables/label_{language}.json" var="dataTablesLang">
 		<spring:param name="language" value="${lenguaje}" />
  	</spring:url>
  	
  <spring:url value="/resources/vendor/libs/masonry/masonry.js" var="masonry" />
  <script src="${masonry}" type="text/javascript"></script>
  

  
  <!-- Mensajes -->
  <c:set var="entityEnabledLabel"><spring:message code="enabled" /></c:set>
  <c:set var="entityDisabledLabel"><spring:message code="disabled" /></c:set>	

	<script>
		jQuery(document).ready(function() {
	    	$("li.admin").addClass("open");
	    	$("li.admin").addClass("active");
	    	$("li.boxes").addClass("active");
	    	$('.datatable').DataTable({
				  dom: 'lBfrtip',
		          "oLanguage": {
		              "sUrl": "${dataTablesLang}"
		          },
		          "bFilter": true, 
		          "bInfo": true, 
		          "bPaginate": true, 
		          "bDestroy": true,
		          "responsive": true,
		          "pageLength": 10,
		          "bLengthChange": true,
		          "responsive": true,
		          "buttons": [
		              {
		                  extend: 'excel'
		              },
		              {
		                  extend: 'pdfHtml5',
		                  orientation: 'portrait',
		                  pageSize: 'LETTER'
		              }
		          ]
		      });
	    });
		
	  	if ("${enabledEntity}"){
	  		Swal.fire("${entityName}", "${entityEnabledLabel}", 'success');
		}
		if ("${disabledEntity}"){
			Swal.fire("${entityName}", "${entityDisabledLabel}", 'error');
		}
		if ($('html').attr('dir') === 'rtl') {
		    $('.tooltip-demo [data-placement=right]').attr('data-placement', 'left').addClass('rtled');
		    $('.tooltip-demo [data-placement=left]:not(.rtled)').attr('data-placement', 'right').addClass('rtled');
	  	}
	  	$('[data-toggle="tooltip"]').tooltip();
	  	
	  	$(function() {
	  		var ancho = "${100/entidad.columns}"+"%";
	  		$('.masonry-grid-item').css({"width":ancho});
	  		
	  	  $('.masonry-grid').masonry({
	  	    itemSelector: '.masonry-grid-item',
	  	    percentPosition: true,
	  	    originLeft: !($('body').attr('dir') === 'rtl' || $('html').attr('dir') === 'rtl')
	  	  });
	  	});
		
	</script>
	
	
</body>
</html>
