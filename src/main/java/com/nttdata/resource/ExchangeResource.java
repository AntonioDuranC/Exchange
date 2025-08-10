package com.nttdata.resource;

import com.nttdata.dto.TypeExchangeResponse;
import com.nttdata.exception.ApiError;
import com.nttdata.exception.CustomApiException;
import com.nttdata.service.ExchangeService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/exchange")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExchangeResource {

    @Inject
    ExchangeService exchangeService;

    @GET
    @Path("/today")
    public Response getTodayExchange(@QueryParam("dni") String dni) {
        if (dni == null || dni.isBlank()) {
            throw new CustomApiException(ApiError.INVALID_DNI);
        }

        TypeExchangeResponse result = exchangeService.getExchangeForDni(dni.trim());
        return Response.ok(result).build();
    }
}
