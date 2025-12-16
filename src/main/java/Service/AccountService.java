package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
  AccountDAO accountDAO;

  public AccountService() {
    accountDAO = new AccountDAO();
  }

//   /**
//    * Creates a new account in the database.
//    * Fails if: username already taken, username blank, password at least 4 characters long
//    * @param account Representation of an account to be created.
//    * @return Representation of newly created account on success, null on failure
//    */
//   public Account createNewUser(Account account) {
//     if (account.getUsername().length() < 1 || account.getPassword().length() < 4) { return null; }
//     return accountDAO.insertAccount(account);
//   }

//   /**
//    * Checks if an account matches an existing username and password
//    * @return Full representation of matching account if it exists, otherwise null.
//    */
//   public Account login(Account account) {
//     return accountDAO.findAccountByUsernameAndPassword(account);
//   }

//   /**
//    * Gets an Account from the database based on its account_id.
//    * @param id account_id trying to verify.
//    * @return Representation of attached account if it exists, otherwise null.
//    */
//   public Account getAccountById(int id) {
//     return accountDAO.findAccountById(id);
//   }

}