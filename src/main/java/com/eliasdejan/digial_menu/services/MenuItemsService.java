package com.eliasdejan.digial_menu.services;

import com.eliasdejan.digial_menu.model.MenuItem;
import com.eliasdejan.digial_menu.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MenuItemsService {
    @Autowired
    MenuItemRepository menuItemRepository;

    public static String UPLOAD_DIRECTORY = "src/main/resources/static/upload";

    public void saveFile(MultipartFile file, int id) throws IOException {

        // Get the file and store it on disk
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());

        // Update the entity with the new file path
        MenuItem menuItem = menuItemRepository.findById(id).get();
        menuItem.setImagePath(file.getOriginalFilename());
        menuItemRepository.save(menuItem);
    }
}
