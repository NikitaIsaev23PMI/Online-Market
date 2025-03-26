package online_market.products_service.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {

    String saveMedia(MultipartFile file) throws IOException;
}
