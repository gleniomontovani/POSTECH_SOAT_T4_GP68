package br.com.postech.techchallenge.microservico.producao.util;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utilitario {

	public static String asJsonString(final Object obj) throws Exception {
		return new ObjectMapper().writeValueAsString(obj);
	}
	
	public static <T> T asStringJsonFromObject(String json, Class<T> type)  throws Exception {
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
		
		return objectMapper.readValue(json, type);
	}
}
