package com.example.Examen1.services;

import com.example.Examen1.models.Bicicleta;
import com.example.Examen1.models.Rueda;
import com.example.Examen1.repository.BicicletaRepository;
import com.example.Examen1.repository.RuedaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RuedaService {
    @Autowired private RuedaRepository ruedaRepository;
    @Autowired private BicicletaRepository bicicletaRepository;

    public List<Rueda> getRuedas(){ return ruedaRepository.findAll(); }
    public Rueda getRuedaById(Long id){ return ruedaRepository.findById(id).orElse(null); }

    public Rueda createRueda(Rueda rueda){
        Long biciId = (rueda.getBicicleta()!=null)? rueda.getBicicleta().getId(): null;
        if(biciId==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar bicicleta.id");
        Bicicleta bici = bicicletaRepository.findById(biciId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe bicicleta con id "+biciId));
        rueda.setBicicleta(bici);
        return ruedaRepository.save(rueda);
    }

    public boolean updateRueda(Long id, Rueda rueda){
        if(!ruedaRepository.existsById(id)) return false;
        Long biciId = (rueda.getBicicleta()!=null)? rueda.getBicicleta().getId(): null;
        if(biciId!=null){
            Bicicleta bici = bicicletaRepository.findById(biciId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe bicicleta con id "+biciId));
            rueda.setBicicleta(bici);
        } else { rueda.setBicicleta(null); }
        rueda.setRuedaId(id);
        ruedaRepository.save(rueda);
        return true;
    }

    public Optional<Rueda> partiallyUpdateRueda(Long id, Rueda patch){
        return ruedaRepository.findById(id).map(actual -> {
            if(patch.getMaterial()!=null) actual.setMaterial(patch.getMaterial());
            if(patch.getColor()!=null)    actual.setColor(patch.getColor());
            if(patch.getPrice()!=null)    actual.setPrice(patch.getPrice());
            if(patch.getWidth()!=0)       actual.setWidth(patch.getWidth());
            if(patch.getBicicleta()!=null && patch.getBicicleta().getId()!=null){
                Long biciId = patch.getBicicleta().getId();
                Bicicleta bici = bicicletaRepository.findById(biciId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe bicicleta con id "+biciId));
                actual.setBicicleta(bici);
            }
            return ruedaRepository.save(actual);
        });
    }

    public boolean deleteRueda(Long id){
        if(!ruedaRepository.existsById(id)) return false;
        ruedaRepository.deleteById(id);
        return true;
    }
}
