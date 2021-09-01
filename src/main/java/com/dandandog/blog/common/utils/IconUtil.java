package com.dandandog.blog.common.utils;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/1 9:38
 */
public class IconUtil {

    private static final String URL = "selection.json";


    public static List<String> findAll() {
        List<String> icons = new ArrayList<>();
        try {
            JSONObject json = readJson();
            JSONArray iconsArray = json.getJSONArray("icons");
            for (int i = 0; i < iconsArray.size(); i++) {
                JSONObject properties = iconsArray.getJSONObject(i).getJSONObject("properties");
                icons.add(properties.getStr("name"));
            }
        } catch (IOException | JSONException ignored) {
        }
        return icons;
    }

    private static JSONObject readJson() throws IOException {
        ClassPathResource resource = new ClassPathResource(URL);
        try (InputStream is = resource.getStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
