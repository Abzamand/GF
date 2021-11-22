package id.co.qualitas.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
//send email

import id.co.qualitas.service.interfaces.HomeService;


@Service
public class HomeServiceImpl implements HomeService {
	
	@Override
	public Object news(Object request){
		Map result = new HashMap();
		String quantity  = (String) request;
		try {
			URL url = new URL("https://www.kpbn.co.id/api_news/news?api_key=50f138c63553512442413098ed7a4c5e&show_data=" + quantity);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestMethod("GET");    
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			String content = "";
			while ((inputLine = in.readLine()) != null) {
				content += inputLine;
			}
			in.close();
			con.disconnect();
			ObjectMapper objectMapper = new ObjectMapper();
			result =  objectMapper.readValue(content, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}

