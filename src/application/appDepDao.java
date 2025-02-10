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

        Scanner sc = new Scanner(System.in)
                ;

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

//        System.out.println("=== TEST BY ID ===\n");
//        Seller seller3 = sellerDao.findById(3);
//        System.out.println(seller3);
//
//
//        System.out.println("\n\n=== TEST BY DEPARTMENT ===\n");
//        List<Seller> sellersByDepartment = sellerDao.findByDepartment(dep);
//        sellersByDepartment.forEach(sel -> System.out.println(sel + "\n"));
//
//        System.out.println("\n\n=== FIND ALL ===");
//        List<Seller> allSellers = sellerDao.findAll();
//        allSellers.forEach(sel -> System.out.println(sel + "\n"));
//
//        System.out.println("=== ADD NEW DEPARTMENT ===");
//        Department dep = new Department(2, "Electronics");
//        departmentDao.insert(dep);
//        System.out.println("Inserted! New seller ID: " + dep.getId());
//
//
//        System.out.println("=== UPDATE SELLER ===");
//        newSeller.setBaseSalary(1600.0);
//        sellerDao.update(newSeller);
//        System.out.println("Updated! New seller data: " + sellerDao.findById(newSeller.getId()));
//
//        System.out.println("\n=== SELLER DELETE =====");
//        System.out.println("Enter id for delete test: ");
//        int id = sc.nextInt();
//        sellerDao.deleteById(id);
//        System.out.println("Delete completed");

        sc.close();

        DB.closeConnection();

    }
}
