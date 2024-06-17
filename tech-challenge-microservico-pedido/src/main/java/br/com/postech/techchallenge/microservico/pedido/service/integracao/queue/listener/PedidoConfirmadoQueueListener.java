package br.com.postech.techchallenge.microservico.pedido.service.integracao.queue.listener;

import org.springframework.stereotype.Service;

import br.com.postech.techchallenge.microservico.pedido.comum.util.Utilitario;
import br.com.postech.techchallenge.microservico.pedido.exception.BusinessException;
import br.com.postech.techchallenge.microservico.pedido.model.request.PedidoRequest;
import br.com.postech.techchallenge.microservico.pedido.service.PedidoService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoConfirmadoQueueListener {
	
	private final PedidoService pedidoService;

	@SqsListener(value = "${queue.pedido.confirmado}")
	public void receive(String message) throws BusinessException {
		try {
            log.debug("Mensagem recebida: {}", message );
            PedidoRequest pedido = Utilitario.asStringJsonFromObject(message, PedidoRequest.class);

            pedidoService.atualizarPedido(pedido);
        } catch (Exception e) {
            throw new BusinessException("Cannot process message from SQS", e);
        }
	}
}
