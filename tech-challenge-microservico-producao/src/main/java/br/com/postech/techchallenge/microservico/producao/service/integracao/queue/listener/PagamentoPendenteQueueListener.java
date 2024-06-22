package br.com.postech.techchallenge.microservico.producao.service.integracao.queue.listener;

import org.springframework.stereotype.Service;

import br.com.postech.techchallenge.microservico.producao.exception.BusinessException;
import br.com.postech.techchallenge.microservico.producao.model.request.ProducaoRequest;
import br.com.postech.techchallenge.microservico.producao.service.ProducaoService;
import br.com.postech.techchallenge.microservico.producao.service.integracao.response.PagamentoResponse;
import br.com.postech.techchallenge.microservico.producao.util.Constantes;
import br.com.postech.techchallenge.microservico.producao.util.Utilitario;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagamentoPendenteQueueListener {

	private final ProducaoService producaoService;

	@SqsListener(value = "${queue.pagamento.pendente}")
	public void receive(String message) throws BusinessException {
		try {
			log.debug("Mensagem de Pagamento Pendente recebida: {}", message);
			PagamentoResponse pagamento = Utilitario.asStringJsonFromObject(message, PagamentoResponse.class);

			var producao = new ProducaoRequest(pagamento.getNumeroPedido(), Constantes.DESCRIPTION_PRODUCTION_CREATE,
					pagamento.getStatusPagamento());
			producaoService.salvarProducaoPedido(producao);
		} catch (Exception e) {
			throw new BusinessException("Cannot process message from SQS", e);
		}
	}
}
