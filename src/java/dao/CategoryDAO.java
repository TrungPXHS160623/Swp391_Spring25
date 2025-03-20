/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import model.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import utils.DatabaseUtil;

/**
 *
 * @author KieuVietPhuoc
 */
public class CategoryDAO extends DatabaseUtil{
    private final Connection connection; // Kết nối cơ sở dữ liệu

    public CategoryDAO() {
        DatabaseUtil dbContext = new DatabaseUtil();
        this.connection = dbContext.getConnection(); // Lấy kết nối từ DBContext
    }
    public Vector<Category> getAllCategories() {
        Vector<Category> listC = new Vector<>();
        String query = "SELECT * FROM Categories";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listC.add(new Category(rs.getString("category_id"), rs.getString("category_name"), rs.getString("description"),rs.getString("created_at"),rs.getString("updated_at")));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listC;
    }
    public static void main(String[] args) {
        CategoryDAO abc = new CategoryDAO();
        System.out.println(abc.getAllCategories());
    }
    
}
