package br.com.postech.techchallenge.microservico.pagamento.service.integracao.queue.listener;

import org.springframework.stereotype.Service;

import br.com.postech.techchallenge.microservico.pagamento.exception.BusinessException;
import br.com.postech.techchallenge.microservico.pagamento.model.request.PagamentoRequest;
import br.com.postech.techchallenge.microservico.pagamento.service.PagamentoService;
import br.com.postech.techchallenge.microservico.pagamento.util.Utilitario;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SolicitacaoPedidoQueueListener {

	private final PagamentoService pagamentoService;
	
	@SqsListener(value = "${queue.pedido.solicitacao}")
	public void receive(String message) throws BusinessException {
		try {
            log.debug("Mensagem de Solicitação de Pedido recebida: {}", message );
            PagamentoRequest pagamento = Utilitario.asStringJsonFromObject(message, PagamentoRequest.class);

            pagamentoService.criarPagamento(pagamento);
        } catch (Exception e) {
            throw new BusinessException("Cannot process message from SQS", e);
        }
	}
}
