package ForMZ.Server.Core.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityListeners;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
@Transactional
class
OpenApiManagerTest {
    @Autowired OpenApiManager openApiManager;
    @Test
    @Rollback(value = false)
    void fetch() throws URISyntaxException, JsonProcessingException, InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        Map<String, List<String>> houseMap = new HashMap<>();
        houseMap.put("11", List.of(new String[]{"110", "140", "170", "200", "215","230", "260", "290", "305", "320", "350", "380", "410", "440", "470", "500", "530", "545", "560", "590", "620", "650", "680", "710", "740"}));
        houseMap.put("27",List.of(new String[]{"110", "140", "170", "200", "230", "260", "290", "710"}));
        houseMap.put("28",List.of(new String[]{"110", "140", "170", "185", "200", "237", "245", "260", "710", "720"}));
        houseMap.put("26",List.of(new String[]{"110", "140", "170", "200", "230", "260", "290", "320", "350", "380", "410", "440", "470", "500", "530", "710"}));
        houseMap.put("29",List.of(new String[]{"110", "140", "155", "170", "200"}));
        houseMap.put("30",List.of(new String[]{"110", "140", "170", "200", "230"}));
        houseMap.put("31",List.of(new String[]{"110", "140", "170", "200","710"}));
        houseMap.put("36",List.of(new String[]{"110"}));
        houseMap.put("41",List.of(new String[]{"110", "111", "113", "115", "117", "130", "131", "133", "135", "150", "170", "171", "173", "190", "210", "220", "250", "270", "271", "273", "280", "281", "285", "287", "290", "310", "360", "370", "390", "410", "430", "450", "460", "461", "463", "465", "480", "500", "550", "570", "590", "610", "630", "650", "670", "800", "820", "830"}));
        houseMap.put("42",List.of(new String[]{"110", "130", "150", "170", "190", "210", "230", "720", "730", "750", "760", "770", "780", "790", "800", "810", "820", "830"}));
        houseMap.put("43",List.of(new String[]{"110", "111", "112", "113", "114", "130", "150", "720", "730", "740", "745", "750", "760", "770", "800"}));
        houseMap.put("44",List.of(new String[]{"130", "131", "133", "150", "180", "200", "210", "230", "250", "270", "710", "760", "770", "790", "800", "810", "825"}));
        houseMap.put("45",List.of(new String[]{"110", "111", "113", "130", "140", "180", "190", "210", "710", "720", "730", "740", "750", "770", "790", "800"}));
        houseMap.put("46",List.of(new String[]{"110", "130", "150", "170", "230", "710", "720", "730", "770", "780", "790", "800", "810", "820", "830", "840", "860", "870", "880", "890", "900", "910"}));
        houseMap.put("47",List.of(new String[]{"110","111", "113", "130", "150", "170", "190", "210", "230", "250", "280", "290","720", "730", "750", "760", "770", "820", "830", "840", "850", "900", "920", "930", "940"}));
        houseMap.put("48",List.of(new String[]{"120", "121", "123", "125", "127", "129", "170", "220", "240", "250", "270", "310", "330", "720", "730", "740", "820", "840", "850", "860", "870", "880", "890"}));
        houseMap.put("50",List.of(new String[]{"110", "130"}));

        Set<Map.Entry<String, List<String>>> set = houseMap.entrySet();
        Iterator<Map.Entry<String, List<String>>> iterator = set.iterator();
        while(iterator.hasNext()){
            try {
                Map.Entry<String, List<String>> next = iterator.next();
                String brtcCode = next.getKey();
                for (String signguCode : next.getValue()) {
                    openApiManager.fetch(brtcCode, signguCode);
                }
            }
            catch (Exception e){
                System.out.println(e);
            }

        }
//        for(int i=0;i<2;i++){
//            openApiManager.fetch("11","110");
//        }
        openApiManager.s();
        LocalDateTime last = LocalDateTime.now();
        Duration diff = Duration.between(now, last);
        System.out.println(diff.toSeconds());
    }
}