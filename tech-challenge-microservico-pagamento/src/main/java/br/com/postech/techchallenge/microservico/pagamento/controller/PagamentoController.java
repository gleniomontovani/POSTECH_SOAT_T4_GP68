package br.com.postech.techchallenge.microservico.pagamento.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.techchallenge.microservico.pagamento.exception.BusinessException;
import br.com.postech.techchallenge.microservico.pagamento.model.request.PagamentoRequest;
import br.com.postech.techchallenge.microservico.pagamento.model.response.PagamentoResponse;
import br.com.postech.techchallenge.microservico.pagamento.service.PagamentoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {
	private final PagamentoService pagamentoService;

	@GetMapping(path = "/{numeroPedido}", produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<PagamentoResponse> consultarPagamentoPorPedido(@PathVariable Long numeroPedido)
			throws Exception {
		return new ResponseEntity<>(pagamentoService.consultarStatusPagamentoPorPedido(numeroPedido), HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<PagamentoResponse> criarPagamento(@RequestBody PagamentoRequest pagamentoRequest) {
		return new ResponseEntity<>(pagamentoService.criarPagamento(pagamentoRequest), HttpStatus.CREATED);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<PagamentoResponse> atualizaPagamento(@RequestBody PagamentoRequest pagamentoRequest)
			throws BusinessException {
		return new ResponseEntity<>(pagamentoService.atualizaPagamento(pagamentoRequest), HttpStatus.OK);
	}
}