package pe.edu.smartspace.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.smartspace.entities.IntegracionAPI;
import pe.edu.smartspace.repositories.IIntegracionAPIRepository;
import pe.edu.smartspace.servicesinterfaces.IIntegracionAPIService;
import pe.edu.smartspace.dtos.IntegracionAPIDTO;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntegracionAPIServiceImpl implements IIntegracionAPIService {


    @Autowired
    private IIntegracionAPIRepository iR;

    @Override
    public List<IntegracionAPI> listar() {
        return iR.findAll();
    }

    @Override
    public void registrar(IntegracionAPI i) {
        iR.save(i);
    }

    @Override
    public IntegracionAPI buscarPorId(Long id) {
        return iR.findById(id).orElse(null);
    }

    @Override
    public void modificar(IntegracionAPI i) {
        iR.save(i);
    }

    @Override
    public void eliminar(Long id) {
        iR.deleteById(id);
    }
}
