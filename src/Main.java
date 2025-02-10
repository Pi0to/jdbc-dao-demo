import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class Main {
    public static void main(String[] args) {


        SellerDao sellerDao = DaoFactory.createSellerDao();
        Department dep = new Department(2, "Electronics");

        System.out.println("=== TEST BY ID ===\n");
        Seller seller3 = sellerDao.findById(3);
        System.out.println(seller3);


        System.out.println("\n\n=== TEST BY DEPARTMENT ===\n");
        List<Seller> sellersByDepartment = sellerDao.findByDepartment(dep);
        sellersByDepartment.forEach(sel -> System.out.println(sel + "\n"));
    }
}