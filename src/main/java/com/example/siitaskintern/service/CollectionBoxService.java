package com.example.siitaskintern.service;

import com.example.siitaskintern.entity.CollectionBox;
import com.example.siitaskintern.repository.CollectionBoxRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CollectionBoxService {
    private final CollectionBoxRepository repository;

    public List<CollectionBox> getAllBoxes(){
        return repository.findAll();
    }

    public Optional<CollectionBox> getBoxById(Long id){
        return repository.findById(id);
    }

    public CollectionBox addBox(CollectionBox collectionBox){
        collectionBox.setCurrentAmount(BigDecimal.ZERO);
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

    public boolean deleteBox(Long Id){
        if (repository.existsById(Id)){
            repository.deleteById(Id);
            return true;
        }
        return false;
    }

    public CollectionBox addMoney(Long id, BigDecimal amount){
        Optional<CollectionBox> existingBox = repository.findById(id);
        if(existingBox.isPresent()){
            CollectionBox collectionBox = existingBox.get();
            BigDecimal newAmount = collectionBox.getCurrentAmount().add(amount);
            collectionBox.setCurrentAmount(newAmount);
            return repository.save(collectionBox);
        }
        return null;
    }
}
