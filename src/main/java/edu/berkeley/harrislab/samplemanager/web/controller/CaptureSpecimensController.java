package edu.berkeley.harrislab.samplemanager.web.controller;

import edu.berkeley.harrislab.samplemanager.domain.Box;
import edu.berkeley.harrislab.samplemanager.domain.Equip;
import edu.berkeley.harrislab.samplemanager.domain.Specimen;
import edu.berkeley.harrislab.samplemanager.domain.SpecimenStorage;
import edu.berkeley.harrislab.samplemanager.domain.Subject;
import edu.berkeley.harrislab.samplemanager.domain.audit.AuditTrail;
import edu.berkeley.harrislab.samplemanager.language.MessageResource;
import edu.berkeley.harrislab.samplemanager.service.AuditTrailService;
import edu.berkeley.harrislab.samplemanager.service.BoxService;
import edu.berkeley.harrislab.samplemanager.service.EquipService;
import edu.berkeley.harrislab.samplemanager.service.MessageResourceService;
import edu.berkeley.harrislab.samplemanager.service.RackService;
import edu.berkeley.harrislab.samplemanager.service.StudyService;
import edu.berkeley.harrislab.samplemanager.service.SubjectService;
import edu.berkeley.harrislab.samplemanager.service.UsuarioService;
import edu.berkeley.harrislab.samplemanager.service.SpecimenService;
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
@RequestMapping("/capture/specimens/*")
public class CaptureSpecimensController {
	private static final Logger logger = LoggerFactory.getLogger(CaptureSpecimensController.class);
	@Resource(name="specimenService")
	private SpecimenService specimenService;
	@Resource(name="specimenStorageService")
	private SpecimenStorageService specimenStorageService;
	@Resource(name="studyService")
	private StudyService studyService;
	@Resource(name="usuarioService")
	private UsuarioService usuarioService;
	@Resource(name="auditTrailService")
	private AuditTrailService auditTrailService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
	@Resource(name="equipService")
	private EquipService equipService;
	@Resource(name="rackService")
	private RackService rackService;
	@Resource(name="boxService")
	private BoxService boxService;
	@Resource(name="subjectService")
	private SubjectService subjectService;
	
	@Resource(name="visitsService")
	private VisitsService visitsService;
    
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String obtenerEntidades(Model model) throws ParseException { 	
    	logger.debug("Mostrando registros en JSP");
    	List<Specimen> entidades = specimenService.getSpecimens();
    	for(Specimen specimen:entidades) {
    		MessageResource mr = null;
    		String descCatalogo = null;
    		mr = this.messageResourceService.getMensaje(specimen.getSpecimenType(),"CAT_SP_TYPE");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			specimen.setSpecimenType(descCatalogo);
    		}
    		mr = this.messageResourceService.getMensaje(specimen.getInStorage(),"CAT_SINO");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			specimen.setInStorage(descCatalogo);
    		}
    		mr = this.messageResourceService.getMensaje(specimen.getVolUnits(),"CAT_VOL_UNITS");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			specimen.setVolUnits(descCatalogo);
    		}
    		mr = this.messageResourceService.getMensaje(specimen.getSpecimenCondition(),"CAT_SP_COND");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			specimen.setSpecimenCondition(descCatalogo);
    		}
    	}
    	model.addAttribute("entidades", entidades);
    	this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"capturespecimenspage");
    	return "capture/specimens/list";
	}
	
	
	/**
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/newEntity/", method = RequestMethod.GET)
	public String initAddEntityForm(Model model) {
    	List<MessageResource> types = this.messageResourceService.getCatalogo("CAT_SP_TYPE");
	    model.addAttribute("types",types);
	    List<MessageResource> sinos = this.messageResourceService.getCatalogo("CAT_SINO");
	    model.addAttribute("sinos",sinos);
	    List<MessageResource> volUnits = this.messageResourceService.getCatalogo("CAT_VOL_UNITS");
	    model.addAttribute("volUnits",volUnits);
	    List<MessageResource> conditions = this.messageResourceService.getCatalogo("CAT_SP_COND");
	    model.addAttribute("conditions",conditions);
	    List<Subject> subjects = this.subjectService.getActiveSubjects();
	    model.addAttribute("subjects",subjects);
	    List<Equip> equips = this.equipService.getActiveEquips();
	    model.addAttribute("equips",equips);
	    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"capturenewspecimenpage");
		return "capture/specimens/enterNewForm";
	}
    
    
    
    /**
     * Custom handler for displaying a entidad.
     *
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping("/{systemId}/")
    public ModelAndView showEntity(@PathVariable("systemId") String systemId) {
    	ModelAndView mav;
    	Specimen entidad = this.specimenService.getSpecimenBySystemId(systemId);
        if(entidad==null){
        	mav = new ModelAndView("403");
        }
        else{
        	mav = new ModelAndView("capture/specimens/viewForm");
        	MessageResource mr = null;
    		String descCatalogo = null;
    		mr = this.messageResourceService.getMensaje(entidad.getSpecimenType(),"CAT_SP_TYPE");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			entidad.setSpecimenType(descCatalogo);
    		}
    		mr = this.messageResourceService.getMensaje(entidad.getInStorage(),"CAT_SINO");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			entidad.setInStorage(descCatalogo);
    		}
    		mr = this.messageResourceService.getMensaje(entidad.getVolUnits(),"CAT_VOL_UNITS");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			entidad.setVolUnits(descCatalogo);
    		}
    		mr = this.messageResourceService.getMensaje(entidad.getSpecimenCondition(),"CAT_SP_COND");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			entidad.setSpecimenCondition(descCatalogo);
    		}
            List<AuditTrail> bitacoraEntidad = auditTrailService.getBitacora(systemId);
            
            SpecimenStorage entidad2 = this.specimenStorageService.getSpecimenStorageBySpecId(systemId);
            List<AuditTrail> bitacoraEntidad2 = null;
            if (entidad2!=null) {
            	bitacoraEntidad2 = auditTrailService.getBitacora(entidad2.getStorageId());
            	for(AuditTrail b:bitacoraEntidad2) {
                	bitacoraEntidad.add(b);
                }
            }
            mav.addObject("entidad",entidad);
            mav.addObject("entidad2",entidad2);
            mav.addObject("bitacora",bitacoraEntidad);
            this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureviewspecimenpage");
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
		Specimen entidadEditar = this.specimenService.getSpecimenBySystemId(systemId);
		if(entidadEditar!=null){
			List<MessageResource> types = this.messageResourceService.getCatalogo("CAT_SP_TYPE");
		    model.addAttribute("types",types);
		    List<MessageResource> sinos = this.messageResourceService.getCatalogo("CAT_SINO");
		    model.addAttribute("sinos",sinos);
		    List<MessageResource> volUnits = this.messageResourceService.getCatalogo("CAT_VOL_UNITS");
		    model.addAttribute("volUnits",volUnits);
		    List<MessageResource> conditions = this.messageResourceService.getCatalogo("CAT_SP_COND");
		    model.addAttribute("conditions",conditions);
		    List<Subject> subjects = this.subjectService.getActiveSubjects();
		    model.addAttribute("subjects",subjects);
		    List<Box> boxes = this.boxService.getActiveBoxes();
		    model.addAttribute("boxes",boxes);
			model.addAttribute("entidad",entidadEditar);
		    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureeditspecimenpage");
			return "capture/specimens/editForm";
		}
		else{
			return "403";
		}
	}
    
    
	/**
     * Custom handler for editing.
     * @param model Modelo enlazado a la vista
     * @param the ID
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/editStorageEntity/{systemId}/", method = RequestMethod.GET)
	public String initEditStorageForm(@PathVariable("systemId") String systemId, Model model) {
		SpecimenStorage entidadEditar = this.specimenStorageService.getSpecimenStorageBySystemId(systemId);
		if(entidadEditar!=null){
			
			List<Equip> equips = this.equipService.getActiveEquips();
		    model.addAttribute("equips",equips);
			model.addAttribute("entidad",entidadEditar);
		    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureeditspecimenpage");
			return "capture/specimens/editStorageForm";
		}
		else{
			return "403";
		}
	}
    
    
    /**
     * Custom handler for disabling.
     *
     * @param ident the ID to disable
     * @param redirectAttributes 
     * @return a String
     */
    @RequestMapping("/deleteStorageEntity/{systemId}/")
    public String disableStorageEntity(@PathVariable("systemId") String systemId, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
    	SpecimenStorage entidadEliminar = this.specimenStorageService.getSpecimenStorageBySystemId(systemId);
    	if(entidadEliminar!=null){
    		Specimen spec = entidadEliminar.getSpecimen();
    		this.specimenStorageService.deleteSpecimenStorage(entidadEliminar);
    		spec.setInStorage("0");
    		this.specimenService.saveSpecimen(spec);
    		redirectAttributes.addFlashAttribute("disabledEntity", true);
    		redirectAttributes.addFlashAttribute("entityName", spec.getSpecimenId());
    		redirecTo = "redirect:/capture/specimens/"+spec.getSystemId()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    /**
     * Custom handler for saving.
     * 
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/saveEntity/", method=RequestMethod.POST)
	public ResponseEntity<String> processNewEntity(@RequestParam( value="specimenId", required=true ) String specimenId
	        , @RequestParam( value="specimenType", required=true) String specimenType
	        , @RequestParam( value="specimenCondition", required=false, defaultValue="") String specimenCondition
	        , @RequestParam( value="labReceiptDate", required=true) String labReceiptDate
	        , @RequestParam( value="volume", required=false, defaultValue="") String volume
	        , @RequestParam( value="varA", required=false, defaultValue="") String varA
	        , @RequestParam( value="varB", required=false, defaultValue="") String varB
	        , @RequestParam( value="volUnits", required=false, defaultValue="") String volUnits
	        , @RequestParam( value="obs", required=false, defaultValue="") String obs
	        , @RequestParam( value="subjectSpecId", required=false, defaultValue="") String subjectSpecId
	        , @RequestParam( value="inStorage", required=true) String inStorage
	        , @RequestParam( value="storageDate", required=false, defaultValue="") String storageDate
	        , @RequestParam( value="orthocode", required=false, defaultValue="") String orthocode
	        , @RequestParam( value="boxSpecId", required=false, defaultValue="") String boxSpecId
	        , @RequestParam( value="position", required=false, defaultValue="") String position
	        )
	{
    	try{
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    		Date fechaIngreso =  null;
    		Date fechaAlmacenamiento =  null;
    		WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        	UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
    		Specimen entidad  = new Specimen(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
    		SpecimenStorage entidad2 = null;
			if (!specimenId.equals("")) entidad.setSpecimenId(specimenId);
			if (!specimenType.equals("")) entidad.setSpecimenType(specimenType);
			if (!specimenCondition.equals("")) entidad.setSpecimenCondition(specimenCondition);
			if (!labReceiptDate.equals("")) {
				fechaIngreso=formatter.parse(labReceiptDate);
				entidad.setLabReceiptDate(fechaIngreso);
			}
			if (!inStorage.equals("")) entidad.setInStorage(inStorage);
			if (!volume.equals("")) entidad.setVolume(Float.valueOf(volume));
			if (!varA.equals("")) entidad.setVarA(Integer.valueOf(varA));
			if (!varB.equals("")) entidad.setVarB(Integer.valueOf(varB));
			if (!volUnits.equals("")) entidad.setVolUnits(volUnits);
			if (!obs.equals("")) entidad.setObs(obs);
			if (!orthocode.equals("")) entidad.setOrthocode(orthocode);
			if (!subjectSpecId.equals("")) entidad.setSubjectId(this.subjectService.getSubjectBySystemId(subjectSpecId));
			if (entidad.getInStorage().equals("1")) {
				entidad2 = new SpecimenStorage(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
				entidad2.setSpecimen(entidad);
				if (!storageDate.equals("")) {
					fechaAlmacenamiento=formatter.parse(storageDate);
					entidad2.setStorageDate(fechaAlmacenamiento);
				}
				if (!boxSpecId.equals("")) entidad2.setBox(this.boxService.getBoxBySystemId(boxSpecId));
				if (!position.equals("")) entidad2.setPos(Integer.valueOf(position));
				this.specimenStorageService.saveSpecimenStorage(entidad2);
			}else {
				this.specimenService.saveSpecimen(entidad);
			}
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
     * Custom handler for saving.
     * 
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/saveEditEntity/", method=RequestMethod.POST)
	public ResponseEntity<String> processEditEntity( @RequestParam(value="systemId", required=true) String systemId
	        , @RequestParam( value="specimenId", required=true ) String specimenId
	        , @RequestParam( value="specimenType", required=true) String specimenType
	        , @RequestParam( value="specimenCondition", required=false, defaultValue="") String specimenCondition
	        , @RequestParam( value="labReceiptDate", required=true) String labReceiptDate
	        , @RequestParam( value="varA", required=false, defaultValue="") String varA
	        , @RequestParam( value="varB", required=false, defaultValue="") String varB
	        , @RequestParam( value="volume", required=false, defaultValue="") String volume
	        , @RequestParam( value="volUnits", required=false, defaultValue="") String volUnits
	        , @RequestParam( value="obs", required=false, defaultValue="") String obs
	        , @RequestParam( value="orthocode", required=false, defaultValue="") String orthocode
	        , @RequestParam( value="subjectSpecId", required=false, defaultValue="") String subjectSpecId
	        )
	{
    	try{
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    		Date fechaIngreso =  null;
    		Specimen entidad = this.specimenService.getSpecimenBySystemId(systemId);
			if (!systemId.equals("")) entidad.setSystemId(systemId);
			if (!specimenId.equals("")) entidad.setSpecimenId(specimenId);
			if (!specimenType.equals("")) entidad.setSpecimenType(specimenType);
			if (!specimenCondition.equals("")) entidad.setSpecimenCondition(specimenCondition);
			if (!labReceiptDate.equals("")) {
				fechaIngreso=formatter.parse(labReceiptDate);
				entidad.setLabReceiptDate(fechaIngreso);
			}
			if (!volume.equals("")) {
				entidad.setVolume(Float.valueOf(volume));
			}else {
				entidad.setVolume(null);
			}
			if (!varA.equals("")) {
				entidad.setVarA(Integer.valueOf(varA));
			}else {
				entidad.setVarA(null);
			}
			if (!varB.equals("")) {
				entidad.setVarB(Integer.valueOf(varB));
			}else {
				entidad.setVarB(null);
			}
			if (!volUnits.equals("")) {
				entidad.setVolUnits(volUnits);
			}else {
				entidad.setVolUnits(null);
			}
			entidad.setObs(obs);
			entidad.setOrthocode(orthocode);
			if (!subjectSpecId.equals("")) {
				entidad.setSubjectId(this.subjectService.getSubjectBySystemId(subjectSpecId));
			}else {
				entidad.setSubjectId(null);
			}
			this.specimenService.saveSpecimen(entidad);
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
     * Custom handler for saving.
     * 
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/saveStorageEntity/", method=RequestMethod.POST)
	public ResponseEntity<String> processStorageEntity(@RequestParam(value="storageId", required=true) String storageId
			, @RequestParam( value="storageDate", required=false, defaultValue="") String storageDate
	        , @RequestParam( value="boxSpecId", required=false, defaultValue="") String boxSpecId
	        , @RequestParam( value="position", required=false, defaultValue="") String position
	        )
	{
    	try{
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    		Date fechaAlmacenamiento =  null;
    		SpecimenStorage entidad = this.specimenStorageService.getSpecimenStorageBySystemId(storageId);
    		if (!storageDate.equals("")) {
				fechaAlmacenamiento=formatter.parse(storageDate);
				entidad.setStorageDate(fechaAlmacenamiento);
			}
			if (!boxSpecId.equals("")) entidad.setBox(this.boxService.getBoxBySystemId(boxSpecId));
			if (!position.equals("")) entidad.setPos(Integer.valueOf(position));
			this.specimenStorageService.updateSpecimenStorage(entidad);
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
		Specimen entidad = this.specimenService.getSpecimenBySystemId(ident);
    	if(entidad!=null){
    		entidad.setPasive('1');
    		this.specimenService.saveSpecimen(entidad);
    		redirectAttributes.addFlashAttribute("disabledEntity", true);
    		redirectAttributes.addFlashAttribute("entityName", entidad.getSpecimenId());
    		redirecTo = "redirect:/capture/specimens/"+entidad.getSystemId()+"/";
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
		Specimen entidad = this.specimenService.getSpecimenBySystemId(ident);
    	if(entidad!=null){
    		entidad.setPasive('0');
    		this.specimenService.saveSpecimen(entidad);
    		redirectAttributes.addFlashAttribute("enabledEntity", true);
    		redirectAttributes.addFlashAttribute("entityName", entidad.getSpecimenId());
    		redirecTo = "redirect:/capture/specimens/"+entidad.getSystemId()+"/";
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
    
    
    /**
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/uploadEntity/", method = RequestMethod.GET)
	public String initUploadForm(Model model) {
	    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureuploadspecimenpage");
		return "capture/specimens/uploadForm";
	}
    
    @RequestMapping(value = "/uploadEntityFile/", method = RequestMethod.POST)
	public String submitUploadForm(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException {
    	boolean checkLabReceiptDate = false;
    	String specimenId;
    	String specimenType,specimenTypeCatKey,inStorage;
    	int nuevos =0, viejos=0;
    	Specimen entidad = new Specimen();
    	List<Specimen> entidades = new ArrayList<Specimen>();
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
				if(encabezado.equalsIgnoreCase("specimenId")) {
					logger.info(encabezado + " found....");
				}
				else if(encabezado.equalsIgnoreCase("specimenType")) {
					logger.info(encabezado + " found....");
				}
				else if(encabezado.equalsIgnoreCase("labReceiptDate")) {
					logger.info(encabezado + " found....");
					checkLabReceiptDate = true;
				}
				else if(encabezado.equalsIgnoreCase("inStorage")) {
					logger.info(encabezado + " found....");
				}
			}
			
			//Create the records
			Iterable<CSVRecord> records = parsed.getRecords();
			
			//Iterate over the records
		    for (CSVRecord record : records) {
		    	specimenId = record.get("specimenId");
		    	specimenType = record.get("specimenType");
		    	inStorage = record.get("inStorage");
		        entidad = this.specimenService.getSpecimenByUserId(specimenId);
		        if(entidad==null) {
		        	entidad = new Specimen(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
		        	nuevos++;
		        }
		        else {
		        	viejos++;
		        }
			    entidad.setSpecimenId(specimenId);
			    specimenTypeCatKey = this.messageResourceService.getMensajeDesc(specimenType,"CAT_SP_TYPE").getCatKey();
			    entidad.setSpecimenType(specimenTypeCatKey);
			    if (checkLabReceiptDate) {
			    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			    	Date fechaIngreso=null;
			    	if (!record.get("labReceiptDate").equals(""))  fechaIngreso=formatter.parse(record.get("labReceiptDate"));
					entidad.setLabReceiptDate(fechaIngreso);
				}
			    entidad.setInStorage(inStorage);
			    this.specimenService.saveSpecimen(entidad);
			    entidades.add(entidad);
		    }
		}
		catch(IllegalArgumentException ile) {
			logger.error(ile.getLocalizedMessage());
			modelMap.addAttribute("importError", true);
			modelMap.addAttribute("errorMessage", ile.getLocalizedMessage());
			return "capture/specimens/uploadResult";
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			modelMap.addAttribute("importError", true);
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "capture/specimens/uploadResult";
		}
		modelMap.addAttribute("entidades", entidades);
		modelMap.addAttribute("nuevos", nuevos);
		modelMap.addAttribute("viejos", viejos);
		for(Specimen specimen:entidades) {
    		MessageResource mr = null;
    		String descCatalogo = null;
    		mr = this.messageResourceService.getMensaje(specimen.getSpecimenType(),"CAT_SP_TYPE");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			specimen.setSpecimenType(descCatalogo);
    		}
    	}
	    return "capture/specimens/uploadResult";
	}    
	
}
