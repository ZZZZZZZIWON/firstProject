package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeDTO;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/coffees")
public class CoffeeApiController {
    @Autowired
    CoffeeRepository coffeeRepository;

    @GetMapping()
    public List<Coffee> index() {
        return coffeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Coffee show(@PathVariable Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Coffee create(@RequestBody CoffeeDTO dto) {
        Coffee target = dto.toEntity();
        return coffeeRepository.save(target);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Coffee> update(@RequestBody CoffeeDTO dto, @PathVariable Long id) {
        Coffee coffee = dto.toEntity();
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target == null || id != target.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        target.patch(coffee);
        Coffee updated = coffeeRepository.save(coffee);
        return ResponseEntity.status(HttpStatus.OK).body(updated);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id) {
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        coffeeRepository.delete(target);
            return ResponseEntity.status(HttpStatus.OK).build();
    }
}
