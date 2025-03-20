<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List, model.Post, dao.PostDAO" %>
<%
    PostDAO dao = new PostDAO();
    List<Post> posts = dao.getAllPostsPerPage(1, 10);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Post List</title>
</head>
<body>
    <h2>Post List</h2>
    <% if (request.getParameter("success") != null) { %>
        <p style="color: green;">Post added successfully!</p>
    <% } %>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Thumbnail</th>
            <th>Title</th>
            <th>Category</th>
            <th>Brief Info</th>
            <th>Featured</th>
            <th>Status</th>
        </tr>
        <% for (Post p : posts) { %>
            <tr>
                <td><%= p.getPost_id() %></td>
                <td>
                    <% if (p.getThumbnail() != null) { %>
                        <img src="<%= request.getContextPath() + p.getThumbnail() %>" alt="Thumbnail" width="100">
                    <% } else { %>
                        No Image
                    <% } %>
                </td>
                <td><%= p.getTitle() %></td>
                <td><%= p.getCategory() %></td>
                <td><%= p.getBrief_info() %></td>
                <td><%= p.getIs_featured() ? "Yes" : "No" %></td>
                <td><%= p.getStatus() %></td>
            </tr>
        <% } %>
    </table>
</body>
</html>
