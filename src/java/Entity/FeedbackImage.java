package Entity;

import java.sql.Timestamp;

public class FeedbackImage {
    private int imageId;
    private int reviewId;
    private String imageUrl;
    private Timestamp createdAt;
    
    public FeedbackImage() {
    }

    public FeedbackImage(int imageId, int reviewId, String imageUrl, Timestamp createdAt) {
        this.imageId = imageId;
        this.reviewId = reviewId;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "FeedbackImage{" + "imageId=" + imageId + ", reviewId=" + reviewId + ", imageUrl=" + imageUrl + ", createdAt=" + createdAt + '}';
    }
}
