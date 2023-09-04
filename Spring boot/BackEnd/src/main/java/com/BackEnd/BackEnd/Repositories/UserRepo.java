package com.BackEnd.BackEnd.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BackEnd.BackEnd.Model.User;

@Repository
public interface UserRepo extends JpaRepository<User,String>  {

    User findByUserId(String userID);


    
}
