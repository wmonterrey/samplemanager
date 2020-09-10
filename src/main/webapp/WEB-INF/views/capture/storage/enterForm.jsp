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
  	<!-- urls -->
    <spring:url value="/capture/specimens/saveEntity/" var="saveUrl"></spring:url>
    <spring:url value="/capture/specimens/newEntity" var="newUrl"></spring:url>	
    <spring:url value="/capture/specimens/" var="listUrl"></spring:url>	
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
			                <div class="card-header-title"><spring:message code="specimens" /></div>
			              </h6>
			              <div>
			                <div class="col-md-12 col-lg-12 col-xl-12">
			                  <div class="card-body">
			                  		<form id="filter-form">
			                  		<div class="row">
				                    <div class="col-md-4">
					                    <div class="form-group">
					                    	<label class="form-label"><spring:message code="equip" /></label> <span class="badge badge-dot badge-danger"></span>
						                	<select class="select2-control form-control" id="equip" name="equip" style="width: 100%" data-allow-clear="false">
						                		<option value=""></option>
					                        	<c:forEach items="${equips}" var="equip">
													<option value="${equip.systemId}"><spring:message code="equip" />: ${equip.id} - ${equip.name}</option>
												</c:forEach>
						                	</select>
					                    </div>
					                 </div>
					                 <div class="col-md-4">
					                    <div class="form-group">
					                    	<label class="form-label"><spring:message code="rack" /></label> <span class="badge badge-dot badge-danger"></span>
						                	<select class="select2-control form-control" id="rack" name="rack" style="width: 100%" data-allow-clear="false">
						                		<option value=""></option>
						                	</select>
					                    </div>
					                 </div>
					                 <div class="col-md-4">
					                    <div class="form-group">
					                    	<label class="form-label"><spring:message code="box" /></label> <span class="badge badge-dot badge-danger"></span>
						                	<select class="select2-control form-control" id="boxSpecId" name="boxSpecId" style="width: 100%" data-allow-clear="false">
						                		<option value=""></option>
						                	</select>
					                    </div>
					                 </div>
				                  </div>
			                      </form>
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
			<div class="modal fade" id="modals-default">
                  <div class="modal-dialog">
                    <form id="edit-form" class="modal-content">
                    	<div class="modal-header">
	                        <h5 class="modal-title">
	                          <strong><spring:message code="specimens" /></strong>
	                        </h5>
	                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">×</button>
	                      </div>
	                      <div class="modal-body">
	                      	<input type="text" id="inStorage" name="inStorage" class="display-hide" value="1"/>
	                      	<input type="text" id="position" name="position" class="display-hide"/>
	                        <div class="form-group">
		                      <div class="input-group">
		                      	<div class="input-group-prepend">
				                  <span class="input-group-text"><i class="fa fa-key"></i></span>
				                </div>
		                        <input type="text" id="specimenId" name="specimenId"
		                        	data-toggle="tooltip" data-state="danger" data-placement="right" title="<spring:message code="specimenId" />" 
		                        		class="form-control" placeholder="<spring:message code="specimenId" />">
		                      </div>
		                    </div>
	                        <div class="form-group">
		                      <div class="input-group">
		                      	<div class="input-group-prepend">
				                  <span class="input-group-text"><i class="fa fa-calendar-alt"></i></span>
				                </div>
				                <fmt:formatDate value="${now}" var="fecEnr" pattern="yyyy-MM-dd" />
		                        <input type="text" id="labReceiptDate" name="labReceiptDate" value="${fecEnr}"  
		                        	data-toggle="tooltip" data-state="danger" data-placement="right" title="<spring:message code="labReceiptDate" />" 
		                        		class="form-control"  data-date-end-date="0d" placeholder="<spring:message code="labReceiptDate" />">
		                      </div>
		                    </div>
		                    <div class="form-group">
		                    	<label class="form-label"><spring:message code="specimenType" /></label> <span class="badge badge-dot badge-danger"></span>
			                	<select class="select2-control form-control" id="specimenType" name="specimenType" style="width: 100%" data-allow-clear="false">
			                		<option value=""></option>
		                        	<c:forEach items="${types}" var="type">
										<option value="${type.catKey}"><spring:message code="${type.messageKey}" /></option>
									</c:forEach>
			                	</select>
		                    </div>
		                    <div class="form-group">
		                    	<label class="form-label"><spring:message code="specimenCondition" /></label>
			                	<select class="select2-control form-control" id="specimenCondition" name="specimenCondition" style="width: 100%" data-allow-clear="false">
			                		<option value=""></option>
		                        	<c:forEach items="${conditions}" var="condition">
										<option value="${condition.catKey}"><spring:message code="${condition.messageKey}" /></option>
									</c:forEach>
			                	</select>
		                    </div>
		                    
		                    <div class="form-group">
		                      <div class="input-group">
		                      	<div class="input-group-prepend">
				                  <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
				                </div>
		                        <input type="text" id="volume" name="volume"
		                        	data-toggle="tooltip" data-state="primary" data-placement="right" title="<spring:message code="volume" />" 
		                        		class="form-control" placeholder="<spring:message code="volume" />">
		                      </div>
		                    </div>
		                    
		                    
		                    <div class="form-group">
		                    	<label class="form-label"><spring:message code="volUnits" /></label>
			                	<select class="select2-control form-control" id="volUnits" name="volUnits" style="width: 100%" data-allow-clear="false">
			                		<option value=""></option>
		                        	<c:forEach items="${volUnits}" var="volUnit">
										<option value="${volUnit.catKey}"><spring:message code="${volUnit.messageKey}" /></option>
									</c:forEach>
			                	</select>
		                    </div>
		                    
		                    <div class="form-group">
		                    	<label class="form-label"><spring:message code="subjectId" /></label>
			                	<select class="select2-control form-control" id="subjectSpecId" name="subjectSpecId" style="width: 100%" data-allow-clear="false">
			                		<option value=""></option>
		                        	<c:forEach items="${subjects}" var="subject">
										<option value="${subject.systemId}"><spring:message code="subjectId" />: ${subject.subjectId} <spring:message code="study" />: ${subject.studyId.name}</option>
									</c:forEach>
			                	</select>
		                    </div>
		                    
		                    <div class="form-group">
		                      <div class="input-group">
		                      	<div class="input-group-prepend">
				                  <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
				                </div>
		                        <input type="text" id="orthocode" name="orthocode"
		                        	data-toggle="tooltip" data-state="primary" data-placement="right" title="<spring:message code="orthocode" />" 
		                        		class="form-control" placeholder="<spring:message code="orthocode" />">
		                      </div>
		                    </div>
		                    
		                    <div class="form-group">
		                      <div class="input-group">
		                      	<div class="input-group-prepend">
				                  <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
				                </div>
		                        <input type="text" id="obs" name="obs"
		                        	data-toggle="tooltip" data-state="primary" data-placement="right" title="<spring:message code="obs" />" 
		                        		class="form-control" placeholder="<spring:message code="obs" />">
		                      </div>
		                    </div>
		                    
		                    <div class="form-group">
		                      <div class="input-group">
		                      	<div class="input-group-prepend">
				                  <span class="input-group-text"><i class="fa fa-calendar-alt"></i></span>
				                </div>
				                <fmt:formatDate value="${now}" var="fecSto" pattern="yyyy-MM-dd" />
		                        <input type="text" id="storageDate" name="storageDate" value="${fecSto}"  
		                        	data-toggle="tooltip" data-state="danger" data-placement="right" title="<spring:message code="storageDate" />" 
		                        		class="form-control"  data-date-end-date="0d" placeholder="<spring:message code="storageDate" />">
		                      </div>
		                    </div>
					                    
	                      </div>
	                      <div class="modal-footer">
	                        <button type="submit" class="btn rounded-pill btn-outline-primary" 
	                          		data-toggle="tooltip" data-placement="bottom" title="<spring:message code="saveRecord" />" 
	                          			id="guardar"><i class="ion ion-md-save"></i>     <spring:message code="save" /></button>
	                        <button type="button" class="btn rounded-pill btn-outline-danger" data-dismiss="modal" 
	                        		data-toggle="tooltip" data-placement="bottom" title="<spring:message code="cancelRecord" />">
	                        			<i class="ion ion-md-close-circle"></i>     <spring:message code="cancel" /></button>
	                      </div>
                    </form>
                  </div>
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
    
    <spring:url value="/resources/vendor/libs/bootstrap-datepicker/bootstrap-datepicker_{language}.js" var="bdLang">
  		<spring:param name="language" value="${lenguaje}" />
  	</spring:url>
  	<script src="${bdLang}"></script>
  	
    <spring:url value="/resources/vendor/libs/select2/select2_{language}.js" var="select2Lang">
  		<spring:param name="language" value="${lenguaje}" />
  	</spring:url>
  	<script src="${select2Lang}"></script>
	
  <!-- Custom scripts required by this view -->
  <spring:url value="/resources/js/views/StoreSpecimen.js" var="processStorage" />
  <script src="${processStorage}"></script>
  
  <spring:url value="/resources/vendor/libs/validate/messages_{language}.js" var="validateLang">
	<spring:param name="language" value="${lenguaje}" />
  </spring:url>
  <script src="${validateLang}"></script>
  
  <spring:url value="/resources/vendor/libs/masonry/masonry.js" var="masonry" />
  <script src="${masonry}" type="text/javascript"></script>
  

  
  <!-- Mensajes -->

  	<c:set var="seleccionar"><spring:message code="select" /></c:set>
  	<c:set var="successmessage"><spring:message code="process.success" /></c:set>
  	<c:set var="errormessage"><spring:message code="process.errors" /></c:set>
  	<c:set var="waitmessage"><spring:message code="process.wait" /></c:set>
  	<c:set var="agregando">true</c:set>
  	
  	<spring:url value="/api/racks" var="racksUrl"/>
  	<spring:url value="/api/boxes" var="boxesUrl"/>
  	<spring:url value="/api/boxSelected" var="boxUrl"/>

	<script>
	jQuery(document).ready(function() {
		$("li.storage").addClass("open");
    	$("li.storage").addClass("active");
    	$("li.storespecimens").addClass("active");
		var parametros = {saveUrl: "${saveUrl}", successmessage: "${successmessage}",
				errormessage: "${errormessage}",waitmessage: "${waitmessage}",
				seleccionar: "${seleccionar}" ,lenguaje: "${lenguaje}",
				racksUrl: "${racksUrl}" ,boxesUrl: "${boxesUrl}" ,boxUrl: "${boxUrl}" ,listUrl: "${listUrl}" 
		};
		ProcessStorage.init(parametros);
	});
	if ($('html').attr('dir') === 'rtl') {
	    $('.tooltip-demo [data-placement=right]').attr('data-placement', 'left').addClass('rtled');
	    $('.tooltip-demo [data-placement=left]:not(.rtled)').attr('data-placement', 'right').addClass('rtled');
  	}
  	$('[data-toggle="tooltip"]').tooltip();
  	
	</script>
</body>
</html>
