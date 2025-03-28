/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Timestamp;

/**
 *
 * @author LENOVO
 */
public class Post {

    private int postId;
    private String title;
    private String content;
    private String summary;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int userId;
    private int categoryId;

    public Post() {
    }

    public Post(int postId, String title, String content, String summary, Timestamp createdAt, Timestamp updatedAt, int userId, int categoryId) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.categoryId = categoryId;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Post{"
                + "postId=" + postId
                + ", title='" + title + '\''
                + ", content='" + content + '\''
                + ", summary='" + summary + '\''
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + ", userId=" + userId
                + ", categoryId=" + categoryId
                + '}';
    }
}
