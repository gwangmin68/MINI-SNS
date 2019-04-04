package kr.hs.dgsw.web190326.Service;

import kr.hs.dgsw.web190326.Domain.Comment;
import kr.hs.dgsw.web190326.Protocol.CommentUsernameProtocol;

import java.util.List;

public interface CommentService {
    List<CommentUsernameProtocol> listAllComments();
    CommentUsernameProtocol view(Long id);
    CommentUsernameProtocol add(Comment comment);
    Comment update(Long id, Comment comment);
    boolean delete(Long id);
}
