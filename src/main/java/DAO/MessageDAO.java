package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

  /**
   * Creates a new message in the database.
   * @return instance of newly-created message on success. On failure, returns null.
   */
  public Message insertMessage(Message message) {
    Connection connection = ConnectionUtil.getConnection();
    try {
      String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
      PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setInt(1, message.getPosted_by());
      preparedStatement.setString(2, message.getMessage_text());
      preparedStatement.setLong(3, message.getTime_posted_epoch());
      preparedStatement.executeUpdate();

      ResultSet rs = preparedStatement.getGeneratedKeys();
      if (rs.next()) {
        return new Message((int) rs.getLong(1), message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
      }
    } catch(SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public List<Message> findAllMessages() {
    List<Message> allMessages = new ArrayList<Message>();
    Connection connection = ConnectionUtil.getConnection();
    try {
      String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM message;";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        allMessages.add(new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4)));
      }
    } catch(SQLException e) {
      System.out.println(e.getMessage());
    }
    return allMessages;
  }

  public Message findMessageById(int message_id) {
    Connection connection = ConnectionUtil.getConnection();
    try {
      String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM message WHERE message_id=?;";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, message_id);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        return new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4));
      }
    } catch(SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public void deleteMessageById(int message_id) {
    Connection connection = ConnectionUtil.getConnection();
    try {
      String sql = "DELETE FROM message WHERE message_id=?;";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, message_id);
      preparedStatement.executeUpdate();
    } catch(SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void updateMessageById(int message_id, String updatedText) {
    Connection connection = ConnectionUtil.getConnection();
    try {
      String sql = "UPDATE message SET message_text=? WHERE message_id=?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, updatedText);
      preparedStatement.setInt(2, message_id);
      preparedStatement.executeUpdate();
    } catch(SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public List<Message> findAllMessagesByUser(int account_id) {
    Connection connection = ConnectionUtil.getConnection();
    List<Message> messages = new ArrayList<Message>();
    try {
      String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM message WHERE posted_by=?;";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, account_id);
      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        messages.add(new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4)));
      }
    } catch(SQLException e) {
      System.out.println(e.getMessage());
    }
    return messages;
  }
}