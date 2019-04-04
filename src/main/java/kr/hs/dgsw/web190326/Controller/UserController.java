package kr.hs.dgsw.web190326.Controller;

import kr.hs.dgsw.web190326.Domain.User;
import kr.hs.dgsw.web190326.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/useradd")
    public User addUser(@RequestBody User user){
        return this.userService.addUser(user);
    }

    @GetMapping("/userlist")
    public List<User> list(){
        return this.userService.list();
    }

    @GetMapping("/userview/{id}")
    public User viewUserById(@PathVariable Long id){
        return this.userService.view(id);
    }

    @PutMapping("/userupdate/{id}")
    public User updateUserById(@PathVariable Long id, @RequestBody User user){
        return this.userService.update(id, user);
    }

    @DeleteMapping("/userdelete/{id}")
    public boolean deleteUserById(@PathVariable Long id){
        return this.userService.delete(id);
    }
}
