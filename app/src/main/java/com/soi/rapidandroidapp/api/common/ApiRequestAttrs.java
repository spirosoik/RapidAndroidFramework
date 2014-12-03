package com.soi.rapidandroidapp.api.common;

/**
 * Created by Spiros I. Oikonomakis on 11/17/14.
 */
public class ApiRequestAttrs {

    public AbstractApiManager<?> apiManager;
    public String resource;
    public Object[] params;

    public ApiRequestAttrs(AbstractApiManager<?> apiManager, String resource, Object ... params)
    {
        this.apiManager = apiManager;
        this.resource = resource;
        this.params = params;
    }

    public ApiRequestAttrs(String resource)
    {
        this.resource = resource;
    }

    public AbstractApiManager<?> getApiManager()
    {
        return apiManager;
    }

    public void setApiManager(AbstractApiManager<?> apiManager)
    {
        this.apiManager = apiManager;
    }

    public String getResource()
    {
        return resource;
    }

    public void setResource(String resource)
    {
        this.resource = resource;
    }

    public Object[] getParams()
    {
        return params;
    }

    public void setParams(Object[] params)
    {
        this.params = params;
    }
}
