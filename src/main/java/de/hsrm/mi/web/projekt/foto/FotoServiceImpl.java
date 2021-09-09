package de.hsrm.mi.web.projekt.foto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.messaging.FotoMessage;
import de.hsrm.mi.web.projekt.utils.FotoBearbeitungService;

@Service
public class FotoServiceImpl implements FotoService {

    Logger logger = LoggerFactory.getLogger(FotoServiceImpl.class);
    @Autowired
    FotoBearbeitungService fbservice;

    @Autowired
    FotoRepository fotoRepository;

    @Autowired
    AdressService adressService;

    @Autowired
    SimpMessagingTemplate broker;

    // @MessageMapping("/topic/foto")
    // @SendTo("/topic/foto")
    // public void msgHolzHandler() {   
    // }

    @Override
    public Foto fotoAbspeichern(Foto foto) {
        broker.convertAndSend("/topic/foto", new FotoMessage("fotoGespeichert", foto.getId()));
        fbservice.aktualisiereMetadaten(foto);
        fbservice.orientiereFoto(foto);
        foto.setOrt(adressService.findeAdresse(foto.getGeobreite(), foto.getGeolaenge()));
        return fotoRepository.save(foto);
    }

    @Override
    public Optional<Foto> fotoAbfragenNachId(Long id) {
        Optional<Foto> foto = fotoRepository.findById(id);
        return foto;
    }

    @Override
    public List<Foto> alleFotosNachZeitstempelSortiert() {
        return fotoRepository.findAll(Sort.by(Direction.ASC, "zeitstempel"));
    }

    @Override
    public void loescheFoto(Long id) {
        broker.convertAndSend("/topic/foto", new FotoMessage("fotoGeloescht", id));
        logger.info("info angekommen");
        fotoRepository.deleteById(id);
    }

    @Override
    public void fotoKommentieren(long id, String autor, String text) {
        Kommentar kommentar = new Kommentar();
        kommentar.setAutor(autor);
        kommentar.setText(text);
        Optional<Foto> foto = fotoAbfragenNachId(id);

        if (foto == null) {
            throw new NoSuchElementException();
        }
        foto.get().addKommentare(kommentar);
    }

    @Override
    public List<Kommentar> alleKommentareFuerFoto(long fotoid) {
        Optional<Foto> foto = fotoAbfragenNachId(fotoid);
        if (foto == null) {
            throw new NoSuchElementException();
        }
        return foto.get().getKommentare();
    }

    @Override
    public void fotoKommentarLoeschen(long fotoid, long kid) {

        Foto foto = fotoRepository.findById(fotoid).orElseThrow();

        for (Kommentar x : foto.getKommentare()) {
            if (x.getId() == kid) {
                foto.getKommentare().remove(x);
                return;
            }
        }
        throw new NoSuchElementException();
    }

}
