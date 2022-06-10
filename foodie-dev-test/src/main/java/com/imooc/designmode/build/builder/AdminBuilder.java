package com.imooc.designmode.build.builder;

/**
 * @author Mengdl
 * @date 2022/04/19
 */
public class AdminBuilder implements Build {
    User user = new User();
    @Override
    public void makeId(String val) {
        user.setId(val);
    }

    @Override
    public void makeName(String val) {
        user.setName(val);
    }

    @Override
    public void makePassword(String val) {
        user.setPassword(val);
    }

    @Override
    public void makePhone(String val) {
        user.setPassword(val);
    }

    @Override
    public User makeUser() {
        return user;
    }

}
