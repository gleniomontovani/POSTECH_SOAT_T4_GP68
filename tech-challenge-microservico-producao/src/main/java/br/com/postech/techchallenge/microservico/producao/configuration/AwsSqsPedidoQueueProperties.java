package br.com.postech.techchallenge.microservico.producao.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "queue.pedido")
public class AwsSqsPedidoQueueProperties {

	private String confirmado;
}
