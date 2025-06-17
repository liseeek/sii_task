package com.example.siitaskintern.repository;

import com.example.siitaskintern.entity.CollectionBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionBoxRepository extends JpaRepository<CollectionBox,Long> {

}
