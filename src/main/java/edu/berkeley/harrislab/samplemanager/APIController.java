package edu.berkeley.harrislab.samplemanager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.berkeley.harrislab.samplemanager.domain.Box;
import edu.berkeley.harrislab.samplemanager.domain.BoxAliquots;
import edu.berkeley.harrislab.samplemanager.domain.Equip;
import edu.berkeley.harrislab.samplemanager.domain.Rack;
import edu.berkeley.harrislab.samplemanager.domain.SpecimenStorage;
import edu.berkeley.harrislab.samplemanager.language.MessageResource;
import edu.berkeley.harrislab.samplemanager.service.BoxService;
import edu.berkeley.harrislab.samplemanager.service.EquipService;
import edu.berkeley.harrislab.samplemanager.service.MessageResourceService;
import edu.berkeley.harrislab.samplemanager.service.RackService;
import edu.berkeley.harrislab.samplemanager.service.SpecimenStorageService;




/**
 * 
 * @author William Aviles
 **/
@Controller
@RequestMapping("/api/*")
public class APIController {
	
	@Resource(name="equipService")
	private EquipService equipService;
	@Resource(name="rackService")
	private RackService rackService;
	@Resource(name="boxService")
	private BoxService boxService;
	@Resource(name="specimenStorageService")
	private SpecimenStorageService specimenStorageService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
	
    private static final Logger logger = LoggerFactory.getLogger(APIController.class);

    /**
     * Retorna una lista de racks. Acepta una solicitud GET para JSON
     * @return Un arreglo JSON de racks
	 * @throws ParseException 
     */
    
    @RequestMapping(value = "racks", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Rack> fetchRacksJson(@RequestParam(value = "equipId", required = true) String equipId) throws ParseException {
        logger.info("Obteniendo los racks en JSON");
        List<Rack> racks = null; 
        Equip equipment = equipService.getEquipBySystemId(equipId);
        if (equipment!=null){
        	racks = rackService.getRacks(equipId);
        }
        return racks;	
    }
    
    
    @RequestMapping(value = "boxes", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Box> fetchBoxesJson(@RequestParam(value = "rackId", required = true) String rackId) throws ParseException {
        logger.info("Obteniendo las cajas en JSON");
        List<Box> boxes = null; 
        Rack rack = this.rackService.getRackBySystemId(rackId);
        if (rack!=null){
        	boxes = boxService.getActiveBoxes(rackId);
        }
        return boxes;	
    }
    
    @RequestMapping(value = "positions", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<String> fetchPositionsJson(@RequestParam(value = "boxId", required = true) String boxId) throws ParseException {
        logger.info("Obteniendo las posiciones en JSON");
        List<String> posiciones = new ArrayList<String>(); 
        Box box = this.boxService.getBoxBySystemId(boxId);
        if (box!=null){
        	for(Integer i = 1; i<=box.getCapacity(); i++){
    			posiciones.add(i.toString());
    		}
        }
        List <SpecimenStorage> specBox = this.specimenStorageService.getSpecimenForBox(boxId);
        for(SpecimenStorage spec:specBox) {
        	String posicion = String.valueOf(spec.getPos());
        	posiciones.remove(posicion);
        }
        return posiciones;	
    }
    
    @RequestMapping(value = "boxSelected", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody BoxAliquots fetchBoxJson(@RequestParam(value = "boxId", required = true) String boxId) throws ParseException {
        logger.info("Obteniendo las muestras en JSON");
        BoxAliquots cajaDatos = new BoxAliquots();
        Box box = this.boxService.getBoxBySystemId(boxId);
        if (box!=null){
        	cajaDatos.setBox(box);
        	List<SpecimenStorage> muestras = this.specimenStorageService.getSpecimenForBox(box.getSystemId());
            for(SpecimenStorage specimenSto:muestras) {
        		MessageResource mr = null;
        		String descCatalogo = null;
        		mr = this.messageResourceService.getMensaje(specimenSto.getSpecimen().getSpecimenType(),"CAT_SP_TYPE");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			specimenSto.getSpecimen().setSpecimenType(descCatalogo);
        		}
        		mr = this.messageResourceService.getMensaje(specimenSto.getSpecimen().getVolUnits(),"CAT_VOL_UNITS");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			specimenSto.getSpecimen().setVolUnits(descCatalogo);
        		}
        	}
            cajaDatos.setAliquots(muestras);
            
        }
        return cajaDatos;	
    }
    
}
