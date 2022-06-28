package com.utfpr.concessionaria.view.entities;

import com.utfpr.concessionaria.generic.IModel;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity(name = "tb_carro")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Carro extends IModel {

    @Getter
    @Setter
    @Column(name = "modelo_carro", updatable = false)
    private String modelo;

    @Getter
    @Setter
    @Column(name = "marca_carro", updatable = false)
    private String marca;

    @JoinColumn(name = "item_catalogo", foreignKey = @ForeignKey(name = "fk_carro_itemcatalogo"))
    @OneToOne
    private ItemCatalogo itemCatalogo;

    @Getter
    @Setter
    @Column(name = "cor_carro", updatable = false)
    private String cor;

    @Getter
    @Setter
    @Column(name = "data_carro", updatable = false)
    private Date ano;

    @Getter
    @Setter
    @Column(name = "placa_carro", length = 7, updatable = false)
    private String placa;

    @Getter
    @Setter
    @Column(name = "chassi_carro",length = 17, updatable = false)
    private String chassi;

    @Getter
    @Setter
    @Column(name = "valor_carro", updatable = false)
    private Double valor;
}
