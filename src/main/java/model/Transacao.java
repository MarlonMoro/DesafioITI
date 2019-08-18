package model;

import model.DTO.TransacaoDTO;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class Transacao {

    private MonthDay data;
    private String descricao;
    private String moeda;
    private BigDecimal valor;
    private String categoria;

    public MonthDay getData() {
        return data;
    }

    public void setData(MonthDay data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public static Transacao fromExtratoArq(String data, String descricao, String valor, String categoria) throws ParseException {
        Transacao transacao = new Transacao();
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
        Number teste = nf.parse(valor);
        transacao.setDescricao(descricao);
        transacao.setValor(new BigDecimal(teste.toString()));
        transacao.setCategoria(categoria.toUpperCase());

        DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("dd-MMM"))
                .toFormatter().withLocale(Locale.US);

        TemporalAccessor temp = dateFormatter.parse(data);
        MonthDay monthDay = MonthDay.from(temp);
        transacao.setData(monthDay);

        return transacao;
    }

    public static Transacao fromTransacaoDTO(TransacaoDTO transacaoDTO) throws ParseException {
        Transacao transacao = new Transacao();

        DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("dd/MMM"))
                .toFormatter().withLocale(new Locale("pt", "BR"));

        TemporalAccessor temp = dateFormatter.parse(transacaoDTO.getData());
        MonthDay monthDay = MonthDay.from(temp);

        transacao.setData(monthDay);
        transacao.setDescricao(transacaoDTO.getDescricao());
        transacao.setMoeda(transacaoDTO.getMoeda());
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
        Number teste = nf.parse(transacaoDTO.getValor());

        transacao.setValor(new BigDecimal(teste.toString()));
        if(transacaoDTO.getCategoria() != null){
            transacao.setCategoria(transacaoDTO.getCategoria());
        }

        return transacao;
    }
}
