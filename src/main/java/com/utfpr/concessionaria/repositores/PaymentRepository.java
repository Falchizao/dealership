package com.utfpr.concessionaria.repositores;

import com.utfpr.concessionaria.generic.IRepository;
import com.utfpr.concessionaria.view.entities.FormaPagamento;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends IRepository<FormaPagamento> {
}
