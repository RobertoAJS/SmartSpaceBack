package pe.edu.smartspace.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();

        config.put("cloud_name", "dfkxd9dra");
        config.put("api_key", "145543556923931");
        config.put("api_secret", "33GuTxd3uUaVh8XrBG-HqRe9Zxk");

        return new Cloudinary(config);
    }
}