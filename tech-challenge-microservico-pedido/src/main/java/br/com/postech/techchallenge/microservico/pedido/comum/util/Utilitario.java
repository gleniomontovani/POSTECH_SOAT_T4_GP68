package br.com.postech.techchallenge.microservico.pedido.comum.util;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.ZonedDateTime;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.postech.techchallenge.microservico.pedido.model.DadosEnvioPix;

public class Utilitario {

	public static String asJsonString(final Object obj) throws Exception {
		return new ObjectMapper().writeValueAsString(obj);
	}
	
	public static <T> T asStringJsonFromObject(String json, Class<T> type)  throws Exception {
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
		
		return objectMapper.readValue(json, type);
	}
	
	public static String gerarQrCodePix(BigDecimal valorPix) {
		final var dadosPix = new DadosEnvioPix(
				Constantes.NOME_DESTINATARIO_PIX_QRCODE,
				Constantes.CHAVE_DESTINATARIO_PIX_QRCODE, 
				valorPix,
				Constantes.CIDADE_DESTINATARIO_PIX_QRCODE, 
				String.valueOf(ZonedDateTime.now().toInstant().toEpochMilli()));

        final var qrCodePix = new QRCodePix(dadosPix);
        qrCodePix.save(Path.of(Constantes.IMAGEM_QRCODE_PATH));
        
        return qrCodePix.toString();
	}
}
