package ForMZ.Server.SearchHistory.Service;

import ForMZ.Server.SearchHistory.Dto.searchHistoryDto;
import ForMZ.Server.SearchHistory.Entity.SearchHistory;
import ForMZ.Server.SearchHistory.Repository.SearchHistoryRepository;
import ForMZ.Server.SearchHistory.Repository.SearchHistoryRepositoryImpl;
import ForMZ.Server.User.Dto.UserJoinDto;
import ForMZ.Server.User.Entity.User;
import ForMZ.Server.User.Repository.UserRepository;
import ForMZ.Server.User.Service.UserService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class SearchHistoryServiceTest {
    @Autowired SearchHistoryService searchHistoryService;
    @Autowired UserService userService;
    @Autowired EntityManager em;

    @Test
    public void findUserSearchHistory_ERROR() throws Exception {
        UserJoinDto userJoinDto = new UserJoinDto("fjfkle352", "www@www.com", "user","/ee");
        userService.join(userJoinDto);
        em.flush();
        em.clear();
        Optional<User> id = userService.findByUserId("www@www.com");
        try {
            List<searchHistoryDto> searchHistoryDtos = searchHistoryService.FindUserSearchHistory(id.get().getId());
            for (searchHistoryDto searchHistoryDto : searchHistoryDtos) {
                System.out.println(searchHistoryDto.getSearchWord());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    @Test
    public void findUserSearchHistory() throws Exception {
        UserJoinDto userJoinDto = new UserJoinDto("fjfkle352", "www@www.com", "user","/ee");
        userService.join(userJoinDto);
        em.flush();
        em.clear();
        Optional<User> id = userService.findByUserId("www@www.com");
        SearchHistory searchHistory = new SearchHistory("find");
        searchHistoryService.save(searchHistory);
        searchHistory.setUser(id.get());
        em.flush();
        em.clear();
        List<searchHistoryDto> searchHistoryDtos = searchHistoryService.FindUserSearchHistory(id.get().getId());
        for (searchHistoryDto searchHistoryDto : searchHistoryDtos) {
            System.out.println(searchHistoryDto.getSearchWord());
        }
    }
    @Test
    public void deleteUserSearchHistory_ERROR() throws Exception {
        UserJoinDto userJoinDto = new UserJoinDto("fjfkle352", "www@www.com", "user","/ee");
        userService.join(userJoinDto);
        em.flush();
        em.clear();
        Optional<User> id = userService.findByUserId("www@www.com");
        em.flush();
        em.clear();
        try {
            searchHistoryService.DeleteSearchHistory(id.get().getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void deleteUserSearchHistory() throws Exception {
        UserJoinDto userJoinDto = new UserJoinDto("fjfkle352", "www@www.com", "user","/ee");
        userService.join(userJoinDto);
        em.flush();
        em.clear();
        Optional<User> id = userService.findByUserId("www@www.com");
        SearchHistory searchHistory = new SearchHistory("find");
        searchHistoryService.save(searchHistory);
        searchHistory.setUser(id.get());
        em.flush();
        em.clear();
        searchHistoryService.DeleteSearchHistory(id.get().getId());
    }
}