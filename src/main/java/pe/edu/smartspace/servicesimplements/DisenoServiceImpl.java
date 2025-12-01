package pe.edu.smartspace.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.smartspace.entities.Diseno;
import pe.edu.smartspace.entities.Usuario;
import pe.edu.smartspace.repositories.IDisenoRepository;
import pe.edu.smartspace.repositories.IUsuarioRepository;
import pe.edu.smartspace.servicesinterfaces.IDisenoService;

import java.util.List;

@Service
public class DisenoServiceImpl implements IDisenoService {


    @Autowired
    private IDisenoRepository dR;

    @Override
    public List<Diseno> listar() {
        return dR.findAll();
    }

    @Override
    public void registrar(Diseno d) {
        dR.save(d);
    }

    @Override
    public Diseno buscarPorId(Long id) {
        return dR.findById(id).orElse(null);
    }

    @Override
    public void modificar(Diseno d) {
        dR.save(d);
    }

    @Override
    public void eliminar(Long id) {
        dR.deleteById(id);
    }

    @Override
    public long contar() { return dR.count();
    }

}
