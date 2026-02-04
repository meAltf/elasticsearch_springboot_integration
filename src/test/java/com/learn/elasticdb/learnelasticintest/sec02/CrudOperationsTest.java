package com.learn.elasticdb.learnelasticintest.sec02;

import com.learn.elasticdb.learnelasticintest.AbstractTest;
import com.learn.elasticdb.learnelasticintest.sec02.entity.Employee;
import com.learn.elasticdb.learnelasticintest.sec02.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;

import java.util.List;
import java.util.stream.IntStream;

public class CrudOperationsTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(CrudOperationsTest.class);

    @Autowired
    private EmployeeRepository repository;

    @Test
    public void crud() {
        var employee = this.createEmployee(1, "sam", 30);

        // save
        this.repository.save(employee);
        this.printAll();

        // find by id
        employee = this.repository.findById(1).orElseThrow();
        Assertions.assertEquals(30, employee.getAge());
        Assertions.assertEquals("sam", employee.getName());

        // update and save
        employee.setAge(32);
        employee = this.repository.save(employee);
        this.printAll();
        Assertions.assertEquals(32, employee.getAge());

        // delete
        this.repository.deleteById(1);
        this.printAll();
        Assertions.assertTrue(this.repository.findById(1).isEmpty());

    }


    private Employee createEmployee(int id, String name, int age) {
        var employee = new Employee();
        employee.setId(id);
        employee.setName(name);
        employee.setAge(age);
        return employee;
    }

    private void printAll() {
        this.repository.findAll()
                .forEach(e -> log.info("employee: {}", e));
    }

}