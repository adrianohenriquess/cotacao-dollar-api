package com.github.adrianoss.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({ "dataHoraCotacao", "valorCotacaoCompra", "valorCotacaoVenda", "dataHoraCotacaoDiaUtilAnterior",
		"valorCotacaoCompraDiaUtilAnterior", "valorCotacaoVendaDiaUtilAnterior" })
@Getter
@Setter
public class CotacaoDiaUtil {

	@JsonProperty("valorCotacaoCompra")
	double valorCotacaoCompra;

	@JsonProperty("valorCotacaoVenda")
	double valorCotacaoVenda;

	@JsonProperty("dataHoraCotacao")
	String dataHoraCotacao;

	@JsonProperty("valorCotacaoCompraDiaUtilAnterior")
	double valorCotacaoCompraDiaUtilAnterior;

	@JsonProperty("valorCotacaoVendaDiaUtilAnterior")
	double valorCotacaoVendaDiaUtilAnterior;

	@JsonProperty("dataHoraCotacaoDiaUtilAnterior")
	String dataHoraCotacaoDiaUtilAnterior;	
}
