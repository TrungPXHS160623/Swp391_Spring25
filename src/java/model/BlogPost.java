/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author Asus
 */
public class BlogPost {
 
    private int id;
    private String title;
    private String brief_info;
    private String thumbnail;
    private String details;
    private Date updatedDate;
    private int PostCategories_id;
    private int User_id;
    private boolean flag_feature;
    private int status;
    private String blogs_postscol;
    private String full_name;
    private String name;

    public BlogPost() {
    }


       public BlogPost(int id, String title, String brief_info, String thumbnail, String details, Date updatedDate, int PostCategories_id, int User_id, boolean flag_feature, int status, String full_name ) {
        this.id = id;
        this.title = title;
        this.brief_info = brief_info;
        this.thumbnail = thumbnail;
        this.details = details;
        this.updatedDate = updatedDate;
        this.PostCategories_id = PostCategories_id;
        this.User_id = User_id;
        this.flag_feature = flag_feature;
        this.status = status;
        this.full_name = full_name;
    }

    public BlogPost( String title, String brief_info, String thumbnail, String details, Date updatedDate, int PostCategories_id, int User_id, boolean flag_feature, int status, String blogs_postscol) {   
        this.title = title;
        this.brief_info = brief_info;
        this.thumbnail = thumbnail;
        this.details = details;
        this.updatedDate = updatedDate;
        this.PostCategories_id = PostCategories_id;
        this.User_id = User_id;
        this.flag_feature = flag_feature;
        this.status = status;
        this.blogs_postscol = blogs_postscol;
   
    }

    public BlogPost(int id, String title, String brief_info, String thumbnail, String details, Date updatedDate, int PostCategories_id, int User_id, boolean flag_feature, int status, String full_name, String name) {
        this.id = id;
        this.title = title;
        this.brief_info = brief_info;
        this.thumbnail = thumbnail;
        this.details = details;
        this.updatedDate = updatedDate;
        this.PostCategories_id = PostCategories_id;
        this.User_id = User_id;
        this.flag_feature = flag_feature;
        this.status = status;
        this.full_name = full_name;
        this.name = name;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief_info() {
        return brief_info;
    }

    public void setBrief_info(String brief_info) {
        this.brief_info = brief_info;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getPostCategories_id() {
        return PostCategories_id;
    }

    public void setPostCategories_id(int PostCategories_id) {
        this.PostCategories_id = PostCategories_id;
    }

    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int User_id) {
        this.User_id = User_id;
    }

    public boolean isFlag_feature() {
        return flag_feature;
    }

    public void setFlag_feature(boolean flag_feature) {
        this.flag_feature = flag_feature;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBlogs_postscol() {
        return blogs_postscol;
    }

    public void setBlogs_postscol(String blogs_postscol) {
        this.blogs_postscol = blogs_postscol;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    @Override
    public String toString() {
        return "BlogPost{" + "id=" + id + ", title=" + title + ", brief_info=" + brief_info + ", thumbnail=" + thumbnail + ", details=" + details + ", updatedDate=" + updatedDate + ", PostCategories_id=" + PostCategories_id + ", User_id=" + User_id + ", flag_feature=" + flag_feature + ", status=" + status + ", blogs_postscol=" + blogs_postscol + ", full_name=" + full_name + '}';
    }

}
