package com.yasar.imagenes.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yasar.imagenes.model.Imagen;
import com.yasar.imagenes.repository.ImagenRepository;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class ImageUploadService {

    @Autowired
    private ImagenRepository imagenRepository;

    // public void uploadImage(MultipartFile file, String UPLOAD_FOLDER) throws
    // Exception {
    // byte[] bytes = file.getBytes();
    // String fileOriginalName = file.getOriginalFilename();
    // String id = UUID.randomUUID().toString();
    // File folder = new File(UPLOAD_FOLDER);
    // if (!folder.exists()) {
    // folder.mkdirs();
    // }
    // Files.write(Paths.get(UPLOAD_FOLDER + File.separator + fileOriginalName),
    // bytes);
    // }

    public String uploadImage(MultipartFile file, String UPLOAD_FOLDER) throws Exception {
        byte[] bytes = file.getBytes(); // ? File es un array de bytes
        String fileOriginalName = file.getOriginalFilename(); // ? Nombre original del archivo
        String extention = fileOriginalName.substring(fileOriginalName.lastIndexOf(".")); // ? Tipo de archivo
        String id = UUID.randomUUID().toString(); // ? Genera un identificador unico
        File folder = new File(UPLOAD_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Files.write(Paths.get(UPLOAD_FOLDER + File.separator + id + extention), bytes);
        String nuevoNombre = id + extention;

        // Guardar en la base de datos
        Imagen imagen = new Imagen();
        imagen.setPath(nuevoNombre);
        imagenRepository.save(imagen);

        return nuevoNombre;
    }
    public ArrayList<Imagen> getImages(){
        ArrayList<Imagen> imagenes = new ArrayList<Imagen>();
        imagenes = (ArrayList<Imagen>) imagenRepository.findAll();
        return imagenes;
    }
}
