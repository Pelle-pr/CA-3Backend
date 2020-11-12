package dto;

import entities.Poem;

public class PoemDTO {

    private String content;
    private String title;


    public PoemDTO(String content, String title) {
        this.content = content;
        this.title = title;
    }

    public PoemDTO(Poem poem) {
        this.content = poem.getContent();
        this.title = poem.getTitle();
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
