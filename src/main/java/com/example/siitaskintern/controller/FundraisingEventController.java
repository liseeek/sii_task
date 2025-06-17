package com.example.siitaskintern.controller;

import com.example.siitaskintern.dto.FinancialReportDto;
import com.example.siitaskintern.entity.FundraisingEvent;
import com.example.siitaskintern.service.FundraisingEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/fundraisingEvents")
@RequiredArgsConstructor
public class FundraisingEventController {

    private final FundraisingEventService service;

    @GetMapping
    public List<FundraisingEvent> getAllEvents() {
        return service.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FundraisingEvent> getEventById(@PathVariable Long id) {
        Optional<FundraisingEvent> event = service.getEventById(id);
        if (event.isPresent()) {
            return ResponseEntity.ok(event.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public FundraisingEvent createEvent(@RequestBody FundraisingEvent event) {
        return service.createEvent(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FundraisingEvent> updateEvent(@PathVariable Long id, @RequestBody FundraisingEvent event) {
        FundraisingEvent updated = service.updateEvent(id, event);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        boolean deleted = service.deleteEvent(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/report")
    public List<FinancialReportDto> getFinancialReport() {
        return service.getAllEvents().stream()
                .map(event -> new FinancialReportDto(
                        event.getName(),
                        event.getTotalAmount(),
                        event.getCurrency()
                ))
                .collect(Collectors.toList());
    }

}
