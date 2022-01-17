package br.com.verturi.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Analysis {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long				id;

	private String				purpose;

	private String				scope;

	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project				project;

	@ManyToOne
	@JoinColumn(name = "adjustmentFactor_id", nullable = false)
	private AdjustmentFactor	adjustmentFactor;

	@JsonManagedReference
	@OneToMany(mappedBy = "analysis", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Function>		functions;

}
