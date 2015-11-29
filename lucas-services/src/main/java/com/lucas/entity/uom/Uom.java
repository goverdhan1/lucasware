/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.entity.uom;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucas.entity.support.Identifiable;
import com.lucas.exception.IncompatibleUOMException;

/**
 * This class encapsulates all behavior related to Units of Measure
 * @see {@link http://vmjira:8090/display/PHX/Units+of+Measure } UOM Wiki page
 * @author Pankaj Tandon
 *
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name="uom")
public class Uom implements Identifiable<Long>, java.io.Serializable {

	private static final long serialVersionUID = -2291700810044326698L;

	private static Logger LOG = LoggerFactory.getLogger(Uom.class);
	
	private Uom(){}; //Immutable. 
	
	public Uom(String name, Uom parent, double factor, String customerFacingName, UomDimension dimension){
		this.name = name;
		this.parent = parent;
		this.factor = factor;
		this.customerFacingName = customerFacingName;
		this.dimension = dimension;
		
	}
	
	
	private Long uomId;
	
	/**
	 * Name used internally. This name is unique and the uniqueness is tested in a case sensitive manner.
	 * So, for example aCase and acase are different.
	 * @see <code>customerFacingName</code>
	 */
	private String name;
	
	/**
	 * UOM of the parent. The parent UOM is always "larger" than it's children.
	 * @see <code>factor</code>
	 */
	private Uom parent;
	
	/**
	 * Represents the number of times this UOM is stored in its parent UOM.
	 * This value is always >= 1
	 */
	private double factor;
	
	/**
	 * The name used by customer
	 */
	private String customerFacingName;
	
	
	/**
	 * The {@link UomDimension} that this Uom is linked to
	 * Note UOMs are not interchangeable across dimensions.
	 */
	private UomDimension dimension;
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name="uom_id")
	@Override
	public Long getId() {
		return uomId;
	}
	
	@Override
	public void setId(Long id) {
		this.uomId = id;
	}
	
	@NotNull
	@Column(name="uom_name", unique = true)
	public String getName() {
		return name;
	}

	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name = "parent_uom_id")
	@AttributeOverride(name="parent.uomId", column = @Column(name = "parent_uom_id"))
	public Uom getParent() {
		return parent;
	}

	@Column(name="factor")
	public double getFactor() {
		return factor;
	}
	
	@Column (name="customer_facing_name")
	public String getCustomerFacingName() {
		return customerFacingName;
	}

	public void setCustomerFacingName(String customerFacingName) {
		this.customerFacingName = customerFacingName;
	}
	
	/**
	 * Two UOMs are considered equal if their name and their parents' name are equal.
	 * Like first name, last name :)
	 * @param that
	 * @return
	 */
	public boolean equals(Uom that){
		boolean boo = false;
		if (StringUtils.equals((that == null?null:that.getName()), this.getName()) ){
			//names are equal
			if (that.getParent() != null && this.getParent() != null ){
				//parents are not null
				if (StringUtils.equals(that.getParent().getName(), this.getParent().getName())){
					//parents names are the same
					boo = true;
				}
			} else {
				if (that.getParent() == null && this.getParent() == null){
					//both are parent-less
					boo = true;
				}
			}
		}
		return boo;
	}
	
	/**
	 * Two Uoms are considered equivalent if their parents are <code>equal</code> and their factors are equal.
	 * Typically these would be similar Uoms with potentially different names but representing the same quantity.
	 * Example Box and Carton.
	 * @param that
	 * @return
	 */
	public boolean equivalent(Uom that){
		boolean boo = false;
		if (this.getParent() != null){
			if (this.getParent().equals(that.parent)){
				if (this.getFactor() == that.getFactor()){
					boo = true;
				}
			}
		} else if (this.getParent() == null && that.getParent() == null){
			if (this.getFactor() == that.getFactor()){
				boo = true;
			}
		}
		return boo;
	}
	
	/**
	 * Returns a nested list of UOMs.
	 * For example: [2 Bottle Each make 1 Mini Case, [6 Mini Case make 1 Inner Case, [4 Inner Case make 1 Case, [1 Case make 1 TOP-LEVEL-UOM, ]]]]
	 */
	public String toString(){
		String top = "TOP-LEVEL-UOM";
		return "[" + this.getFactor() + " " + this.getName() + " make 1 " + 
				(this.getParent() == null?top:(this.getParent().getName())) + ", " +
				(this.getParent() == null?"":(this.getParent().toString())) + "]";
	}
	
	/**
	 * Determines the number of times the passed in <code>Uom</code> is present in the current <code>Uom</code>
	 * Note if the passed in <code>Uom</code> is larger than the current <code>Uom</code>, then IncomptibleUOMException will be thrown.
	 * @see findFactor(Uom uom1, Uom uom2).
	 * If the UOM passed is not in the same UOM hierarchy/family as the current UOM (for example, if lbs is passed to litres), then an
	 * {@link IncompatibleUOMExpcetion} will be thrown.
	 * @see {@link isInSameFamily(Uom uom)}
	 * @param that
	 * @return
	 */
	public double findFactor(Uom that){
		if (!this.isInSameFamily(that)){
			throw new IncompatibleUOMException("The UOMs are incompatible! UOM1: " + this.toString() + ", UOM2: " + that.toString());
		}
		double factor = 1.0;
		if (this.isLargerThan(that)){
			while ((that.getParent() != null) && (!that.getParent().equals(this.getParent()) )){
				factor = factor * that.getFactor();
				that = that.getParent();
			}	
		} 
		
		if (this.isSmallerThan(that)){
			throw new IncompatibleUOMException ("UOM: " + that + " is larger than this UOM: " + this + ". Consider using the static method of the same name.");
		}
		return factor;
	}
	
	/**
	 * Returns how many times uom2 exists in uom1
	 * If uom1 is larger than uom2, this will return a value > 1.0
	 * If uom1 and uom2 are equivalent, this will return 1.0
	 * If uom1 is smaller than uom2, this will return a value < 1.0
	 * @param uom1
	 * @param uom2
	 * @return
	 */
	public static double findFactor(Uom uom1, Uom uom2){
		double factor = 1.0;
		if (uom1.isLargerThan(uom2)){
			factor = uom1.findFactor(uom2);
		} else if (uom2.isLargerThan(uom1)){
			factor = 1.0/uom2.findFactor(uom1);
		}
		return factor;
	}
	
	/**
	 * Returns if the current Uom is larger (has a factor > 1.0) than the passed in Uom represented by <code>that</code>
	 * Note, equal uoms return a false.
	 * @param that
	 * @return
	 */
	public boolean isLargerThan(Uom that){
		if (!this.isInSameFamily(that)){
			throw new IncompatibleUOMException("The UOMs are incompatible! UOM1: " + this.toString() + ", UOM2: " + that.toString());
		}
		boolean thisIsLarger = false;
		if (!this.equals(that)){
			while ((that.getParent() != null)){
				if (that.getParent().equals(this) ) {
					thisIsLarger = true;
					break;
				}
				that = that.getParent();
			}
		}
		return thisIsLarger;
	}

	/**
	 * Returns if the current Uom is smaller than (has a factor < 1.0) than the passed in Uom represented by <code>that</code>
	 * Note, equal uoms return a false.
	 * @param that
	 * @return
	 */
	public boolean isSmallerThan(Uom that){
		//Because of the equality case, we cannot negate isLargerThan
		if (!this.isInSameFamily(that)){
			throw new IncompatibleUOMException("The UOMs are incompatible! UOM1: " + this.toString() + ", UOM2: " + that.toString());
		}
		boolean thisIsSmaller = true;
		if (!this.equals(that)){
			while ((that.getParent() != null)){
				if (that.getParent().equals(this) ) {
					thisIsSmaller = false;
					break;
				}
				that = that.getParent();
			}
		} else {
			thisIsSmaller = false;
		}
		return thisIsSmaller;
	}
	
	
	/**
	 * Returns the top level parent of the passed in Uom
	 * @return
	 */
	public static Uom getTopLevelParent(Uom uom){
		Uom grandpa;
		
		while (true){
			if (uom.getParent() == null){
				grandpa = uom;
				break;
			} else {
				uom = uom.getParent();
			}
		}
		return grandpa;
	}
	/**
	 * Tests if the passed in Uom belongs to the same hierarchy/family as this Uom.
	 * @param that
	 * @return
	 */
	public boolean isInSameFamily(Uom that){
		boolean boo = false;
		if (this.getDimension().equals(that.getDimension())){
			Uom fred = Uom.getTopLevelParent(that);
			Uom alan = Uom.getTopLevelParent(this);
			boo = fred.equals(alan);
		} 
		return boo;
		
	}
	
	@NotNull
	@Column(length=30)
	@Enumerated(EnumType.STRING)
	public UomDimension getDimension() {
		return dimension;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(Uom parent) {
		this.parent = parent;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}

	public void setDimension(UomDimension dimension) {
		this.dimension = dimension;
	}
	
	
}
