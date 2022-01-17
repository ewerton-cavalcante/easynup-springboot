package br.com.verturi.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "`Function`")
public class Function {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long			id;

	private String			name;

	@ManyToOne
	@JoinColumn(name = "functionType_id", nullable = false)
	private FunctionType	functionType;

	@ManyToOne
	@JoinColumn(name = "actionType_id", nullable = false)
	private ActionType		actionType;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	private Analysis		analysis;

}
