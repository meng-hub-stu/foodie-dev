package com.imooc.designmode.build.builder;

/**
 * 测试创建建造者对象
 * @author Mengdl
 * @date 2022/04/19
 */
public class Admin {

    private Build build;

    public void setBuild(Build build) {
        this.build = build;
    }

    public User makeUser(String id, String name, String password, String phone){
        build.makeId(id);
        build.makeName(name);
        build.makePassword(password);
        build.makePhone(phone);
        return this.build.makeUser();
    }

    /**
     * 建造者模式测试
     * @param args
     */
    public static void main(String[] args) {
        Admin admin = new Admin();
        admin.setBuild(new AdminBuilder());
        User user=admin.makeUser("1","张三","123456","88888888");
        System.out.println(user);
    }

}
