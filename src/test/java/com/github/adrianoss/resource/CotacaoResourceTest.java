package com.github.adrianoss.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import com.github.adrianoss.tools.DateUtil;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CotacaoResourceTest {

	@Test
	public void testDataCotacaoValida() {
		given().queryParam("dataCotacao", "03-29-2022")
				.when().get("/cotacao").then().statusCode(200)
				.body(is("{\"dataHoraCotacao\":\"2022-03-29 15:02:02.979\",\"dataHoraCotacaoDiaUtilAnterior\":\"2022-03-28 17:46:38.607\",\"valorCotacaoCompra\":4.748,\"valorCotacaoCompraDiaUtilAnterior\":4.7899,\"valorCotacaoVenda\":4.7486,\"valorCotacaoVendaDiaUtilAnterior\":4.7905}"));
	}
	
	@Test
	public void testDataCotacaoInvalida() {
		given().queryParam("dataCotacao", "03-32-2022")
				.when().get("/cotacao").then().statusCode(400);
	}
	
	@Test
	public void testInformandoDataNoFimDeSemana() {
		given().queryParam("dataCotacao", "03-27-2022")
				.when().get("/cotacao").then().statusCode(400);
	}
	
	@Test
	public void testInformandoDataVazia() {
		given().queryParam("dataCotacao", "")
				.when().get("/cotacao").then().statusCode(400);
	}
	
	@Test
	public void testInformandoDataComFormatoInvalido() {
		given().queryParam("dataCotacao", "28/03/2022")
				.when().get("/cotacao").then().statusCode(400);
	}
	
	@Test
	public void testInformandoUmaDataQueODiaAnteriorEhDomingo() {
		given().queryParam("dataCotacao", "03-28-2022")
				.when().get("/cotacao").then().statusCode(200)
				.body(is("{\"dataHoraCotacao\":\"2022-03-28 17:46:38.607\",\"dataHoraCotacaoDiaUtilAnterior\":\"2022-03-25 17:47:18.116\",\"valorCotacaoCompra\":4.7899,\"valorCotacaoCompraDiaUtilAnterior\":4.7776,\"valorCotacaoVenda\":4.7905,\"valorCotacaoVendaDiaUtilAnterior\":4.7782}"));				
	}
	
	@Test
	public void testInformandoDataQueNaoRetornaDados() {
		LocalDate hoje = LocalDate.now();
		LocalDate amanha = hoje.plusDays(1);
		//enquanto amanha for fim de semana, adiciono um dia a mais para não passar o parametro fim de semana e estourar outro erro
		//que não seja o 404
		while (DateUtil.verificaSeEhFimDeSemana(amanha)) {
			amanha = amanha.plusDays(1);
		}
		
		String data = amanha.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
		given().queryParam("dataCotacao", data)
				.when().get("/cotacao").then().statusCode(404);
	}
	
	@Test
	public void testInformandoDataNula() {
		String valor = null;
		given().queryParam("dataCotacao", valor)
				.when().get("/cotacao").then().statusCode(400);
	}
	
	@Test
	public void testInformandoDataComEspacosEmBranco() {
		given().queryParam("dataCotacao", "    03-29-2022     ")
				.when().get("/cotacao").then().statusCode(400);
	}
}