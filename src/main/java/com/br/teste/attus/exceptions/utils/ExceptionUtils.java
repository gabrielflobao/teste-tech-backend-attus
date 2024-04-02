package com.br.teste.attus.exceptions.utils;

import com.br.teste.attus.exceptions.AbstractException;
import com.br.teste.attus.exceptions.endereco.EnderecoNotFoundException;

import java.util.List;
import java.util.Optional;

public class ExceptionUtils {
    public static void checkListEmptyExceptionWithMsg(List<?> empty, AbstractException ex) {
        if (empty==null||empty.isEmpty()) {
            throw ex;
        }
    }


}
