package br.com.verturi.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.verturi.model.Company;
import br.com.verturi.model.Project;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {

	List<Project> findByCompany(Company company);
}
