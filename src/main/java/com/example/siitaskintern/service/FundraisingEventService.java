package com.example.siitaskintern.service;

import com.example.siitaskintern.entity.FundraisingEvent;
import com.example.siitaskintern.repository.FundraisingEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FundraisingEventService {

    private final FundraisingEventRepository repository;

    public List<FundraisingEvent> getAllEvents() {
        return repository.findAll();
    }

    public Optional<FundraisingEvent> getEventById(Long id) {
        return repository.findById(id);
    }

    public FundraisingEvent createEvent(FundraisingEvent event) {
        event.setTotalAmount(BigDecimal.ZERO);
        return repository.save(event);
    }

    public FundraisingEvent updateEvent(Long id, FundraisingEvent updatedEvent) {
        Optional<FundraisingEvent> existingEvent = repository.findById(id);
        if (existingEvent.isPresent()) {
            FundraisingEvent event = existingEvent.get();
            event.setName(updatedEvent.getName());
            event.setDescription(updatedEvent.getDescription());
            event.setCurrency(updatedEvent.getCurrency());
            return repository.save(event);
        }
        return null;
    }

    public boolean deleteEvent(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
