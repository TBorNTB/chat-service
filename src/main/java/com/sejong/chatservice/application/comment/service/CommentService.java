package com.sejong.chatservice.application.comment.service;

import com.sejong.chatservice.core.comment.command.CommentCommand;
import com.sejong.chatservice.core.comment.command.ShowCursorCommentCommand;
import com.sejong.chatservice.application.comment.dto.request.CommentRequest;
import com.sejong.chatservice.application.comment.dto.response.CommentResponse;
import com.sejong.chatservice.core.comment.domain.Comment;
import com.sejong.chatservice.core.comment.repository.CommentRepository;
import com.sejong.chatservice.core.common.pagination.Cursor;
import com.sejong.chatservice.core.common.pagination.CursorPageRequest;
import com.sejong.chatservice.core.common.pagination.CursorPageResponse;
import com.sejong.chatservice.core.common.pagination.PageSearchCommand;
import com.sejong.chatservice.core.enums.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponse createComment(CommentCommand command) {
        //todo userId를 통해 실제로 해당 유저가 있는지 조회하는 코드 작성
        //todo postId 랑 해당하는 타입 기준으로 있는지 조회하는 코드 작성

        Comment comment = Comment.of(command, LocalDateTime.now());
        Comment commentResponse = commentRepository.save(comment);
        return CommentResponse.from(commentResponse);

    }

    @Transactional(readOnly = true)
    public CursorPageResponse<List<Comment>> getComments(CursorPageRequest cursorPageRequest, Long postId, PostType postType) {

        List<Comment> comments = commentRepository.findAllComments(
                postId,
                postType,
                cursorPageRequest);

        return CursorPageResponse.from(comments,cursorPageRequest.getSize(), comment -> Cursor.of(comment.getId()) );

    }

    @Transactional
    public CommentResponse updateComment(String userId, Long commentId,  CommentRequest request ) {
        //todo userId를 통해 실제로 해당 유저가 있는지 조회하는 코드 작성 만약 일치하지 않으면 오류 반환
        Comment comment = commentRepository.findByCommentId(commentId);
        comment.validateUserId(Long.valueOf(userId));
        Comment updatedComment = comment.updateComment(request.getContent(), LocalDateTime.now());
        Comment commentResponse = commentRepository.updateComment(updatedComment);
        return CommentResponse.updateFrom(commentResponse);
    }

    @Transactional
    public CommentResponse deleteComment(String userId, Long commentId) {
        //todo userId를 통해 실제로 해당 유저가 있는지 조회하는 코드 작성 만약 일치하지 않으면 오류반환
        Comment comment = commentRepository.findByCommentId(commentId);
        comment.validateUserId(Long.valueOf(userId));

        Long deletedId = commentRepository.deleteComment(commentId);
        return CommentResponse.deleteFrom(deletedId);
    }
}
