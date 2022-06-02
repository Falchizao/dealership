package com.utfpr.concessionaria.view.entities;

import com.utfpr.concessionaria.generic.IModel;
import lombok.*;

import javax.persistence.*;

@Entity(name = "tb_cliente")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cliente extends IModel {
    // Atributos

    @Getter
    @Setter
    @Column(name = "nome_cliente", length = 255, nullable = false)
    private String nomeCliente;

    @Getter
    @Setter
    @Column(name = "cpf_cliente", length = 14, nullable = false)
    private String cpfCliente;

    @Getter
    @Setter
    @Column(name = "rg_cliente", length = 12, nullable = false)
    private String rgCliente;

    @Getter
    @Setter
    @Column(name = "endereco_cliente", length = 255, nullable = false)
    private String enderecoCliente;

    @Getter
    @Setter
    @Column(name = "telefone_cliente", length = 15, nullable = false)
    private String telefoneCliente;

    @Getter
    @Setter
    @Column(name = "email_cliente", length = 255, nullable = false)
    private String emailCliente;

}