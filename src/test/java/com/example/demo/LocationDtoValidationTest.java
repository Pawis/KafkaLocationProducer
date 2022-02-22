package com.example.demo;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.model.dto.LocationDto;


@ExtendWith(SpringExtension.class)
public class LocationDtoValidationTest {
	
	private static Validator validator;
	
	@BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	
	@Test
	void latitiude_cannto_be_null() {
		
		LocationDto locatiobDto = new LocationDto("1",null,1);
		
		Set<ConstraintViolation<LocationDto>> constraintViolations =
                validator.validate( locatiobDto );
		
		Assertions.assertEquals(1, constraintViolations.size() );
		Assertions.assertEquals( "must not be null", constraintViolations.iterator().next().getMessage());
		
	}
	
	@Test
	void longitude_cannto_be_null() {

		LocationDto locatiobDto = new LocationDto("1",1,null);

		Set<ConstraintViolation<LocationDto>> constraintViolations =
				validator.validate( locatiobDto );

		Assertions.assertEquals(1, constraintViolations.size() );
		Assertions.assertEquals( "must not be null", constraintViolations.iterator().next().getMessage());
		
	}
	
	@Test
	void deviceId_must_be_positive() {

		LocationDto locatiobDto = new LocationDto("-1",1,1);

		Set<ConstraintViolation<LocationDto>> constraintViolations =
				validator.validate( locatiobDto );

		Assertions.assertEquals(1, constraintViolations.size() );
		Assertions.assertEquals( "must be between 0 and 9999999", constraintViolations.iterator().next().getMessage());
		
	}
	
	@Test
	void latitiude_must_be_shother_than_7_numbers() {
		
		LocationDto locatiobDto = new LocationDto("1",12345678,1);

		Set<ConstraintViolation<LocationDto>> constraintViolations =
				validator.validate( locatiobDto );

		Assertions.assertEquals(1, constraintViolations.size() );
		Assertions.assertEquals( "numeric value out of bounds (<7 digits>.<0 digits> expected)", constraintViolations.iterator().next().getMessage());
		
	}
	
	@Test
	void longitude_must_be_shother_than_7_numbers() {
		
		LocationDto locatiobDto = new LocationDto("1",1,12345678);

		Set<ConstraintViolation<LocationDto>> constraintViolations =
				validator.validate( locatiobDto );

		Assertions.assertEquals(1, constraintViolations.size() );
		Assertions.assertEquals( "numeric value out of bounds (<7 digits>.<0 digits> expected)", constraintViolations.iterator().next().getMessage());
		
	}
	

}


