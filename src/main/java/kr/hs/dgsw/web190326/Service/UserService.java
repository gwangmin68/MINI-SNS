package kr.hs.dgsw.web190326.Service;

import kr.hs.dgsw.web190326.Domain.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    User view(Long id);
    List<User> list();
    User update(Long id, User user);
    boolean delete(Long id);
}
