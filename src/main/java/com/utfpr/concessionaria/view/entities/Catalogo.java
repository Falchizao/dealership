package com.utfpr.concessionaria.view.entities;

import com.utfpr.concessionaria.generic.IModel;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "catalogo")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Catalogo extends IModel{

    @Getter
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "catalogo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCatalogo> itemCatalogo;

    @LastModifiedDate
    private LocalDateTime lastModified;
}
