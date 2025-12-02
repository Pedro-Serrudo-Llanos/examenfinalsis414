package com.example.Examen1.services;

import com.example.Examen1.models.Bicicleta;
import com.example.Examen1.repository.BicicletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BicicletaService {

    @Autowired
    private BicicletaRepository bicicletaRepository;

    public List<Bicicleta> getBicicletas() {
        return bicicletaRepository.findAll();
    }

    public Bicicleta getBicicletaById(Long id) {
        return bicicletaRepository.findById(id).orElse(null);
    }

    public Bicicleta createBicicleta(Bicicleta bicicleta) {
        return bicicletaRepository.save(bicicleta);
    }

    public boolean updateBicicleta(Long id, Bicicleta bicicleta) {
        if (bicicletaRepository.existsById(id)) {
            bicicleta.setId(id);
            bicicletaRepository.save(bicicleta);
            return true;
        }
        return false;
    }

    public Optional<Bicicleta> partiallyUpdateBicicleta(Long id, Bicicleta patch) {
        return bicicletaRepository.findById(id).map(actual -> {
            if (patch.getMarca() != null)       actual.setMarca(patch.getMarca());
            if (patch.getTipo() != null)        actual.setTipo(patch.getTipo());
            if (patch.getPrecio() != null)      actual.setPrecio(patch.getPrecio());
            if (patch.getDisponible() != null)  actual.setDisponible(patch.getDisponible());
            return bicicletaRepository.save(actual);
        });
    }

    public boolean deleteBicicleta(Long id) {
        try {
            if (!bicicletaRepository.existsById(id)) return false;
            bicicletaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
