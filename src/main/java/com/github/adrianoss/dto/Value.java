package com.github.adrianoss.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Value {

	@JsonProperty("cotacaoCompra")
	double cotacaoCompra;

	@JsonProperty("cotacaoVenda")
	double cotacaoVenda;

	@JsonProperty("dataHoraCotacao")
	String dataHoraCotacao;
	
}
