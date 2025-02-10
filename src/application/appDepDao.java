package application;

import db.DB;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class appDepDao {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== TEST BY ID ===\n");
        Department depById= departmentDao.findById(3);
        System.out.println(depById);

        System.out.println("\n\n=== FIND ALL ===");
        List<Department> allDepartments = departmentDao.findAll();
        allDepartments.forEach(dep -> System.out.println(dep + "\n"));

        System.out.println("=== ADD NEW DEPARTMENT ===");
        Department dep = new Department(null, "Coffe beams");
        departmentDao.insert(dep);
        System.out.println("Inserted! New seller ID: " + dep.getId());

        System.out.println("=== UPDATE DEPARTMENT ===");
        dep.setName("Music");
        departmentDao.update(dep);
        System.out.println("Updated! New department data: " + departmentDao.findById(dep.getId()));

        System.out.println("\n=== DEPARTMENT DELETE =====");
        System.out.println("Enter id for delete test: ");
        int id = sc.nextInt();
        departmentDao.deleteById(id);
        System.out.println("Delete completed");

        sc.close();

        DB.closeConnection();

    }
}
