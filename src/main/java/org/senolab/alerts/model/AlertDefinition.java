package org.senolab.alerts.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.senolab.alerts.service.OpenAPICallService;

import java.io.FileReader;
import java.io.IOException;

public class AlertDefinition {
    private final String BASE_ENDPOINT = "/alerts/v2/alert-definitions";
    private String METHOD;
    private StringBuilder urlEndpoint;
    private String jsonBody;
    private OpenAPICallService openAPICallService;
    private String edgeRC;
    private boolean jsonOnly;
    private String accountKey;

    public AlertDefinition(String edgercFile, boolean jsonOnly, String accountKey) {
        this.urlEndpoint = new StringBuilder(BASE_ENDPOINT);
        this.METHOD = "GET";
        this.edgeRC = edgercFile;
        this.jsonOnly = jsonOnly;
        this.accountKey = accountKey;
    }

    public void listAllAlerts() {
        if(accountKey != null) {
            urlEndpoint.append("?accountSwitchKey="+accountKey);
        }
        openAPICallService = new OpenAPICallService(edgeRC, METHOD, urlEndpoint.toString(), jsonOnly);
    }

    public void getAnAlert(String id) {
        urlEndpoint.append("/").append(id);
        if(accountKey != null) {
            urlEndpoint.append("?accountSwitchKey="+accountKey);
        }
        openAPICallService = new OpenAPICallService(edgeRC, METHOD, urlEndpoint.toString(), jsonOnly);
    }

    public void deleteAnAlert(String id) {
        this.METHOD = "DELETE";
        urlEndpoint.append("/").append(id);
        if(accountKey != null) {
            urlEndpoint.append("?accountSwitchKey="+accountKey);
        }
        openAPICallService = new OpenAPICallService(edgeRC, METHOD, urlEndpoint.toString(), jsonOnly);
    }

    public void createAnAlert(String jsonBodyFile) {
        if(accountKey != null) {
            urlEndpoint.append("?accountSwitchKey="+accountKey);
        }
        this.METHOD = "POST";
        String requestBody = null;
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(new FileReader(jsonBodyFile));
        } catch (IOException e) {
            System.out.println(this.getClass().getSimpleName()+": "+e.getMessage());
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println(this.getClass().getSimpleName()+": "+e.getMessage());
            e.printStackTrace();
        }
        if(obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;
            requestBody = jsonObject.toJSONString();
        }
        if(obj instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) obj;
            requestBody = jsonArray.toJSONString();
        }
        openAPICallService = new OpenAPICallService(edgeRC, METHOD, urlEndpoint.toString(), requestBody, jsonOnly);
    }

    public void resetEndpoint() {
        this.urlEndpoint = new StringBuilder(BASE_ENDPOINT);
    }

    public String execute() {
        return openAPICallService.execute();
    }


}
