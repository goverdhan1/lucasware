package com.lucas.entity.wave;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.lucas.entity.ui.canvas.Canvas;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "wave")
public class Wave {
	
	private Long Id;
	String waveName;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "wave_id")
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	@Column(name = "wave_name", unique = true)
	@NotNull
	public String getWaveName() {
		return waveName;
	}
	public void setWaveName(String waveName) {
		this.waveName = waveName;
	}
	
	public boolean equals(Object other) {
		if (this == other){
			return true;
		}
		if (!(other instanceof Wave)){
			return false;
		}
		Wave castOther = (Wave) other;
		return new EqualsBuilder().append(waveName, castOther.waveName).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(waveName).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("waveName", waveName).toString();
	}


}
