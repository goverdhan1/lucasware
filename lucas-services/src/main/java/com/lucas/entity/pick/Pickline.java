package com.lucas.entity.pick;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

import com.lucas.entity.wave.Wave;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "pickline")
public class Pickline {
	
	private Long Id;
	private int quantity;
	private Wave wave;
	Boolean completed;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "pickline_id")
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	
	@Column(name = "quantity")
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@ManyToOne
    @JoinColumn(name = "wave_id") 
	@Cascade(value={org.hibernate.annotations.CascadeType.ALL})
	public Wave getWave() {
		return wave;
	}
	public void setWave(Wave wave) {
		this.wave = wave;
	}
	
	@Column(name = "completed")
	public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	
	public boolean equals(Object other) {
		if (this == other){
			return true;
		}
		if (!(other instanceof Pickline)){
			return false;
		}
		Pickline castOther = (Pickline) other;
		return new EqualsBuilder().append(Id, castOther.Id).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(Id).toHashCode();
	}

	
	
}
