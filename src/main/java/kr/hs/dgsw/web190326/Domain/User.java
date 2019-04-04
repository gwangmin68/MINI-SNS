package kr.hs.dgsw.web190326.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue//자동 증가값
    private Long id;

    private String username;
    private String email;
    private String imgPath = null;
    private String imgName = null;

    @CreationTimestamp//가입일
    private LocalDateTime joined;
    @UpdateTimestamp//수정일
    private LocalDateTime modified;

    public User(String username, String email){
        this.username = username;
        this.email = email;
    }

    public User(String username, String email, String imgPath, String imgName){
        this.username = username;
        this.email = email;
        this.imgPath = imgPath;
        this.imgName = imgName;
    }
}
