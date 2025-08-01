package com.sejong.chatservice.application.reply.controller;

import com.sejong.chatservice.application.pagination.CursorPageReqDto;
import com.sejong.chatservice.core.common.pagination.CursorPageRequest;
import com.sejong.chatservice.core.reply.command.ReplyCreateCommand;
import com.sejong.chatservice.application.reply.dto.request.ReplyCommentRequest;
import com.sejong.chatservice.application.reply.dto.response.ReplyCommentResponse;
import com.sejong.chatservice.application.reply.service.ReplyService;
import com.sejong.chatservice.core.common.pagination.CursorPageResponse;
import com.sejong.chatservice.core.reply.domain.Reply;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/{commentParentId}")
    public ResponseEntity<ReplyCommentResponse> createReplyComment(
            @RequestHeader("x-user") String userId,
            @PathVariable(name="commentParentId") Long commentParentId,
            @Valid @RequestBody ReplyCommentRequest replyCommentRequest
    ){
        ReplyCreateCommand command = ReplyCreateCommand.of(replyCommentRequest.getComment(), Long.valueOf(userId), commentParentId);
        ReplyCommentResponse response = replyService.createReplyComment(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{commentParentId}")
    public ResponseEntity<CursorPageResponse<List<Reply>>> showReplyComments(
            @PathVariable(name="commentParentId") Long commentParentId,
            @ParameterObject @Valid CursorPageReqDto cursorPageReqDto
            ){
        CursorPageRequest pageRequest = cursorPageReqDto.toPageRequest();
        CursorPageResponse<List<Reply>> response = replyService.getAllReplyComments(commentParentId, pageRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/{replyId}")
    public ResponseEntity<ReplyCommentResponse> updateReplyComment(
            @RequestHeader("x-user") String userId,
            @PathVariable(name="replyId") Long replyId,
            @Valid @RequestBody ReplyCommentRequest replyCommentRequest
    ){
        ReplyCommentResponse response = replyService.updateReplyComment(userId, replyId, replyCommentRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<ReplyCommentResponse> deleteReplyComment(
            @RequestHeader("x-user") String userId,
            @PathVariable(name="replyId") Long replyId
    ){
        ReplyCommentResponse response = replyService.deleteReplyComment(userId, replyId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
