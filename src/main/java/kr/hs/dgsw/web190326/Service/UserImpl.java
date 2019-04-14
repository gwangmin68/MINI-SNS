package kr.hs.dgsw.web190326.Service;

import kr.hs.dgsw.web190326.Domain.Comment;
import kr.hs.dgsw.web190326.Domain.User;
import kr.hs.dgsw.web190326.Repository.CommentRepository;
import kr.hs.dgsw.web190326.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public User addUser(User user) {
        Optional<User> found = this.userRepository.findByEmail(user.getEmail());
        if(found.isPresent()) return null;
        return this.userRepository.save(user);
    }

    @Override
    public User view(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> list() {
        return this.userRepository.findAll();
    }

    @Override
    public User update(Long id, User user) {
        return this.userRepository.findById(id)
                .map(found -> {
                    found.setUsername(Optional.ofNullable(user.getUsername()).orElse(found.getUsername()));
                    found.setPassword(Optional.ofNullable(user.getPassword()).orElse(found.getPassword()));
                    found.setEmail(Optional.ofNullable(user.getEmail()).orElse(found.getEmail()));
                    found.setImgPath(Optional.ofNullable(user.getImgPath()).orElse(found.getImgPath()));
                    found.setImgName(Optional.ofNullable(user.getImgName()).orElse(found.getImgName()));
                    return this.userRepository.save(found);
                })
                .orElse(null);
    }

    @Override
    public boolean delete(Long id) {
        try{
            this.userRepository.deleteById(id);
            List<Comment> comments = this.commentRepository.findAll();
            comments.forEach(item -> {
                if(item.getUserId().equals(id))
                    commentRepository.deleteById(item.getId());
            });
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
