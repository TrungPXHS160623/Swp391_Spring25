/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto;

import Entity.Post;
import Entity.PostMedia;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class PostDto {

    private int postId;
    private String title;
    private String content;    // Dành cho detail view
    private String summary;    // Dành cho list view
    private String dayUpdate;  // Ngày cập nhật dạng chuỗi (ví dụ: dd/MM/yyyy)
    private String author;     // Tên tác giả (dành cho detail view)
    private String mediaUrl;   // Đường dẫn ảnh chính (is_primary)
    private String description;// Mô tả của ảnh (dành cho detail view)
    private String category;   // Tên danh mục
    // Nếu cần thêm danh sách media (nhiều hơn 1 ảnh), có thể thêm thuộc tính:
    private List<PostMedia> mediaList;

    public PostDto() {
    }

    public PostDto(int postId, String title, String content, String summary, String dayUpdate, String author, String mediaUrl, String description, String category, List<PostMedia> mediaList) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.dayUpdate = dayUpdate;
        this.author = author;
        this.mediaUrl = mediaUrl;
        this.description = description;
        this.category = category;
        this.mediaList = mediaList;
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

    public String getDayUpdate() {
        return dayUpdate;
    }

    public void setDayUpdate(String dayUpdate) {
        this.dayUpdate = dayUpdate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<PostMedia> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<PostMedia> mediaList) {
        this.mediaList = mediaList;
    }

    @Override
    public String toString() {
        return "PostDto{"
                + "postId=" + postId
                + ", title='" + title + '\''
                + ", content='" + content + '\''
                + ", summary='" + summary + '\''
                + ", dayUpdate='" + dayUpdate + '\''
                + ", author='" + author + '\''
                + ", mediaUrl='" + mediaUrl + '\''
                + ", description='" + description + '\''
                + ", category='" + category + '\''
                + ", mediaList=" + mediaList
                + '}';
    }
}
