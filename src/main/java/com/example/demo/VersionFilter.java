package com.example.demo;

import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Component
public class VersionFilter implements javax.servlet.Filter {

    public static final String HEADER_VERSION = "Api-Version";
    public static final String DEFAULT_VERSION = "1.1.0";

    public class RequestWithVersionWrapper extends HttpServletRequestWrapper {
        private String version;

        public RequestWithVersionWrapper(HttpServletRequest request, String version) {
            super(request);
            this.version = version;
        }

        public String getHeader(String name) {
            if (HEADER_VERSION.equals(name)) {
                return version;
            } else {
                return super.getHeader(name);
            }
        }

        public Enumeration getHeaderNames() {
            List<String> names = Collections.list(super.getHeaderNames());
            names.add(HEADER_VERSION);
            return Collections.enumeration(names);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String version = req.getHeader(HEADER_VERSION);
        if (version == null) {
            version = DEFAULT_VERSION;

            request = new RequestWithVersionWrapper(req, version);
        }

        resp.addHeader(HEADER_VERSION, version);
        chain.doFilter(request, response);
    }
}
