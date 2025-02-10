package Controller;

import Dao.CategoryDao;
import Entity.Category;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="CategoryController", urlPatterns={"/categorycontroller"})
public class CategoryController extends HttpServlet {
    private CategoryDao categoryDao = new CategoryDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";
        
        switch (action) {
            case "list":
                listCategories(request, response);
                break;
            case "delete":
                deleteCategory(request, response);
                break;
            case "restore":
                restoreCategory(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            default:
                listCategories(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        
        switch (action) {
            case "add":
                addCategory(request, response);
                break;
            case "update":
                updateCategory(request, response);
                break;
            case "search":
                searchCategory(request, response);
                break;
            default:
                listCategories(request, response);
        }
    }
    
    private void listCategories(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        List<Category> categories = categoryDao.getAll();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("category_list.jsp").forward(request, response);
    }
    
    private void addCategory(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String name = request.getParameter("name");
        //categoryDao.insert(new Category(name));
        response.sendRedirect("category?action=list");
    }
    
    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        categoryDao.hardDelete(id);
        response.sendRedirect("category?action=list");
    }
    
    private void restoreCategory(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        categoryDao.restore(id);
        response.sendRedirect("category?action=list");
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Category category = categoryDao.getById(id);
        request.setAttribute("category", category);
        request.getRequestDispatcher("category_form.jsp").forward(request, response);
    }
    
    private void updateCategory(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        //categoryDao.update(new Category(id, name));
        response.sendRedirect("category?action=list");
    }
    
    private void searchCategory(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Category> categories = categoryDao.search(keyword);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("category_list.jsp").forward(request, response);
    }
}