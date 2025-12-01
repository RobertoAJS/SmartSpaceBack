package pe.edu.smartspace.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.smartspace.entities.Favorito;
import pe.edu.smartspace.repositories.IFavoritoRepository;
import pe.edu.smartspace.servicesinterfaces.IFavoritoService;

import java.util.List;

@Service
public class FavoritoServiceImpl implements IFavoritoService {

    @Autowired
    private IFavoritoRepository repo;

    @Override
    public List<Favorito> listar() {
        return repo.findAll();
    }

    @Override
    public List<Favorito> listarPorUsuario(Long idUsuario) {
        return repo.findByUsuario_IdUsuario(idUsuario);
    }

    @Override
    public void registrar(Favorito f) {
        repo.save(f);
    }

    @Override
    public Favorito buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void modificar(Favorito f) {
        repo.save(f);
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    @Override
    public boolean perteneceAlUsuario(Long favoritoId, Long userId) {
        return repo.perteneceAlUsuario(favoritoId, userId);
    }
}

