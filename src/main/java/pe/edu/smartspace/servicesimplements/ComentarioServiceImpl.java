package pe.edu.smartspace.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.smartspace.dtos.ComentarioDTO;
import pe.edu.smartspace.entities.Comentario;
import pe.edu.smartspace.entities.Diseno;
import pe.edu.smartspace.entities.Usuario;

import pe.edu.smartspace.repositories.IComentarioRepository;
import pe.edu.smartspace.repositories.IDisenoRepository;
import pe.edu.smartspace.repositories.IUsuarioRepository;
import pe.edu.smartspace.servicesinterfaces.IComentarioService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements IComentarioService {

    @Autowired
    private IComentarioRepository cR;

    @Override
    public List<Comentario> listar() {
        return cR.findAll();
    }

    @Override
    public List<Comentario> listarPorDiseno(Long idDiseno) {
        return cR.listarPorDiseno(idDiseno);
    }

    @Override
    public void registrar(Comentario c) {
        cR.save(c);
    }

    @Override
    public Comentario buscarPorId(Long id) {
        return cR.findById(id).orElse(null);
    }

    @Override
    public void modificar(Comentario c) {
        cR.save(c);
    }

    @Override
    public void eliminar(Long id) {
        cR.deleteById(id);
    }
}

