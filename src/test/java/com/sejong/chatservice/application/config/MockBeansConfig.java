package com.sejong.chatservice.application.config;

import com.sejong.chatservice.application.comment.service.CommentService;
import com.sejong.chatservice.application.postlike.service.LikeService;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

public class MockBeansConfig {

    @Bean
    public CommentService commentService() {
        return mock(CommentService.class);
    }

    @Bean
    public LikeService likeService() {
        return mock(LikeService.class);
    }
}

