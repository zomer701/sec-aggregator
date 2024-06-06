package io.scommerce.core.inbound.aggregator.filters;

import io.scommerce.core.inbound.aggregator.utils.CryptoUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class AuthFilter implements WebFilter {

    @Value("${security.auth.secret}")
    private String auth;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("sign"))
                .map(CryptoUtil::decrypt)
                .filter(authHeader -> authHeader.equals(auth))
                .map(jwtString -> chain.filter(exchange))
                .orElseGet(() -> {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().writeWith(Mono.empty());
                        }
                );
    }
}
