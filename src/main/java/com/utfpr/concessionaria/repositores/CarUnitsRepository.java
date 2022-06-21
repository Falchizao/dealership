package com.utfpr.concessionaria.repositores;

import com.utfpr.concessionaria.generic.IRepository;
import com.utfpr.concessionaria.view.entities.CarUnitMovement;
import org.springframework.stereotype.Repository;

@Repository
public interface CarUnitsRepository extends IRepository<CarUnitMovement> {
}
