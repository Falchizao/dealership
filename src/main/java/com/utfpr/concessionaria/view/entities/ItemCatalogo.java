package com.utfpr.concessionaria.view.entities;

import com.utfpr.concessionaria.generic.IModel;
import lombok.*;
import javax.persistence.*;

@Entity(name = "item_catalogo")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemCatalogo extends IModel {

    @Getter
    @ManyToOne
    @JoinColumn(name = "catalogo")
    private Catalogo catalogo;

    @OneToOne
    @Getter
    private Carro carro;

    @Getter
    @Setter
    private Integer quantity;
}
