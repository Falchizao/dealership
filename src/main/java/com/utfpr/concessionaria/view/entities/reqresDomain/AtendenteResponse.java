package com.utfpr.concessionaria.view.entities.reqresDomain;
import com.utfpr.concessionaria.enums.PermissoesAtendente;
import lombok.*;

@Getter
@Setter
public class AtendenteResponse {

    private String nomeAtendente;
    private PermissoesAtendente permissoesAtendente;

}
