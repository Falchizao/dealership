package com.utfpr.concessionaria.generic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepository<T extends IModel> extends JpaRepository<T, Long> {
}
