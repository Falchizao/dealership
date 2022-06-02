package com.utfpr.concessionaria.generic;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class IModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
}
