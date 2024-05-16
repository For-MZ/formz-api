package com.example.demo.Core;


import com.example.demo.Entity.Housing;
import com.example.demo.Repository.HousingRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.Entity.QHousing.housing;

@Configuration
@RequiredArgsConstructor
public class OpenApiManager {
    private final HousingRepository housingRepository;

    public void fetch(String brtcCode,String signguCode) throws JsonProcessingException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "https://data.myhome.go.kr/rentalHouseList?serviceKey=0OhBU7ZCGIobDVKDeBJDpmDRqK3IRNF6jlf%2FJB2diFAf%2FfR2czYO9A4UTGcsOwppV6W2HVUeho%2FFPwXoL6DwqA%3D%3D&brtcCode="+brtcCode+"&signguCode="+signguCode+"&numOfRows=1000&pageNo=1";
        System.out.println(baseUrl);
        URI uri = new URI(baseUrl);
        String jsonString = restTemplate.getForObject(uri, String.class);
        JsonNode json = objectMapper.readTree(jsonString);
        JsonNode hsmpList = json.get("hsmpList");
        System.out.println(hsmpList);
        if (hsmpList.isArray()) {
            List<Housing> housings = new ArrayList<>();
            JsonNode[] rowArray = new JsonNode[hsmpList.size()];
            for (int i = 0; i < hsmpList.size(); i++) {
                Housing housing = new Housing();
                housing.setHsmpSn(hsmpList.get(i).get("hsmpSn").asText());
                housing.setInsttNm(hsmpList.get(i).get("insttNm").asText());
                housing.setBrtcNm(hsmpList.get(i).get("brtcNm").asText());
                housing.setSignguNm(hsmpList.get(i).get("signguNm").asText());
                housing.setHsmpNm(hsmpList.get(i).get("hsmpNm").asText());
                housing.setRnAdres(hsmpList.get(i).get("rnAdres").asText());
                housing.setCompetDe(hsmpList.get(i).get("competDe").asText());
                housing.setHshldCo(hsmpList.get(i).get("hshldCo").asText());
                housing.setSuplyTyNm(hsmpList.get(i).get("suplyTyNm").asText());
                housing.setStyleNm(hsmpList.get(i).get("styleNm").asText());
                housing.setSuplyPrvuseAr(hsmpList.get(i).get("suplyPrvuseAr").asText());
                housing.setSuplyCmnuseAr(hsmpList.get(i).get("suplyCmnuseAr").asText());
                housing.setHouseTyNm(hsmpList.get(i).get("houseTyNm").asText());
                housing.setHeatMthdDetailNm(hsmpList.get(i).get("heatMthdDetailNm").asText());
                housing.setBuldStleNm(hsmpList.get(i).get("buldStleNm").asText());
                housing.setElvtrInstlAtNm(hsmpList.get(i).get("elvtrInstlAtNm").asText());
                housing.setParkngCo(hsmpList.get(i).get("parkngCo").asText());
                housing.setBassRentGtn(hsmpList.get(i).get("bassRentGtn").asText());
                housing.setBassMtRntchrg(hsmpList.get(i).get("bassMtRntchrg").asText());
                housing.setBassCnvrsGtnLmt(hsmpList.get(i).get("bassCnvrsGtnLmt").asText());
                housings.add(housing);
            }
            housingRepository.saveAll(housings);
        }

    }

}
