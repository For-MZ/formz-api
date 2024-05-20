package ForMZ.Server.Core;

import ForMZ.Server.Core.API.Housing;
import ForMZ.Server.Core.API.OpenApiManager;
import ForMZ.Server.Repository.HousingRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class OpenApiManagerTest {
    @Autowired
    OpenApiManager openApiManager;
    @Autowired
    HousingRepository housingRepository;
    @Test
    void fetch() throws URISyntaxException, JsonProcessingException {
        long a = System.currentTimeMillis();
        HashMap<String, List<String>> map = new HashMap<>();
        String[] s_array = {"110","140","170","200","215","230","260","290","305","320","350", "380", "410", "440", "470", "500", "530", "545", "560", "590", "620", "650", "680", "710", "740"};
        map.put("11", List.of(s_array));
        for (Map.Entry<String, List<String>> stringListEntry : map.entrySet()) {
            for (String s : stringListEntry.getValue()) {
                openApiManager.fetch(stringListEntry.getKey(),s);
            }
        }
        long b = System.currentTimeMillis();
        System.out.println((b-a)/1000);
        a = System.currentTimeMillis();
        List<Housing> all = housingRepository.findAll();
        b = System.currentTimeMillis();
        System.out.println(b-a);
    }
}