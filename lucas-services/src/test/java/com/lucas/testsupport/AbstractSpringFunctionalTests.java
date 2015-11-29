package com.lucas.testsupport;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * This class encapsulates functionality needed for tests using the Spring test runner
 * @author Pankaj Tandon
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/spring/services-bootstrap-context.xml"})
public abstract class AbstractSpringFunctionalTests {


}
