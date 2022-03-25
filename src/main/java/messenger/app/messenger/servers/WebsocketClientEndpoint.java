package messenger.app.messenger.servers;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URI;

public class WebsocketClientEndpoint {

    static  Socket socket;

    public void createSocket(URI endpointURI) {
        IO.Options options = IO.Options.builder()
                .build();
        WebsocketClientEndpoint.socket = IO.socket(endpointURI, options);
    }

    public void connect() {
        WebsocketClientEndpoint.socket.connect();
    }

    public void disconnect() {
        WebsocketClientEndpoint.socket.disconnect();
    }


    public void listenEvent(String eventName, Emitter.Listener fn) {
        WebsocketClientEndpoint.socket.on(eventName, fn);
    }

    public void emitEvent(String eventName, Object...args) {
        WebsocketClientEndpoint.socket.emit(eventName, args);
    }
}