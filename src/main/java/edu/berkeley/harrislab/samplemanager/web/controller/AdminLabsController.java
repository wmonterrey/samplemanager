package edu.berkeley.harrislab.samplemanager.web.controller;

import edu.berkeley.harrislab.samplemanager.domain.Lab;
import edu.berkeley.harrislab.samplemanager.domain.audit.AuditTrail;
import edu.berkeley.harrislab.samplemanager.service.AuditTrailService;
import edu.berkeley.harrislab.samplemanager.service.MessageResourceService;
import edu.berkeley.harrislab.samplemanager.service.UsuarioService;
import edu.berkeley.harrislab.samplemanager.service.LabService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controlador web de peticiones relacionadas a laboratorios
 * 
 * @author William Aviles
 */
@Controller
@RequestMapping("/admin/labs/*")
public class AdminLabsController {
	private static final Logger logger = LoggerFactory.getLogger(AdminLabsController.class);
	@Resource(name="labService")
	private LabService labService;
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
    	logger.debug("Mostrando Laboratorios en JSP");
    	List<Lab> laboratorios = labService.getLabs();
    	model.addAttribute("laboratorios", laboratorios);
    	this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"adminlabspage");
    	return "admin/labs/list";
	}
	
	
	/**
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/newLab/", method = RequestMethod.GET)
	public String initAddEntityForm(Model model) {
	    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"adminnewlabpage");
		return "admin/labs/enterForm";
	}
    
    /**
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/uploadLab/", method = RequestMethod.GET)
	public String initUploadForm(Model model) {
	    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"adminuploadlabpage");
		return "admin/labs/uploadForm";
	}
    
    @RequestMapping(value = "/uploadLabFile/", method = RequestMethod.POST)
	public String submitUploadForm(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException {
    	boolean checkLabName = false, checkLabContact = false, checkLabAddress = false, checkLabPhoneNumber = false, checkLabEmail = false, checkLabObs = false;
    	String labId;
    	int nuevos =0, viejos=0;
    	Lab lab = new Lab();
    	List<Lab> laboratorios = new ArrayList<Lab>();
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
				if(encabezado.equalsIgnoreCase("labId")) {
					logger.info(encabezado + " found....");
				}
				else if(encabezado.equalsIgnoreCase("labName")) {
					logger.info(encabezado + " found....");
					checkLabName = true;
				}
				else if(encabezado.equalsIgnoreCase("labContact")) {
					logger.info(encabezado + " found....");
					checkLabContact = true;
				}
				else if(encabezado.equalsIgnoreCase("labAddress")) {
					logger.info(encabezado + " found....");
					checkLabAddress = true;
				}
				else if(encabezado.equalsIgnoreCase("labPhoneNumber")) {
					logger.info(encabezado + " found....");
					checkLabPhoneNumber = true;
				}
				else if(encabezado.equalsIgnoreCase("labEmail")) {
					logger.info(encabezado + " found....");
					checkLabEmail = true;
				}
				else if(encabezado.equalsIgnoreCase("labObs")) {
					logger.info(encabezado + " found....");
					checkLabObs = true;
				}
			}
			
			//Create the records
			Iterable<CSVRecord> records = parsed.getRecords();
			
			//Iterate over the records
		    for (CSVRecord record : records) {
		        labId = record.get("labId");
		        lab = this.labService.getLabByUserId(labId);
		        if(lab==null) {
		        	lab = new Lab(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
		        	nuevos++;
		        }
		        else {
		        	viejos++;
		        }
			    lab.setLabId(labId);
		        if (checkLabName) lab.setLabName(record.get("labName"));
		        if (checkLabContact) lab.setLabContact(record.get("labContact"));
		        if (checkLabAddress) lab.setLabAddress(record.get("labAddress"));
		        if (checkLabPhoneNumber) lab.setLabPhoneNumber(record.get("labPhoneNumber"));
		        if (checkLabEmail) lab.setLabEmail(record.get("labEmail"));
		        if (checkLabObs) lab.setLabObs(record.get("labObs"));
			    this.labService.saveLab(lab);
		        laboratorios.add(lab);
		    }
		}
		catch(IllegalArgumentException ile) {
			logger.error(ile.getLocalizedMessage());
			modelMap.addAttribute("importError", true);
			modelMap.addAttribute("errorMessage", ile.getLocalizedMessage());
			return "admin/labs/uploadResult";
		}
		catch(Exception e) {
			logger.error(e.getLocalizedMessage());
			modelMap.addAttribute("importError", true);
			modelMap.addAttribute("errorMessage", e.getLocalizedMessage());
			return "admin/labs/uploadResult";
		}
		modelMap.addAttribute("laboratorios", laboratorios);
		modelMap.addAttribute("nuevos", nuevos);
		modelMap.addAttribute("viejos", viejos);
	    return "admin/labs/uploadResult";
	}
    
    
    
    /**
     * Custom handler for displaying a lab.
     *
     * @param labSystemId the ID of the lab to display
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping("/{labSystemId}/")
    public ModelAndView showEntity(@PathVariable("labSystemId") String labSystemId) {
    	ModelAndView mav;
    	Lab lab = this.labService.getLabBySystemId(labSystemId);
        if(lab==null){
        	mav = new ModelAndView("403");
        }
        else{
        	mav = new ModelAndView("admin/labs/viewForm");
            List<AuditTrail> bitacoraLaboratorio = auditTrailService.getBitacora(labSystemId);
            mav.addObject("lab",lab);
            mav.addObject("bitacora",bitacoraLaboratorio);
            this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"adminviewlabpage");
        }
        return mav;
    }
    
    
	/**
     * Custom handler for editing.
     * @param model Modelo enlazado a la vista
     * @param the ID
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/editEntity/{id}/", method = RequestMethod.GET)
	public String initEnterForm(@PathVariable("id") String id, Model model) {
		Lab entidadEditar = this.labService.getLabBySystemId(id);
		if(entidadEditar!=null){
			model.addAttribute("entidad",entidadEditar);
		    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"admineditlabpage");
			return "admin/labs/enterForm";
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
	public ResponseEntity<String> processEntity( @RequestParam(value="labSystemId", required=false, defaultValue="") String labSystemId
	        , @RequestParam( value="labId", required=true ) String labId
	        , @RequestParam( value="labName", required=false, defaultValue="") String labName
	        , @RequestParam( value="labContact", required=false, defaultValue="") String labContact
	        , @RequestParam( value="labAddress", required=false, defaultValue="") String labAddress
	        , @RequestParam( value="labPhoneNumber", required=false, defaultValue="") String labPhoneNumber
	        , @RequestParam( value="labEmail", required=false, defaultValue="") String labEmail
	        , @RequestParam( value="labObs", required=false, defaultValue="") String labObs
	        )
	{
    	try{
    		WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        	UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
    		Lab lab = this.labService.getLabByUserId(labId);
			if(lab==null) lab = new Lab(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
			if (!labSystemId.equals("")) lab.setLabSystemId(labSystemId);
			if (!labId.equals("")) lab.setLabId(labId);
			if (!labName.equals("")) lab.setLabName(labName);
			if (!labContact.equals("")) lab.setLabContact(labContact);
			if (!labAddress.equals("")) lab.setLabAddress(labAddress);
			if (!labPhoneNumber.equals("")) lab.setLabPhoneNumber(labPhoneNumber);
			if (!labEmail.equals("")) lab.setLabEmail(labEmail);
			if (!labObs.equals("")) lab.setLabObs(labObs);
			this.labService.saveLab(lab);
			return createJsonResponse(lab);
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
		Lab lab = this.labService.getLabBySystemId(ident);
    	if(lab!=null){
    		lab.setPasive('1');
    		this.labService.saveLab(lab);
    		redirectAttributes.addFlashAttribute("disabledEntity", true);
    		redirectAttributes.addFlashAttribute("entityName", lab.getLabName());
    		redirecTo = "redirect:/admin/labs/"+lab.getLabSystemId()+"/";
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
		Lab lab = this.labService.getLabBySystemId(ident);
    	if(lab!=null){
    		lab.setPasive('0');
    		this.labService.saveLab(lab);
    		redirectAttributes.addFlashAttribute("enabledEntity", true);
    		redirectAttributes.addFlashAttribute("entityName", lab.getLabName());
    		redirecTo = "redirect:/admin/labs/"+lab.getLabSystemId()+"/";
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
