package com.yasar.imagenes.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yasar.imagenes.model.Imagen;
import com.yasar.imagenes.service.ImageUploadService;

@Controller
@RequestMapping("/")
public class ImageUploadController {
    @Autowired
    private ImageUploadService imageUploadService;

    private static String UPLOAD_FOLDER = "/images"; // Cambiar esto a la ubicación donde quieras almacenar las imagenes

    @GetMapping("/")
    public String index(){
        return "index.html";
    }

    @GetMapping("/upload")
    public String uploadForm() {
        return "uploadForm.html";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)
            throws Exception {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Por favor, selecciona un archivo para subir.");
            return "redirect:/upload";
        }
        try {
            // ? Todo esto se movio al servicio
            String id = imageUploadService.uploadImage(file, UPLOAD_FOLDER);
            redirectAttributes.addFlashAttribute("message", "Archivo subido con éxito: \n" + id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/upload";
    }

    @GetMapping("/list")
    public String listImages(ModelMap modelo) {
        List<Imagen> imagenes = imageUploadService.getImages();
        modelo.addAttribute("imagenes", imagenes);
        return "listImages.html";
    }

}
