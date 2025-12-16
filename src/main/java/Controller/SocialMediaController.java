package Controller;

import Model.*;
import Service.*;

import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("register", this::postNewUserHandler);
        app.post("login", this::postUserLogin);
        app.post("messages", this::postNewMessage);
        app.get("messages", this::getAllMessages);
        app.get("messages/{message_id}", this::getMessageById);
        app.delete("messages/{message_id}", this::deleteMessageById);
        app.patch("messages/{message_id}", this::updateMessageById);
        app.get("accounts/{account_id}/messages", this::getMessagesByUser);

        return app;
    }

    /**
     * Handles a post which attempts to register a new user. On successful creation, returns a 200 OK response,
     * otherwise returns 400 Client Error response
     * @param context
     */
    private void postNewUserHandler(Context context) {
        // extract the username and password from the json and put it into an Account
        Account account = context.bodyAsClass(Account.class);
        AccountService accountService = new AccountService();

        // user AccountService to insert a new account. If non-null, send back a 200
        Account newUser = accountService.createNewUser(account);
        if (newUser != null) { 
            context.status(200);
            context.json(newUser);
        }
        else { context.status(400); }
    }

    /**
     * Body contains JSON containing information regarding an Account, save for the id
     * If username and password match, return a 200 OK and the json for the full account
     * Otherwise, return a 401 Unauthorized
     * @param context
     */
    private void postUserLogin(Context context) {
        Account account = context.bodyAsClass(Account.class);
        AccountService accountService = new AccountService();

        Account checkUser = accountService.login(account);
        if (checkUser != null) {
            context.status(200);
            context.json(checkUser);
        }
        else { context.status(401); }
    }

    /**
     * Handles a post with a message in the body text which uploads a new message.
     * If message_text is blank or longer than 255, or if user_id doesn't exist, returns 400.
     * Otherwise returns 200 and JSON of the newly created message.
     * @param context
     */
    private void postNewMessage(Context context) {
        Message message = context.bodyAsClass(Message.class);
        MessageService messageService = new MessageService();
        Message newMessage = messageService.newMessage(message);
        if (newMessage != null) {
            context.status(200);
            context.json(newMessage);
        }
        else { context.status(400); }
    }

    /**
     * Handles a get request to messages which returns a JSON of a list of all messages and a 200.
     */
    private void getAllMessages(Context context) {
        MessageService messageService = new MessageService();
        List<Message> allMessages = messageService.getAllMessages();
        context.status(200);
        context.json(allMessages);
    }

    /**
     * Handles a get request to messages/{message_id} and returns a 200 and a JSON of the message, if it exists
     */
    private void getMessageById(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        MessageService messageService = new MessageService();
        Message message = messageService.getMessageById(message_id);
        if (message != null) { context.json(message); }
        context.status(200);
    }

    private void deleteMessageById(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        MessageService messageService = new MessageService();
        Message deletedMessage = messageService.deleteMessageById(message_id);
        if (deletedMessage != null) { context.json(deletedMessage); }
        context.status(200);
    }

    private void updateMessageById(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = context.bodyAsClass(Message.class);
        MessageService messageService = new MessageService();
        Message finalMessage = messageService.updateMessageById(message_id, updatedMessage.getMessage_text());
        if (finalMessage != null) {
            context.json(finalMessage);
            context.status(200);
        }
        else { context.status(400); }
    }

    private void getMessagesByUser(Context context) {
        int account_id = Integer.parseInt(context.pathParam("account_id"));
        MessageService messageService = new MessageService();
        List<Message> allMessagesByUser = messageService.getAllMessagesByUser(account_id);
        context.json(allMessagesByUser);
        context.status(200);
    }
}