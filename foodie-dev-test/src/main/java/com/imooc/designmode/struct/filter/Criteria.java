package com.imooc.designmode.struct.filter;

import java.util.List;

/**
 * 为标准（Criteria）创建一个接口。
 * @author Mengdl
 * @date 2022/04/20
 */
public interface Criteria {
    /**
     * 获取对象
     * @param persons
     * @return
     */
    public List<Person> meetCriteria(List<Person> persons);

}
