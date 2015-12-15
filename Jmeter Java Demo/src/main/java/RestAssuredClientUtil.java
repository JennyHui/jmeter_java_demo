
import com.google.gson.*;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// shout out to king shushu
public class RestAssuredClientUtil extends RestAssured {

    public static RequestSpecification getRequestSpecification(String body, Headers headers) {
        return given().headers(headers).body(body);
    }

    public static Headers getHeaders(List<Header> headers) {
        return new Headers(headers);
    }

    public static List<Header> getHeaderList(String headers) {
        JsonObject jsonObject = new JsonParser().parse(headers).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> setHeaders = jsonObject.entrySet();
        return setHeaders.stream().map(entry -> new Header(entry.getKey(), entry.getValue().getAsString())).collect(Collectors.toList());
    }

    public static Response request(RequestSpecification requestSpecification, String method, String uri) throws Exception {
        if (method.equals("GET"))
            return requestSpecification.get(uri);
        else if (method.equals("POST"))
            return requestSpecification.post(uri);
        else if (method.equals("PUT"))
            return requestSpecification.put(uri);
        else if (method.equals("PATCH"))
            return requestSpecification.patch(uri);
        else if (method.equals("HEAD"))
            return requestSpecification.head(uri);
        else if (method.equals("DELETE"))
            return requestSpecification.delete(uri);
        else if (method.equals("OPTIONS"))
            return requestSpecification.options(uri);
        else
            throw new Exception("该方法不支持");
    }


    public static Response executeHttp(RequestData requestData) {
        Response response = null;
        try {
            response = request(getRequestSpecification(requestData.getBody(),
                            getHeaders(getHeaderList(requestData.getHeaders()))),
                    requestData.getMethod(), requestData.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String getHeaders(Headers headers) {
        JsonObject jsonObject = new JsonObject();
        Map<String, String> cookies = new HashMap<>();
        for (Header header : headers) {
            if (header.getName().equals("Set-Cookie")) {
                Map<String, String> cookie = parseCookies(header.getValue());
                cookies.putAll(cookie);
            }
            jsonObject.add(header.getName(), new Gson().toJsonTree(header.getValue()));
        }
        jsonObject.add("Cookie", JsonUtil.toElement(new Gson().toJson(cookies, cookies.getClass())));
        return new Gson().toJson(jsonObject);
    }

    public static Map<String, String> parseCookies(String cookie) {
        return JsonUtil.toMap(cookie, "; ");
    }
}
