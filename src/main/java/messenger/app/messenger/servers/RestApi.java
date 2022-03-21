package messenger.app.messenger.servers;

public class RestApi {
    String serverURL = "http://localhost:3000/";

    protected String getUrl(String path) {
        return serverURL + path;
    }
}
