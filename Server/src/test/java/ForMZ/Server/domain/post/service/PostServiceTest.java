package ForMZ.Server.domain.post.service;

import ForMZ.Server.domain.category.entity.Category;
import ForMZ.Server.domain.category.entity.CategoryName;
import ForMZ.Server.domain.post.dto.AllPostsBySearch;
import ForMZ.Server.domain.post.entity.Post;
import ForMZ.Server.domain.post.exception.PostBySerachNotFoundException;
import ForMZ.Server.domain.post.repository.PostRepository;
import ForMZ.Server.domain.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;




@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private PostServiceImpl postService;

    @Test
    @DisplayName("검색 시 게시물 리스트 반환")
    void getPostsBySerach() {
        //given
        List<Post> posts = new ArrayList<>();
        IntStream.range(0, 5).forEach(
                i -> {
                    Post post = new Post(new User(), new Category(CategoryName.EMPLOYMENT), "title" + i, "content" + i, "");
                    posts.add(post);
                });
        given(postRepository.findPostsBySearchOrderByCreatedDate(anyString(), any(Pageable.class))).willReturn(posts);

        //when
        AllPostsBySearch postsBySearch = postService.getPostsBySearch("word", 10);

        //then
        assertEquals(5, postsBySearch.getPostList().size());
    }

    @Test
    @DisplayName("검색 시 게시물 리스트 반환 - 예외")
    void getPostsBySerach_EX() {
        //given
        List<Post> posts = new ArrayList<>();
        given(postRepository.findPostsBySearchOrderByCreatedDate(anyString(), any(Pageable.class))).willReturn(posts);

        //expected
        assertThrows(PostBySerachNotFoundException.class, () -> postService.getPostsBySearch("word", 10));
    }
}
