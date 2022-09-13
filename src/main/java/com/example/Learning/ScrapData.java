package com.example.Learning;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class ScrapData {
    int id;
    String title;
    Map<String, Object> params = new HashMap<>();

    public void setParam(String key, Object param) {
        params.put(key, param);
    }
    public Object getParamOr(String key, String defaultValue) {
         return params.getOrDefault(key, defaultValue);
    }

    public ScrapData() {

    }

    public ScrapData(int id, String title, Map<String, Object> params) {
        this.id = id;
        this.title = title;
        this.params = params;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
