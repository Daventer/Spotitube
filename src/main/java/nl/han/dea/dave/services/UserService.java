package nl.han.dea.dave.services;

import nl.han.dea.dave.datasource.daos.UserDao;

import javax.inject.Inject;

public class UserService {

    private UserDao userDao;

    public boolean authentication(String userName, String password) {
        return userExist(userName) && password.equals(getPasswordFromUser(userName));
    }

    public boolean tokenIsValid(String token){
        return getUserIdFromToken(token) != 0;
    }

    public int getUserIdFromToken(String token){
        return userDao.getUserIdFromToken(token);
    }

    public boolean userExist(String userName){
        return userDao.getUser(userName) != null;
    }

    public String getPasswordFromUser(String username){
        return userDao.getUser(username).getPassword();
    }

    public void setNewToken(String userName, String token) {
        userDao.setNewToken(userName, token);
    }

    @Inject
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
