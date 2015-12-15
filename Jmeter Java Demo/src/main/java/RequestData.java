// shout out to king shushu
public class RequestData {
    private String path = "";
    private String url = "";
    private String hostAbbreviation = "";
    private String method = "";
    private String headers = "{}";
    private String body = null;
    private String interfaceName = "";

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void setHostAbbreviation(String hostAbbreviation) {
        this.hostAbbreviation = hostAbbreviation;
    }
}
