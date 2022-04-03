package com.github.adrianoss.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.github.adrianoss.dto.Root;


/**
 * Interface CotacaoDollarService
 * Interface responsável pela comunicação do o serviço externo de cotação de dollar
 * 
 * @author Adriano Silva
 */
@RegisterRestClient
@Path("/CotacaoDolarDia(dataCotacao=@dataCotacao)")
public interface CotacaoDollarService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Root findCotacaoPorData(@QueryParam("@dataCotacao") String dataCotacao);
}
