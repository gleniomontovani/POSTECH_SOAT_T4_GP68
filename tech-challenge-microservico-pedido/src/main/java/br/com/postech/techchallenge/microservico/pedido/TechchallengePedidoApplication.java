package br.com.postech.techchallenge.microservico.pedido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.com.postech.techchallenge.microservico.pedido.configuration.AwsSqsQueueProperties;

@SpringBootApplication
@EnableConfigurationProperties({ AwsSqsQueueProperties.class})
public class TechchallengePedidoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechchallengePedidoApplication.class, args);
	}
}