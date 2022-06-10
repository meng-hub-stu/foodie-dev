package com.imooc.designmode.build.builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 链式建造者模式
 * @author Mengdl
 * @date 2022/04/19
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class User {
    String id;
    String name;
    String password;
    String phone;


    /**
     *私有的建造构造器
     */
    public User(Builder builder) {
        id = builder.id;
        name = builder.name;
        password = builder.password;
        phone = builder.phone;
    }

    /**
     * 建造者
     */
    public static final class Builder{
        private String id;
        private String name;
        private String password;
        private String phone;

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder phone(String val) {
            phone = val;
            return this;
        }

        public User create() {
            return new User(this);
        }
    }

    public static void main(String[] args) {
        User user=new User.Builder().id("1").name("张三").password("123456")
                .phone("15820638007").create();
        System.out.println(user.toString());
    }

}
