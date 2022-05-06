package com.utfpr.concessionaria.models;

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
    private Long idAtendente;

    @Column(name = "nome_atendente")
    private String nomeAtendente;




}
