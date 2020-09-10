package edu.berkeley.harrislab.samplemanager.web.controller;

import edu.berkeley.harrislab.samplemanager.domain.Rack;
import edu.berkeley.harrislab.samplemanager.domain.Equip;
import edu.berkeley.harrislab.samplemanager.domain.audit.AuditTrail;
import edu.berkeley.harrislab.samplemanager.service.AuditTrailService;
import edu.berkeley.harrislab.samplemanager.service.MessageResourceService;
import edu.berkeley.harrislab.samplemanager.service.UsuarioService;
import edu.berkeley.harrislab.samplemanager.service.RackService;
import edu.berkeley.harrislab.samplemanager.service.EquipService;
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
 * 
 * 
 * @author William Aviles
 */
@Controller
@RequestMapping("/admin/racks/*")
public class AdminRacksController {
	private static final Logger logger = LoggerFactory.getLogger(AdminRacksController.class);
	@Resource(name="rackService")
	private RackService rackService;
	@Resource(name="equipService")
	private EquipService equipService;
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
    	List<Rack> entidades = rackService.getRacks();
    	model.addAttribute("entidades", entidades);
    	this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"adminrackspage");
    	return "admin/racks/list";
	}
	
	
	/**
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/newEntity/", method = RequestMethod.GET)
	public String initAddEntityForm(Model model) {
	    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"adminnewrackpage");
	    List<Equip> equips = this.equipService.getActiveEquips();
	    model.addAttribute("equips",equips);
		return "admin/racks/enterForm";
	}
    
    /**
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/uploadEntity/", method = RequestMethod.GET)
	public String initUploadForm(Model model) {
	    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"adminuploadrackpage");
		return "admin/racks/uploadForm";
	}
    
    @RequestMapping(value = "/uploadEntityFile/", method = RequestMethod.POST)
	public String submitUploadForm(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException {
    	boolean checkName = false, checkObs = false;
    	String id;
    	String equip;
    	int nuevos =0, viejos=0;
    	Rack entidad = new Rack();
    	List<Rack> entidades = new ArrayList<Rack>();
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
				else if(encabezado.equalsIgnoreCase("equip")) {
					logger.info(encabezado + " found....");
				}
			}
			
			//Create the records
			Iterable<CSVRecord> records = parsed.getRecords();
			
			//Iterate over the records
		    for (CSVRecord record : records) {
		        id = record.get("id");
		        equip = record.get("equip");
		        entidad = this.rackService.getRackByUserId(id);
		        if(entidad==null) {
		        	entidad = new Rack(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
		        	nuevos++;
		        }
		        else {
		        	viejos++;
		        }
			    entidad.setId(id);
			    entidad.setEquip(this.equipService.getEquipByUserId(equip));
		        if (checkName) entidad.setName(record.get("name"));
		        if (checkObs) entidad.setObs(record.get("obs"));
			    this.rackService.saveRack(entidad);
			    entidades.add(entidad);
		    }
		}
		catch(IllegalArgumentException ile) {
			logger.error(ile.getLocalizedMessage());
			modelMap.addAttribute("importError", true);
			modelMap.addAttribute("errorMessage", ile.getLocalizedMessage());
			return "admin/racks/uploadResult";
		}
		catch(Exception e) {
			logger.error(e.getLocalizedMessage());
			modelMap.addAttribute("importError", true);
			modelMap.addAttribute("errorMessage", e.getLocalizedMessage());
			return "admin/racks/uploadResult";
		}
		modelMap.addAttribute("entidades", entidades);
		modelMap.addAttribute("nuevos", nuevos);
		modelMap.addAttribute("viejos", viejos);
	    return "admin/racks/uploadResult";
	}
    
    
    
    /**
     * Custom handler for displaying a entidad.
     *
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping("/{systemId}/")
    public ModelAndView showEntity(@PathVariable("systemId") String systemId) {
    	ModelAndView mav;
    	Rack entidad = this.rackService.getRackBySystemId(systemId);
        if(entidad==null){
        	mav = new ModelAndView("403");
        }
        else{
        	mav = new ModelAndView("admin/racks/viewForm");
            List<AuditTrail> bitacoraEntidad = auditTrailService.getBitacora(systemId);
            mav.addObject("entidad",entidad);
            mav.addObject("bitacora",bitacoraEntidad);
            this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"adminviewrackpage");
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
		Rack entidadEditar = this.rackService.getRackBySystemId(systemId);
		if(entidadEditar!=null){
			List<Equip> equips = this.equipService.getActiveEquips();
		    model.addAttribute("equips",equips);
			model.addAttribute("entidad",entidadEditar);
		    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"admineditrackpage");
			return "admin/racks/enterForm";
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
	        , @RequestParam( value="equip", required=true) String equip
	        , @RequestParam( value="name", required=false, defaultValue="") String name
	        , @RequestParam( value="obs", required=false, defaultValue="") String obs
	        )
	{
    	try{
    		WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        	UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
    		Rack entidad = this.rackService.getRackByUserId(id);
			if(entidad==null) entidad = new Rack(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
			if (!systemId.equals("")) entidad.setSystemId(systemId);
			if (!id.equals("")) entidad.setId(id);
			if (!equip.equals("")) entidad.setEquip(this.equipService.getEquipBySystemId(equip));
			if (!name.equals("")) entidad.setName(name);
			if (!obs.equals("")) entidad.setObs(obs);
			this.rackService.saveRack(entidad);
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
		Rack entidad = this.rackService.getRackBySystemId(ident);
    	if(entidad!=null){
    		entidad.setPasive('1');
    		this.rackService.saveRack(entidad);
    		redirectAttributes.addFlashAttribute("disabledEntity", true);
    		redirectAttributes.addFlashAttribute("entityName", entidad.getName());
    		redirecTo = "redirect:/admin/racks/"+entidad.getSystemId()+"/";
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
		Rack entidad = this.rackService.getRackBySystemId(ident);
    	if(entidad!=null){
    		entidad.setPasive('0');
    		this.rackService.saveRack(entidad);
    		redirectAttributes.addFlashAttribute("enabledEntity", true);
    		redirectAttributes.addFlashAttribute("entityName", entidad.getName());
    		redirecTo = "redirect:/admin/racks/"+entidad.getSystemId()+"/";
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
