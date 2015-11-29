package com.lucas.services.uom;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.lucas.entity.uom.Uom;
import com.lucas.entity.uom.UomDimension;
import com.lucas.exception.IncompatibleUOMException;

public class UomUnitTests {
	private Uom aCase;
	private Uom aCase2;
	private Uom aCase3;
	private Uom aCase4;
	private Uom aCase5;
	private Uom aFakeCase;
	private Uom yetAnotherFakeCase;
	
	private Uom anInnerCase;
	private Uom aMiniCase;
	private Uom aBottleEach;
	private Uom aFlaskEach;
	private Uom aGobletEach;
	
	private Uom aPack;
	private Uom aCan;
	private Uom anOilCartCan;
	
	private Uom anInch;
	private Uom aCentimeter;
	
	@Before
	public void before(){
		this.defineTestUOMs();
	}
	
	@Test
	public void testEqualityWithNullUOM(){
		Assert.assertTrue(!aCase.equals(null));
	}
	@Test
	public void testInequalityByName(){
		Assert.assertTrue(!aCase.equals(aMiniCase));
	}
	
	@Test
	public void testEqualityByName(){
		Assert.assertTrue(aCase.equals(aCase));
	}
	@Test
	public void testEqualityByNameMissingParents(){
		Assert.assertTrue(aCase4.equals(aCase5));
	}
	@Test
	public void testEqualitySameNameOneParentOneMissingParent(){
		Assert.assertTrue(!aCase.equals(aCase2));
	}
	
	@Test
	public void testEqualitySameNameUnEqualParents(){
		Assert.assertTrue(!aCase2.equals(aCase3));
	}
	
	@Test
	public void testToString(){ 
		String s = aBottleEach.toString();
		Assert.assertTrue(s.equals("[2.0 Bottle Each make 1 Mini Case, [6.0 Mini Case make 1 Inner Case, [4.0 Inner Case make 1 Case, [1.0 Case make 1 TOP-LEVEL-UOM, ]]]]"));
		
		s = anInnerCase.toString();
		Assert.assertTrue(s.equals("[4.0 Inner Case make 1 Case, [1.0 Case make 1 TOP-LEVEL-UOM, ]]"));
		
		s = aCase.toString();
		Assert.assertTrue(s.equals("[1.0 Case make 1 TOP-LEVEL-UOM, ]"));
		
	}
	
	@Test 
	public void testFindFactorInnerCaseToInnerCaseDistance(){
		double d = anInnerCase.findFactor(anInnerCase);
		Assert.assertTrue(d == 1.0);
	}	
	
	@Test
	public void testFindFactorInnerCaseToBottleEachDistance(){ 
		double d = anInnerCase.findFactor(aBottleEach);
		Assert.assertTrue(d == 12.0);
	}
	 
	@Test
	public void testFindFactorCaseToBottleEachDistance(){
		double d = aCase.findFactor(aBottleEach);
		Assert.assertTrue(d == 48.0);
	}	

	@Test
	public void testFindFactorCaseToInnerCaseDistance(){
		double d = aCase.findFactor(anInnerCase);
		Assert.assertTrue(d == 4.0);
	}
	
	@Test (expected = IncompatibleUOMException.class)
	public void testFindFactorWithIncompatibleUOMs(){
		anInnerCase.findFactor(aCan);
	}
	
	@Test
	public void testCaseToBottleEachDistance(){
		double d = aCase.findFactor(aBottleEach);
		Assert.assertTrue(d == 48.0);
	}
	
	
	@Test (expected = IncompatibleUOMException.class)
	public void testBottleEachToCaseDistance(){
		aBottleEach.findFactor(aCase);
	}
	
	@Test
	public void testStaticFindFactorCaseToCase(){
		double d = Uom.findFactor(aCase, aCase);
		Assert.assertTrue(d == 1.0);
	}
	
	@Test
	public void testStaticFindFactorCaseToBottleEach(){
		double d = Uom.findFactor(aCase, aBottleEach);
		Assert.assertTrue(d == 48.0);
	}
	
	@Test
	public void testStaticFindFactorBottleEachToCase(){
		double d = Uom.findFactor(aBottleEach, aCase);
		Assert.assertTrue(d == (1.0/48.0));
	}
	
	@Test
	public void testStaticFindFactorBottleEachToBottleEach(){
		double d = Uom.findFactor(aBottleEach, aBottleEach);
		Assert.assertTrue(d == 1.0);
	}
	
	@Test
	public void testStaticFindFactorInnerCaseToMiniCase(){
		double d = Uom.findFactor(anInnerCase, aMiniCase);
		Assert.assertTrue(d == 6.0);
	}
	
	@Test
	public void testStaticFindFactorMiniCaseToInnerCase(){
		double d = Uom.findFactor(aMiniCase, anInnerCase);
		Assert.assertTrue(d == (1.0/6.0));
	}
	
	@Test
	public void testCentimetersInInches(){
		double d = Uom.findFactor(anInch, aCentimeter);
		Assert.assertTrue(d == 2.54);
	}
	
	@Test
	public void testInchesInCentimeters(){
		double d = Uom.findFactor(aCentimeter, anInch );
		Assert.assertTrue(d == (1.0/2.54));
	}
	
	@Test
	public void testCaseIsLargerThanBottleEach(){
		boolean boo = aCase.isLargerThan(aBottleEach);
		Assert.assertTrue(boo);
	}
	
	@Test (expected = IncompatibleUOMException.class)
	public void testCaseIsLargerThanBottleEachIncompatible(){
		anInch.isLargerThan(aBottleEach);
	}
	
	@Test (expected = IncompatibleUOMException.class)
	public void testCaseIsSmallerThanBottleEachIncompatible(){
		anInch.isSmallerThan(aBottleEach);
	}
	
	@Test
	public void testCaseIsNotLargerThanCase(){
		boolean boo = aCase.isLargerThan(aCase);
		Assert.assertTrue(!boo);
	}
	
	@Test
	public void testMiniCaseIsNotLargerThanMiniCase(){
		boolean boo = aMiniCase.isLargerThan(aMiniCase);
		Assert.assertTrue(!boo);
	}

	@Test
	public void testBottleEachIsSmallerThanCase(){
		boolean boo = aBottleEach.isSmallerThan(aCase);
		Assert.assertTrue(boo);
	}


	@Test
	public void testBottleEachIsNotLargerThanCase(){
		boolean boo = aBottleEach.isLargerThan(aCase);
		Assert.assertTrue(!boo);
	}

	@Test
	public void testBottleEachIsLargerThanBottleEach(){
		boolean boo = aBottleEach.isLargerThan(aBottleEach);
		Assert.assertTrue(!boo);
	}
	
	@Test
	public void testBottleEachIsSmallerThanBottleEach(){
		boolean boo = aBottleEach.isSmallerThan(aBottleEach);
		Assert.assertTrue(!boo);
	}

	@Test
	public void testCaseIsNotSmallerThanABottleEach(){
		boolean boo = aCase.isSmallerThan(aBottleEach);
		Assert.assertTrue(!boo);
	}
	
	@Test
	public void testTopLevelParentWhenSame(){
		Uom fred = Uom.getTopLevelParent(anInnerCase);
		Uom alan = Uom.getTopLevelParent(aMiniCase);
		Assert.assertNotNull(fred);
		Assert.assertTrue(fred.equals(alan));
	}
	
	@Test
	public void testTopLevelParentWhenDifferent(){
		Uom fred = Uom.getTopLevelParent(anInnerCase);
		Uom alan = Uom.getTopLevelParent(anOilCartCan);
		Assert.assertNotNull(fred);
		Assert.assertTrue(!fred.equals(alan));
	}
	
	@Test
	public void testSameFamily(){
		Assert.assertTrue(aMiniCase.isInSameFamily(anInnerCase));
		Assert.assertTrue(anInnerCase.isInSameFamily(aMiniCase));
	}
	
	@Test
	public void testDifferentFamilySameDimension(){
		Assert.assertTrue(!anInnerCase.isInSameFamily(anOilCartCan));
		Assert.assertTrue(!anOilCartCan.isInSameFamily(anInnerCase));
	}	
	
	@Test
	public void testDifferentFamilyDifferentDimension(){
		Assert.assertTrue(!anInch.isInSameFamily(anOilCartCan));
	}	
	
	@Test
	public void testEquivalencyBottleEachAndFlaskEach(){
		Assert.assertTrue(aBottleEach.equivalent(aFlaskEach));
	}
	
	@Test
	public void testEquivalencyCaseAndFakeCaseNoParent(){
		Assert.assertTrue(aCase.equivalent(aFakeCase));
	}
	
	@Test
	public void testEquivalencyBottleEachAndGobletEach(){
		Assert.assertTrue(!aBottleEach.equivalent(aGobletEach));
	}

	
	@Test
	public void testEquivalencyCaseAndFakeCaseDiffererntFactorNoParent(){
		Assert.assertTrue(!aCase.equivalent(yetAnotherFakeCase)); 
	}
	
	private void defineTestUOMs(){
		aCase = new Uom("Case", null, 1.0, "Bottle Case", UomDimension.QUANTITY); // a UOM 
		aFakeCase = new Uom("FakeCase", null, 1.0, "Bottle Case",  UomDimension.QUANTITY);  //Different name but same factor
		yetAnotherFakeCase = new Uom("FakeCase", null, 2.0, "Bottle Case",  UomDimension.QUANTITY);  //Different name and different factor
		
		anInnerCase = new Uom("Inner Case", aCase, 4.0, "Inner Case",  UomDimension.QUANTITY);
		aMiniCase = new Uom("Mini Case", anInnerCase, 6.0, "Mini Case",  UomDimension.QUANTITY);
		aBottleEach = new Uom("Bottle Each", aMiniCase, 2.0, "Bottle Each",  UomDimension.QUANTITY ); //A UOM
		aFlaskEach = new Uom("Flask Each", aMiniCase, 2.0, "Flask Each",  UomDimension.QUANTITY ); //Different name, same factor
		aGobletEach = new Uom("Flask Each", aMiniCase, 3.0, "Goblet Each",  UomDimension.QUANTITY ); //Different name and different factor
		
		aPack = new Uom("Pack", null, 1.0, "Round Pack",  UomDimension.QUANTITY);
		aCan = new Uom ("Can", aPack, 6.0, "Cannister", UomDimension.QUANTITY);
		anOilCartCan = new Uom("Oil Cart Can", aCan, 4.0, "OQC",  UomDimension.QUANTITY); 
		
		aCase2 = new Uom("Case", aPack, 1.0, "Bottle Case",  UomDimension.QUANTITY);
		aCase3 = new Uom("Case", aMiniCase, 1.0, "Bottle Case",  UomDimension.QUANTITY);

		aCase4 = new Uom("Case", null, 1.0, "Bottle Case",  UomDimension.QUANTITY);
		aCase5 = new Uom("Case", null, 1.0, "Bottle Case",  UomDimension.QUANTITY);
		
		anInch = new Uom("Inch", null, 1.0, "Inch",  UomDimension.DISTANCE);
		aCentimeter = new Uom("cm", anInch, 2.54, "Centimeter",  UomDimension.DISTANCE);
	}
}
