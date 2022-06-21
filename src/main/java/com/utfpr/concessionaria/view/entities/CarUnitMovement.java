package com.utfpr.concessionaria.view.entities;

import com.utfpr.concessionaria.enums.UnitMovements;
import com.utfpr.concessionaria.generic.IModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CarUnitMovement extends IModel {

    @ManyToOne
    private Carro cars;

    private Integer movedQuantity;
    private UnitMovements movementType;

}
