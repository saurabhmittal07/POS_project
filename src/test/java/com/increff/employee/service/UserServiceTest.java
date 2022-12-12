package com.increff.employee.service;

import com.increff.employee.pojo.UserPojo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserServiceTest extends AbstractUnitTest{

    @Autowired
    private UserService userService;

    private UserPojo  createUser() throws ApiException{
        UserPojo userPojo = new UserPojo();
        userPojo.setRole("Employee");
        userPojo.setEmail("a@gmail.com");
        userPojo.setPassword("password123");
        userService.add(userPojo);
        return userPojo;
    }

    @Test
    public void testAdd() throws ApiException {
        createUser();
    }

    @Test
    public void testGetByEmail() throws ApiException{
        createUser();
        UserPojo userPojo = userService.get("a@gmail.com");
        assertEquals("employee", userPojo.getRole());
        assertEquals("password123", userPojo.getPassword());
    }

    @Test
    public void testGetAll() throws ApiException{
        createUser();
        List<UserPojo> users = userService.getAll();
        UserPojo userPojo = users.get(0);
        assertEquals("employee", userPojo.getRole());
        assertEquals("password123", userPojo.getPassword());
        assertEquals(1,users.size());
    }

    @Test public void testDeleteUser() throws ApiException{
        UserPojo userPojo = createUser();
        userService.delete(userPojo.getId());
        List<UserPojo> users = userService.getAll();
        assertEquals(0,users.size());
    }
}
