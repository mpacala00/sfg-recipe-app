package pl.mpacala.sfgrecipieapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import pl.mpacala.sfgrecipieapp.services.RecipeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class IndexControllerTest {

    @Mock //fake implementation for the test
    RecipeService recipeService;

    //mocks usually need verification to test interactions
    @Mock
    Model model;

    IndexController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new IndexController(recipeService);
    }

    @Test
    void index() {

        String viewName = controller.index(model);
        assertEquals(viewName, "index");

        //after )) specify a method to test
        verify(recipeService, times(1)).getRecipes();

        //anySet() - any set is expected to be put in this place, in this case Hash Set of recipes
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());

        //eq is a matcher, stands for equals and is needed in order to specify what String is expected
        //When using matchers, all arguments have to be provided by matchers.
        //For example:
        //incorrect:
        //someMethod(anyObject(), "raw String");
        //    //correct:
        //    someMethod(anyObject(), eq("String by matcher"));
    }
}