package com.utfpr.concessionaria.repositores;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarroRepository extends JpaRepository<CarroRepository, Long> {
}
