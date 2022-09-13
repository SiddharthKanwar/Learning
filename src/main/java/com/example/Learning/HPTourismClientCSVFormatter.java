package com.example.Learning;

import sendinblue.Pair;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HPTourismClientCSVFormatter {
    public static final LinkedHashMap<String, String> keyMapper = new LinkedHashMap<>();
    static {
        keyMapper.put("Name", "name");
        keyMapper.put("Mobile Number", "Mobile Number:");
        keyMapper.put("Address", "Address:");
        keyMapper.put("Contact Person", "Contact Person:");
        keyMapper.put("Landline Number", "Landline Number:");
        keyMapper.put("Email", "Email:");
        keyMapper.put("Tehsil", "Tehsil:");
        keyMapper.put("District","District:");
        keyMapper.put("Landmark","Landmark:");
        keyMapper.put("Post Office","Post Office:");
    }
    public static Map<String, String> format(ScrapData scrapData) {
        return keyMapper.keySet().stream().map(key-> new Pair(key, scrapData.getParamOr(keyMapper.get(key), "").toString())).collect(Collectors.toMap(Pair::getName, Pair::getValue, (x, y) -> y, LinkedHashMap::new));
    }
}
