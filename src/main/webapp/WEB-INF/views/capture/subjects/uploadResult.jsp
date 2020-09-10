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
			            
			            <div class="card mb-4">
			              <h6 class="card-header">
			                <i class="fa fa-file-upload"></i>&nbsp;<strong><spring:message code="importResults" /></strong>
			              </h6>
			              <div class="card-body">
			                <div class="import-error alert alert-danger">
			                 		${errorMessage}
			                </div>
			                <div class="import-results col-md-12 col-lg-12 col-xl-12">
			                  	<strong><spring:message code="importResultsMessage" arguments="${nuevos},${viejos}" htmlEscape="false"/></strong>
			                  	<table id="lista_entidades" class="table table-striped table-bordered datatable" width="100%">
				                <thead>
				                	<tr>
					                    <th><spring:message code="subjectId" /></th>
					                    <th><spring:message code="study" /></th>
				                	</tr>
				                </thead>
				                <tbody>
				                	<c:forEach items="${entidades}" var="entidad">
				                		<tr>
				                            <td><c:out value="${entidad.subjectId}" /></td>
				                            <td><c:out value="${entidad.studyId.name}" /></td>
				                		</tr>
				                	</c:forEach>
				                </tbody>
				                </table>
			                  	<div class="row justify-content-end">
			                  		<spring:url value="/capture/subjects/" var="listUrl"/>
									<a class="btn rounded-pill btn-outline-primary" href="${fn:escapeXml(listUrl)}"><spring:message code="end" /></a>
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
  	
  	<spring:url value="/resources/vendor/libs/datatables/label_{language}.json" var="dataTablesLang">
  		<spring:param name="language" value="${lenguaje}" />
  	</spring:url>

  	<!-- Custom scripts required by this view -->

	<script>
	jQuery(document).ready(function() {
    	$("li.capture").addClass("open");
    	$("li.capture").addClass("active");
    	$("li.subjects").addClass("active");	    	
    	$('#lista_entidades').DataTable({
			  dom: 'lBfrtip',
	          "oLanguage": {
	              "sUrl": "${dataTablesLang}"
	          },
	          "bFilter": true, 
	          "bInfo": true, 
	          "bPaginate": true, 
	          "bDestroy": true,
	          "responsive": true,
	          "pageLength": 5,
	          "lengthMenu": [5, 10, 20, 50, 100],
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
		$('.import-error').hide();
	  
	    if ("${importError}"){
		  $('.import-error').show();
	    }
		
	</script>
	
	
</body>
</html>
