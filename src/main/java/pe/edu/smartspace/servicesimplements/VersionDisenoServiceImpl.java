package pe.edu.smartspace.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.smartspace.dtos.VersionDisenoDTO;
import pe.edu.smartspace.entities.Diseno;
import pe.edu.smartspace.entities.VersionDiseno;
import pe.edu.smartspace.repositories.IDisenoRepository;
import pe.edu.smartspace.repositories.IVersionDisenoRepository;
import pe.edu.smartspace.servicesinterfaces.IVersionDisenoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VersionDisenoServiceImpl implements IVersionDisenoService {

    @Autowired
    private IVersionDisenoRepository vR;

    @Override
    public List<VersionDiseno> listar() {
        return vR.findAll();
    }

    @Override
    public void registrar(VersionDiseno v) {
        vR.save(v);
    }

    @Override
    public VersionDiseno buscarPorId(Long id) {
        return vR.findById(id).orElse(null);
    }

    @Override
    public void modificar(VersionDiseno v) {
        vR.save(v);
    }

    @Override
    public void eliminar(Long id) {
        vR.deleteById(id);
    }
}
