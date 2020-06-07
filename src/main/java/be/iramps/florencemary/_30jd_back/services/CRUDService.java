package be.iramps.florencemary._30jd_back.services;

import be.iramps.florencemary._30jd_back.DTO.DTOEntity;

import java.util.List;

/**
 * Interface de CRUD
 * Définit des méthodes abstraites génériques pour les actions CREATE, READ(1-ALL), UPDATE, DELETE
 */
public interface CRUDService {
    DTOEntity read(Integer id);
    List<DTOEntity> read();
    DTOEntity create(DTOEntity dtoEntity);
    DTOEntity update(Integer id, DTOEntity dtoEntity);
    DTOEntity delete(Integer id);
}
