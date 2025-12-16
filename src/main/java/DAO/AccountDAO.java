package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;


public class AccountDAO {

  /**
   * Creates a new account
   * @param account an object modeling an account to be added
   * @return account object representing the newly created object if successful, otherwise return null
   */
  public Account insertAccount(Account account) {
    Connection connection = ConnectionUtil.getConnection();
    try {
      String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
      PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, account.getUsername());
      preparedStatement.setString(2, account.getPassword());
      preparedStatement.executeUpdate();

      ResultSet rs = preparedStatement.getGeneratedKeys();
      if (rs.next()) {
        return new Account((int) rs.getLong(1), account.getUsername(), account.getPassword());
      }
    } catch(SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public Account findAccountByUsernameAndPassword(Account account) {
    Connection connection = ConnectionUtil.getConnection();
    try {
      String sql = "SELECT * FROM account WHERE username=? AND password=?;";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, account.getUsername());
      preparedStatement.setString(2, account.getPassword());
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        return new Account(rs.getInt(1), rs.getString(2), rs.getString(3));
      }
    } catch(SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public Account findAccountById(int id) {
    Connection connection = ConnectionUtil.getConnection();
    try {
      String sql = "SELECT * FROM account WHERE account_id=?;";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        return new Account(rs.getInt(1), rs.getString(2), rs.getString(3));
      }
    } catch(SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

}