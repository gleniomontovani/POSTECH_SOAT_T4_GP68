package br.com.postech.techchallenge.microservico.producao.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.postech.techchallenge.microservico.producao.configuration.AwsSqsPedidoQueueProperties;
import br.com.postech.techchallenge.microservico.producao.model.response.ProducaoResponse;
import br.com.postech.techchallenge.microservico.producao.repository.ProducaoJpaRepository;
import br.com.postech.techchallenge.microservico.producao.repository.ProducaoMongoRepository;
import br.com.postech.techchallenge.microservico.producao.service.ProducaoService;
import br.com.postech.techchallenge.microservico.producao.service.impl.ProducaoServiceImpl;
import br.com.postech.techchallenge.microservico.producao.service.integracao.queue.producer.ProducaoQueueProducer;
import br.com.postech.techchallenge.microservico.producao.util.ObjectCreatorHelper;

class ProducaoServiceTest {
	
	private ProducaoService producaoService;
	@Mock
	private ProducaoJpaRepository producaoJpaRepository;
	@Mock
	private ProducaoMongoRepository producaoMongoRepository;
	@Mock
	private ProducaoQueueProducer producaoQueueProducer;
	@Mock
	private AwsSqsPedidoQueueProperties pedidoQueueProperties;
	
	AutoCloseable openMocks;
	
	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		producaoService = new ProducaoServiceImpl(producaoJpaRepository, producaoMongoRepository, producaoQueueProducer,
				pedidoQueueProperties);
	}
	
	@AfterEach
	void close() throws Exception {
		openMocks.close();
	}

	@Test
	void devePermitirListarTodasProducaoPorSituacao() {
		var producaoModel1 = ObjectCreatorHelper.obterProducao();
		producaoModel1.setId(1L);
		
		var producaoModel2 = ObjectCreatorHelper.obterProducao();
		producaoModel2.setId(2L);
		
		var producoes = Arrays.asList(producaoModel1, producaoModel2);
		
		when(producaoJpaRepository.findBySituacaoProducao(any())).thenReturn(producoes);
		
		var producaosResponse = producaoService.listarTodasProducaoPorSituacao(1);
		
		assertThat(producaosResponse).hasSize(2);
		
		assertThat(producaosResponse)
			.asList()
			.allSatisfy(producao -> {
				assertThat(producao).isNotNull();
				assertThat(producao).isInstanceOf(ProducaoResponse.class);
			});
		
		verify(producaoJpaRepository, times(1)).findBySituacaoProducao(any());
	}

	@Test
	void devePermitirBuscarProducaoPorNumeroPedido() {
		var producaoModel = ObjectCreatorHelper.obterProducao();
		producaoModel.setId(1L);
		
		when(producaoJpaRepository.findByNumeroPedido(anyLong())).thenReturn(Optional.of(producaoModel));
		
		var produtoResponse = producaoService.buscarProducaoPorNumeroPedido(1L);
		
		assertThat(produtoResponse).isInstanceOf(ProducaoResponse.class).isNotNull();
		assertThat(produtoResponse).extracting(ProducaoResponse::getId).isEqualTo(producaoModel.getId());
		assertThat(produtoResponse).extracting(ProducaoResponse::getNumeroPedido).isEqualTo(producaoModel.getNumeroPedido());
		assertThat(produtoResponse).extracting(ProducaoResponse::getObservacao).isEqualTo(producaoModel.getObservacao());
		assertThat(produtoResponse).extracting(ProducaoResponse::getDataInicioPreparo).isEqualTo(producaoModel.getDataInicioPreparo().toString());
		assertThat(produtoResponse).extracting(ProducaoResponse::getStatusPedido).isEqualTo(producaoModel.getSituacaoProducao().getDescricao());
		
		verify(producaoJpaRepository, times(1)).findByNumeroPedido(anyLong());
	}

	@Test
	void devePermitirSalvarProducaoPedido() {
		var producaoModel = ObjectCreatorHelper.obterProducao();
		producaoModel.setId(1L);
		
		var producaoRequest = ObjectCreatorHelper.obterProducaoRequest();		
		
		when(producaoJpaRepository.save(any())).thenReturn(producaoModel);
		
		var produtoResponse = producaoService.salvarProducaoPedido(producaoRequest);
		
		assertThat(produtoResponse).isInstanceOf(ProducaoResponse.class).isNotNull();
		assertThat(produtoResponse).extracting(ProducaoResponse::getId).isEqualTo(producaoModel.getId());
		assertThat(produtoResponse).extracting(ProducaoResponse::getNumeroPedido).isEqualTo(producaoModel.getNumeroPedido());
		assertThat(produtoResponse).extracting(ProducaoResponse::getObservacao).isEqualTo(producaoModel.getObservacao());
		assertThat(produtoResponse).extracting(ProducaoResponse::getDataInicioPreparo).isEqualTo(producaoModel.getDataInicioPreparo().toString());
		assertThat(produtoResponse).extracting(ProducaoResponse::getStatusPedido).isEqualTo(producaoModel.getSituacaoProducao().getDescricao());
				
		verify(producaoJpaRepository, times(1)).save(any());
	}

	@Test
	void devePermitirAtualizarStatusProducao() {
		var producaoModel = ObjectCreatorHelper.obterProducao();
		producaoModel.setId(1L);
		
		var producaoRequest = ObjectCreatorHelper.obterProducaoRequest();
		
		when(producaoJpaRepository.findByNumeroPedido(anyLong())).thenReturn(Optional.of(producaoModel));
		when(producaoJpaRepository.save(any())).thenReturn(producaoModel);
		
		var produtoResponse = producaoService.atualizarStatusProducao(producaoRequest);
		
		assertThat(produtoResponse).isInstanceOf(ProducaoResponse.class).isNotNull();
		assertThat(produtoResponse).extracting(ProducaoResponse::getId).isEqualTo(producaoModel.getId());
		assertThat(produtoResponse).extracting(ProducaoResponse::getNumeroPedido).isEqualTo(producaoModel.getNumeroPedido());
		assertThat(produtoResponse).extracting(ProducaoResponse::getObservacao).isEqualTo(producaoModel.getObservacao());
		assertThat(produtoResponse).extracting(ProducaoResponse::getDataInicioPreparo).isEqualTo(producaoModel.getDataInicioPreparo().toString());

		verify(producaoJpaRepository, times(1)).findByNumeroPedido(anyLong());
		verify(producaoJpaRepository, times(1)).save(any());
	}
}
