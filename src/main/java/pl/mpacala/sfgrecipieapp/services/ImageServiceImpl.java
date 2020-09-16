package pl.mpacala.sfgrecipieapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.mpacala.sfgrecipieapp.model.Recipe;
import pl.mpacala.sfgrecipieapp.repositories.RecipeRepository;

import javax.transaction.Transactional;
import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {

        try {
            Recipe recipe = recipeRepository.findById(recipeId).get();

            //for conversion from java primitive byte to wrapper object Byte
            //wrapper objects are recommended by hibernate team
            //primitives cannot be null
            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i=0;
            for(Byte b: file.getBytes()) {
                //assign each byte from the file to the array
                byteObjects[i++] = b;
            }

            recipe.setImage(byteObjects);

            recipeRepository.save(recipe);

        } catch (IOException e) {
            log.debug("Error occured: "+e);

            e.printStackTrace();
        }

        log.debug("received the file");
    }
}
