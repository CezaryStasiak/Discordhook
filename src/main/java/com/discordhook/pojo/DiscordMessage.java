package com.discordhook.pojo;

public final class DiscordMessage {

    private String content;
    private String username;
    private String avatar_url;

    public void setContent(String content) {
        this.content = content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar_url() {
        return avatar_url;
    }
}
