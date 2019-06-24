package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));

        return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }


    // - handling the POST back - this is going to work for both - save, update
    // - @ModelAttribute tells Spring to bind the form post parameters to the RecipeCommand object
    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        // our service returns back a new implementation of the command
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        // - redirect tells Spring MVC to redirect to a specific URL.
        // - we are going to append that with the ID of the saved object
        return "redirect:/recipe/" + savedCommand.getId() + "/show";

        // - the functionality is - we'll see the form it'll come up we'll enter in all the properties that we want
        //   and then when we post it with the save, they'll go into this. It'll save to the DB and then come back
        //   and we'll redirect to the recipe show and show the saved recipe
    }
}
