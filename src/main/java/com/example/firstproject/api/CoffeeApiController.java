package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.service.CoffeeService;
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
    CoffeeService coffeeService;

    @GetMapping()
    public List<Coffee> index() {
        return coffeeService.index();
    }

    @GetMapping("/{id}")
    public Coffee show(@PathVariable Long id) {
        return coffeeService.show(id);
    }

    @PostMapping
    public ResponseEntity<Coffee> create(@RequestBody CoffeeDto dto) {
        Coffee created = coffeeService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Coffee> update(@RequestBody CoffeeDto dto, @PathVariable Long id) {
        Coffee updated = coffeeService.update(dto, id);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id) {
        Coffee deleted = coffeeService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.OK).body(deleted):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
