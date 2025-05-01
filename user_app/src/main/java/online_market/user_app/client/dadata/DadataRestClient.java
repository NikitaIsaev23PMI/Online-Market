package online_market.user_app.client.dadata;

import java.util.List;

public interface DadataRestClient {

    List<String> getListOfAddresses(String query);
}
