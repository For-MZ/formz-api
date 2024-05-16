package com.example.demo.Repository;

import com.example.demo.Entity.Housing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HousingRepository extends JpaRepository<Housing,Long>,HousingRepositoryCustom {

}
