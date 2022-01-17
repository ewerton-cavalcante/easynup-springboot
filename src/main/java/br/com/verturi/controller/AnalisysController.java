package br.com.verturi.controller;

import java.util.List;

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

import br.com.verturi.model.Analysis;
import br.com.verturi.model.Function;
import br.com.verturi.repository.AnalysisRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/analysis")
public class AnalisysController {

	@Autowired
	AnalysisRepository repository;

	@GetMapping
	public ResponseEntity<List<Analysis>> findAll(@RequestParam String filter, @RequestParam Integer pageSize,
			@RequestParam Integer pageNumber, @RequestParam String sortOrder) {

		Direction sortDirection = Direction.fromString(sortOrder.toUpperCase());
		Sort sortBy = Sort.by(sortDirection, "name");

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sortBy);

		return new ResponseEntity<>(repository.findAll(pageable).getContent(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Analysis> findById(@PathVariable("id") long id) {
		return new ResponseEntity<>(repository.findById(id).get(), HttpStatus.OK);
	}

	@GetMapping(value = "/count")
	public ResponseEntity<Long> count() {
		return new ResponseEntity<>(repository.count(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Analysis> save(@RequestBody Analysis analysis) {
		try {
			for (Function function : analysis.getFunctions())
				function.setAnalysis(analysis);
			Analysis _analysis = repository.save(analysis);
			return new ResponseEntity<>(_analysis, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
		try {
			repository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
