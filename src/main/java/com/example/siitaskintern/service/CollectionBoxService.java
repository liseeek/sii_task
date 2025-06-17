package com.example.siitaskintern.service;

import com.example.siitaskintern.entity.CollectionBox;
import com.example.siitaskintern.entity.FundraisingEvent;
import com.example.siitaskintern.repository.CollectionBoxRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CollectionBoxService {
    private final CollectionBoxRepository repository;
    private final FundraisingEventService fundraisingEventService;
    private final CurrencyExchangeService currencyExchangeService;

    public List<CollectionBox> getAllBoxes(){
        return repository.findAll();
    }

    public Optional<CollectionBox> getBoxById(Long id){
        return repository.findById(id);
    }

    public CollectionBox addBox(CollectionBox collectionBox){
        collectionBox.setCurrentAmount(BigDecimal.ZERO);
        collectionBox.setEmpty(true);
        collectionBox.setAssigned(false);
        return repository.save(collectionBox);
    }

    public CollectionBox updateBox(Long Id, CollectionBox updateBox){
        Optional<CollectionBox> existingBox = repository.findById(Id);
        if(existingBox.isPresent()){
            CollectionBox collectionBox = existingBox.get();
            collectionBox.setName(updateBox.getName());
            collectionBox.setDescription(updateBox.getDescription());
            collectionBox.setTargetAmount(updateBox.getTargetAmount());
            collectionBox.setCurrency(updateBox.getCurrency());
            return repository.save(collectionBox);
        }
        return null;
    }

    public CollectionBox assignBoxToEvent(Long boxId, Long eventId) {
        CollectionBox box = repository.findById(boxId).orElse(null);
        FundraisingEvent event = fundraisingEventService.getEventById(eventId).orElse(null);

        if (box == null || event == null) {
            throw new RuntimeException("Box or Event not found");
        }

        if (!box.isEmpty()) {
            throw new RuntimeException("Cannot assign non-empty collection box");
        }

        if (box.isAssigned()) {
            throw new RuntimeException("Collection box is already assigned");
        }
        box.setFundraisingEvent(event);
        box.setAssigned(true);

        return repository.save(box);
    }


    public boolean deleteBox(Long id) {
        Optional<CollectionBox> boxOpt = repository.findById(id);
        if (boxOpt.isPresent()) {
            CollectionBox box = boxOpt.get();
            box.setCurrentAmount(BigDecimal.ZERO);
            box.setEmpty(true);
            box.setAssigned(false);
            box.setFundraisingEvent(null);
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public CollectionBox addMoney(Long id, BigDecimal amount, String currency) {
        Optional<CollectionBox> boxOpt = repository.findById(id);
        if (boxOpt.isPresent()) {
            CollectionBox box = boxOpt.get();
            box.setCurrentAmount(box.getCurrentAmount().add(amount));
            box.setCurrency(currency);
            box.setEmpty(false);

            return repository.save(box);
        }
        return null;
    }

    public CollectionBox emptyBox(Long boxId) {
        Optional<CollectionBox> boxOpt = repository.findById(boxId);
        if (boxOpt.isEmpty()) {
            throw new RuntimeException("Collection box not found");
        }

        CollectionBox box = boxOpt.get();

        if (!box.isAssigned()) {
            throw new RuntimeException("Box is not assigned to any event");
        }

        if (box.isEmpty()) {
            throw new RuntimeException("Box is already empty");
        }

        FundraisingEvent event = box.getFundraisingEvent();

        BigDecimal convertedAmount = currencyExchangeService.convertCurrency(
                box.getCurrentAmount(),
                box.getCurrency(),
                event.getCurrency()
        );

        event.setTotalAmount(event.getTotalAmount().add(convertedAmount));
        box.setCurrentAmount(BigDecimal.ZERO);
        box.setEmpty(true);
        box.setAssigned(false);
        box.setFundraisingEvent(null);

        return repository.save(box);
    }
}
