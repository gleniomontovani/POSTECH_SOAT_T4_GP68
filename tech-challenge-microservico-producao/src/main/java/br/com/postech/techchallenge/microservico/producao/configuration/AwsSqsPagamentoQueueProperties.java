package br.com.postech.techchallenge.microservico.producao.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "queue.pagamento")
public class AwsSqsPagamentoQueueProperties {

	private String pendente;
	private String confirmado;
}
