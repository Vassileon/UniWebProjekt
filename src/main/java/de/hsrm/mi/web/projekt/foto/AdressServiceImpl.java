package de.hsrm.mi.web.projekt.foto;

import java.net.URI;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javaxt.json.JSONObject;

@Service
public class AdressServiceImpl implements AdressService{

    @Override
    public Optional<String> findeAdresse(double geobreite, double geolaenge) {
        
        String adresse = "https://nominatim.openstreetmap.org/reverse?lat="+
         geobreite +"&lon="+
         geolaenge +"&format=json";

        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.getForObject(URI.create(adresse), String.class);
        JSONObject jObject = new JSONObject(res);
        String display_name = jObject.get("display_name").toString();

        if(display_name == null){
            return Optional.empty();
        }else{
            return Optional.of(display_name); 
        }
        
    }
    
}
