<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Post</title>
</head>
<body>
    <h2>Add New Post</h2>
    <% if (request.getParameter("success") != null) { %>
        <p style="color: green;">Post added successfully!</p>
    <% } %>
    <% if (request.getParameter("error") != null) { %>
        <p style="color: red;">Error adding post. Please try again.</p>
    <% } %>

    <form action="PostController" method="post" enctype="multipart/form-data">
        <label>User ID:</label>
        <input type="number" name="user_id" required><br>

        <label>Title:</label>
        <input type="text" name="title" required><br>

        <label>Category:</label>
        <input type="text" name="category" required><br>

        <label>Brief Info:</label>
        <input type="text" name="brief_info" required><br>

        <label>Description:</label>
        <textarea name="description" required></textarea><br>

        <label>Featured:</label>
        <input type="checkbox" name="is_featured"><br>

        <label>Status:</label>
        <select name="status">
            <option value="Published">Published</option>
            <option value="Draft">Draft</option>
        </select><br>

        <label>Choose Thumbnail:</label>
        <input type="file" name="thumbnail"><br>

        <input type="submit" value="Add Post">
    </form>
</body>
</html>
