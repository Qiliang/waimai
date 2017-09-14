package com.xiaoql.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AppVersion {

    @Id
    String version;
    String url;
    int status;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
