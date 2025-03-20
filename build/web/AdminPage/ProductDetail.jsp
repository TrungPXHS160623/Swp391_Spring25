<%-- 
    Document   : productDetail
    Created on : Mar 6, 2025, 8:40:07 AM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product Details</title>
    <style>
        .container {
            width: 80%;
            margin: auto;
            border: 1px solid #ccc;
            padding: 20px;
            border-radius: 5px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            font-weight: bold;
        }
        input, select, textarea {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
        }
        .image-preview {
            width: 150px;
            height: auto;
        }
        .btn-container {
            margin-top: 20px;
        }
        .btn {
            padding: 10px;
            margin-right: 10px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Product Details</h2>
        <form action="ProductDetailController" method="post" enctype="multipart/form-data">
            <input type="hidden" name="product_id" value="${product.product_id}" />
            
            <div class="form-group">
                <label>Thumbnail:</label>
                <br>
                <img class="image-preview" src="${coverProduct.image_url}" alt="Product Image" />
                <input type="file" name="image" accept="image/*">
            </div>
            
            <div class="form-group">
                <label>Category:</label>
                <select name="subcategory">
                    <c:forEach var="sub" items="${subcategories}">
                        <option value="${sub.id}" ${sub.id == product.subcategory_id ? 'selected' : ''}>${sub.name}</option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="form-group">
                <label>Title:</label>
                <input type="text" name="product_name" value="${product.product_name}" required>
            </div>
            
            <div class="form-group">
                <label>List Price:</label>
                <input type="number" step="0.01" name="list_price" value="${product.list_price}" required>
            </div>
            
            <div class="form-group">
                <label>Sale Price:</label>
                <input type="number" step="0.01" name="sale_price" value="${product.sale_price}">
            </div>
            
            <div class="form-group">
                <label>Description:</label>
                <textarea name="description">${product.description}</textarea>
            </div>
            
            <div class="form-group">
                <label>Stock:</label>
                <input type="number" name="stock" value="${product.stock}">
            </div>
            
            <div class="form-group">
                <label>Status:</label>
                <select name="status">
                    <option value="1" ${product.status == 1 ? 'selected' : ''}>Active</option>
                    <option value="0" ${product.status == 0 ? 'selected' : ''}>Inactive</option>
                </select>
            </div>
            
            <div class="form-group">
                <input type="checkbox" name="featured" value="1" ${product.featured == 1 ? 'checked' : ''}> Featured
            </div>
            
            <div class="btn-container">
                <button type="submit" class="btn" name="action" value="add">Add new Product</button>
                <button type="submit" class="btn" name="action" value="edit">Change</button>
                <button type="submit" class="btn" name="action" value="save">Save change</button>
            </div>
        </form>
    </div>
</body>
</html>