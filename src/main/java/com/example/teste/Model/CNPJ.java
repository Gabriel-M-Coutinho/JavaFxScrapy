package com.example.teste.Model;

import java.util.List;

public class CNPJ {

    private String CNPJ;
    private String Razao;
    private String Porte;
    private String CapitalSocial;
    private String NaturezaJuridica;
    private String SimplesNacional;
    private String Mei;
    private String Tipo;
    private String DataAbertura;
    private String NomeFantasia;
    private String SituacaoCadastral;
    private String SituacaoDesde;
    private String Telefone;
    private String Email;
    private String MunicipioIBGE;
    private String Endereco;
    private String CEP;
    private String Bairro;
    private String Complemento;
    private String Estado;
    private String Numero;
    private String CnaePrincipal;
    private List<String> CnaeSecundario;

    public CNPJ() {

    }

    public CNPJ(String CNPJ, String razao, String porte, String capitalSocial, String naturezaJuridica, String simplesNacional, String mei, String tipo, String dataAbertura, String nomeFantasia, String situacaoCadastral, String situacaoDesde, String telefone, String email, String municipioIBGE, String endereco, String cep, String bairro, String complemento, String estado, String numero) {
        this.CNPJ = CNPJ;
        this.Razao = razao;
        this.Porte = porte;
        this.CapitalSocial = capitalSocial;
        this.NaturezaJuridica = naturezaJuridica;
        this.SimplesNacional = simplesNacional;
        this.Mei = mei;
        this.Tipo = tipo;
        this.DataAbertura = dataAbertura;
        this.NomeFantasia = nomeFantasia;
        this.SituacaoCadastral = situacaoCadastral;
        this.SituacaoDesde = situacaoDesde;
        this.Telefone = telefone;
        this.Email = email;
        this.MunicipioIBGE = municipioIBGE;
        this.Endereco = endereco;
        this.CEP = cep;
        this.Bairro = bairro;
        this.Complemento = complemento;
        this.Estado = estado;
        this.Numero = numero;
    }

    // Getters e Setters
    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getRazao() {
        return Razao;
    }

    public void setRazao(String razao) {
        Razao = razao;
    }

    public String getPorte() {
        return Porte;
    }

    public void setPorte(String porte) {
        Porte = porte;
    }

    public String getCapitalSocial() {
        return CapitalSocial;
    }

    public void setCapitalSocial(String capitalSocial) {
        CapitalSocial = capitalSocial;
    }

    public String getNaturezaJuridica() {
        return NaturezaJuridica;
    }

    public void setNaturezaJuridica(String naturezaJuridica) {
        NaturezaJuridica = naturezaJuridica;
    }

    public String getSimplesNacional() {
        return SimplesNacional;
    }

    public void setSimplesNacional(String simplesNacional) {
        SimplesNacional = simplesNacional;
    }

    public String getMei() {
        return Mei;
    }

    public void setMei(String mei) {
        Mei = mei;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getDataAbertura() {
        return DataAbertura;
    }

    public void setDataAbertura(String dataAbertura) {
        DataAbertura = dataAbertura;
    }

    public String getNomeFantasia() {
        return NomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        NomeFantasia = nomeFantasia;
    }

    public String getSituacaoCadastral() {
        return SituacaoCadastral;
    }

    public void setSituacaoCadastral(String situacaoCadastral) {
        SituacaoCadastral = situacaoCadastral;
    }

    public String getSituacaoDesde() {
        return SituacaoDesde;
    }

    public void setSituacaoDesde(String situacaoDesde) {
        SituacaoDesde = situacaoDesde;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMunicipioIBGE() {
        return MunicipioIBGE;
    }

    public void setMunicipioIBGE(String municipioIBGE) {
        MunicipioIBGE = municipioIBGE;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        Bairro = bairro;
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String complemento) {
        Complemento = complemento;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public void setCnaePrincipal(String cnaePrincipal){
        CnaePrincipal = cnaePrincipal;
    }
    public void setCnaeSecundario(List<String> list){
        CnaeSecundario = list;
    }


    @Override
    public String toString() {
        return "CNPJ{" +
                "CnaePrincipal" + CnaePrincipal + '\''+
                "CNPJ='" + CNPJ + '\'' +
                ", Razao='" + Razao + '\'' +
                ", Porte='" + Porte + '\'' +
                ", CapitalSocial='" + CapitalSocial + '\'' +
                ", NaturezaJuridica='" + NaturezaJuridica + '\'' +
                ", SimplesNacional='" + SimplesNacional + '\'' +
                ", Mei='" + Mei + '\'' +
                ", Tipo='" + Tipo + '\'' +
                ", DataAbertura='" + DataAbertura + '\'' +
                ", NomeFantasia='" + NomeFantasia + '\'' +
                ", SituacaoCadastral='" + SituacaoCadastral + '\'' +
                ", SituacaoDesde='" + SituacaoDesde + '\'' +
                ", Telefone='" + Telefone + '\'' +
                ", Email='" + Email + '\'' +
                ", MunicipioIBGE='" + MunicipioIBGE + '\'' +
                ", Endereco='" + Endereco + '\'' +
                ", CEP='" + CEP + '\'' +
                ", Bairro='" + Bairro + '\'' +
                ", Complemento='" + Complemento + '\'' +
                ", Estado='" + Estado + '\'' +
                ", Numero='" + Numero + '\'' +
                '}';
    }
}