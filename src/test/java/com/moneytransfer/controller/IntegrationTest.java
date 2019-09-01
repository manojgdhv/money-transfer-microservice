package com.moneytransfer.controller;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;


abstract class IntegrationTest implements AutoCloseable {
    private ResteasyClient resteasyClient = new ResteasyClientBuilder().build();
    private TJWSEmbeddedJaxrsServer server;
    private Object object;

    public IntegrationTest(Object object) {
        this.object = object;
    }

    protected  TJWSEmbeddedJaxrsServer start(){
        server = new TJWSEmbeddedJaxrsServer();
        server.setPort(8080);
        server.setBindAddress("localhost");
        server.getDeployment().getResources().add(object);
        server.start();
        return server;
    }

    private String baseUri() {
        return "http://localhost:8080";
    }


    public ResteasyWebTarget newRequest(String uriTemplate) {
        return resteasyClient.target(baseUri() + uriTemplate);
    }

    @Override
    public void close() {
        if (server != null) {
            server.stop();
            server = null;
        }
    }
}