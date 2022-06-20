package com.utfpr.concessionaria.view.entities;
import com.utfpr.concessionaria.generic.IModel;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity(name = "item_catalogo")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemCatalogo extends IModel {

    @JoinColumn(name = "inventario", foreignKey = @ForeignKey(name = "fk_itemcatalogo_catalogo"))
    @ManyToOne
    @Getter
    private Catalogo catalogo;

    @OneToOne
    @Getter
    private Carro carro;

    @Getter
    @Setter
    private Integer quantity;
}
