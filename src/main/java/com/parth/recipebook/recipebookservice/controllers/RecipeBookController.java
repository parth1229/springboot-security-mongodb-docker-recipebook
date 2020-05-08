package com.parth.recipebook.recipebookservice.controllers;

import com.parth.recipebook.recipebookservice.models.AuthRequest;
import com.parth.recipebook.recipebookservice.models.AuthResponse;
import com.parth.recipebook.recipebookservice.models.UserRecipeList;
import com.parth.recipebook.recipebookservice.models.UserShoppingList;
import com.parth.recipebook.recipebookservice.services.RecipeUserDetailsService;
import com.parth.recipebook.recipebookservice.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class RecipeBookController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RecipeUserDetailsService recipeUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/userRecipes/{email}",method = RequestMethod.GET)
    public ResponseEntity<?> returnRecipeList(@PathVariable String email){
        if(email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email Id should not be blank");
        }
        UserRecipeList userRecipeList =
                restTemplate.getForObject("http://RecipeService:8082/recipes/" + email, UserRecipeList.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userRecipeList);
    }

    @RequestMapping(value = "/userRecipes",method = RequestMethod.POST)
    public ResponseEntity<?> saveRecipeList(@RequestBody UserRecipeList userRecipeList){
        if(userRecipeList == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nothing to save");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(restTemplate.postForObject("http://RecipeService:8082/recipes", userRecipeList, UserRecipeList.class));
    }

    @RequestMapping(value = "/userShoppingList/{email}", method = RequestMethod.GET)
    public ResponseEntity<?> returnShoppingList(@PathVariable String email){
        if(email == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email Id should not be blank");
        }
        UserShoppingList userShoppingList =
                restTemplate.getForObject("http://ShoppingService:8083/ingredients/" + email, UserShoppingList.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userShoppingList);
    }


    @RequestMapping(value = "/userShoppingList", method = RequestMethod.POST)
    public ResponseEntity<?> saveShoppingList(@RequestBody UserShoppingList userShoppingList){
        if(userShoppingList == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nothing to save");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(restTemplate.postForObject("http://ShoppingService:8083/ingredients", userShoppingList, UserShoppingList.class));
    }


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest requestBody)  {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestBody.getUsername(),requestBody.getPassword())
            );
        }
        catch (BadCredentialsException ex){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Incorrect username or password");
        }
        final UserDetails userDetails = recipeUserDetailsService.loadUserByUsername(requestBody.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new AuthResponse(jwt));
    }
}
