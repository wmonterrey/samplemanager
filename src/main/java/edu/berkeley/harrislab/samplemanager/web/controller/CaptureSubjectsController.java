package edu.berkeley.harrislab.samplemanager.web.controller;

import edu.berkeley.harrislab.samplemanager.domain.Study;
import edu.berkeley.harrislab.samplemanager.domain.Subject;
import edu.berkeley.harrislab.samplemanager.domain.audit.AuditTrail;
import edu.berkeley.harrislab.samplemanager.service.AuditTrailService;
import edu.berkeley.harrislab.samplemanager.service.MessageResourceService;
import edu.berkeley.harrislab.samplemanager.service.StudyService;
import edu.berkeley.harrislab.samplemanager.service.UsuarioService;
import edu.berkeley.harrislab.samplemanager.service.SubjectService;
import edu.berkeley.harrislab.samplemanager.service.VisitsService;
import edu.berkeley.harrislab.samplemanager.users.model.UserSistema;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import javax.annotation.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author William Aviles
 */
@Controller
@RequestMapping("/capture/subjects/*")
public class CaptureSubjectsController {
	private static final Logger logger = LoggerFactory.getLogger(CaptureSubjectsController.class);
	@Resource(name="subjectService")
	private SubjectService subjectService;
	@Resource(name="studyService")
	private StudyService studyService;
	@Resource(name="usuarioService")
	private UsuarioService usuarioService;
	@Resource(name="auditTrailService")
	private AuditTrailService auditTrailService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;

	
	@Resource(name="visitsService")
	private VisitsService visitsService;
    
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String obtenerEntidades(Model model) throws ParseException { 	
    	logger.debug("Mostrando registros en JSP");
    	List<Subject> entidades = subjectService.getSubjects();
    	model.addAttribute("entidades", entidades);
    	this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"capturesubjectspage");
    	return "capture/subjects/list";
	}
	
	
	/**
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/newEntity/", method = RequestMethod.GET)
	public String initAddEntityForm(Model model) {
    	List<Study> studies = this.studyService.getActiveStudys();
	    model.addAttribute("studies",studies);
	    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"capturenewsubjectpage");
		return "capture/subjects/enterForm";
	}
    
    /**
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/uploadEntity/", method = RequestMethod.GET)
	public String initUploadForm(Model model) {
	    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureuploadsubjectpage");
		return "capture/subjects/uploadForm";
	}
    
    @RequestMapping(value = "/uploadEntityFile/", method = RequestMethod.POST)
	public String submitUploadForm(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException {
    	boolean checkEnrollmentDate = false; boolean isNew=false;
    	String subjectId;
    	String studyId;
    	int nuevos =0, viejos=0;
    	Subject entidad = new Subject();
    	List<Subject> entidades = new ArrayList<Subject>();
    	WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
    	UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());

		try {
			//Read the file
			Reader in = new InputStreamReader(file.getInputStream());
			
			//Define the format
			CSVFormat format = CSVFormat.DEFAULT
				      .withFirstRecordAsHeader()
				      .withIgnoreHeaderCase();
			
			//Create the parser
			CSVParser parsed = CSVParser.parse(in, format);
			
			//Verify that labId Exist in the file
			List<String> encabezados = parsed.getHeaderNames();
			for(String encabezado:encabezados) {
				if(encabezado.equalsIgnoreCase("subjectId")) {
					logger.info(encabezado + " found....");
				}
				else if(encabezado.equalsIgnoreCase("studyId")) {
					logger.info(encabezado + " found....");
				}
				else if(encabezado.equalsIgnoreCase("enrollmentDate")) {
					logger.info(encabezado + " found....");
					checkEnrollmentDate = true;
				}
			}
			
			//Create the records
			Iterable<CSVRecord> records = parsed.getRecords();
			
			//Iterate over the records
		    for (CSVRecord record : records) {
		    	subjectId = record.get("subjectId");
		    	studyId = record.get("studyId");
		        entidad = this.subjectService.getSubjectByUserId(subjectId,studyId);
		        if(entidad==null) {
		        	entidad = new Subject(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
		        	nuevos++; isNew=true;
		        }
		        else {
		        	viejos++; isNew = false;
		        }
			    entidad.setSubjectId(subjectId);
			    entidad.setStudyId(this.studyService.getStudyByUserId(studyId));
			    if (checkEnrollmentDate) {
			    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			    	Date fechaIngreso=null;
			    	if (!record.get("enrollmentDate").equals(""))  fechaIngreso=formatter.parse(record.get("enrollmentDate"));
					entidad.setEnrollmentDate(fechaIngreso);
				}
			    this.subjectService.saveSubject(entidad);
			    entidades.add(entidad);
		    }
		}
		catch(IllegalArgumentException ile) {
			logger.error(ile.getLocalizedMessage());
			modelMap.addAttribute("importError", true);
			modelMap.addAttribute("errorMessage", ile.getLocalizedMessage());
		}
		catch(Exception e) {
			logger.error(e.getLocalizedMessage());
			modelMap.addAttribute("importError", true);
			if(isNew) {
				nuevos--;
			}
			else{
				viejos--; 
			}
			String mensaje ="";
			if(entidad!=null) mensaje = entidad.getSubjectId();
			mensaje = "subjectId: "+ mensaje + "---->" +e.getLocalizedMessage();
			modelMap.addAttribute("errorMessage", mensaje);
			modelMap.addAttribute("entidades", entidades);
			modelMap.addAttribute("nuevos", nuevos);
			modelMap.addAttribute("viejos", viejos);
			return "capture/subjects/uploadResult";
		}
		modelMap.addAttribute("entidades", entidades);
		modelMap.addAttribute("nuevos", nuevos);
		modelMap.addAttribute("viejos", viejos);
	    return "capture/subjects/uploadResult";
	}
    
    
    
    /**
     * Custom handler for displaying a entidad.
     *
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping("/{systemId}/")
    public ModelAndView showEntity(@PathVariable("systemId") String systemId) {
    	ModelAndView mav;
    	Subject entidad = this.subjectService.getSubjectBySystemId(systemId);
        if(entidad==null){
        	mav = new ModelAndView("403");
        }
        else{
        	mav = new ModelAndView("capture/subjects/viewForm");
            List<AuditTrail> bitacoraEntidad = auditTrailService.getBitacora(systemId);
            mav.addObject("entidad",entidad);
            mav.addObject("bitacora",bitacoraEntidad);
            this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureviewsubjectpage");
        }
        return mav;
    }
    
    
	/**
     * Custom handler for editing.
     * @param model Modelo enlazado a la vista
     * @param the ID
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/editEntity/{systemId}/", method = RequestMethod.GET)
	public String initEnterForm(@PathVariable("systemId") String systemId, Model model) {
		Subject entidadEditar = this.subjectService.getSubjectBySystemId(systemId);
		if(entidadEditar!=null){
			List<Study> studies = this.studyService.getActiveStudys();
		    model.addAttribute("studies",studies);
			model.addAttribute("entidad",entidadEditar);
		    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureeditsubjectpage");
			return "capture/subjects/enterForm";
		}
		else{
			return "403";
		}
	}
    
    /**
     * Custom handler for saving.
     * 
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/saveEntity/", method=RequestMethod.POST)
	public ResponseEntity<String> processEntity( @RequestParam(value="systemId", required=false, defaultValue="") String systemId
	        , @RequestParam( value="subjectId", required=true ) String subjectId
	        , @RequestParam( value="studyId", required=true) String studyId
	        , @RequestParam( value="enrollmentDate", required=false, defaultValue="") String enrollmentDate
	        )
	{
    	try{
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    		Date fechaIngreso =  null;
    		WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        	UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
    		Subject entidad = this.subjectService.getSubjectBySystemId(systemId);
			if(entidad==null) entidad = new Subject(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
			if (!systemId.equals("")) entidad.setSystemId(systemId);
			if (!subjectId.equals("")) entidad.setSubjectId(subjectId);
			if (!studyId.equals("")) entidad.setStudyId(this.studyService.getStudyBySystemId(studyId));
			if (!enrollmentDate.equals("")) {
				fechaIngreso=formatter.parse(enrollmentDate);
				entidad.setEnrollmentDate(fechaIngreso);
			}
			this.subjectService.saveSubject(entidad);
			return createJsonResponse(entidad);
    	}
		catch (DataIntegrityViolationException e){
			String message = e.getMostSpecificCause().getMessage();
			Gson gson = new Gson();
		    String json = gson.toJson(message);
		    return createJsonResponse(json);
		}
		catch(Exception e){
			Gson gson = new Gson();
		    String json = gson.toJson(e.toString());
		    return createJsonResponse(json);
		}
    	
	}
    
    /**
     * Custom handler for disabling.
     *
     * @param ident the ID to disable
     * @param redirectAttributes 
     * @return a String
     */
    @RequestMapping("/disableEntity/{ident}/")
    public String disableEntity(@PathVariable("ident") String ident, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
		Subject entidad = this.subjectService.getSubjectBySystemId(ident);
    	if(entidad!=null){
    		entidad.setPasive('1');
    		this.subjectService.saveSubject(entidad);
    		redirectAttributes.addFlashAttribute("disabledEntity", true);
    		redirectAttributes.addFlashAttribute("entityName", entidad.getSubjectId());
    		redirecTo = "redirect:/capture/subjects/"+entidad.getSystemId()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    
    /**
     * Custom handler for enabling.
     *
     * @param ident the ID to enable
     * @param redirectAttributes
     * @return a String
     */
    @RequestMapping("/enableEntity/{ident}/")
    public String enableEntity(@PathVariable("ident") String ident, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
		Subject entidad = this.subjectService.getSubjectBySystemId(ident);
    	if(entidad!=null){
    		entidad.setPasive('0');
    		this.subjectService.saveSubject(entidad);
    		redirectAttributes.addFlashAttribute("enabledEntity", true);
    		redirectAttributes.addFlashAttribute("entityName", entidad.getSubjectId());
    		redirecTo = "redirect:/capture/subjects/"+entidad.getSystemId()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    
    private ResponseEntity<String> createJsonResponse( Object o )
	{
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Content-Type", "application/json");
	    Gson gson = new Gson();
	    String json = gson.toJson(o);
	    return new ResponseEntity<String>( json, headers, HttpStatus.CREATED );
	}
	
}
