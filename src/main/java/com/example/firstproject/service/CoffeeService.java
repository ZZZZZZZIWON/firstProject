package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoffeeService {

    @Autowired
    CoffeeRepository coffeeRepository;

    public List<Coffee> index() {
        return coffeeRepository.findAll();
    }

    public Coffee show(Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    public Coffee create(CoffeeDto dto) {
        Coffee coffee = dto.toEntity();
        if(coffee.getId() != null) {
            return null;
        }
        return coffeeRepository.save(coffee);
    }

    public Coffee update(CoffeeDto dto, Long id) {
        Coffee coffee = dto.toEntity();
        Coffee target = coffeeRepository.findById(id).orElse(null);

        if(target == null || coffee.getId() != id) {
            return null;
        }
        target.patch(coffee);
        return coffeeRepository.save(coffee);
    }

    public Coffee delete(Long id) {
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target == null) {
            return null;
        }
        coffeeRepository.delete(target);
        return target;
    }
}
