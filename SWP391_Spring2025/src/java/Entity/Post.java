/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;

/**
 *
 * @author LENOVO
 */
public class Post {

    private int postId;
    private String title;
    private String content;
    private String summary;
    private Date createdAt;
    private Date updateAt;
    private String user_name;
    private String category_name;
    private PostMedia coverMedia;

    public Post() {
    }

    public Post(int postId, String title, String content, String summary, Date createdAt, Date updateAt, String user_name, String category_name, PostMedia coverMedia) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.user_name = user_name;
        this.category_name = category_name;
        this.coverMedia = coverMedia;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public PostMedia getCoverMedia() {
        return coverMedia;
    }

    public void setCoverMedia(PostMedia coverMedia) {
        this.coverMedia = coverMedia;
    }

}
