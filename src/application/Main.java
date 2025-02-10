package application;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in)
;

        SellerDao sellerDao = DaoFactory.createSellerDao();
        Department dep = new Department(2, "Electronics");

        System.out.println("=== TEST BY ID ===\n");
        Seller seller3 = sellerDao.findById(3);
        System.out.println(seller3);


        System.out.println("\n\n=== TEST BY DEPARTMENT ===\n");
        List<Seller> sellersByDepartment = sellerDao.findByDepartment(dep);
        sellersByDepartment.forEach(sel -> System.out.println(sel + "\n"));

        System.out.println("\n\n=== FIND ALL ===");
        List<Seller> allSellers = sellerDao.findAll();
        allSellers.forEach(sel -> System.out.println(sel + "\n"));

        System.out.println("=== ADD NEW SELLER ===");
        Seller newSeller = new Seller(null, "Asma Lenny", "asma@gmail.com", new Date(), 1599.90, dep);
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New seller ID: " + newSeller.getId());


        System.out.println("=== UPDATE SELLER ===");
        newSeller.setBaseSalary(1600.0);
        sellerDao.update(newSeller);
        System.out.println("Updated! New seller data: " + sellerDao.findById(newSeller.getId()));

        System.out.println("\n=== SELLER DELETE =====");
        System.out.println("Enter id for delete test: ");
        int id = sc.nextInt();
        sellerDao.deleteById(id);
        System.out.println("Delete completed");

        sc.close();

        DB.closeConnection();
     }
}