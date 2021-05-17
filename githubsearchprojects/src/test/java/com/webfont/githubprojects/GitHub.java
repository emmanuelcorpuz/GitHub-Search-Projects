package com.webfont.githubprojects;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.webfont.githubprojects.entity.Project;




public class GitHub {

	public static void main(String[] args) {
		List<Project> projects = new ArrayList<Project>();
		try {
			//URL url = new URL("https://api.github.com/user/repos");//your url i.e fetch data from .
			URL url = new URL("https://api.github.com/search/repositories?q=Impact&order=desc");//your url i.e fetch data from .
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			String username = "emmanuelcorpuz@gmail.com";
			String password = "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";
			String encoded = Base64.getEncoder().encodeToString((username+":"+password).getBytes(StandardCharsets.UTF_8));  //Java 8
			conn.setRequestProperty("Authorization", "Basic "+encoded);
			conn.setRequestMethod("GET");
			//conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
			conn.setRequestProperty("User-Agent", "emmanuelcorpuz@gmail.com");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : "
						+ conn.getResponseCode());
			}
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String output;
			StringBuilder sb = new StringBuilder();
			while ((output = br.readLine()) != null) {
				sb.append(output);
				//System.out.println(output);
			}
			conn.disconnect();

			String json = sb.toString();

			//JSONObject myResponse = new JSONObject(json.substring(1, json.length() - 1));
			//			JSONObject myResponse = new JSONObject("{\"projects\":" + json + "}");
			//			JSONArray items = myResponse.getJSONArray("projects");

			JSONObject myResponse = new JSONObject(json);
			JSONArray items = myResponse.getJSONArray("items");

			for (int i = 0; i < items.length(); i++) {
				long id = items.getJSONObject(i).getLong("id");
				String name = items.getJSONObject(i).getString("name");
				String full_name = items.getJSONObject(i).getString("full_name");
				String html_url = items.getJSONObject(i).getString("html_url");
				Object description = items.getJSONObject(i).get("description");
				System.out.println(id);
				System.out.println(name);
				System.out.println(full_name);
				System.out.println(html_url);
				if (!description.equals(null)) {
					System.out.println(description);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
