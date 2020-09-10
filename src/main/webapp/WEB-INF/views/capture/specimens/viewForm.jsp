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
			            
			            <div class="row">
              				<div class="col-xl-12">

                				<div class="nav-tabs-top mb-4">
				                  <ul class="nav nav-tabs">
				                    <li class="nav-item">
				                      <a class="nav-link active" data-toggle="tab" href="#navs-top-data"><i class="fa fa-database"></i>&nbsp;<strong><spring:message code="data" /></strong></a>
				                    </li>
				                    <li class="nav-item">
				                      <a class="nav-link" data-toggle="tab" href="#navs-top-storage"><i class="fa fa-braille"></i>&nbsp;<strong><spring:message code="storage" /></strong></a>
				                    </li>
				                    <li class="nav-item">
				                      <a class="nav-link" data-toggle="tab" href="#navs-top-metadata"><i class="fa fa-pen"></i>&nbsp;<strong><spring:message code="metadata" /></strong></a>
				                    </li>
				                    <li class="nav-item">
				                      <a class="nav-link" data-toggle="tab" href="#navs-top-audittrail"><i class="fa fa-archive"></i>&nbsp;<strong><spring:message code="audittrail" /></strong></a>
				                    </li>
				                  </ul>
				                  <div class="tab-content">
				                    <div class="tab-pane fade show active" id="navs-top-data">
				                      <div class="card-body">
							                <div class="card-body table-responsive">
							                	<table id="lista_datos" class="table table-striped table-bordered" width="100%">
									                <tbody>
									                	<tr>
									                		<td><spring:message code="systemId" /></td>
									                		<td><c:out value="${entidad.systemId}" /></td>
									                	</tr>
									                	<tr>
									                		<td><spring:message code="specimenId" /></td>
									                		<td><c:out value="${entidad.specimenId}" /></td>
									                	</tr>
									                	<tr>
									                		<td><spring:message code="labReceiptDate" /></td>
									                		<td><c:out value="${entidad.labReceiptDate}" /></td>
									                	</tr>
									                	<tr>
									                		<td><spring:message code="specimenType" /></td>
									                		<td><c:out value="${entidad.specimenType}" /></td>
									                	</tr>
									                	<tr>
									                		<td><spring:message code="specimenCondition" /></td>
									                		<td><c:out value="${entidad.specimenCondition}" /></td>
									                	</tr>
									                	<tr>
									                		<td><spring:message code="varA" /></td>
									                		<td><c:out value="${entidad.varA}" /></td>
									                	</tr>
									                	<tr>
									                		<td><spring:message code="varB" /></td>
									                		<td><c:out value="${entidad.varB}" /></td>
									                	</tr>
									                	<tr>
									                		<td><spring:message code="volume" /></td>
									                		<td><c:out value="${entidad.volume}" /></td>
									                	</tr>
									                	<tr>
									                		<td><spring:message code="volUnits" /></td>
									                		<td><c:out value="${entidad.volUnits}" /></td>
									                	</tr>
									                	<tr>
									                		<td><spring:message code="subjectId" /></td>
									                		<td><c:out value="${entidad.subjectId.subjectId}" /></td>
									                	</tr>
									                	<tr>
									                		<td><spring:message code="obs" /></td>
									                		<td><c:out value="${entidad.obs}" /></td>
									                	</tr>
									                </tbody>
							                	</table>
							                </div>
							                <div class="row justify-content-end">
							                	<spring:url value="/capture/specimens/" var="listUrl"></spring:url>	
							                	<spring:url value="/capture/specimens/editEntity/{systemId}/"
								                                        var="editUrl">
					                                <spring:param name="systemId" value="${entidad.systemId}" />
					                            </spring:url>
				                         		<spring:url value="/capture/specimens/disableEntity/{systemId}/" var="disableUrl">
				                              		<spring:param name="systemId" value="${entidad.systemId}" />
				                         		</spring:url>
				                         		<spring:url value="/capture/specimens/enableEntity/{systemId}/" var="enableUrl">
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
				                      </div>
				                    </div>
				                    <div class="tab-pane fade" id="navs-top-storage">
				                      <div class="card-body">
							                <div class="card-body table-responsive">
							                	<table id="lista_datos" class="table table-striped table-bordered" width="100%">
									                <tbody>
									                	<tr>
									                		<td><spring:message code="systemId" /></td>
									                		<td><c:out value="${entidad2.storageId}" /></td>
									                	</tr>
									                	<tr>
									                		<td><spring:message code="specimenId" /></td>
									                		<td><c:out value="${entidad.specimenId}" /></td>
									                	</tr>
									                	<tr>
									                		<td><spring:message code="inStorage" /></td>
									                		<td><c:out value="${entidad.inStorage}" /></td>
									                	</tr>
									                	<tr>
									                		<td><spring:message code="equip" /></td>
									                		<td><c:out value="${entidad2.box.rack.equip.name}" /></td>
									                	</tr>
									                	<tr>
									                		<td><spring:message code="rack" /></td>
									                		<td><c:out value="${entidad2.box.rack.name}" /></td>
									                	</tr>
									                	<tr>
									                		<td><spring:message code="box" /></td>
									                		<td><c:out value="${entidad2.box.name}" /></td>
									                	</tr>
									                	<tr>
									                		<td><spring:message code="pos" /></td>
									                		<td><c:out value="${entidad2.pos}" /></td>
									                	</tr>
									                </tbody>
							                	</table>
							                </div>
							                <div class="row justify-content-end">
							                	<spring:url value="/capture/specimens/" var="listUrl"></spring:url>	
							                	<spring:url value="/capture/specimens/editStorageEntity/{systemId}/"
								                                        var="editStorageUrl">
					                                <spring:param name="systemId" value="${entidad2.storageId}" />
					                            </spring:url>
					                            <spring:url value="/capture/specimens/deleteStorageEntity/{systemId}/"
								                                        var="deleteStorageUrl">
					                                <spring:param name="systemId" value="${entidad2.storageId}" />
					                            </spring:url>
				                         		<c:if test="${not empty entidad2}">	
													<a class="btn rounded-pill btn-outline-primary" 
														data-toggle="tooltip" data-placement="bottom" title="<spring:message code="editRecord" />" 
															href="${fn:escapeXml(editStorageUrl)}"><i class="ion ion-md-create"></i>     <spring:message code="edit" /></a>
		
													<a class="btn rounded-pill btn-outline-danger" 
														data-toggle="tooltip" data-placement="bottom" title="<spring:message code="disableRecord" />" 
															href="${fn:escapeXml(deleteStorageUrl)}"><i class="ion ion ion-md-close-circle"></i>     <spring:message code="disable" /></a>
												</c:if>
											 	<a class="btn rounded-pill btn-outline-primary" 
											 		data-toggle="tooltip" data-placement="bottom" title="<spring:message code="returnRecord" />" 
											 		 href="${fn:escapeXml(listUrl)}"><i class="ion ion-md-arrow-back"></i>     <spring:message code="back" /></a>
											</div>
				                      </div>
				                    </div>
				                    <div class="tab-pane fade" id="navs-top-metadata">
				                      <div class="card-body">
				                      	<div class="card-body table-responsive">
							                	<table id="lista_datos" class="table table-striped table-bordered" width="100%">
									                <tbody>
									                	<tr>
									                		<td><spring:message code="systemId" /></td>
									                		<td><c:out value="${entidad.systemId}" /></td>
									                	</tr>
									                	<tr>
									                		<td><spring:message code="specimenId" /></td>
									                		<td><c:out value="${entidad.specimenId}" /></td>
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
				                      </div>
				                    </div>
				                    <div class="tab-pane fade" id="navs-top-audittrail">
				                      <div class="card-body">
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
  
  <spring:url value="/resources/vendor/libs/validate/messages_{language}.js" var="validateLang">
	<spring:param name="language" value="${lenguaje}" />
  </spring:url>
  <script src="${validateLang}"></script>
  
   	<spring:url value="/resources/vendor/libs/datatables/label_{language}.json" var="dataTablesLang">
 		<spring:param name="language" value="${lenguaje}" />
  	</spring:url>
  

  
  <!-- Mensajes -->
  <c:set var="entityEnabledLabel"><spring:message code="enabled" /></c:set>
  <c:set var="entityDisabledLabel"><spring:message code="disabled" /></c:set>	

	<script>
		jQuery(document).ready(function() {
	    	$("li.capture").addClass("open");
	    	$("li.capture").addClass("active");
	    	$("li.specimens").addClass("active");
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
		
	</script>
	
	
</body>
</html>
