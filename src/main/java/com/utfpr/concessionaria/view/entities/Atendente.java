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
    @Column(name = "nome_atendente", nullable = false, updatable = false)
    private String nomeAtendente;

    @Getter
    @Setter
    @Column(name = "permissao", updatable = false)
    private PermissoesAtendente permissao;

    @Getter
    @Setter
    @Column(name = "senha", updatable = false)
    private String senha;

}
