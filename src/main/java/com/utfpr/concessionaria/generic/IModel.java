package com.utfpr.concessionaria.generic;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class IModel implements Serializable { //NFe / NFC-e
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @Setter
    private Long id;
}
