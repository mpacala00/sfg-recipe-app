package pl.mpacala.sfgrecipieapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import pl.mpacala.sfgrecipieapp.model.Recipe;
import pl.mpacala.sfgrecipieapp.services.RecipeService;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

    //a simple way to test SpringMVC Controllers
    @Test
    void testMockMVS() throws Exception {
        //common builder pattern
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        //standalone so it doesn't involve context

        //get -> import static MockMvcRequestBuilders.get
        mockMvc.perform(get("/"))
                .andExpect(status().isOk()) //status.isOk() is 200 http response
                .andExpect(view().name("index")); //template name
    }

    @Test
    void index() {

        //given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        Recipe recipe = new Recipe();
        recipe.setId(2L); //cant add new empty Recipe because it will be treated as one recipe in the set
        recipes.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipes);
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.index(model);
        assertEquals(viewName, "index");

        //then
        //after )) specify a method to test
        verify(recipeService, times(1)).getRecipes();
        //capture Set of recipes
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());

        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());

        //eq is a matcher, stands for equals and is needed in order to specify what String is expected
        //When using matchers, all arguments have to be provided by matchers.
        //For example:
        //incorrect:
        //someMethod(anyObject(), "raw String");
        //    //correct:
        //    someMethod(anyObject(), eq("String by matcher"));
    }
}