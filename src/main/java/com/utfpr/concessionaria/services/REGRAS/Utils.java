package com.utfpr.concessionaria.services.REGRAS;

import com.utfpr.concessionaria.enums.PermissoesAtendente;
import com.utfpr.concessionaria.services.CRUD.AtendentesCRUDservice;
import com.utfpr.concessionaria.view.entities.Venda;
import org.springframework.stereotype.Service;

@Service
public record Utils(AtendentesCRUDservice atendentesCRUDservice) { //Service to deploy useful codes

    //Return the permission of the worker to apply discount
    public Integer tipoDescontoFuncionario(Venda venda) {
        PermissoesAtendente ps = atendentesCRUDservice.getPermissao(venda.getAtendente());
        return switch (ps) {
            case PERMITEDESCONTOCOMPLETO -> 1;
            case PERMITEDESCONTOPARCIAL -> 2;
            case SEMDESCONTO -> 3;
        };
    }

}
