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
	  <!-- urls -->
		<spring:url value="/users/editUser" var="editUrl"/>
		<spring:url value="/users/chgpass" var="chgpassUrl"/>
			
	<div class="page-loader">
    	<div class="bg-primary"></div>
  	</div>
  	
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
			            <div class="card mb-4" id="viewuser-element">
			              <h6 class="card-header with-elements">
			                <div class="card-header-title"><i class="fa fa-user"></i>&nbsp;<strong><c:out value="${user.username}" /></strong></div>
			                <div class="card-title-elements ml-md-auto">
			                	<div class="dropdown" id="nesting-dropdown">
			                		<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><spring:message code="actions" /></button>
			                		<div class="dropdown-menu">
			                			<a class="dropdown-item" href="${fn:escapeXml(editUrl)}"><i class="fa fa-edit"></i> <spring:message code="edit" /></a>
			                			<a class="dropdown-item" href="${fn:escapeXml(chgpassUrl)}"><i class="fa fa-key"></i> <spring:message code="changepass" /></a>
                        			</div>
			                	</div>
			              	</div>
			              </h6>
			              <div class="col-md-12 col-lg-12 col-xl-12">
			                <div class="card-body demo-vertical-spacing">
			                	<div class="row">
									<div class="col-md-3">
										<div>
											<label class="control-label"><spring:message code="userdesc" />:</label>
											<div>
												<p class="form-control-static">
													 <strong><c:out value="${user.completeName}" /></strong>
												</p>
											</div>
										</div>
									</div>
									<!--/span-->
									<div class="col-md-3">
										<div>
											<label class="control-label"><spring:message code="useremail" />:</label>
											<div>
												<p class="form-control-static">
													 <strong><c:out value="${user.email}" /></strong>
												</p>
											</div>
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label"><spring:message code="enabled" />:</label>
											<div>
												<p class="form-control-static">
													<c:choose>
														<c:when test="${user.enabled}">
															<strong><spring:message code="CAT_SINO_SI" /></strong>
														</c:when>
														<c:otherwise>
															<strong><spring:message code="CAT_SINO_NO" /></strong>
														</c:otherwise>
													</c:choose>
												</p>
											</div>
										</div>
									</div>
									<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label"><spring:message code="locked" />:</label>
											<div>
												<p class="form-control-static">
													<c:choose>
														<c:when test="${user.accountNonLocked}">
															<strong><spring:message code="CAT_SINO_NO" /></strong>
														</c:when>
														<c:otherwise>
															<strong><spring:message code="CAT_SINO_SI" /></strong>
														</c:otherwise>
													</c:choose>
												</p>
											</div>
										</div>
									</div>
									<!--/span-->
								</div>
								<!--/row-->
			                 	<div class="row">
								<!--/span-->
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label"><spring:message code="usercred" />:</label>
										<div>
											<p class="form-control-static">
												<c:choose>
													<c:when test="${user.credentialsNonExpired}">
														<strong><spring:message code="CAT_SINO_NO" /></strong>
													</c:when>
													<c:otherwise>
														<strong><spring:message code="CAT_SINO_SI" /></strong>
													</c:otherwise>
												</c:choose>
											</p>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label"><spring:message code="userexp" />:</label>
										<div>
											<p class="form-control-static">
												<c:choose>
													<c:when test="${user.accountNonExpired}">
														<strong><spring:message code="CAT_SINO_NO" /></strong>
													</c:when>
													<c:otherwise>
														<strong><spring:message code="CAT_SINO_SI" /></strong>
													</c:otherwise>
												</c:choose>
											</p>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label"><spring:message code="createdBy" />:</label>
										<div>
											<p class="form-control-static">
												 <strong><c:out value="${user.createdBy}" /></strong>
											</p>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label"><spring:message code="dateCreated" />:</label>
										<div>
											<p class="form-control-static">
												<strong><c:out value="${user.created}" /></strong>
											</p>
										</div>
									</div>
								</div>
								<!--/span-->
							</div>
							<!--/row-->	
							<div class="row">
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label"><spring:message code="modifiedBy" />:</label>
									<div>
										<p class="form-control-static">
											 <strong><c:out value="${user.modifiedBy}" /></strong>
										</p>
									</div>
								</div>
							</div>
							<!--/span-->
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label"><spring:message code="dateModified" />:</label>
									<div>
										<p class="form-control-static">
											<strong><c:out value="${user.modified}" /></strong>
										</p>
									</div>
								</div>
							</div>
							<!--/span-->
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label"><spring:message code="lastAccess" />:</label>
									<div>
										<p class="form-control-static">
											 <strong><c:out value="${user.lastAccess}" /></strong>
										</p>
									</div>
								</div>
							</div>
							<!--/span-->
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label"><spring:message code="dateCredentials" />:</label>
									<div>
										<p class="form-control-static">
											<strong><c:out value="${user.lastCredentialChange}" /></strong>
										</p>
									</div>
								</div>
							</div>
							<!--/span-->
							</div>
			                <!--/row-->	  
			                </div>
			               
			              </div>
			              <a class="btn btn-primary" href="${fn:escapeXml(editUrl)}"><i class="fa fa-edit"></i> <spring:message code="edit" /></a>
			
			            </div>
			            
			            <div class="card mb-4" id="viewuser-element-2">
			              <h6 class="card-header with-elements">
			                <div class="card-header-title"><i class="fa fa-check"></i>&nbsp;<strong><spring:message code="userroles" /></strong></div>
			              </h6>
			              <div class="col-md-12 col-lg-12 col-xl-12">
			                <div class="card-body table-responsive">
			                	<table id="lista_roles" class="table table-striped table-bordered datatable" width="100%">
					                <thead>
					                	<tr>
						                    <th><spring:message code="userroles" /></th>
											<th><spring:message code="enabled" /></th>
											<th><spring:message code="addedBy" /></th>
											<th><spring:message code="dateAdded" /></th>
					                	</tr>
					                </thead>
					                <tbody>
					                	<c:forEach items="${rolesusuario}" var="rol">
										<tr>
											<td><spring:message code="${rol.rol.authority}" /></td>
											<c:choose>
												<c:when test="${rol.pasive=='0'.charAt(0)}">
													<td><span class="badge badge-success"><spring:message code="CAT_SINO_SI" /></span></td>
												</c:when>
												<c:otherwise>
													<td><span class="badge badge-danger"><spring:message code="CAT_SINO_NO" /></span></td>
												</c:otherwise>
											</c:choose>
											<td><c:out value="${rol.recordUser}" /></td>
											<td><c:out value="${rol.recordDate}" /></td>
										</tr>
									</c:forEach>
					                </tbody>
					            </table>
			                </div>
			                
			              </div>
			            </div>
			            
			            
			            <div class="card mb-4" id="viewuser-element-3">
			              <h6 class="card-header with-elements">
			                <div class="card-header-title"><i class="fa fa-pen"></i>&nbsp;<strong><spring:message code="audittrail" /></strong></div>
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
			            
			            <div class="card mb-4" id="viewuser-element-4">
			              <h6 class="card-header with-elements">
			                <div class="card-header-title"><i class="fa fa-pen"></i>&nbsp;<strong><spring:message code="access" /></strong></div>
			              </h6>
			              <div class="col-md-12 col-lg-12 col-xl-12">
			                <div class="card-body table-responsive">
			                	<table id="lista_accesos" class="table table-striped table-bordered datatable" width="100%">
					                <thead>
					                	<tr>
						                    <th class="hidden-xs"><spring:message code="session" /></th>
											<th class="hidden-xs"><spring:message code="ipaddress" /></th>
											<th><spring:message code="logindate" /></th>
					                	</tr>
					                </thead>
					                <tbody>
					                <c:forEach items="${accesses}" var="acceso">
										<tr>
											<td class="hidden-xs"><c:out value="${acceso.sessionId}" /></td>
											<td class="hidden-xs"><c:out value="${acceso.remoteIpAddress}" /></td>
											<td><c:out value="${acceso.loginDate}" /></td>
										</tr>
									</c:forEach>
					                </tbody>
					            </table>
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
    
    <!-- Modal -->
  	  <div class="modal fade" id="basic" tabindex="-1" data-role="basic" data-backdrop="static" data-aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div id="titulo"></div>
				</div>
				<div class="modal-body">
					<input type="hidden" id="accionUrl"/>
					<div id="cuerpo">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="cancel" /></button>
					<button type="button" class="btn btn-info" onclick="ejecutarAccion()"><spring:message code="ok" /></button>
				</div>
			</div>
			<!-- /.modal-content -->
	    </div>
	  <!-- /.modal-dialog -->
  	  </div>
  	  <!-- Modal -->
  	  <div class="modal fade" id="rolesForm" data-role="rolesForm" data-backdrop="static" data-aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h2 class="modal-title"><spring:message code="add" /> <spring:message code="userroles" /></h2>
				</div>
				<div class="modal-body">
					<input type="hidden" id="inputAddRolUrl"/>
					<div id="cuerpo">
						<fieldset class="form-group">
		                 	<i class="fa fa-check"></i>
		                    <label><spring:message code="userroles" /></label>
		                    <select id="roles" name="roles" class="form-control">
		                      <c:forEach items="${roles}" var="rol">
		                      	<option value="${rol.authority}"><spring:message code="${rol.authority}" /></option>
		                      </c:forEach>
		                    </select>
		                 </fieldset>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="cancel" /></button>
					<button type="button" id="buttonAgregarRol" class="btn btn-info" onclick="ejecutarAgregarRol()"><spring:message code="ok" /></button>
				</div>
			</div>
			<!-- /.modal-content -->
	    </div>
	  </div>
	  <!-- /.modal-dialog -->
	
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
  
   	<spring:url value="/resources/vendor/libs/datatables/label_{language}.json" var="dataTablesLang">
 		<spring:param name="language" value="${lenguaje}" />
  	</spring:url>
  

	<script>
		jQuery(document).ready(function() {
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
		
	</script>
	
	
</body>
</html>
