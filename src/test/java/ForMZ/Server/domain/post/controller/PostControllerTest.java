package ForMZ.Server.domain.post.controller;

import ForMZ.Server.domain.post.dto.AllPostsBySearch;
import ForMZ.Server.domain.post.dto.PostListRes;
import ForMZ.Server.domain.post.service.PostServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static ForMZ.Server.domain.post.constant.PostConstant.PostResponseMessage.GET_POST_BY_SEARCH_SUCCESS;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
public class PostControllerTest {

    @MockBean
    private PostServiceImpl postService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("검색 시 게시물 리스트 반환")
    void getPostsBySearch() throws Exception {
        //given
        List<PostListRes> posts = new ArrayList<>();
        PostListRes postListRes = PostListRes.builder()
                .title("title")
                .build();
        posts.add(postListRes);
        AllPostsBySearch postsBySearch = new AllPostsBySearch(posts);
        given(postService.getPostsBySearch(anyString(), anyInt())).willReturn(postsBySearch);

        //when
        mockMvc.perform(get("/community/posts/search")
                        .param("word", "word")
                        .param("page", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(OK.value()))
                .andExpect(jsonPath("$.data.postList.[0].title").value("title"))
                .andExpect(jsonPath("$.message").value(GET_POST_BY_SEARCH_SUCCESS.getMessage()))
                .andDo(print());
        }
}
