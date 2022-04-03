package com.github.adrianoss.business;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.github.adrianoss.domain.CotacaoDiaUtil;
import com.github.adrianoss.dto.Root;
import com.github.adrianoss.service.CotacaoDollarService;
import com.github.adrianoss.tools.DateUtil;


/**
 * Classe CotacaoBusiness
 * Classe responsável pelo processamento dos valores recebidos pelo serviço externo de cotação
 * 
 * @author Adriano Silva
 */
@RequestScoped
public class CotacaoBusiness {
	
	@RestClient
	@Inject
	CotacaoDollarService service;

	public CotacaoDiaUtil findCotacaoPorData(String dataCotacao) {
		if (dataCotacao != null) {			
			Root cotacaoDataInformada = service.findCotacaoPorData(dataCotacao);
			
			if (cotacaoDataInformada != null && 
					cotacaoDataInformada.getValue() != null && 
						!cotacaoDataInformada.getValue().isEmpty()) {			
				CotacaoDiaUtil cotacaoDiaUtil = new CotacaoDiaUtil();
				cotacaoDiaUtil.setValorCotacaoCompra(cotacaoDataInformada.getValue().get(0).getCotacaoCompra());
				cotacaoDiaUtil.setValorCotacaoVenda(cotacaoDataInformada.getValue().get(0).getCotacaoVenda());
				cotacaoDiaUtil.setDataHoraCotacao(cotacaoDataInformada.getValue().get(0).getDataHoraCotacao());
				
				String dataUtilAnterior = DateUtil.retornaDiaUtilAnteriorA(dataCotacao);
				
				Root cotacaoDiaAnterior = service.findCotacaoPorData(dataUtilAnterior);
				cotacaoDiaUtil.setValorCotacaoCompraDiaUtilAnterior(cotacaoDiaAnterior.getValue().get(0).getCotacaoCompra());
				cotacaoDiaUtil.setValorCotacaoVendaDiaUtilAnterior(cotacaoDiaAnterior.getValue().get(0).getCotacaoVenda());
				cotacaoDiaUtil.setDataHoraCotacaoDiaUtilAnterior(cotacaoDiaAnterior.getValue().get(0).getDataHoraCotacao());
				return cotacaoDiaUtil;
			}
		}
		
		return null;
	}

	
}
