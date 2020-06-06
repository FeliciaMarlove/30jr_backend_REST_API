package be.iramps.florencemary._30jd_back.DTO;

import org.modelmapper.ModelMapper;

/**
 * Utilitaire de conversion d'un objet profond vers un objet plat (DTO) ou d'un objet plat (DTO) vers un objet profond
 */
public class DtoUtils {
    /**
     * Convertit un objet profond en DTO
     * @param obj l'objet profond à convertir
     * @param mapper le DTO utilisé pour la conversion
     * @return DTOEntity l'objet converti en DTO
     */
    public DTOEntity convertToDto(Object obj, DTOEntity mapper) {
    return new ModelMapper().map(obj, mapper.getClass());
  }

    /**
     * Convertit un DTO en objet profond
     * @param obj l'objet profond à récupérer
     * @param mapper le DTO à convertir
     * @return Object l'objet profond
     */
  public Object convertToEntity(Object obj, DTOEntity mapper) {
        return new ModelMapper().map(mapper, obj.getClass());
  }
}
