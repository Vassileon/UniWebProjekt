package de.hsrm.mi.web.projekt.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.web.projekt.foto.Foto;
import de.hsrm.mi.web.projekt.foto.FotoService;
import de.hsrm.mi.web.projekt.foto.Kommentar;


@RestController
@RequestMapping("/api")
public class FotoRestController {
    
    @Autowired FotoService fotoService;

    @GetMapping("/foto")
    public List<Foto> getApiFoto() {
        return fotoService.alleFotosNachZeitstempelSortiert();
    }
    
    @DeleteMapping("/foto/{id}")
    public void delFotoById(@PathVariable long id){
        if(fotoService.fotoAbfragenNachId(id).isPresent()){
            fotoService.loescheFoto(id);
        }
    }

    @GetMapping("/foto/{id}/kommentar")
    public List<Kommentar> getFotoKommentare(@PathVariable long id) {
        return fotoService.alleKommentareFuerFoto(id);
    }

    @DeleteMapping("/foto/{id}/kommentar/{kid}")
    public void delKommentarById(@PathVariable long id, @PathVariable long kid){
        if(fotoService.fotoAbfragenNachId(id).isPresent()){
            fotoService.fotoKommentarLoeschen(id, kid);;
        }
    }
}
