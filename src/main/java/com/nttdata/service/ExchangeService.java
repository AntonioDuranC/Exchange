package com.nttdata.service;

import com.nttdata.client.ExchangeApi;
import com.nttdata.dto.TypeExchangeResponse;
import com.nttdata.exception.ApiError;
import com.nttdata.exception.CustomApiException;
import com.nttdata.model.ExchangeQueryRecord;
import com.nttdata.repository.ExchangeQueryRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ApplicationScoped
public class ExchangeService {

    private static final int DAILY_LIMIT = 10;

    @Inject
    ExchangeQueryRepository repository;

    @Inject
    @RestClient
    ExchangeApi exchangeApi;

    @Transactional
    public TypeExchangeResponse getExchangeForDni(String dni) {
        LocalDate today = LocalDate.now();
        long count = repository.countByDniAndDate(dni, today);

        if (count >= DAILY_LIMIT) {
            throw new CustomApiException(ApiError.DAILY_LIMIT_EXCEEDED);
        }

        try {
            return exchangeApi.getToday();
        } catch (Exception e) {
            throw new CustomApiException(ApiError.EXTERNAL_API_ERROR);
        } finally {
            repository.persist(new ExchangeQueryRecord(dni, LocalDateTime.now(), today));
        }
    }
}
