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
public class ProductImage {

    private int image_id;
    private int product_id;
    private String image_url;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean is_primary;

    public ProductImage() {
    }

    public ProductImage(int image_id, int product_id, String image_url, Timestamp createdAt, Timestamp updatedAt, boolean is_primary) {
        this.image_id = image_id;
        this.product_id = product_id;
        this.image_url = image_url;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.is_primary = is_primary;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
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

    public boolean isIs_primary() {
        return is_primary;
    }

    public void setIs_primary(boolean is_primary) {
        this.is_primary = is_primary;
    }

    @Override
    public String toString() {
        return "ProductImage{" + "image_id=" + image_id + ", product_id=" + product_id + ", image_url=" + image_url + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }

}
