package nl.han.dea.dave.services;

import nl.han.dea.dave.daos.UserDao;

import javax.inject.Inject;

public class UserService {

    private UserDao userDao;

    public static String TOKEN = "1234-1234-1234";

    public boolean authentication(String userName, String password) {
        return userExist(userName) && password.equals(getPasswordFromUser(userName));
    }

    public boolean tokenIsValid(String token){
        return token.equals(TOKEN);
    }

    public boolean userExist(String userName){
        return userDao.getUser(userName) != null;
    }

    public String getPasswordFromUser(String username){
        return userDao.getUser(username).getPassword();
    }

    @Inject
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
