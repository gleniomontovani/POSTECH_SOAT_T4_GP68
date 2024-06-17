package br.com.postech.techchallenge.microservico.pagamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.com.postech.techchallenge.microservico.pagamento.configuration.AwsSqsQueueProperties;

@SpringBootApplication
@EnableConfigurationProperties({ AwsSqsQueueProperties.class})
public class TechchallengePagamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechchallengePagamentoApplication.class, args);
	}
}