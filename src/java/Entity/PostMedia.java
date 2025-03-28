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
public class PostMedia {

    private int mediaId;
    private int postId;
    private String mediaUrl;
    private String mediaType;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String description;
    private boolean isPrimary;

    public PostMedia() {
    }

    public PostMedia(int mediaId, int postId, String mediaUrl, String mediaType, Timestamp createdAt, Timestamp updatedAt, String description, boolean isPrimary) {
        this.mediaId = mediaId;
        this.postId = postId;
        this.mediaUrl = mediaUrl;
        this.mediaType = mediaType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.description = description;
        this.isPrimary = isPrimary;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    @Override
    public String toString() {
        return "PostMedia{"
                + "mediaId=" + mediaId
                + ", postId=" + postId
                + ", mediaUrl='" + mediaUrl + '\''
                + ", mediaType='" + mediaType + '\''
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + ", description='" + description + '\''
                + ", isPrimary=" + isPrimary
                + '}';
    }
}
