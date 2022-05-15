package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface snilsRepository extends JpaRepository<snils, String> {

    @Query("select c from snils c " +
            "where lower(c.snils_num) like lower(concat('%', :searchTerm, '%')) ")
    List<snils> search(@Param("searchTerm") String searchTerm);
}
