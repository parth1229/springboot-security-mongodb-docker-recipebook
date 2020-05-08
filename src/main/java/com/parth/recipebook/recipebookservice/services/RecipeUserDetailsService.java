package com.parth.recipebook.recipebookservice.services;

import com.parth.recipebook.recipebookservice.dao.RecipeBookRepository;
import com.parth.recipebook.recipebookservice.models.MyUser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RecipeUserDetailsService implements UserDetailsService {

    @Autowired
    private RecipeBookRepository recipeBookRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = recipeBookRepository.findByEmail(username);
        return new User(myUser.getEmail(), myUser.getPassword(),new ArrayList<>());
    }

}
