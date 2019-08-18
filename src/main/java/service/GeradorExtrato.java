package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.DTO.ExtratoDTO;
import model.DTO.TransacaoDTO;
import model.Extrato;
import model.Transacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class GeradorExtrato {

    public void leitorArquivo(Extrato extrato) {
        List<String> linhasArquivo = new ArrayList<>();
        BufferedReader br = null;
        String strLinha;

        try {
            br = new BufferedReader(new FileReader("./extrato.log"));
            while ((strLinha = br.readLine()) != null) {
                linhasArquivo.add(strLinha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        linhasArquivo.remove(0);

        String[] transacaoLinha;
        Transacao transacao;
        for (String linha : linhasArquivo) {
            transacaoLinha = linha.split("\\s{2,}");
            try {
                transacao = Transacao
                        .fromExtratoArq(transacaoLinha[0], transacaoLinha[1], transacaoLinha[2],
                                (transacaoLinha.length == 4) ? removeAcentos(transacaoLinha[3]) : "SEM CATEGORIA");
                extrato.getMovimentacoes().add(transacao);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void leitorApi(Extrato extrato){
        URL obj = null;
        ObjectMapper objMapper = new ObjectMapper();
        ExtratoDTO extratoDTO = null;

        try {
            obj = new URL("https://my-json-server.typicode.com/cairano/backend-test/db");

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            extratoDTO = objMapper.readValue(response.toString(), ExtratoDTO.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            for(TransacaoDTO transacaoDTO : extratoDTO.getPagamentos()){
                extrato.getMovimentacoes().add(Transacao.fromTransacaoDTO(transacaoDTO));
            }
            for(TransacaoDTO transacaoDTO : extratoDTO.getRecebimentos()){
                extrato.getMovimentacoes().add(Transacao.fromTransacaoDTO(transacaoDTO));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private String removeAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
    }
}
