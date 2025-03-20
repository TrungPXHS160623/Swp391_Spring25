/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class Product {

    private int product_id;
    private String product_name;
    private String subcategory_name; // Chuyển từ subcategory_id sang
    private Date created_at;
    private Date updated_at;
    private int status;
    private float list_price;
    private float sale_price;
    private int featured;
    private int stock;
    private ProductImage coverProduct;
    private List<ProductImage> imageList;

    public Product() {
    }

    public Product(int product_id, String image_url, String product_name, String subcategory_name, Date created_at, Date updated_at, int status, float list_price, float sale_price, int featured, int stock, ProductImage coverProduct) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.subcategory_name = subcategory_name;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status;
        this.list_price = list_price;
        this.sale_price = sale_price;
        this.featured = featured;
        this.stock = stock;
        this.coverProduct = coverProduct;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSubcategory_name() {
        return subcategory_name;
    }

    public void setSubcategory_name(String subcategory_name) {
        this.subcategory_name = subcategory_name;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getList_price() {
        return list_price;
    }

    public void setList_price(float list_price) {
        this.list_price = list_price;
    }

    public float getSale_price() {
        return sale_price;
    }

    public void setSale_price(float sale_price) {
        this.sale_price = sale_price;
    }

    public int getFeatured() {
        return featured;
    }

    public void setFeatured(int featured) {
        this.featured = featured;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<ProductImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<ProductImage> imageList) {
        this.imageList = imageList;
    }

    public ProductImage getCoverProduct() {
        return coverProduct;
    }

    public void setCoverProduct(ProductImage coverProduct) {
        this.coverProduct = coverProduct;
    }
}
