package com.example.task1.repository;

import com.example.task1.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AddressRepository extends JpaRepository<Address, Long> {

}
