package online_market.user_app.client.dadata;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class MainDadataRestClient implements DadataRestClient {

    private final RestClient restClient;

    @Value("${services.api.keys.dadata-key-public}")
    private String apiKey;

    @Override
    public List<String> getListOfAddresses(String query) {
        Map<String, String> body = Map.of("query", query);
        String json = this.restClient.post()
                .uri("suggestions/api/4_1/rs/suggest/address")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization","Token " +  apiKey)
                .body(body)
                .retrieve()
                .body(String.class);

        JSONObject responseObject = new JSONObject(json);
        JSONArray suggestions = responseObject.getJSONArray("suggestions");

        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < suggestions.length(); i++) {
            resultList.add(suggestions.getJSONObject(i).getString("value"));
        }
        return resultList;
    }
}
