package com.br.teste.attus.dto;
/**
 * Author : Gabriel F F Lobão
 */
public class ErrorDTO {
    private String message;
    private String lancamento;

    public ErrorDTO(String message, String lancamento) {
        this.message = message;
        this.lancamento = lancamento;
    }

    public ErrorDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLancamento() {
        return lancamento;
    }

    public void setLancamento(String lancamento) {
        this.lancamento = lancamento;
    }
}
