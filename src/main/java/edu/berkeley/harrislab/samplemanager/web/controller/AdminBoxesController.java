package edu.berkeley.harrislab.samplemanager.web.controller;

import edu.berkeley.harrislab.samplemanager.domain.Box;
import edu.berkeley.harrislab.samplemanager.domain.Rack;
import edu.berkeley.harrislab.samplemanager.domain.SpecimenStorage;
import edu.berkeley.harrislab.samplemanager.domain.audit.AuditTrail;
import edu.berkeley.harrislab.samplemanager.language.MessageResource;
import edu.berkeley.harrislab.samplemanager.service.AuditTrailService;
import edu.berkeley.harrislab.samplemanager.service.MessageResourceService;
import edu.berkeley.harrislab.samplemanager.service.UsuarioService;
import edu.berkeley.harrislab.samplemanager.service.BoxService;
import edu.berkeley.harrislab.samplemanager.service.RackService;
import edu.berkeley.harrislab.samplemanager.service.SpecimenStorageService;
import edu.berkeley.harrislab.samplemanager.service.VisitsService;
import edu.berkeley.harrislab.samplemanager.users.model.UserSistema;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
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
 * 
 * 
 * @author William Aviles
 */
@Controller
@RequestMapping("/admin/boxes/*")
public class AdminBoxesController {
	private static final Logger logger = LoggerFactory.getLogger(AdminBoxesController.class);
	@Resource(name="boxService")
	private BoxService boxService;
	@Resource(name="rackService")
	private RackService rackService;
	@Resource(name="usuarioService")
	private UsuarioService usuarioService;
	@Resource(name="auditTrailService")
	private AuditTrailService auditTrailService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
	@Resource(name="specimenStorageService")
	private SpecimenStorageService specimenStorageService;
	
	@Resource(name="visitsService")
	private VisitsService visitsService;
    
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String obtenerEntidades(Model model) throws ParseException { 	
    	logger.debug("Mostrando registros en JSP");
    	List<Box> entidades = boxService.getBoxes();
    	model.addAttribute("entidades", entidades);
    	this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"adminboxespage");
    	return "admin/boxes/list";
	}
	
	
	/**
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/newEntity/", method = RequestMethod.GET)
	public String initAddEntityForm(Model model) {
	    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"adminnewboxpage");
	    List<Rack> racks = this.rackService.getActiveRacks();
	    model.addAttribute("racks",racks);
		return "admin/boxes/enterForm";
	}
    
    /**
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/uploadEntity/", method = RequestMethod.GET)
	public String initUploadForm(Model model) {
	    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"adminuploadboxpage");
		return "admin/boxes/uploadForm";
	}
    
    @RequestMapping(value = "/uploadEntityFile/", method = RequestMethod.POST)
	public String submitUploadForm(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException {
    	boolean checkName = false, checkObs = false;
    	String id;
    	String rack;
    	Integer rows,columns;
    	int nuevos =0, viejos=0;
    	Box entidad = new Box();
    	List<Box> entidades = new ArrayList<Box>();
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
				if(encabezado.equalsIgnoreCase("id")) {
					logger.info(encabezado + " found....");
				}
				else if(encabezado.equalsIgnoreCase("name")) {
					logger.info(encabezado + " found....");
					checkName = true;
				}
				else if(encabezado.equalsIgnoreCase("obs")) {
					logger.info(encabezado + " found....");
					checkObs = true;
				}
				else if(encabezado.equalsIgnoreCase("rack")) {
					logger.info(encabezado + " found....");
				}
				else if(encabezado.equalsIgnoreCase("rows")) {
					logger.info(encabezado + " found....");
				}
				else if(encabezado.equalsIgnoreCase("columns")) {
					logger.info(encabezado + " found....");
				}
			}
			
			//Create the records
			Iterable<CSVRecord> records = parsed.getRecords();
			
			//Iterate over the records
		    for (CSVRecord record : records) {
		        id = record.get("id");
		        rack = record.get("rack");
		        rows = Integer.valueOf(record.get("rows"));
		        columns = Integer.valueOf(record.get("columns"));
		        entidad = this.boxService.getBoxByUserId(id);
		        if(entidad==null) {
		        	entidad = new Box(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
		        	nuevos++;
		        }
		        else {
		        	viejos++;
		        }
			    entidad.setId(id);
			    entidad.setRack(this.rackService.getRackByUserId(rack));
		        if (checkName) entidad.setName(record.get("name"));
		        if (checkObs) entidad.setObs(record.get("obs"));
		        entidad.setRows(rows);
		        entidad.setColumns(columns);
		        entidad.setCapacity(rows*columns);
			    this.boxService.saveBox(entidad);
			    entidades.add(entidad);
		    }
		}
		catch(IllegalArgumentException ile) {
			logger.error(ile.getLocalizedMessage());
			modelMap.addAttribute("importError", true);
			modelMap.addAttribute("errorMessage", ile.getLocalizedMessage());
			return "admin/boxes/uploadResult";
		}
		catch(Exception e) {
			logger.error(e.getLocalizedMessage());
			modelMap.addAttribute("importError", true);
			modelMap.addAttribute("errorMessage", e.getLocalizedMessage());
			return "admin/boxes/uploadResult";
		}
		modelMap.addAttribute("entidades", entidades);
		modelMap.addAttribute("nuevos", nuevos);
		modelMap.addAttribute("viejos", viejos);
	    return "admin/boxes/uploadResult";
	}
    
    
    
    /**
     * Custom handler for displaying a entidad.
     *
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping("/{systemId}/")
    public ModelAndView showEntity(@PathVariable("systemId") String systemId) {
    	ModelAndView mav;
    	Box entidad = this.boxService.getBoxBySystemId(systemId);
        if(entidad==null){
        	mav = new ModelAndView("403");
        }
        else{
        	mav = new ModelAndView("admin/boxes/viewForm");
            List<AuditTrail> bitacoraEntidad = auditTrailService.getBitacora(systemId);
            List<SpecimenStorage> muestras = this.specimenStorageService.getSpecimenForBox(systemId);
            for(SpecimenStorage specimenSto:muestras) {
        		MessageResource mr = null;
        		String descCatalogo = null;
        		mr = this.messageResourceService.getMensaje(specimenSto.getSpecimen().getSpecimenType(),"CAT_SP_TYPE");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			specimenSto.getSpecimen().setSpecimenType(descCatalogo);
        		}
        	}
            mav.addObject("entidad",entidad);
            mav.addObject("bitacora",bitacoraEntidad);
            mav.addObject("muestras",muestras);
            this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"adminviewboxpage");
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
		Box entidadEditar = this.boxService.getBoxBySystemId(systemId);
		if(entidadEditar!=null){
			List<Rack> racks = this.rackService.getActiveRacks();
		    model.addAttribute("racks",racks);
			model.addAttribute("entidad",entidadEditar);
		    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"admineditboxpage");
			return "admin/boxes/enterForm";
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
	        , @RequestParam( value="id", required=true ) String id
	        , @RequestParam( value="rack", required=true) String rack
	        , @RequestParam( value="rows", required=true) Integer rows
	        , @RequestParam( value="columns", required=true) Integer columns
	        , @RequestParam( value="name", required=false, defaultValue="") String name
	        , @RequestParam( value="obs", required=false, defaultValue="") String obs
	        )
	{
    	try{
    		WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        	UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
    		Box entidad = this.boxService.getBoxByUserId(id);
			if(entidad==null) entidad = new Box(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
			if (!systemId.equals("")) entidad.setSystemId(systemId);
			if (!id.equals("")) entidad.setId(id);
			if (!rack.equals("")) entidad.setRack(this.rackService.getRackBySystemId(rack));
			if (!name.equals("")) entidad.setName(name);
			entidad.setRows(rows);
			entidad.setColumns(columns);
			entidad.setCapacity(rows*columns);
			if (!obs.equals("")) entidad.setObs(obs);
			this.boxService.saveBox(entidad);
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
		Box entidad = this.boxService.getBoxBySystemId(ident);
    	if(entidad!=null){
    		entidad.setPasive('1');
    		this.boxService.saveBox(entidad);
    		redirectAttributes.addFlashAttribute("disabledEntity", true);
    		redirectAttributes.addFlashAttribute("entityName", entidad.getName());
    		redirecTo = "redirect:/admin/boxes/"+entidad.getSystemId()+"/";
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
		Box entidad = this.boxService.getBoxBySystemId(ident);
    	if(entidad!=null){
    		entidad.setPasive('0');
    		this.boxService.saveBox(entidad);
    		redirectAttributes.addFlashAttribute("enabledEntity", true);
    		redirectAttributes.addFlashAttribute("entityName", entidad.getName());
    		redirecTo = "redirect:/admin/boxes/"+entidad.getSystemId()+"/";
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
