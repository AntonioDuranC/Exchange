package com.nttdata.client;

import com.nttdata.dto.TypeExchangeResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@RegisterRestClient(configKey = "com.nttdata.client.ExchangeApi")
public interface ExchangeApi {

    @GET
    @Path("/tipo-cambio/today.json")
    @Produces(MediaType.APPLICATION_JSON)
    TypeExchangeResponse getToday();
}
