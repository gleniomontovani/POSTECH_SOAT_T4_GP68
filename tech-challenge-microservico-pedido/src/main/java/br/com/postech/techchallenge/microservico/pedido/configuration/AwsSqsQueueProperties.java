package br.com.postech.techchallenge.microservico.pedido.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "queue.pedido")
public class AwsSqsQueueProperties {

	private String solicitacao;
	private String confirmado;
}
