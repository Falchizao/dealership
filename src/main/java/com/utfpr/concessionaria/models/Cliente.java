package com.utfpr.concessionaria.models;

import lombok.*;

import javax.persistence.*;

@Entity(name = "tb_cliente")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "nome_cliente")
    private String nomeCliente;

    @Column(name = "cpf_cliente")
    private String cpfCliente;

    @Column(name = "rg_cliente")
    private String rgCliente;

    @Column(name = "endereco_cliente")
    private String enderecoCliente;

    @Column(name = "telefone_cliente")
    private String telefoneCliente;

    @Column(name = "email_cliente")
    private String emailCliente;

}