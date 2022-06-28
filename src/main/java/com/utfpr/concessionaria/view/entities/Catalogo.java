package com.utfpr.concessionaria.view.entities;

import com.utfpr.concessionaria.generic.IModel;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "catalogo")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Catalogo extends IModel{

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "catalogo")
    @Column(name = "itemCatalogo")
    private List<ItemCatalogo> itemCatalogo;
}
