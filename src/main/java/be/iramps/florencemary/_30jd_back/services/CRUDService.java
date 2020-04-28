package be.iramps.florencemary._30jd_back.services;

import be.iramps.florencemary._30jd_back.DTO.DTOEntity;

import java.util.List;

public interface CRUDService {
    DTOEntity read(Integer id);
    List<DTOEntity> read();
    DTOEntity create(DTOEntity dtoEntity);
    DTOEntity update(Integer id, DTOEntity dtoEntity);
    DTOEntity delete(Integer id);
}
