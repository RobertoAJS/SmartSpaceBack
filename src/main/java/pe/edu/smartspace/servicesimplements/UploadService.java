package pe.edu.smartspace.servicesimplements;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class UploadService {

    @Autowired
    private Cloudinary cloudinary;

    public String subirArchivo(MultipartFile file) throws IOException {
        // 1. Convertimos el archivo a bytes y lo subimos a Cloudinary
        // ObjectUtils.emptyMap() significa que no enviamos configuraciones extra (como recortar imagen, etc.)
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        // 2. Obtenemos la URL segura (https) de la respuesta
        return uploadResult.get("secure_url").toString();
    }
}
