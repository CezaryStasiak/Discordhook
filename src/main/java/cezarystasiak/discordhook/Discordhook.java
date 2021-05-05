package cezarystasiak.discordhook;

import cezarystasiak.discordhook.pojo.DiscordMessage;
import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public final class Discordhook {

    private final URL hookUrl;
    private HttpsURLConnection hookConnection;
    private String message;

    public Discordhook(URL hookUrl) {
        this.hookUrl = hookUrl;
    }

    public Discordhook(String hookUrl) throws MalformedURLException {
        this.hookUrl = new URL(hookUrl);
    }

    public void toss(String message) throws IOException {

        if (message == null) {
            throw new NullPointerException("message cannot be null");
        }

        simpleSend(message);
    }

    private void simpleSend(String message) throws IOException {
        openConnection();
        addMessage(message);
        send();
    }

    private void openConnection() throws IOException {
        if (this.hookConnection != null) {
            this.hookConnection.disconnect();
            this.hookConnection = null;
        }

        this.hookConnection = (HttpsURLConnection) hookUrl.openConnection();
        prepMetas();
    }

    private void prepMetas() throws ProtocolException {
        this.hookConnection.addRequestProperty("Content-Type", "application/json");
        this.hookConnection.setDoOutput(true);
        this.hookConnection.setRequestMethod("POST");
    }

    private void addMessage(String content) throws IOException {
        final DiscordMessage message = new DiscordMessage(content);
        this.hookConnection.getOutputStream().write(new Gson().toJson(message).getBytes());
    }

    private void send() throws IOException {
        this.hookConnection.getInputStream().close();
        this.hookConnection.disconnect();
    }
}
