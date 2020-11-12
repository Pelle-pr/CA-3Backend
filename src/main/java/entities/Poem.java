package entities;

import dto.PoemDTO;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
public class Poem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String title;

    @Lob
    @Column(length = 10000)
    private String content;

    @ManyToMany(mappedBy = "poemList")
    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Poem(String content, String title) {
        this.content = content;
        this.title = title;
    }

    public Poem() {
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}