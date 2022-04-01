package vttp2022.ssf.assessment.videosearch.service;

import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import vttp2022.ssf.assessment.videosearch.models.Game;

@Service
public class SearchService {
      
   // private static final String URL = "https://api.rawg.io/api";

    @Value("${rawg.api.key}")
    private String apiKey;

    public List<Game> search(String searchString, Integer count) {
        
        List<Game> gamesList =new ArrayList();
       
        String gameUri = UriComponentsBuilder
        .fromUriString("https://api.rawg.io/api")
        .toUriString();

        
        JsonObjectBuilder gameObj = Json.createObjectBuilder();
            gameObj
            .add("search", searchString)
            .add("page_size", count)
            .add("key", apiKey);

        RestTemplate template = new RestTemplate();
        RequestEntity<String> req = RequestEntity
            .post(gameUri)
            .body(gameObj.build().toString());



            try {
                ResponseEntity<String> response = template.exchange(req, String.class);
                Game result = new Game();
                    
                JsonReader jsonReader = Json.createReader(new StringReader(response.getBody()));
                JsonObject gamObject = jsonReader.readObject();
                JsonArray results  = gamObject.getJsonArray("results");
    
                for(int i=0; i< results.size(); i++){
                    
                    JsonObject o = results.getJsonObject(i);
                    
                    Game game = new Game(); 
                    

                    game.setName(o.getString("name"));
                    game.setRating((float)o.getInt("rating"));


                    gamesList.add(game);
                }
    
            
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            
            return gamesList;
        }
    }
