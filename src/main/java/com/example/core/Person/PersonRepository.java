package com.example.core.Person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Tyler Bobella on 3/20/17.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
