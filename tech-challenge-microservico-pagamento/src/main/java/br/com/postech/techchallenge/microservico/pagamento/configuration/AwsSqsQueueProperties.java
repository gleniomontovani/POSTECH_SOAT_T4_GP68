package br.com.postech.techchallenge.microservico.pagamento.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "queue.pagamento")
public class AwsSqsQueueProperties {

	private String pendente;
	private String confirmado;
}
