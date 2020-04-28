package be.iramps.florencemary._30jd_back.services;

import be.iramps.florencemary._30jd_back.DTO.DTOEntity;
import be.iramps.florencemary._30jd_back.DTO.DtoUtils;
import be.iramps.florencemary._30jd_back.DTO.PathGet;
import be.iramps.florencemary._30jd_back.DTO.PathPost;
import be.iramps.florencemary._30jd_back.models.Path;
import be.iramps.florencemary._30jd_back.repositories.PathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PathService implements CRUDService {
    private PathRepository pathRepository;

    @Autowired
    public PathService(PathRepository pathRepository) {
        this.pathRepository = pathRepository;
    }

    @Override
    public DTOEntity read(Integer id) {
        Optional<Path> optPath = pathRepository.findById(id);
        return optPath.isPresent() ?
                new DtoUtils().convertToDto(optPath.get(), new PathGet()) : null ;
    }

    @Override
    public List<DTOEntity> read() {
        List<DTOEntity> list = new ArrayList<>();
        for(Path p: pathRepository.findAll()) list.add(new DtoUtils().convertToDto(p, new PathGet()));
        return list;
    }

    @Override
    public DTOEntity create(DTOEntity dtoEntity) {
        Path p = (Path)new DtoUtils().convertToEntity(new Path(), dtoEntity);
        pathRepository.save(p);
        return new DtoUtils().convertToDto(p, new PathGet());
    }

    @Override
    public DTOEntity update(Integer id, DTOEntity dtoEntity) {
        return null;
    }

    @Override
    public DTOEntity delete(Integer id) {
        return null;
    }
}
