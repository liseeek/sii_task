package com.example.siitaskintern.dto;

import com.example.siitaskintern.entity.CollectionBox;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class CollectionBoxDto {
    private Long id;
    private String name;
    private BigDecimal currentAmount;
    private String currency;
    private boolean isEmpty;
    private boolean isAssigned;

    private Long eventId;
    private String eventName;

    public CollectionBoxDto(CollectionBox box) {
        this.id = box.getId();
        this.name = box.getName();
        this.currentAmount = box.getCurrentAmount();
        this.currency = box.getCurrency();
        this.isEmpty = box.isEmpty();
        this.isAssigned = box.isAssigned();

        if (box.getFundraisingEvent() != null) {
            this.eventId = box.getFundraisingEvent().getId();
            this.eventName = box.getFundraisingEvent().getName();
        }
    }
}

