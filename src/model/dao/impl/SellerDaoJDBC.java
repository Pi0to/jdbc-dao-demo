package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
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
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("INSERT INTO seller " +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                    "VALUES " +
                    "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthday().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if(rowsAffected > 0){
            rs = st.getGeneratedKeys();
                if(rs.next()){
                    obj.setId(rs.getInt(1));
                }
            }
            else throw new DbException("Unexpected error!! No rows affected!");
        }
        catch (SQLException e){
            new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
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
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
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

            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE DepartmentId = ? " +
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

            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
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
