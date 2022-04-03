package com.github.adrianoss.resource;

import java.time.temporal.ChronoUnit;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.github.adrianoss.business.CotacaoBusiness;
import com.github.adrianoss.domain.CotacaoDiaUtil;
import com.github.adrianoss.tools.DateUtil;


/**
 * Classe CotacaoResource
 * 
 * Classe responsável pela exposição do serviço de cotação de dólar
 * 
 * @author Adriano Silva
 */
@Path("/cotacao")
public class CotacaoResource {
	
	@Inject
	CotacaoBusiness cotacaoBusiness;
	

	/**
	 * Método que faz a consulta de valores do dolar em uma determinada data
	 * <br/>
	 * @Timeout() - máximo de 3 segundos 
	 * <br/>
	 * @Retry()   - Máximo de 3 tentativas caso o serviço falhe
	 * <br/>
	 * @CircuitBreaker(requestVolumeThreshold = 10, failureRatio = 0.1)
	 * Se das ultimas 10 requisições, 10% falharem, o circuito será "aberto"
	 * e o serviço não responderá mais 
	 * 
	 * 
	 * */
	@GET
    @Tag(name = "Cotação do Dólar", description = "Fornece a cotação do dólar, na data informada e do dia útil anterior.") 
    @Operation(summary = "Cotação do Dólar de uma data e do dia útil anterior.", description = "Fornece a cotação do dólar, na data informada e do dia útil anterior.")
    @Parameter(description = "Data da cotação é obrigatória.", required = true, example = "MM-dd-yyyy")
	@APIResponseSchema(value = CotacaoDiaUtil.class, responseDescription = "Objeto de retorno", responseCode = "200")
	@Produces(MediaType.APPLICATION_JSON)
	@Timeout(value = 3, unit = ChronoUnit.SECONDS)
	@Retry(maxRetries = 3)
	@CircuitBreaker(requestVolumeThreshold = 10, failureRatio = 0.1)
    public Response cotacaoDolarPor(@NotNull @QueryParam("dataCotacao") String dataCotacao) {
		String dataHoraCotacao = new StringBuilder().append("'").append(dataCotacao).append("'").toString();
		
		if(!DateUtil.isValid(dataHoraCotacao)) {
			return Response.status(Response.Status.BAD_REQUEST).build();
    	}
		
		if(DateUtil.verificaSeEhFimDeSemana(dataHoraCotacao)){
			return Response.status(Response.Status.BAD_REQUEST).build();
    	}
		
		CotacaoDiaUtil cotacaoDiaUtil = cotacaoBusiness.findCotacaoPorData(dataHoraCotacao);
		
		
		if (cotacaoDiaUtil == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		return Response.ok(cotacaoDiaUtil).build();
    }
}