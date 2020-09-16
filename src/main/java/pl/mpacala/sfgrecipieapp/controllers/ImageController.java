package pl.mpacala.sfgrecipieapp.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.mpacala.sfgrecipieapp.commands.RecipeCommand;
import pl.mpacala.sfgrecipieapp.services.ImageService;
import pl.mpacala.sfgrecipieapp.services.RecipeService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public String getImage(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));

        return "/recipe/imageUploadForm";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String postImage(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(Long.valueOf(recipeId), file);

        return "redirect:/recipe/" +recipeId+ "/show";
    }

    @GetMapping("/recipe/{recipeId}/recipe-image")
    public void renderImageFromDb(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
        //second argument is asking spring to give http servlet response

        RecipeCommand command = recipeService.findCommandById(Long.valueOf(recipeId));

        byte[] byteArray = new byte[command.getImage().length];

        int i=0;

        for(Byte wrappedByte: command.getImage()) {
            byteArray[i++] = wrappedByte; //auto-unboxing from wrapper to primitive
        }

        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(byteArray);
        IOUtils.copy(is, response.getOutputStream());
    }
}
