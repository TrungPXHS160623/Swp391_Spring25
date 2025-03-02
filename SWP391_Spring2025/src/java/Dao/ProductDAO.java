/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Context.DBContext;
import Entity.Product;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class ProductDAO {

    private static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());

    // Liệt kê tất cả các sản phẩm
    public List<Product> getAllProduct() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.[product_id], p.[image_url], p.[product_name], s.[subcategory_name], "
                + "p.[list_price], p.[sale_price], p.[status], p.[featured] "
                + "FROM [Products] p JOIN [SubCategories] s ON p.subcategory_id = s.subcategory_id";
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách sản phẩm", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách sản phẩm", e);
        }
        return products;
    }

    // Tìm kiếm theo tên sản phẩm
    public List<Product> searchProduct(String keyword) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.[product_id], p.[image_url], p.[product_name], s.[subcategory_name], "
                + "p.[list_price], p.[sale_price], p.[status], p.[featured] "
                + "FROM [Products] p JOIN [SubCategories] s ON p.subcategory_id = s.subcategory_id WHERE product_name LIKE ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi tìm kiếm sản phẩm", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi tìm kiếm sản phẩm", e);
        }
        return products;
    }

    // chức năng Phân trang sản phẩm
    public List<Product> getAllProduct2(int page, int limit) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.[product_id], p.[image_url], p.[product_name], s.[subcategory_name], "
                + "p.[list_price], p.[sale_price], p.[status], p.[featured] "
                + "FROM [Products] p JOIN [SubCategories] s ON p.subcategory_id = s.subcategory_id ORDER BY product_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, (page - 1) * limit);
            stmt.setInt(2, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi phân trang danh mục", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL khi Phân trang danh mục", e);
        }
        return products;
    }

    // Chuyển đổi từ ResultSet thành Product Object
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProduct_id(rs.getInt("product_id"));
        product.setImage_url(rs.getString("image_url"));
        product.setProduct_name(rs.getString("product_name"));
        product.setSubcategory_name(rs.getString("subcategory_name"));
        product.setStatus(rs.getInt("status"));
        product.setList_price(rs.getFloat("list_price"));
        product.setSale_price(rs.getFloat("sale_price"));
        product.setFeatured(rs.getInt("featured"));
        return product;
    }

    // Chức năng sắp xếp theo tên sản phẩm
    public List<Product> getAllProductSortedByName(boolean name_ascending) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.[product_id],p.[image_url],p.[product_name],s.[subcategory_name],"
                + " p.[status],p.[list_price], p.[sale_price],p.[featured] "
                + "FROM [Products] p JOIN [SubCategories] s ON p.[subcategory_id] = s.[subcategory_id]"
                + "ORDER BY p.product_name " + (name_ascending ? "ASC" : "DESC");

        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách sản phẩm sắp xếp", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách sản phẩm sắp xếp", e);
        }
        return products;
    }

    // Chức năng sắp xếp theo danh mục
    public List<Product> getAllProductSortedByCategory(boolean category_ascending) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.[product_id],p.[image_url],p.[product_name],s.[subcategory_name],"
                + " p.[status],p.[list_price],p.[sale_price],p.[featured] "
                + "FROM [Products] p JOIN [SubCategories] s ON p.[subcategory_id] = s.[subcategory_id]"
                + "ORDER BY s.subcategory_name " + (category_ascending ? "ASC" : "DESC");

        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách sản phẩm sắp xếp", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách sản phẩm sắp xếp", e);
        }
        return products;
    }

    // Chức năng sắp xếp theo giá niêm yết
    public List<Product> getAllProductSortedByListPrice(boolean listprice_ascending) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.[product_id],p.[image_url],p.[product_name],s.[subcategory_name],"
                + " p.[status],p.[list_price],p.[sale_price],p.[featured] "
                + "FROM [Products] p JOIN [SubCategories] s ON p.[subcategory_id] = s.[subcategory_id]"
                + "ORDER BY p.list_price " + (listprice_ascending ? "ASC" : "DESC");

        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách sản phẩm sắp xếp", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách sản phẩm sắp xếp", e);
        }
        return products;
    }

    // Chức năng sắp xếp theo giá bán
    public List<Product> getAllProductSortedBySalePrice(boolean saleprice_ascending) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.[product_id],p.[image_url],p.[product_name],s.[subcategory_name],"
                + " p.[status],p.[list_price],p.[sale_price],p.[featured] "
                + "FROM [Products] p JOIN [SubCategories] s ON p.[subcategory_id] = s.[subcategory_id]"
                + "ORDER BY p.sale_price " + (saleprice_ascending ? "ASC" : "DESC");

        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách sản phẩm sắp xếp", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách sản phẩm sắp xếp", e);
        }
        return products;
    }

    // Chức năng sắp xếp theo nổi bật
    public List<Product> getAllProductSortedByFeatured(boolean featured_ascending) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.[product_id],p.[image_url],p.[product_name],s.[subcategory_name],"
                + " p.[status],p.[list_price], p.[sale_price],p.[featured] "
                + "FROM [Products] p JOIN [SubCategories] s ON p.[subcategory_id] = s.[subcategory_id]"
                + "ORDER BY p.featured " + (featured_ascending ? "ASC" : "DESC");

        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách sản phẩm sắp xếp", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách sản phẩm sắp xếp", e);
        }
        return products;
    }

    // Chức năng sắp xếp theo trạng thái
    public List<Product> getAllProductSortedByStatus(boolean status_ascending) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.[product_id],p.[image_url],p.[product_name],s.[subcategory_name],"
                + " p.[status],p.[list_price], p.[sale_price],p.[featured] "
                + "FROM [Products] p JOIN [SubCategories] s ON p.[subcategory_id] = s.[subcategory_id]"
                + "ORDER BY p.status " + (status_ascending ? "ASC" : "DESC");

        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách sản phẩm sắp xếp", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách sản phẩm sắp xếp", e);
        }
        return products;
    }

    // Phương thức gọi tất cả danh mục
    public List<String> getAllCategories() {
        List<String> products = new ArrayList<>();
        String sql = "SELECT DISTINCT category_name FROM Categories"; // Truy vấn lấy các category từ bảng Categories
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(rs.getString("category_name"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách category", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách category", e);
        }
        return products;
    }

    // Phương thức gọi tất cả nổi bật
    public List<String> getAllFeatured() {
        List<String> products = new ArrayList<>();
        String sql = "SELECT DISTINCT featured FROM Products";
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(rs.getString("featured"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách Featured", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách Featured", e);
        }
        return products;
    }

    // Phương thức gọi tất cả trạng thái
    public List<String> getAllStatus() {
        List<String> products = new ArrayList<>();
        String sql = "SELECT DISTINCT status FROM Products";
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(rs.getString("status"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách Status", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách Status", e);
        }
        return products;
    }

    // Phương thức trong ProductDAO để lọc sản phẩm theo category
    public List<Product> getFilteredProducts(String categoryName) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.product_id, p.image_url, p.product_name, s.subcategory_name, "
                + "p.list_price, p.sale_price, p.status, p.featured "
                + "FROM Products p "
                + "JOIN SubCategories s ON p.subcategory_id = s.subcategory_id "
                + "WHERE s.subcategory_name LIKE ?";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set parameters: category and search keyword
            stmt.setString(1, "%" + categoryName + "%"); // Lọc theo tên danh mục

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lọc sản phẩm theo category và từ khóa", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lọc sản phẩm", e);
        }
        return products;
    }

    // Phương thức trong ProductDAO để lọc sản phẩm theo nội dung
    public List<Product> getFilteredProductsbyFeatured(String featured) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.product_id, p.image_url, p.product_name, s.subcategory_name, "
                + "p.list_price, p.sale_price, p.status, p.featured "
                + "FROM Products p "
                + "JOIN SubCategories s ON p.subcategory_id = s.subcategory_id "
                + "WHERE p.featured LIKE ?";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set parameters: featured and search keyword
            stmt.setString(1, "%" + featured + "%"); // Lọc theo tên nổi bật

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lọc sản phẩm theo featured và từ khóa", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lọc sản phẩm", e);
        }
        return products;
    }

    // Phương thức trong ProductDAO để lọc sản phẩm theo trạng thái
    public List<Product> getFilteredProductsbyStatus(String status) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.product_id, p.image_url, p.product_name, s.subcategory_name, "
                + "p.list_price, p.sale_price, p.status, p.featured "
                + "FROM Products p "
                + "JOIN SubCategories s ON p.subcategory_id = s.subcategory_id "
                + "WHERE p.status LIKE ?";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set parameters: status and search keyword
            stmt.setString(1, "%" + status + "%"); // Lọc theo tên trạng thái

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lọc sản phẩm theo status và từ khóa", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lọc sản phẩm", e);
        }
        return products;
    }

    public static void main(String[] args) {
        ProductDAO pDAO = new ProductDAO();

        // Tính tổng sổ sản phẩm hiện có
        List<Product> products = pDAO.getAllProduct();
        System.out.println("Tổng số danh mục: " + products.size());

        // In ra toàn bộ thông tin cần thiết của 1 sản phẩm
        for (Product product : products) {
            System.out.println("---------------------------------------------------");
            System.out.println("ID sản phẩm: " + product.getProduct_id());
            System.out.println("Tên sản phẩm: " + product.getProduct_name());
            System.out.println("URL ảnh: " + product.getImage_url());
            System.out.println("ID phân loại con: " + product.getSubcategory_name());
            System.out.println("Trạng thái: " + product.getStatus());
            System.out.println("Giá niêm yết: " + product.getList_price());
            System.out.println("Giá bán: " + product.getSale_price());
            System.out.println("Sản phẩm nổi bật: " + product.getFeatured());
        }

        // Test phân trang sản phẩm (Kiểm tra tối đa 5)
        List<Product> paginatedCategories = pDAO.getAllProduct2(1, 5);
        System.out.println("////////////////////////////////////////////////////////");
        System.out.println("Danh mục trang 1 (tối đa 5): " + paginatedCategories.size());

        // Test tìm kiểm 1 sản phẩm theo tên
        List<Product> searchResults = pDAO.searchProduct("1");
        System.out.println("------------------------------------");
        System.out.println("Tìm kiếm 'Test': " + searchResults.size() + " kết quả");

        // Kiểm tra sắp xếp tăng dần theo tên sản phẩm
        List<Product> sortedAscProducts = pDAO.getAllProductSortedByName(true);
        System.out.println("------------------------------------");
        System.out.println("Sắp xếp tăng dần theo tên sản phẩm:");
        for (Product product : sortedAscProducts) {
            System.out.println(product.getProduct_name());
        }

        // Kiểm tra sắp xếp giảm dần theo tên sản phẩm
        List<Product> sortedDescProducts = pDAO.getAllProductSortedByName(false);
        System.out.println("------------------------------------");
        System.out.println("Sắp xếp giảm dần theo tên sản phẩm:");
        for (Product product : sortedDescProducts) {
            System.out.println(product.getProduct_name());
        }

        // Kiểm tra sắp xếp giảm dần theo danh mục
        List<Product> sortedDescCategories = pDAO.getAllProductSortedByCategory(false);
        System.out.println("------------------------------------");
        System.out.println("Sắp xếp giảm dần theo tên sản phẩm:");
        for (Product product : sortedDescCategories) {
            System.out.println(product.getSubcategory_name());
        }

        // Kiểm tra lọc sản phẩm theo danh mục
        String categoryName = "Laptop";
        List<Product> filteredProducts = pDAO.getFilteredProducts(categoryName);
        System.out.println("------------------------------------");
        System.out.println("Sản phẩm thuộc danh mục '" + categoryName + "': " + filteredProducts.size() + " kết quả");
        for (Product product : filteredProducts) {
            System.out.println("ID sản phẩm: " + product.getProduct_id());
            System.out.println("Tên sản phẩm: " + product.getProduct_name());
            System.out.println("Danh mục: " + product.getSubcategory_name());
            System.out.println("Giá bán: " + product.getSale_price());
            System.out.println("Trạng thái: " + product.getStatus());
            System.out.println("---------------------------------------------------");
        }

        // Kiểm tra lọc sản phẩm theo nổi bật
        String featured = "1";
        List<Product> filteredProducts2 = pDAO.getFilteredProductsbyFeatured(featured);
        System.out.println("2------------------------------------2");
        System.out.println("Sản phẩm thuộc nổi bật '" + featured + "': " + filteredProducts2.size() + " kết quả");
        for (Product product : filteredProducts2) {
            System.out.println("ID sản phẩm: " + product.getProduct_id());
            System.out.println("Tên sản phẩm: " + product.getProduct_name());
            System.out.println("Danh mục: " + product.getSubcategory_name());
            System.out.println("Giá bán: " + product.getSale_price());
            System.out.println("Trạng thái: " + product.getStatus());
            System.out.println("---------------------------------------------------");
        }

        // Kiểm tra lọc sản phẩm theo trạng thái
        String Status = "1";
        List<Product> filteredProducts3 = pDAO.getFilteredProductsbyStatus(Status);
        System.out.println("3------------------------------------3");
        System.out.println("Sản phẩm thuộc Trạng thái '" + Status + "': " + filteredProducts3.size() + " kết quả");
        for (Product product : filteredProducts3) {
            System.out.println("ID sản phẩm: " + product.getProduct_id());
            System.out.println("Tên sản phẩm: " + product.getProduct_name());
            System.out.println("Danh mục: " + product.getSubcategory_name());
            System.out.println("Giá bán: " + product.getSale_price());
            System.out.println("Trạng thái: " + product.getStatus());
            System.out.println("---------------------------------------------------");
        }
    }
}
