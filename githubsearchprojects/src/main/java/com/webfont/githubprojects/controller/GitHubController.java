package com.webfont.githubprojects.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webfont.githubprojects.entity.Project;




@RestController
@RequestMapping("/webfont")
public class GitHubController {

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(path = {"/project/{searchString}"}, produces = "application/json")
	public ResponseEntity<?> getGitHubProjects(@PathVariable(required=true,name="searchString") String searchString,
            @RequestParam(required=true) Map<String,String> qparams){
		List<Project> projects = new ArrayList<Project>();
		try {
			URL url = new URL("https://api.github.com/search/repositories?q=" + searchString + "&order=desc");//your url i.e fetch data from .
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			String username = qparams.get("username"); 
			String token = qparams.get("token"); 
			String encoded = Base64.getEncoder().encodeToString((username+":"+token).getBytes(StandardCharsets.UTF_8));  //Java 8
			conn.setRequestProperty("Authorization", "Basic "+encoded);
			conn.setRequestMethod("GET");
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
				Project project = new Project();
				project.setId(id);
				project.setName(name);
				project.setFull_name(full_name);
				project.setHtml_url(html_url);
				if (!description.equals(null)) {
					project.setDescription((String) description);
				}
				projects.add(project);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(projects,HttpStatus.OK);
	}



}
