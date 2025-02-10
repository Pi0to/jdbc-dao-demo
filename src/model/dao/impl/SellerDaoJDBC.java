package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {


    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName  \n" +
                    "FROM seller INNER JOIN department  \n" +
                    "ON seller.DepartmentId = department.Id  \n" +
                    "WHERE seller.Id = ?");

            st.setInt(1, id);

            rs = st.executeQuery();

            if(rs.next()) {

                Department dep = new Department();
                dep = instantiateDepartment(rs);

                Seller sl = new Seller();
                sl = instantiateSeller(rs, dep);

                return sl;
            }

            return null;

        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    @Override
    public List<Seller> findByDepartment (Department dep){

        PreparedStatement st = null;
        ResultSet rs = null;

        try{

            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName  \n" +
                    "FROM seller INNER JOIN department  \n" +
                    "ON seller.DepartmentId = department.Id \n" +
                    "WHERE DepartmentId = ? \n" +
                    "ORDER BY Name");

            st.setInt(1, dep.getId());

            rs = st.executeQuery();


            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {

                Department dp = map.get(rs.getInt("DepartmentID"));

                if(dp == null){
                    dp = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentID"), dp);
                }

                Seller sl = new Seller();
                sl = instantiateSeller(rs, dp);
                sellers.add(sl);

            }
            return sellers;

        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {

        PreparedStatement st = null;
        ResultSet rs = null;

        try{

            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName  \n" +
                    "FROM seller INNER JOIN department  \n" +
                    "ON seller.DepartmentId = department.Id \n" +
                    "ORDER BY Name");

            rs = st.executeQuery();


            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {

                Department dp = map.get(rs.getInt("DepartmentID"));

                if(dp == null){
                    dp = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentID"), dp);
                }

                Seller sl = new Seller();
                sl = instantiateSeller(rs, dp);
                sellers.add(sl);

            }
            return sellers;

        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {

        Department dep = new Department();

        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));

        return dep;
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException{

        Seller sl = new Seller();

        sl.setName(rs.getString("Name"));
        sl.setEmail(rs.getString("Email"));
        sl.setBirthday(rs.getDate("BirthDate"));
        sl.setBaseSalary(rs.getDouble("BaseSalary"));
        sl.setDepartment(dep);

        return sl;
    }
}
