package cezarystasiak.discordhook;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public final class Discordhook {

    private final URL hookUrl;
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
        HttpsURLConnection hook = (HttpsURLConnection) hookUrl.openConnection();

        hook.addRequestProperty("Content-Type", "application/json");
        hook.setDoOutput(true);
        hook.setRequestMethod("POST");
        hook.getOutputStream().write((" {\"content\" : \""+ message +"\"} ").getBytes());

        hook.getInputStream().close();
        hook.disconnect();
    }
}
