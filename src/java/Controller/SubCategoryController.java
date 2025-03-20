package Controller;

import Dao.SubCategoryDao;
import Entity.SubCategory;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SubCategoryController", urlPatterns = {"/subcategorycontroller"})
public class SubCategoryController extends HttpServlet {
    private SubCategoryDao subCategoryDao = new SubCategoryDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            listSubCategories(request, response);
        } else {
            switch (action) {
                case "delete":
                    deleteSubCategory(request, response);
                    break;
                case "restore":
                    restoreSubCategory(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                default:
                    listSubCategories(request, response);
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            listSubCategories(request, response);
        } else {
            switch (action) {
                case "add":
                    addSubCategory(request, response);
                    break;
                case "update":
                    updateSubCategory(request, response);
                    break;
                case "search":
                    searchSubCategory(request, response);
                    break;
                default:
                    listSubCategories(request, response);
                    break;
            }
        }
    }

    private void listSubCategories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<SubCategory> subCategories = subCategoryDao.getAll();
        request.setAttribute("subCategories", subCategories);
        request.getRequestDispatcher("subcategory-list.jsp").forward(request, response);
    }

    private void addSubCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        //subCategoryDao.insert(new SubCategory(name, categoryId));
        response.sendRedirect("/subcategorycontroller");
    }

    private void updateSubCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        //subCategoryDao.update(new SubCategory(id, name, categoryId));
        response.sendRedirect("/subcategorycontroller");
    }

    private void deleteSubCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        //subCategoryDao.delete(id);
        response.sendRedirect("/subcategorycontroller");
    }

    private void restoreSubCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        subCategoryDao.restore(id);
        response.sendRedirect("/subcategorycontroller");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        SubCategory subCategory = subCategoryDao.getById(id);
        request.setAttribute("subCategory", subCategory);
        request.getRequestDispatcher("subcategory-form.jsp").forward(request, response);
    }

    private void searchSubCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<SubCategory> subCategories = subCategoryDao.search(keyword);
        request.setAttribute("subCategories", subCategories);
        request.getRequestDispatcher("subcategory-list.jsp").forward(request, response);
    }
}