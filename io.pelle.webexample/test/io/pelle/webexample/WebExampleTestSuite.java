package io.pelle.webexample;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UserDAOTest.class, UserDAOControllerTest.class })
public class WebExampleTestSuite
{

}
