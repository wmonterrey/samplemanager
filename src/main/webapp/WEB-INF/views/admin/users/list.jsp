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
			            <spring:url value="/admin/users/newUser/" var="newUser"/>	
	              		<button id="lista_usuarios_new" onclick="location.href='${fn:escapeXml(newUser)}'" type="button" class="btn btn-outline-primary"><i class="fa fa-plus"></i>&nbsp; <spring:message code="add" /></button><br><br>
			            <div class="card mb-4" id="users-element">
			              <h6 class="card-header with-elements">
			              	<div class="card-title-elements">
			              	</div>
			                <div class="card-header-title"><spring:message code="users" /></div>
			              </h6>
			              <div class="row no-gutters row-bordered">
			                <div class="col-md-12 col-lg-12 col-xl-12">
			                  <div class="card-body">
			                  	<table id="lista_usuarios" class="table table-striped table-bordered datatable" width="100%">
				                <thead>
				                	<tr>
					                    <th><spring:message code="username" /></th>
					                    <th><spring:message code="userdesc" /></th>
					                    <th><spring:message code="useremail" /></th>
					                    <th><spring:message code="enabled" /></th>
					                    <th><spring:message code="userlock" /></th>
					                    <th><spring:message code="usercred" /></th>
					                    <th><spring:message code="actions" /></th>
				                	</tr>
				                </thead>
				                <tbody>
				                	<c:forEach items="${usuarios}" var="usuario">
				                		<tr>
				                			<spring:url value="/admin/users/{username}/"
				                                        var="usuarioUrl">
				                                <spring:param name="username" value="${usuario.username}" />
				                            </spring:url>
				                            <spring:url value="/admin/users/editUser/{username}/"
				                                        var="editUrl">
				                                <spring:param name="username" value="${usuario.username}" />
				                            </spring:url>
				                            <spring:url value="/admin/users/habdes/disable1/{username}/"
				                                        var="disableUrl">
				                                <spring:param name="username" value="${usuario.username}" />
				                            </spring:url>
				                            <spring:url value="/admin/users/habdes/enable1/{username}/"
				                                        var="enableUrl">
				                                <spring:param name="username" value="${usuario.username}" />
				                            </spring:url>
				                            <spring:url value="/admin/users/lockunl/lock1/{username}/"
				                                        var="lockUrl">
				                                <spring:param name="username" value="${usuario.username}" />
				                            </spring:url>
				                            <spring:url value="/admin/users/lockunl/unlock1/{username}/"
				                                        var="unlockUrl">
				                                <spring:param name="username" value="${usuario.username}" />
				                            </spring:url>
				                            <spring:url value="/admin/users/chgpass/{username}/"
				                                        var="chgpassUrl">
				                                <spring:param name="username" value="${usuario.username}" />
				                            </spring:url>
				                            <td><a href="${fn:escapeXml(usuarioUrl)}"><c:out value="${usuario.username}" /></a></td>
				                            <td><c:out value="${usuario.completeName}" /></td>
				                            <td><c:out value="${usuario.email}" /></td>
				                            <c:choose>
				                                <c:when test="${usuario.enabled}">
				                                    <td><span class="badge badge-success"><spring:message code="CAT_SINO_SI" /></span></td>
				                                </c:when>
				                                <c:otherwise>
				                                    <td><span class="badge badge-danger"><spring:message code="CAT_SINO_NO" /></span></td>
				                                </c:otherwise>
				                            </c:choose>
				                            <c:choose>
				                                <c:when test="${usuario.accountNonLocked}">
				                                    <td><span class="badge badge-success"><spring:message code="CAT_SINO_NO" /></span></td>
				                                </c:when>
				                                <c:otherwise>
				                                    <td><span class="badge badge-danger"><spring:message code="CAT_SINO_SI" /></span></td>
				                                </c:otherwise>
				                            </c:choose>
				                            <c:choose>
				                                <c:when test="${usuario.credentialsNonExpired}">
				                                    <td><span class="badge badge-success"><spring:message code="CAT_SINO_NO" /></span></td>
				                                </c:when>
				                                <c:otherwise>
				                                    <td><span class="badge badge-danger"><spring:message code="CAT_SINO_SI" /></span></td>
				                                </c:otherwise>
				                            </c:choose>
				                            <td>
				                                <a href="${fn:escapeXml(usuarioUrl)}" class="btn btn-outline-primary btn-sm"><i class="fa fa-search"></i></a>
				                                <a href="${fn:escapeXml(editUrl)}" class="btn btn-outline-primary btn-sm"><i class="fa fa-edit"></i></a>
				                                <a href="${fn:escapeXml(chgpassUrl)}" class="btn btn-outline-primary btn-sm"><i class="fa fa-key"></i></a>
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
	    	$("li.users").addClass("active");	    	
	    	$('#lista_usuarios').DataTable({
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
	</script>
</body>
</html>
