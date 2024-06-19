package com.example.demo.dao;

import com.example.demo.entities.Delegation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DelegationRepository extends CrudRepository<Delegation, Long> {


}
