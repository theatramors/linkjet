package ru.codexus.linkjet.dto;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Link {

    private String id;
    private String url;
    private LocalDateTime expiresIn;

    public Link(String id, String url, LocalDateTime expiresIn) {
        this.id = id;
        this.url = url;
        this.expiresIn = expiresIn;
    }

    public static Link mapRow(ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getString("id");
        var url = rs.getString("url");
        var expiredIn = rs.getTimestamp("expired_in");

        return new Link(id, url, expiredIn != null ? expiredIn.toLocalDateTime() : null);
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Nullable
    public LocalDateTime getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(LocalDateTime expiresIn) {
        this.expiresIn = expiresIn;
    }
}
