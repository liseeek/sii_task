package com.example.siitaskintern.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionBoxStatusDto {
    private Long id;
    private String name;
    private boolean isAssigned;
    private boolean isEmpty;

    public CollectionBoxStatusDto(Long id, String name, boolean isAssigned, boolean isEmpty) {
        this.id = id;
        this.name = name;
        this.isAssigned = isAssigned;
        this.isEmpty = isEmpty;
    }
}
