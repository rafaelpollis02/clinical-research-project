package br.com.clinicalresearch.integration;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://viacep.com.br/ws")
public interface WebServiceZipCode {

    @GET
    @Path("{zipCode}/json")
    Response getZipCodeByWebService(String zipCode);

}
