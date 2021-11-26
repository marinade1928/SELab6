package com.unittest.codecoverage.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unittest.codecoverage.exceptions.PersonException;
import com.unittest.codecoverage.models.Gender;
import com.unittest.codecoverage.models.Person;
import com.unittest.codecoverage.models.validators.PersonValidator;
import com.unittest.codecoverage.services.impl.PersonServiceImpl;
import com.unittest.codecoverage.repositories.PersonRepository;
import com.unittest.codecoverage.services.PersonService;
import com.unittest.codecoverage.services.impl.PersonServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
	
	@InjectMocks
	PersonService service = new PersonServiceImpl();
	@Mock
	PersonRepository repository;
	
	@Test
	public void testInsert_shouldInsertPersonWithSuccessWhenAllPersonsInfoIsFilled() {
		Person person = new Person();
		person.setName("Name");
		person.setAge(21);
		person.setGender(Gender.M);
		
		when(repository.insert(any(Person.class))).thenReturn(person);
		
		service.insert(person);
	}
	
	@Test
	public void testInsert_shouldThrowPersonExceptionWhenPersonIsNull() {
		
		List<String> expectedErrors = Lists.newArrayList("Name is required", "Gender is required");
		String expectedMessage = String.join(";", expectedErrors);
		Person person = null;
		
		assertThatThrownBy(() -> service.insert(person))
			.isInstanceOf(PersonException.class)
			.hasFieldOrPropertyWithValue("errors", expectedErrors)
			.hasMessage(expectedMessage);
	}
	
	@Test
	public void testInsert_shouldThrowPersonExceptionWhenPersonNameIsNull() {
		
		List<String> expectedErrors = Lists.newArrayList("Name is required");
		String expectedMessage = String.join(";", expectedErrors);
		Person person = new Person();
		person.setGender(Gender.M);
		
		assertThatThrownBy(() -> service.insert(person))
			.isInstanceOf(PersonException.class)
			.hasFieldOrPropertyWithValue("errors", expectedErrors)
			.hasMessage(expectedMessage);
	}
	
	@Test
	public void testInsert_shouldThrowPersonExceptionWhenPersonNameIsBlank() {
		
		List<String> expectedErrors = Lists.newArrayList("Name is required");
		String expectedMessage = String.join(";", expectedErrors);
		Person person = new Person();
		person.setGender(Gender.M);
		person.setName(" ");
		
		assertThatThrownBy(() -> service.insert(person))
			.isInstanceOf(PersonException.class)
			.hasFieldOrPropertyWithValue("errors", expectedErrors)
			.hasMessage(expectedMessage);
	}
	
	@Test
	public void testInsert_shouldThrowPersonExceptionWhenPersonGenderIsNull() {
		
		List<String> expectedErrors = Lists.newArrayList("Gender is required");
		String expectedMessage = String.join(";", expectedErrors);
		Person person = new Person();
		person.setName("Name");
		person.setGender(null);

		assertThatThrownBy(() -> service.insert(person))
			.isInstanceOf(PersonException.class)
			.hasFieldOrPropertyWithValue("errors", expectedErrors)
			.hasMessage(expectedMessage);
	}

	@Test
	public void testGetAge_Person_and_testRequiredName_PersonValidator() {
		Person p1 = new Person();
		p1.setName("TestNameForP1");
		p1.setAge(23);
		Person p2 = new Person();
		p2.setName("TestNameForP2");
		p2.setAge(23);

		PersonValidator personValidator = new PersonValidator();
		personValidator.requiredName(p1.getName());
		personValidator.requiredName(p2.getName());

		assertEquals(p1.getAge(), p2.getAge());
	}

	@Test
	public void testInsert_PersonRepository_initialize_PersonException(){
		Person p1 = new Person();
		p1.setName("TestNameForP1");
		Person p2 = new Person();
		p2.setName("TestNameForP2");

		PersonRepository personRepository = new PersonRepository();
		personRepository.insert(p1);
		personRepository.insert(p2);

		PersonException personException = new PersonException("Err");

		assertEquals(personRepository.get(p1.getName()), personRepository.get(p2.getName()));
	}

	@Test
	public void testGet_PersonServiceImpl(){
		Person p1 = new Person();
		p1.setName("TestNameForP1");
		p1.setGender(Gender.F);
		p1.setAge(50);
		Person p2 = new Person();
		p2.setName("TestNameForP2");
		p2.setGender(Gender.M);

		PersonServiceImpl personServiceimpl = new PersonServiceImpl();
		personServiceimpl.insert(p1);
		personServiceimpl.insert(p2);

		assertEquals(personServiceimpl.get(p1.getName()), personServiceimpl.get(p2.getName()));
	}






}
