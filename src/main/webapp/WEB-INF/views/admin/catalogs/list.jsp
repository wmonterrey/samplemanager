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
			            <div class="card mb-4" id="entity-element">
			              <h6 class="card-header with-elements">
			              	<div class="card-title-elements">
			              	</div>
			                <div class="card-header-title"><spring:message code="catalogs" /></div>
			              </h6>
			              <div class="row no-gutters row-bordered">
			                <div class="col-md-12 col-lg-12 col-xl-12">
			                  <div class="card-body">
			                  	<table id="lista_entidades" class="table table-striped table-bordered datatable" width="100%">
				                <thead>
				                	<tr>
					                    <th><spring:message code="ident" /></th>
										<th><spring:message code="spanish" /></th>
										<th><spring:message code="english" /></th>
										<th><spring:message code="enabled" /></th>
										<th></th>
				                	</tr>
				                </thead>
				                <tbody>
				                	<c:forEach items="${entidades}" var="entidad">
				                		<tr>
				                			<spring:url value="/admin/catalogs/{messageKey}/"
				                                        var="viewUrl">
				                                <spring:param name="messageKey" value="${entidad.messageKey}" />
				                            </spring:url>
				                            <td><c:out value="${entidad.messageKey}" /></td>
				                            <td><c:out value="${entidad.spanish}" /></td>
				                            <td><c:out value="${entidad.english}" /></td>
				                            <c:choose>
				                                <c:when test="${entidad.pasive eq '0'.charAt(0)}">
				                                    <td><span class="badge badge-success"><spring:message code="CAT_SINO_SI" /></span></td>
				                                </c:when>
				                                <c:otherwise>
				                                    <td><span class="badge badge-danger"><spring:message code="CAT_SINO_NO" /></span></td>
				                                </c:otherwise>
				                            </c:choose>
				                            <td>
				                            	<a href="${fn:escapeXml(viewUrl)}" data-toggle="tooltip" data-placement="bottom" title="<spring:message code="editEntidadToolTip" />" class="btn btn-outline-primary btn-sm"><i class="fa fa-edit"></i></a>
				                            </td>
				                		</tr>
				                	</c:forEach>
				                </tbody>
				                </table>
			                  
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


	<script>
		jQuery(document).ready(function() {
	    	$("li.admin").addClass("open");
	    	$("li.admin").addClass("active");
	    	$("li.catalogs").addClass("active");	    	
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
		
		  if ($('html').attr('dir') === 'rtl') {
			    $('.tooltip-demo [data-placement=right]').attr('data-placement', 'left').addClass('rtled');
			    $('.tooltip-demo [data-placement=left]:not(.rtled)').attr('data-placement', 'right').addClass('rtled');
		  }
		  $('[data-toggle="tooltip"]').tooltip();
	</script>
</body>
</html>
