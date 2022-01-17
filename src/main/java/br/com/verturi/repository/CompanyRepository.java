package br.com.verturi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.verturi.model.Company;

public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {

}
