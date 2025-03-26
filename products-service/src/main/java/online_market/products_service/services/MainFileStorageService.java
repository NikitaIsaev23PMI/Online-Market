package online_market.products_service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class MainFileStorageService implements FileStorageService {

    @Value("${media.storage.location}")
    private String MediaDirectory;

    public String saveMedia(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(MediaDirectory);
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath);

        return "/uploads/" + fileName;
    }
}
