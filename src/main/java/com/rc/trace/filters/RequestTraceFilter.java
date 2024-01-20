package com.rc.trace.filters;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class RequestTraceFilter extends OncePerRequestFilter {

    private String extractDomainFromToken(HttpServletRequest request) {
        // TODO: Implement this
        return "RTCL";
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String clientRequestId = request.getHeader("X-Client-Request-ID");
        MDC.put("x-client-request-id", clientRequestId != null ? clientRequestId : "unknown");

        String domain = extractDomainFromToken(request);
        MDC.put("domain", domain != null ? domain : "unknown");

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove("x-client-request-id");
            MDC.remove("domain");
        }
    }
}
