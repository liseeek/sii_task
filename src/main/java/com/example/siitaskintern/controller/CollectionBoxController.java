package com.example.siitaskintern.controller;

import com.example.siitaskintern.dto.CollectionBoxDto;
import com.example.siitaskintern.dto.CollectionBoxStatusDto;
import com.example.siitaskintern.dto.MoneyRequest;
import com.example.siitaskintern.entity.CollectionBox;
import com.example.siitaskintern.service.CollectionBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/collectionBoxes")
@RequiredArgsConstructor
public class CollectionBoxController {

    private final CollectionBoxService service;

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
        CollectionBox updated = service.addMoney(id, request.getAmount(), request.getCurrency());

        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{boxId}/assign/{eventId}")
    public ResponseEntity<CollectionBoxDto> assignBoxToEvent(
            @PathVariable Long boxId,
            @PathVariable Long eventId) {
        try {
            CollectionBox assigned = service.assignBoxToEvent(boxId, eventId);
            return ResponseEntity.ok(new CollectionBoxDto(assigned));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{boxId}/empty")
    public ResponseEntity<CollectionBox> emptyBox(@PathVariable Long boxId) {
        try {
            CollectionBox emptiedBox = service.emptyBox(boxId);
            return ResponseEntity.ok(emptiedBox);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/status")
    public List<CollectionBoxStatusDto> getAllBoxesWithStatus() {
        return service.getAllBoxes().stream()
                .map(box -> new CollectionBoxStatusDto(
                        box.getId(),
                        box.getName(),
                        box.isAssigned(),
                        box.isEmpty()
                ))
                .collect(Collectors.toList());
    }
}

