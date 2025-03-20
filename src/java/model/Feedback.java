package model;

import java.sql.Timestamp;

public class Feedback {
    private int feedback_id;
    private String user_name;
    private String email;
    private String phone_number;
    private String product_name;
    private int rating;
    private String feedback_content;
    private String media_url;
    private String status;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Feedback() {
    }

    public Feedback(int feedback_id, String user_name, String email, String phone_number, String product_name, int rating, 
                    String feedback_content, String media_url, String status, Timestamp created_at, Timestamp updated_at) {
        this.feedback_id = feedback_id;
        this.user_name = user_name;
        this.email = email;
        this.phone_number = phone_number;
        this.product_name = product_name;
        this.rating = rating;
        this.feedback_content = feedback_content;
        this.media_url = media_url;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(int feedback_id) {
        this.feedback_id = feedback_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFeedback_content() {
        return feedback_content;
    }

    public void setFeedback_content(String feedback_content) {
        this.feedback_content = feedback_content;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Feedback{" + "feedback_id=" + feedback_id + ", user_name=" + user_name + ", email=" + email + ", phone_number=" + phone_number + ", product_name=" + product_name + ", rating=" + rating + ", feedback_content=" + feedback_content + ", media_url=" + media_url + ", status=" + status + ", created_at=" + created_at + ", updated_at=" + updated_at + '}';
    }
    
    
}
