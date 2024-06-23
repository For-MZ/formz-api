package ForMZ.Server.User.Service;

import ForMZ.Server.User.Dto.ChangeProFileDto;
import ForMZ.Server.User.Dto.UserDto;
import ForMZ.Server.User.Dto.UserJoinDto;
import ForMZ.Server.User.Entity.User;
import ForMZ.Server.User.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.asserts.Assertion;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTest {
    @Autowired UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired EntityManager em;
    @Test
    public void join_duplication_test(){
        UserJoinDto userJoinDto = new UserJoinDto("id","fjfkle352","www@www.com","user","type","/ee");
        userService.join(userJoinDto);
        try {
            UserJoinDto userJoinDto2 = new UserJoinDto("id", "pw", "www@www.com", "user", "type", "/ee");
            userService.join(userJoinDto2);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void join_normal_test(){
        UserJoinDto userJoinDto = new UserJoinDto("id","fjfkle352","www@www.com","user","type","/ee");
        userService.join(userJoinDto);
        em.clear();
        Optional<User> byUserId = userRepository.findByUserId(userJoinDto.getLoginId());
        Assert.assertEquals(byUserId.get().getLoginId(),"id");
    }

    @Test
    public void login_error_test_id(){
        UserJoinDto userJoinDto = new UserJoinDto("id","fjfkle352","www@www.com","user","type","/ee");
        userService.join(userJoinDto);
        try {
            userService.login("id2","fjfkle352");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login_error_test_pw(){
        UserJoinDto userJoinDto = new UserJoinDto("id","fjfkle352","www@www.com","user","type","/ee");
        userService.join(userJoinDto);
        try {
            userService.login("id","fjfkle3");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void login_normal_test_(){
        UserJoinDto userJoinDto = new UserJoinDto("id","fjfkle352","www@www.com","user","type","/ee");
        userService.join(userJoinDto);
        try {
            List<String> login = userService.login("id", "fjfkle352");
            System.out.println(login);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void delete_error_test(){
        UserJoinDto userJoinDto = new UserJoinDto("id","fjfkle352","www@www.com","user","type","/ee");
        userService.join(userJoinDto);
        try {
            List<String> login = userService.login("id", "fjfkle352");
            Optional<User> id = userRepository.findByUserId("id");
            userRepository.delete(id.get());
            userService.deleteUser(login.get(0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void delete_normal_test(){
        UserJoinDto userJoinDto = new UserJoinDto("id","fjfkle352","www@www.com","user","type","/ee");
        userService.join(userJoinDto);
        try {
            List<String> login = userService.login("id", "fjfkle352");
            userService.deleteUser(login.get(0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void userProfile_error_test(){
        UserJoinDto userJoinDto = new UserJoinDto("id","fjfkle352","www@www.com","user","type","/ee");
        userService.join(userJoinDto);
        try {
            List<String> login = userService.login("id", "fjfkle352");
            userService.deleteUser(login.get(0));
            userService.findUserProfile(login.get(0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void userProfile_normal_test(){
        UserJoinDto userJoinDto = new UserJoinDto("id","fjfkle352","www@www.com","user","type","/ee");
        userService.join(userJoinDto);
        try {
            List<String> login = userService.login("id", "fjfkle352");
            System.out.println(userService.findUserProfile(login.get(0)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void changeProfile_error_test(){
        UserJoinDto userJoinDto = new UserJoinDto("id","fjfkle352","www@www.com","user","type","/ee");
        ChangeProFileDto changeProFileDto = new ChangeProFileDto("id2","fjfkle352","www@www.com","user");
        userService.join(userJoinDto);
        try {
            List<String> login = userService.login("id", "fjfkle352");
            userService.deleteUser(login.get(0));
            userService.ChangeUserProfile(login.get(0),changeProFileDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void changeProfile_normal_test(){
        UserJoinDto userJoinDto = new UserJoinDto("id","fjfkle352","www@www.com","user","type","/ee");
        ChangeProFileDto changeProFileDto = new ChangeProFileDto("id2","fjfkle352","www@www.com","user");
        userService.join(userJoinDto);
        try {
            List<String> login = userService.login("id", "fjfkle352");
            UserDto userDto = userService.ChangeUserProfile(login.get(0), changeProFileDto);
            System.out.println(userDto);
            em.flush();
            em.clear();
            User id2 = userRepository.findByUserId("id").get();
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}