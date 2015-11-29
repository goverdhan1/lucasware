package com.lucas.services.uom;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.lucas.entity.ItemTuple;
import com.lucas.entity.uom.Uom;
import com.lucas.entity.uom.UomDimension;
import com.lucas.exception.UomAlreadyExistsException;
import com.lucas.services.util.CollectionsUtilService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/spring/services-bootstrap-context.xml"})
@TransactionConfiguration(transactionManager="resourceTransactionManager", defaultRollback = true)
public class UomServiceFunctionalTests {
	
	@Inject
	UomService uomService;
	 
	private Uom aCase; 
	private Uom anInnerCase;
	private Uom aMiniCase;
	private Uom aBottleEach;
	private Uom aPack;
	private Uom aCan;
	private Uom anOilCartCan;
	private Uom anInch;
	private Uom aCentimeter;
	
	private List<Uom> testUomList = new ArrayList<Uom>();
	
	
	@Before
	public void before(){
		this.defineTestUOMs();
	}
	

	@Test
	public void testInsertion() {
		int found = 0;
		this.createTestUOMs();
		List<Uom> uomList = uomService.getUomList();
		for (Uom uom2 : CollectionsUtilService.nullGuard(uomList)) {
			if (contains(testUomList, uom2)){
				found++;
			}
		}
		Assert.assertTrue(found == 9);

	}
	
	@Test
	public void testRetrieval(){
		this.createTestUOMs();
		Uom retrievedUom = uomService.findUomByName("Case");
		Assert.assertNotNull(retrievedUom);
		Assert.assertTrue(retrievedUom.equals(aCase));
	}

	
	@Test (expected = UomAlreadyExistsException.class)
	public void testInsertionOfExistingUom(){
		this.createTestUOMs();
		Uom aNewCase = new Uom("Case", null, 1.0, "Bottle Case",  UomDimension.QUANTITY);
		uomService.createUom(aNewCase);
	}
	
	/**
	 * Test how many miniCases in 2 BottleEaches
	 */
	@Test
	public void testBottleEachTupleWithMiniCases(){
		this.createTestUOMs();
		ItemTuple largerItemTuple = new ItemTuple(3L, aBottleEach);
		double d = uomService.findFactor(largerItemTuple, aMiniCase);
		Assert.assertTrue(d == 1.5);
	}
	
	/**
	 * Test how many bottleEaches in 6 MiniCase tuples
	 */
	@Test
	public void testMiniCaseTupleWithBottleEach(){
		this.createTestUOMs();
		ItemTuple largerItemTuple = new ItemTuple(6L, aMiniCase);
		double d = uomService.findFactor(largerItemTuple, aBottleEach);
		Assert.assertTrue(d == 12);
	}
	
	/**
	 * Test how many innerCase in 6 BottleEach tuples
	 */
	@Test
	public void testBottleEachTupleWithInnerCase(){
		this.createTestUOMs();
		ItemTuple largerItemTuple = new ItemTuple(6L, aBottleEach);
		double d = uomService.findFactor(largerItemTuple, anInnerCase);
		Assert.assertTrue(d == 0.5);
	}
	
	@After
	public void after() {
		this.deleteTestUOMs();
	}
	
	private void defineTestUOMs(){
		aCase = new Uom("Case", null, 1.0, "Bottle Case",  UomDimension.QUANTITY);
		anInnerCase = new Uom("Inner Case", aCase, 4.0, "Inner Case",  UomDimension.QUANTITY);
		aMiniCase = new Uom("Mini Case", anInnerCase, 6.0, "Mini Case",  UomDimension.QUANTITY);
		aBottleEach = new Uom("Bottle Each", aMiniCase, 2.0, "Bottle Each", UomDimension.QUANTITY);
		aPack = new Uom("Pack", null, 1.0, "Round Pack",  UomDimension.QUANTITY);
		aCan = new Uom ("Can", aPack, 6.0, "Cannister",  UomDimension.QUANTITY);
		anOilCartCan = new Uom("Oil Cart Can", aCan, 4.0, "OQC",  UomDimension.QUANTITY); 
		
		anInch = new Uom("Inch", null, 1.0, "Inch",  UomDimension.DISTANCE);
		aCentimeter = new Uom("cm", anInch, 2.54, "Centimeter",  UomDimension.DISTANCE);
		
		testUomList.add(aCase);
		testUomList.add(anInnerCase);
		testUomList.add(aMiniCase);
		testUomList.add(aBottleEach);
		testUomList.add(aPack);
		testUomList.add(aCan);
		testUomList.add(anOilCartCan);
		
		testUomList.add(anInch);
		testUomList.add(aCentimeter);
	}
	
	private void createTestUOMs(){		
		for (Uom  uom : CollectionsUtilService.nullGuard(testUomList)) {
			uomService.createUom(uom);
		}
	}
	
	private void deleteTestUOMs(){
		//Flip the list to prevent FK issues.
		List <Uom> flippedList = new ArrayList<Uom>();
		int j = testUomList.size();
		for (int i = 0; i < testUomList.size(); i++) {
			flippedList.add(testUomList.get(--j));
		}
		for (Uom  uom : CollectionsUtilService.nullGuard(flippedList)) {
			uomService.deleteUom(uom.getId());
		}
		
	}
	
	private boolean contains(List<Uom> testList, Uom currentUom){  
		boolean boo = false;
		for (Uom uom : CollectionsUtilService.nullGuard(testList)) {
			if (currentUom.equals(uom)){
				boo = true;
				break;
			}
		}
		return boo;
	}
}
