package org.motechproject.simpleemr.service.it;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.motechproject.simpleemr.domain.Person;
import org.motechproject.simpleemr.repository.PersonDataService;
import org.motechproject.simpleemr.repository.PatientDataService;
import org.motechproject.simpleemr.service.PersonService;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.motechproject.testing.osgi.container.MotechNativeTestContainerFactory;
import org.ops4j.pax.exam.ExamFactory;
import org.motechproject.testing.osgi.BasePaxIT;
import org.ops4j.pax.exam.spi.reactors.PerSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jdo.JDODataStoreException;
import javax.jdo.JDOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


@RunWith(PaxExam.class)
@ExamReactorStrategy(PerSuite.class)
@ExamFactory(MotechNativeTestContainerFactory.class)
public class PersonServiceIT extends BasePaxIT {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private PersonService personService;
    @Inject
    private PersonDataService personDataService;
    @Inject
    private PatientDataService patientDataService;

    @Before
    public void setUp() {
        personDataService.deleteAll();
    }

    @Test
    public void testPersonService() throws Exception {

        logger.info("testPersonService");

        Person firstPerson = personDataService.create(new Person("Marge", "Simpson"));
        Person secondPerson = personDataService.create(new Person("Homer", "Simpson"));

        logger.info("Created person id {}", personDataService.getDetachedField(firstPerson, "id"));
        logger.info("Created person id {}", personDataService.getDetachedField(secondPerson, "id"));

        Person person = personService.findPersonsByName(firstPerson.getFirstName()).get(0);
        logger.info("Found person id {} : {}", personDataService.getDetachedField(person, "id"), person.toString());
        assertEquals(firstPerson, person);

        List<Person> persons = personService.getPersons();
        assertTrue(persons.contains(firstPerson));
        assertTrue(persons.contains(secondPerson));

        personService.delete(firstPerson);
        persons = personService.findPersonsByName(firstPerson.getFirstName());
        assertTrue(persons.isEmpty());
    }

    @After
    public void tearDown() {
        personDataService.deleteAll();
    }
}
