package br.com.selfmaintenance.utils;

public class RespostaApi {
    private int status;
    private String mensagem;
    private Object dados;

    public RespostaApi(int status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
    }

    public RespostaApi(int status, String mensagem, Object dados) {
        this.status = status;
        this.mensagem = mensagem;
        this.dados = dados;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Object getDados() {
        return dados;
    }

    public void setDados(Object dados) {
        this.dados = dados;
    }
}
