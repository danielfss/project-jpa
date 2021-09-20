package br.com.danielfss.projectjpa.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.danielfss.projectjpa.entities.User;
import br.com.danielfss.projectjpa.repositories.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	// O AUTOWIRED ESTÁ INJETANDO AUTOMATICAMENTE UMA INSTÂNCIA DO UserRepository
	@Autowired
	private UserRepository repository;
	
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		List<User> result = repository.findAll();
		return ResponseEntity.ok(result);
	}
	
	// ENDPOINT COM PAGINAÇÃO. POR PADRÃO É LISTADO 20 ITENS, MAS É POSSÍVEL ALTERAR.
	/* https://localhost:8080/users/page
	 * https://localhost:8080/users/page?page=1
	 * https://localhost:8080/users/page?page=0&size=12
	 * https://localhost:8080/users/page?page=0&size=12&sort=name
	 * https://localhost:8080/users/page?page=0&size=12&sort=name,desc
	 * https://localhost:8080/users/page?page=0&size=12&sort=name,salary,asc
	 */
	@GetMapping(value = "/page")
	public ResponseEntity<Page<User>> findAll(Pageable pageable) {
		Page<User> result = repository.findAll(pageable);
		return ResponseEntity.ok(result);
	}
	
	// searchSalary FOI CRIADO NA INTERFACE UserRepository, UTILIZANDO UMA QUERY SQL NORMAL OU JPQL.
	/*
	 * https://localhost:8080/users/search-salary
	 * https://localhost:8080/users/search-salary?minSalary=10000
	 * https://localhost:8080/users/search-salary?minSalary=9000&maxSalary=10000
	 * https://localhost:8080/users/search-salary?minSalary=5000&size=5
	 */
	@GetMapping(value = "/search-salary")
	public ResponseEntity<Page<User>> searchBySalary(@RequestParam(defaultValue = "0") Double minSalary, @RequestParam(defaultValue = "1000000000000") Double maxSalary, Pageable pageable) {
	    Page<User> result = repository.searchSalary(minSalary, maxSalary, pageable);
	    return ResponseEntity.ok(result);
	}
	
	@GetMapping(value = "/search-name")
	public ResponseEntity<Page<User>> searchByName(
			@RequestParam(defaultValue = "0") String name,
			Pageable pageable) {
	    Page<User> result = repository.searchName(name, pageable);
	    return ResponseEntity.ok(result);
	}
	
}
