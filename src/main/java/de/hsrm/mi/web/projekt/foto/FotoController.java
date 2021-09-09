package de.hsrm.mi.web.projekt.foto;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

@SessionAttributes(names = {"loggedinusername"})
@Controller
public class FotoController {
    
    @Autowired
    FotoService fotoService;

    @PostMapping("/foto")
    public String postFoto(MultipartFile datei, Model model){
        Foto foto = new Foto();
        foto.setDateiname(datei.getOriginalFilename());
        try {
            foto.setFotodaten(datei.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        foto.setMimetype(datei.getContentType());
        if(datei.getSize() > 17){
            fotoService.fotoAbspeichern(foto);
        }
        model.addAttribute("fotos", fotoService.alleFotosNachZeitstempelSortiert());

        return "foto/liste";
    }

    @GetMapping("/foto")
    public String getFoto(Model model){
        model.addAttribute("fotos", fotoService.alleFotosNachZeitstempelSortiert());
        return "foto/liste";
    }

    @GetMapping("/foto/{id}")
    public ResponseEntity<byte[]> getFotoId(@PathVariable Long id) {
        Optional<Foto> fotoOptional = fotoService.fotoAbfragenNachId(id);
        Foto foto = fotoOptional.get();
        return ResponseEntity.ok()
                .header("Content-Type", foto.getMimetype())
                .body(foto.getFotodaten()); 
        
    }

    @GetMapping("/foto/{id}/del")
    public String getFotoIdDel(@PathVariable Long id){
        fotoService.loescheFoto(id);
        return ("redirect:/foto");
    }

    @GetMapping("/foto/{id}/kommentar")
    public String getFotoIdKommentar(@PathVariable long id,Model model){

        Optional<Foto> oFoto = fotoService.fotoAbfragenNachId(id);

        if(oFoto.isPresent()){
            Foto foto = oFoto.get();
            model.addAttribute("foto", foto);
            model.addAttribute("kommentare", foto.getKommentare());
            return ("foto/kommentare");
        }else{
            return ("foto/liste");
        }
    }

    @PostMapping("/foto/{id}/kommentar")
    public String foto_id_kommentar_post(Model model, @PathVariable long id,@RequestParam String kommentar) {
        if (kommentar != "" && model.containsAttribute("loggedinusername")) {
            fotoService.fotoKommentieren(id, model.getAttribute("loggedinusername").toString(), kommentar);
        }
        return "redirect:/foto/{id}/kommentar";
    }
    
}
