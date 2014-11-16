package com.soi.rapidandroidapp.api.interceptors;

import java.util.HashMap;
import java.util.Map;

import retrofit.RequestInterceptor;

/**
 * Created by Spiros I. Oikonomakis on 10/31/14.
 *
 * RequestInterceptor with HTTP headers for the BASE API
 */
public class FoursquareApiRequestInterceptor implements RequestInterceptor {

    /**
     * Custom HTTP Headers map with key and value
     */
    private Map<String, String> headers = new HashMap<String, String>();

    public FoursquareApiRequestInterceptor() {
    }

    public FoursquareApiRequestInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public void intercept(RequestFacade request) {
        if (headers.size()  > 0) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                request.addHeader(header.getKey(), header.getValue());
            }
        }
    }
}
