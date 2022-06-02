package com.utfpr.concessionaria.view.entities;

import com.utfpr.concessionaria.generic.IModel;
import com.utfpr.concessionaria.enums.PermissoesAtendente;
import lombok.*;

import javax.persistence.*;


@Entity(name = "tb_atendente")

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Atendente extends IModel {

    @Getter
    @Setter
    @Column(name = "nome_atendente", length = 255, nullable = false)
    private String nomeAtendente;

    @Getter
    @Setter
    @Column(name = "permissao", length = 255)
    private PermissoesAtendente permissao;

    @Getter
    @Setter
    @Column(name = "senha", length = 255)
    private String senha;

}
