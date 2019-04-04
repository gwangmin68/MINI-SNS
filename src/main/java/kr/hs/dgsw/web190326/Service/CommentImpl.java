package kr.hs.dgsw.web190326.Service;

import kr.hs.dgsw.web190326.Domain.Comment;
import kr.hs.dgsw.web190326.Domain.User;
import kr.hs.dgsw.web190326.Protocol.CommentUsernameProtocol;
import kr.hs.dgsw.web190326.Repository.CommentRepository;
import kr.hs.dgsw.web190326.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void init(){
        User u = this.userRepository.save(new User("aaa", "aaa@dgsw"));
        this.commentRepository.save(new Comment(u.getId(),"hi there 111"));
        this.commentRepository.save(new Comment(u.getId(),"hi there 222"));
        this.commentRepository.save(new Comment(u.getId(),"hi there 333"));
    }

    @Override
    public List<CommentUsernameProtocol> listAllComments() {
        List<Comment> commentList = this.commentRepository.findAll();
        List<CommentUsernameProtocol> cupList = new ArrayList<>();
        commentList.forEach(comment -> {
            Optional<User> found = this.userRepository.findById(comment.getUserId());
            String username = found.isPresent() ? found.get().getUsername() : null;
            cupList.add(new CommentUsernameProtocol(comment, username));
        });
        return cupList;
    }

    @Override
    public CommentUsernameProtocol view(Long id) {
        Comment comment = this.commentRepository.findById(id).get();
        return new CommentUsernameProtocol(comment, this.userRepository.findById(comment.getUserId()).get().getUsername());
    }

    @Override
    public CommentUsernameProtocol add(Comment comment) {
        Optional<User> found = this.userRepository.findById(comment.getUserId());
        if(!found.isPresent()) return null;
        return new CommentUsernameProtocol(this.commentRepository.save(comment), this.userRepository.findById(comment.getUserId())
                .map(found2 -> found2.getUsername())
                .orElse(null));
    }

    @Override
    public Comment update(Long id, Comment comment) {
        return this.commentRepository.findById(id)
                .map(found -> {
                    found.setContent(Optional.ofNullable(comment.getContent()).orElse(found.getContent()));
                    return this.commentRepository.save(found);
                })
                .orElse(null);
    }

    @Override
    public boolean delete(Long id) {
        try{
            this.commentRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
