package com.wepiao.admin.user.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

public class HttpServletRequestCopier extends HttpServletRequestWrapper {

    private byte[]                   rawData;
    private HttpServletRequest       request;
    private ServletInputStreamCopier servletStreamCopier;

    public HttpServletRequestCopier(HttpServletRequest request) throws IOException {
        super(request);
        this.request = request;
        this.servletStreamCopier = new ServletInputStreamCopier();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (rawData == null) {
            rawData = IOUtils.toByteArray(this.request.getReader());
            servletStreamCopier.setInputStream(new ByteArrayInputStream(rawData));
        }
        return servletStreamCopier;
    }

    /**
     * 读过之后要重置InputStream, 以便业务代码(Controller)再读.
     */
    public void resetInputStream() {
        servletStreamCopier.setInputStream(new ByteArrayInputStream(rawData));
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (rawData == null) {
            rawData = IOUtils.toByteArray(this.request.getReader());
            servletStreamCopier.setInputStream(new ByteArrayInputStream(rawData));
        }
        return new BufferedReader(new InputStreamReader(servletStreamCopier));
    }
}
