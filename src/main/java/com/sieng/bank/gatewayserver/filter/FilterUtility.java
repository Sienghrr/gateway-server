package com.sieng.bank.gatewayserver.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class FilterUtility {
    public static final String CORRELATION_ID = "siengbank-correlation-id";

    public String getCorrelationId(HttpHeaders requestHeader) {
        if(requestHeader.get(CORRELATION_ID) != null) {
            List<String> requestHeaderList = requestHeader.get(CORRELATION_ID);
            if (requestHeaderList != null) {
                return requestHeaderList.stream().findFirst().get();
            }
        }
        return null;
    }

    // set header in request
    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate()
                .request(exchange.getRequest().mutate().header(name, value).build()).build();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }
}
