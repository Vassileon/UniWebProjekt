package de.hsrm.mi.web.projekt.sichtung;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
@SessionAttributes(names = {"meinesichtungen", "loggedinusername"})
public class SichtungController implements WebMvcConfigurer{
    Logger logger = LoggerFactory.getLogger(SichtungController.class);

    @ModelAttribute("meinesichtungen")
    public void initListe(Model model){
        ArrayList<Sichtung> meinesichtungen = new ArrayList<Sichtung>();
        model.addAttribute("meinesichtungen", meinesichtungen);
        meinesichtungen.add(new Sichtung("Leon", "Wiesbaden", LocalDate.parse("2021-03-23"),"17 Eiskugel gegessen" ));
        meinesichtungen.add(new Sichtung("Leon", "Wiesbaden", LocalDate.parse("2021-03-28"),"17 Pinguine gesehen" ));
    }

    @GetMapping("/sichtung/meine")
    public String getSichtungMeine(){
        return "sichtung/meine/liste";
    }

    @GetMapping("/sichtung/meine/neu")
    public String getNeueSichtung(Model model){
        model.addAttribute("meinesichtungform", new Sichtung());
        return "sichtung/meine/bearbeiten";
    }

    @PostMapping("/sichtung/meine/neu")
    public String postNeueSichtung(@Valid @ModelAttribute("meinesichtungform") Sichtung neueSichtung,
                                BindingResult result,
                                @ModelAttribute("meinesichtungen") ArrayList<Sichtung> meinesichtung
                                ){
        if(result.hasErrors()){
            logger.info("Welcher Idiot hat hier was falsch eingetippt?");
            return "sichtung/meine/bearbeiten";
        }
        logger.info("ist wohl alles gut gegangen");
        meinesichtung.add(neueSichtung);
        return "redirect:/sichtung/meine";
        
        
    }

    @GetMapping("/sichtung/meine/{x}/edit")
    public String getMeineSichtungEdit(Model model, @PathVariable int x, @ModelAttribute("meinesichtungen") ArrayList<Sichtung> meineSichtungen){
        model.addAttribute("meinesichtungform", meineSichtungen.get(x));
        meineSichtungen.remove(x);
        return "sichtung/meine/bearbeiten";
    }
 
    @GetMapping("/sichtung/meine/{x}/del")
    public String delMeineSichtung(@PathVariable int x, @ModelAttribute("meinesichtungen") ArrayList<Sichtung> meineSichtungen){
        meineSichtungen.remove(x);
        return "redirect:/sichtung/meine";
    }

}
 