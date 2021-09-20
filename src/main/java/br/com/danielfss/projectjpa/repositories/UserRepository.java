package br.com.danielfss.projectjpa.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.danielfss.projectjpa.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT obj FROM User obj WHERE obj.salary >= :minSalary AND obj.salary <= :maxSalary")
	Page<User> searchSalary(Double minSalary, Double maxSalary, Pageable pageable);

	@Query("SELECT obj FROM User obj WHERE LOWER(obj.name) LIKE LOWER(CONCAT('%',:name,'%'))")
	Page<User> searchName(String name, Pageable pageable);
	
	// Query Creation.
	// FAZ A MESMA COISA QUE AS QUERIES ACIMA. DESTA FORMA É MAIS GASTAMOS MENOS TEMPO ESCREVENDO O CÓDIGO.
	Page<User> findBySalaryBetween(Double minSalary, Double maxSalary, Pageable pageable);

	Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

	/*
	 * SPRING DATA JPA DOCUMENTATION
	 * FORMAS: SQL RAÍZ (NATIVE QUERY), JPQL, QUERY METHODS. 
	 */
}
