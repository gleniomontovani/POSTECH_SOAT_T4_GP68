package br.com.postech.techchallenge.microservico.pedido.service.integracao.queue.producer;

import java.util.Objects;

import org.springframework.stereotype.Service;

import br.com.postech.techchallenge.microservico.pedido.comum.util.Utilitario;
import br.com.postech.techchallenge.microservico.pedido.exception.BusinessException;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoQueueProducer {

	private final SqsTemplate template;
	
	public <T> void send(String queueName, T message) throws BusinessException {
		if (Objects.isNull(message)) {
            log.warn("SQS Producer cant produce 'null' value");
            throw new BusinessException("SQS Producer cant produce 'null' value");
        }
		try {
			String payload = Utilitario.asJsonString(message);
			log.debug("Message {} " + payload);
	        log.debug("Queue name {} " + queueName);
	        
			template.send(queueName, payload);
		} catch (Exception e) {
			throw new BusinessException("Não foi possivel converte o objeto para envio na fila!", e);
		}		
	}
}
