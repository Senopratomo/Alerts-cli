package org.senolab.alerts.service;

import com.akamai.edgegrid.signer.ClientCredential;
import com.akamai.edgegrid.signer.exceptions.RequestSigningException;
import com.akamai.edgegrid.signer.googlehttpclient.GoogleHttpClientEdgeGridInterceptor;
import com.akamai.edgegrid.signer.googlehttpclient.GoogleHttpClientEdgeGridRequestSigner;
import com.google.api.client.http.*;
import com.google.api.client.http.apache.ApacheHttpTransport;
import org.senolab.alerts.util.Edgerc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

public class OpenAPICallService {
    private final int MAX_BODY = 131072;
    private String[] apiClientInfo;

    private StringBuilder uriPath;
    private ClientCredential credential;
    private HttpRequest request;
    private HttpResponse response;
    private boolean jsonOnly;

    public OpenAPICallService(String edgerc, String method, String httpPath, boolean jsonOnly) {
        apiClientInfo = Edgerc.extractTokens(edgerc);
        this.jsonOnly = jsonOnly;

        //Build the URL based on the path specified and the host from edgerc file
        uriPath = buildURIPath(apiClientInfo[0], httpPath);

        //Build the credential based on tokens provided in edgerc file
        credential = buildCredential(apiClientInfo[2], apiClientInfo[3], apiClientInfo[4], apiClientInfo[0]);

        //Build the HTTP request
        try {
            request = buildHttpRequest(method, uriPath.toString());
        } catch (IOException e) {
            System.out.println(this.getClass().getSimpleName()+": "+e.getMessage());
            e.printStackTrace();
        }
        response = null;

        //Setting Host header
        HttpHeaders headers = request.getHeaders();
        headers.set("Host", apiClientInfo[1]);
    }

    public OpenAPICallService(String edgerc, String method, String httpPath, String jsonBody, boolean jsonOnly) {
        apiClientInfo = Edgerc.extractTokens(edgerc);
        this.jsonOnly = jsonOnly;

        //Build the URL based on the path specified and the host from edgerc file
        uriPath = buildURIPath(apiClientInfo[0], httpPath);

        //Build the credential based on tokens provided in edgerc file
        credential = buildCredential(apiClientInfo[2], apiClientInfo[3], apiClientInfo[4], apiClientInfo[0]);

        //Build the HTTP request
        try {
            request = buildHttpRequest(method, uriPath.toString(), jsonBody);
        } catch (IOException e) {
            System.out.println(this.getClass().getSimpleName()+": "+e.getMessage());
            e.printStackTrace();
        }
        response = null;

        //Add application-json header
        HttpHeaders headers = request.getHeaders();
        headers.setContentType("application/json");
        //Setting Host header
        headers.set("Host", apiClientInfo[1]);
    }

    private ClientCredential buildCredential(String clientToken, String accessToken, String clientSecret, String host) {
        return new ClientCredential.ClientCredentialBuilder()
                .clientToken(clientToken)
                .accessToken(accessToken)
                .clientSecret(clientSecret)
                .host(host)
                .build();
    }

    private StringBuilder buildURIPath(String host, String path) {
        return new StringBuilder()
                .append("https://")
                .append(host)
                .append(path);
    }

    private HttpRequestFactory createSigningRequestFactory() {
        HttpTransport httpTransport = new ApacheHttpTransport();
        return httpTransport.createRequestFactory(new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
                request.setInterceptor(new GoogleHttpClientEdgeGridInterceptor(credential));
            }
        });
    }

    private HttpRequest buildHttpRequest(String method, String urlPath) throws IOException {
        HttpRequestFactory requestFactory = createSigningRequestFactory();
        URI uri = URI.create(urlPath);
        HttpRequest request = null;
        if (method.equalsIgnoreCase("get")) {
            request = requestFactory.buildGetRequest(new GenericUrl(uri));
        } else if (method.equalsIgnoreCase("delete")) {
            request = requestFactory.buildDeleteRequest(new GenericUrl(uri));
        } else if (method.equalsIgnoreCase("put")) {
            request = requestFactory.buildPutRequest(new GenericUrl(uri), null);
        } else if (method.equalsIgnoreCase("post")) {
            request = requestFactory.buildPostRequest(new GenericUrl(uri), null);
        } else {
            request = requestFactory.buildRequest(method.toUpperCase(), new GenericUrl(uri), null);
        }
        request.setFollowRedirects(true);
        request.setReadTimeout(400000);
        if(!jsonOnly) {
            System.out.println(method+" "+urlPath);
        }
        return request;
    }

    private HttpRequest buildHttpRequest(String method, String urlPath, String jsonBody) throws IOException {
        HttpRequestFactory requestFactory = createSigningRequestFactory();
        URI uri = URI.create(urlPath);
        HttpRequest request = null;
        if (method.equalsIgnoreCase("patch")) {
            request = requestFactory.buildPatchRequest(new GenericUrl(uri), ByteArrayContent.fromString(null, jsonBody));
        } else if (method.equalsIgnoreCase("put")) {
            request = requestFactory.buildPutRequest(new GenericUrl(uri), ByteArrayContent.fromString(null, jsonBody));
        } else if (method.equalsIgnoreCase("post")) {
            request = requestFactory.buildPostRequest(new GenericUrl(uri), ByteArrayContent.fromString(null, jsonBody));
        } else {
            request = requestFactory.buildRequest(method.toUpperCase(), new GenericUrl(uri), null);
        }
        request.setFollowRedirects(true);
        request.setReadTimeout(400000);
        if(!jsonOnly) {
            System.out.println(method+" "+urlPath);
            System.out.println("Body: "+jsonBody);
        }

        return request;
    }

    public String execute()  {
        //Sign request and execute
        GoogleHttpClientEdgeGridRequestSigner requestSigner = new GoogleHttpClientEdgeGridRequestSigner(credential);
        StringBuilder results = new StringBuilder("");

        try {
            requestSigner.sign(request);
            response = request.execute();

            //Extract the HTTP Response code and response
            if(response.getContent() != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getContent(), "UTF-8"));
                String json = reader.readLine();

                while (json != null) {
                    results.append(json+"\n");
                    json = reader.readLine();
                }
                reader.close();
            }
        } catch (HttpResponseException hre) {
            System.out.println("HTTP Response code: "+hre.getStatusCode());
            System.out.println("HTTP Response headers: \n"+hre.getHeaders());
            System.out.println("HTTP Response body: \n"+hre.getContent());
        } catch (RequestSigningException e) {
            System.out.println(this.getClass().getSimpleName()+": "+e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(this.getClass().getSimpleName() + ": " + e.getMessage());
            e.printStackTrace();
        }
        if(!jsonOnly) {
            //Print HTTP response code + response headers
            System.out.println("HTTP Response code: "+response.getStatusCode());
            String genericHeaders = response.getHeaders().toString();
            System.out.println("HTTP Response headers: \n"+genericHeaders.substring(362,genericHeaders.length()-1));
            System.out.println("HTTP Response Body: ");
            System.out.println(results.toString());
        } else {
            System.out.println(results.toString());
        }
        return results.toString();
    }
}