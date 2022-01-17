package br.com.verturi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.verturi.model.Analysis;

public interface AnalysisRepository extends PagingAndSortingRepository<Analysis, Long> {

}
