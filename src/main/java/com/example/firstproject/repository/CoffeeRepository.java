package com.example.firstproject.repository;

import com.example.firstproject.entity.Coffee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CoffeeRepository extends CrudRepository<Coffee, Long> {
    @Override
    ArrayList<Coffee> findAll();
}
