package com.parth.recipebook.recipebookservice.dao;

import com.parth.recipebook.recipebookservice.models.MyUser;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface RecipeBookRepository extends MongoRepository<MyUser, String> {

    public MyUser findByEmail(String email);
}
