package br.com.postech.techchallenge.microservico.producao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.com.postech.techchallenge.microservico.producao.configuration.AwsSqsPedidoQueueProperties;


@SpringBootApplication
@EnableConfigurationProperties({ AwsSqsPedidoQueueProperties.class, AwsSqsPedidoQueueProperties.class})
public class TechchallengeProducaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechchallengeProducaoApplication.class, args);
	}
}
