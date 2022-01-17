package br.com.verturi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.verturi.model.Company;
import br.com.verturi.model.Project;
import br.com.verturi.repository.CompanyRepository;
import br.com.verturi.repository.ProjectRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/companies")
public class CompanyController {

	@Autowired
	CompanyRepository repository;
	
	@Autowired
	ProjectRepository projectRepository;

	/*
	 * HttpHeaders headers;
	 * 
	 * { headers = new HttpHeaders(); headers.add("Access-Control-Allow-Origin",
	 * "*"); headers.add("Access-Control-Allow-Methods",
	 * "GET, POST, PATCH, PUT, DELETE, OPTIONS");
	 * headers.add("Access-Control-Allow-Headers",
	 * "Origin, Content-Type, X-Auth-Token"); }
	 */

	@GetMapping
	public ResponseEntity<List<Company>> findAll(@RequestParam String filter, @RequestParam Integer pageSize,
			@RequestParam Integer pageNumber, @RequestParam String sortOrder) {

		Direction sortDirection = Direction.fromString(sortOrder.toUpperCase());
		Sort sortBy = Sort.by(sortDirection, "name");

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sortBy);

		return new ResponseEntity<>(repository.findAll(pageable).getContent(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Company> findById(@PathVariable("id") long id) {
		return new ResponseEntity<>(repository.findById(id).get(), HttpStatus.OK);
	}

	@GetMapping(value = "/count")
	public ResponseEntity<Long> count() {
		return new ResponseEntity<>(repository.count(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Company> save(@RequestBody Company company) {
		try {
			Company _company = repository.save(company);
			return new ResponseEntity<>(_company, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		try {
			
			// Remove projetos associados
			Optional<Company> company = repository.findById(id);
			List<Project> projects = projectRepository.findByCompany(company.get());
			projectRepository.deleteAll(projects);
			
			repository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
