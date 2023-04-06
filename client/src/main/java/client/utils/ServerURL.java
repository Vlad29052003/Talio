package client.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class ServerURL {

    public final String host;
    public final int port;

    /**
     * Creates a new {@link ServerURL} object.
     *
     * @param host of the server. e.g. 'localhost'
     * @param port of the server
     */
    public ServerURL(String host, int port){
        this.host = host;
        this.port = port;
    }

    @Override
    public String toString() {
        return host + ":" + port;
    }

    /**
     * Tries to parse a string as a URL and extracts the host and port
     *
     * @param raw address of the server
     * @return {@link ServerURL} with host and port or null
     */
    public static ServerURL parseURL(String raw){
        if(!raw.startsWith("http://")) raw = "http://" + raw;
        try {
            URL server = new URL(raw);
            String host = server.getHost();
            int port = server.getPort();
            if(port == -1) port = 8080;
            return new ServerURL(host, port);
        } catch (MalformedURLException e) {
            return null;
        }
    }

}
