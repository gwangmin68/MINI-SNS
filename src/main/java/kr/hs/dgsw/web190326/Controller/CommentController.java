package kr.hs.dgsw.web190326.Controller;

import kr.hs.dgsw.web190326.Domain.Comment;
import kr.hs.dgsw.web190326.Protocol.CommentUsernameProtocol;
import kr.hs.dgsw.web190326.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentlist")
    public List<CommentUsernameProtocol> listComments(){
        return this.commentService.listAllComments();
    }

    @GetMapping("/commentview/{id}")
    public CommentUsernameProtocol viewCommentById(@PathVariable Long id){
        return this.commentService.view(id);
    }

    @PostMapping("/commentadd")
    public CommentUsernameProtocol addComment(@RequestBody Comment comment){
        return this.commentService.add(comment);
    }

    @PutMapping("/commentupdate/{id}")
    public Comment update(@PathVariable Long id, @RequestBody Comment comment){
        return this.commentService.update(id, comment);
    }

    @DeleteMapping("/commentdelete/{id}")
    public boolean delete(@PathVariable Long id){
        return this.commentService.delete(id);
    }
}