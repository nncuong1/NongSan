/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Cart;
import model.User;

/**
 *
 * @author NGUYEN NGOC CUONG
 */
public class CartData extends JDBCConnection {

    UserData userData = new UserData();

    public void add(Cart cart) {
        Connection conn = super.getConnection();
        try {
            String sql = "INSERT INTO cart(idBuyer,buyDate) VALUES(?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, cart.getBuyer().getId());
            preparedStatement.setString(2, cart.getBuyDate());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys
                    = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                cart.setId(generatedKeys.getInt(1));
            }

        } catch (Exception e) {
            System.out.println("Loi add : " + e);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void delete(int id) {
        Connection conn = super.getConnection();
        try {
            String sql = "DELETE FROM cart WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Loi delete : " + e);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void update(Cart cart) {
        Connection conn = super.getConnection();
        try {
            String sql = "UPDATE cart set idBuyer = ? WHERE id  = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, cart.getBuyer().getId());
            preparedStatement.setInt(2, cart.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Loi update : " + e);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public Cart getCart(int id) {
        Connection conn = super.getConnection();
        try {
            String sql = "SELECT c.id AS 'cart_ID', c.idBuyer, c.buyDate, "
                    + " FROM cart c INNER JOIN user u ON c.idBuyer = u.id WHERE c.id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setId(rs.getInt("cart_ID"));
                cart.setBuyDate(rs.getString("buyDate"));

//                User user = new User();
                User user = userData.getUser(rs.getInt("idBuyer"));
//                user.setId(rs.getInt("idBuyer"));

                cart.setBuyer(user);
                //product.setQuantity(rs.getInt("quantity"));
                return cart;
            }
        } catch (Exception e) {
            System.out.println("Loi get cart: " + e);
        } finally {

        }
        return null;
    }

    public List<Cart> getListCart() {
        List<Cart> carts = new ArrayList<>();
        Connection conn = super.getConnection();
        try {
            String sql = "SELECT * FROM cart";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setId(rs.getInt("id"));
                cart.setBuyDate(rs.getString("buyDate"));

                User user = userData.getUser(rs.getInt("idBuyer"));
//                user.setId(rs.getInt("idBuyer"));
//                user.setId(rs.getInt("idBuyer"));

                cart.setBuyer(user);
                carts.add(cart);
            }
        } catch (Exception e) {
            System.out.println("Loi get : " + e);
        } finally {

        }
        return carts;
    }

    public List<Cart> search(String name) {
        List<Cart> cartList = new ArrayList<Cart>();
        String sql = "SELECT cart.id, cart.buyDate, cart.idBuyer, user.name, user.username, user.id AS user_id "
                + "FROM cart INNER JOIN user " + "ON cart.idBuyer = user.id WHERE user.name LIKE ? OR user.username LIKE ?";
      
        Connection conn = super.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            ps.setString(2, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cart cart = new Cart();
                cart.setId(rs.getInt("id"));
                cart.setBuyDate(rs.getString("buyDate"));
                 
                User user = userData.getUser(rs.getInt("idBuyer"));
                cart.setBuyer(user);
                
                cartList.add(cart);

            }
        } catch (Exception e) {
            System.out.println("Loi search cart : " + e);
        } finally {

        }
        return cartList;
    }

}
