package com.example.siitaskintern.controller;

import com.example.siitaskintern.entity.CollectionBox;
import com.example.siitaskintern.service.CollectionBoxService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/collectionBoxes")
@RequiredArgsConstructor
public class CollectionBoxController {

    private final CollectionBoxService service;

    @GetMapping
    public List<CollectionBox> getAllBoxes() {
        return service.getAllBoxes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionBox> getBoxById(@PathVariable Long id) {
        Optional<CollectionBox> collectionBox = service.getBoxById(id);
        if (collectionBox.isPresent()) {
            return ResponseEntity.ok(collectionBox.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public CollectionBox addBox(@RequestBody CollectionBox collectionBox) {
        return service.addBox(collectionBox);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollectionBox> updateBox(@PathVariable Long id, @RequestBody CollectionBox collectionBox) {
        CollectionBox updatedBox = service.updateBox(id, collectionBox);
        if (updatedBox != null) {
            return ResponseEntity.ok(updatedBox);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBox(@PathVariable Long id) {
        boolean deleted = service.deleteBox(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/addMoney")
    public ResponseEntity<CollectionBox> addMoney(@PathVariable Long id, @RequestBody MoneyRequest request) {
        CollectionBox updated = service.addMoney(id, request.getAmount());
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @Setter
    @Getter
    public static class MoneyRequest {
        private BigDecimal amount;
    }
}

