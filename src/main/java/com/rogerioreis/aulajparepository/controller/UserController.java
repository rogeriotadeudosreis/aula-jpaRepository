package com.rogerioreis.aulajparepository.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rogerioreis.aulajparepository.entities.User;
import com.rogerioreis.aulajparepository.repository.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserRepository repository;

	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		List<User> result = repository.findAll();
		return ResponseEntity.ok(result);
	}

	/*
	 * Recursos do enepoint abaixo para buscar no browser: http:8080/users/page
	 * (traz por página até 20 itens por padrão, começando com a página 0)
	 * http:8080/users/page/?page=1 (traz a próxima página 1)
	 * http:8080/users/page/?page=0&size=12 (traz 12 itens por página)
	 * http:8080/users/page/?page=0&size=12&sort=name (ordena por nome)
	 * http:8080/users/page/?page=0&size=12&sort=name,desc (ordenado decrescente)
	 * http:8080/users/page/?page=0&size=12&sort=name,salary,asc (ordenado conforme
	 * definidos argumentos)
	 * 
	 */
	@GetMapping(value = "/page")
	public ResponseEntity<Page<User>> findAll(Pageable pageable) {
		Page<User> result = repository.findAll(pageable);
		return ResponseEntity.ok(result);
	}

	/*
	 * O serviço abaixo busca um salário entre um valor mínimo e valor máximo 
	 */
	@GetMapping(value = "/search-salary")
	public ResponseEntity<Page<User>> searchBySalary(@RequestParam(defaultValue = "0") Double minSalary,
			@RequestParam(defaultValue = "1000000000000") Double maxSalary, Pageable pageable) {
		Page<User> result = repository.findBySalaryBetween(minSalary, maxSalary, pageable);
		return ResponseEntity.ok(result);
	}
	
	/*
	 * O seviço abaixo busca um nome qualquer
	 */

	@GetMapping(value = "/search-name")
	public ResponseEntity<Page<User>> searchByName(@RequestParam(defaultValue = "") String name, Pageable pageable) {
		Page<User> result = repository.findByNameContainingIgnoreCase(name, pageable);
		return ResponseEntity.ok(result);
	}
	
	

}
