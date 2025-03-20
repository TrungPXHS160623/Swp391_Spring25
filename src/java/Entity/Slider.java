/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;

/**
 *
 * @author Acer
 */
public class Slider {
    private int id;
    private String imageUrl;
    private String title;
    private String backLink;
    private int status;
    private String notes;
    private Date createdDate;
    private Date updatedDate;
    private Integer lastUpdatedBy;

    public Slider() {
    }

    public Slider(int id, String imageUrl, String title, String backLink, int status, String notes, Date createdDate, Date updatedDate, Integer lastUpdatedBy) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.backLink = backLink;
        this.status = status;
        this.notes = notes;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
    //for insert
    public Slider(String imageUrl, String title, String backLink, int status, String notes, Integer lastUpdatedBy) {
    this.imageUrl = imageUrl;
    this.title = title;
    this.backLink = backLink;
    this.status = status;
    this.notes = notes;
    this.lastUpdatedBy = lastUpdatedBy;
}
    //for update
    public Slider(int id, String imageUrl, String title, String backLink, int status, String notes, Integer lastUpdatedBy) {
    this.id = id;
    this.imageUrl = imageUrl;
    this.title = title;
    this.backLink = backLink;
    this.status = status;
    this.notes = notes;
    this.lastUpdatedBy = lastUpdatedBy;
}
    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getBackLink() {
        return backLink;
    }

    public int getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackLink(String backLink) {
        this.backLink = backLink;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Override
    public String toString() {
        return "Slider{" + "id=" + id + ", imageUrl=" + imageUrl + ", title=" + title + ", backLink=" + backLink + ", status=" + status + ", notes=" + notes + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", lastUpdatedBy=" + lastUpdatedBy + '}';
    }
    
    
}
