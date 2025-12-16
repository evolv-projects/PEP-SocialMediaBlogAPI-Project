package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
  MessageDAO messageDAO;

  public MessageService() {
    messageDAO = new MessageDAO();
  }

  public Message newMessage(Message message) {
    //message text not blank, not over 255 characters, posted_by is a real user
    if (message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255) {
      return null;
    }
    return messageDAO.insertMessage(message);
  }

  public List<Message> getAllMessages() {
    return messageDAO.findAllMessages();
  }

  public Message getMessageById(int message_id) {
    return messageDAO.findMessageById(message_id);
  }

  public Message deleteMessageById(int message_id) {
    Message deletedMessage = this.getMessageById(message_id);
    if (deletedMessage == null) { return null; }
    messageDAO.deleteMessageById(message_id);
    return deletedMessage;
  }

  public Message updateMessageById(int message_id, String updatedText) {
    if (updatedText.length() == 0 || updatedText.length() > 255 || this.getMessageById(message_id) == null) {
      return null;
    }
    messageDAO.updateMessageById(message_id, updatedText);
    return this.getMessageById(message_id);
  }

  public List<Message> getAllMessagesByUser(int account_id) {
    return messageDAO.findAllMessagesByUser(account_id);
  }
}