package com.discordhook;

import com.discordhook.pojo.DiscordMessage;
import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public final class Discordhook {

    private final URL hookUrl;
    private HttpsURLConnection hookConnection;
    private final DiscordMessage discordMessage;

    public Discordhook(String hookUrl) throws MalformedURLException {
        this.discordMessage = new DiscordMessage();
        this.hookUrl = new URL(hookUrl);
    }

    public void toss(String message) throws IOException {
        addMessage(message);
        send();
    }

    public void addMessage(String content) {
        this.discordMessage.setContent(content);
    }

    public void asUser(String username) {
        this.discordMessage.setUsername(username);
    }

    public void withAvatar(String avatarUrl) throws MalformedURLException {
        // url check
        final URL url = new URL(avatarUrl);

        this.discordMessage.setAvatar_url(avatarUrl);
    }

    public void send() throws IOException {
        validateDiscordMessage();
        openNewConnection();
        injectAndSend();
    }

    private void injectAndSend() throws IOException {
        this.hookConnection.getOutputStream().write(new Gson().toJson(discordMessage).getBytes());
        this.hookConnection.getInputStream().close();
        this.hookConnection.disconnect();
    }

    private void openNewConnection() throws IOException {
        // clear and disconnect if connection is still active
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

    private void validateDiscordMessage() {
        if (this.discordMessage.getContent() == null) {
            throw new NullPointerException("Message cannot be null");
        }
    }
}
