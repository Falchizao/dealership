package com.utfpr.concessionaria.view.entities;

import lombok.*;

import javax.persistence.*;


@Entity(name = "tb_atendente")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Atendente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atendente")
    public Long idAtendente;

    @Column(name = "nome_atendente", length = 255, nullable = false)
    private String nomeAtendente;




}
