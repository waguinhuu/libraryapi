package io.github.waguinhuu.libraryapi.exceptions;

import lombok.Getter;

public class CampoValidoException extends RuntimeException {

    @Getter
    String campo;

    public CampoValidoException(String campo, String mensagem) {
        super(mensagem);
        this.campo = campo;
    }
}
