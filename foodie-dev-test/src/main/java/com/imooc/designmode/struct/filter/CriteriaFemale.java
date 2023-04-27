package com.imooc.designmode.struct.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mengdl
 * @date 2022/04/20
 */
public class CriteriaFemale implements Criteria{
    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> femalePersons = new ArrayList<Person>();
        for (Person person : persons) {
            if(person.getGender().equalsIgnoreCase("FEMALE")){
                femalePersons.add(person);
            }
        }
        return femalePersons;
    }
}
