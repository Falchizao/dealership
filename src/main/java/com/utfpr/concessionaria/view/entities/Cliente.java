package com.utfpr.concessionaria.view.entities;

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
    // Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "nome_cliente", length = 255, nullable = false)
    private String nomeCliente;

    @Column(name = "cpf_cliente", length = 14, nullable = false)
    private String cpfCliente;

    @Column(name = "rg_cliente", length = 12, nullable = false)
    private String rgCliente;

    @Column(name = "endereco_cliente", length = 255, nullable = false)
    private String enderecoCliente;

    @Column(name = "telefone_cliente", length = 15, nullable = false)
    private String telefoneCliente;

    @Column(name = "email_cliente", length = 255, nullable = false)
    private String emailCliente;

}