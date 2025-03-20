package model;

import java.util.Date;

public class Post {

    private int post_id;
    private int user_id;
    private String title;
    private String category;
    private String thumbnail;
    private String brief_info;
    private String description;
    private Boolean is_featured;
    private String status;
    private Date created_at;
    private Date updated_at;

    public Post() {
    }

    public Post(int post_id, int user_id, String title, String category, String thumbnail,
                String brief_info, String description, Boolean is_featured, String status,
                Date created_at, Date updated_at) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.title = title;
        this.category = category;
        this.thumbnail = thumbnail;
        this.brief_info = brief_info;
        this.description = description;
        this.is_featured = is_featured;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getBrief_info() {
        return brief_info;
    }

    public void setBrief_info(String brief_info) {
        this.brief_info = brief_info;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIs_featured() {
        return is_featured;
    }

    public void setIs_featured(Boolean is_featured) {
        this.is_featured = is_featured;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Post{" +
                "post_id=" + post_id +
                ", user_id=" + user_id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", brief_info='" + brief_info + '\'' +
                ", description='" + description + '\'' +
                ", is_featured=" + is_featured +
                ", status='" + status + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
