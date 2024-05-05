package ForMZ.Server.domain.comment.controller;

import ForMZ.Server.domain.comment.dto.*;
import ForMZ.Server.domain.comment.service.CommentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import static ForMZ.Server.domain.comment.constant.CommentConstant.CResponseMessage.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc
public class CommentControllerTest {
    @MockBean
    private CommentServiceImpl commentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("댓글 작성")
    void createCmt() throws Exception {
        //given
        CommentReq commentReq = new CommentReq();

        doNothing().when(commentService).createComment(any(CommentReq.class));

        //when
        mockMvc.perform(post("/comments")
                        .content(objectMapper.writeValueAsString(commentReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(CREATED.value()))
                .andExpect(jsonPath("$.message").value(CREATE_SUCCESS.getMessage()))
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 조회")
    void getCmt() throws Exception {
        //given
        List<CommentRes> cmts = new ArrayList<>();
        CommentRes res = new CommentRes();
        res.setComment("content");
        cmts.add(res);
        AllCommentRes allCommentRes = new AllCommentRes(cmts);

        given(commentService.getComment(anyLong())).willReturn(allCommentRes);

        //when
        mockMvc.perform(get("/comments/all/{postId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(OK.value()))
                .andExpect(jsonPath("$.data.comments.[0].comment").value("content"))
                .andExpect(jsonPath("$.message").value(GET_SUCCESS.getMessage()))
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 수정")
    void updateCmt() throws Exception {
        //given
        CommentUpdateRes updateRes = new CommentUpdateRes("update content");
        CommentUpdateReq updateReq = new CommentUpdateReq();
        updateReq.setContent("update content");

        given(commentService.updateComment(anyLong(), any(CommentUpdateReq.class))).willReturn(updateRes);

        //when
        mockMvc.perform(put("/commments/{commentId}", 1L)
                        .content(objectMapper.writeValueAsString(updateReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(OK.value()))
                .andExpect(jsonPath("$.data.content").value("update content"))
                .andExpect(jsonPath("$.message").value(UPDATE_SUCCESS.getMessage()))
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteCmt() throws Exception {
        //given
        doNothing().when(commentService).deleteComment(anyLong());

        //when
        mockMvc.perform(delete("/comments/delete/{commentId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("statusCode").value(OK.value()))
                .andExpect(jsonPath("message").value(DELETE_SUCCESS.getMessage()))
                .andDo(print());
    }
}
