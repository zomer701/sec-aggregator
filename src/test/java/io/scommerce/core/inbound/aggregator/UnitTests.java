//package io.scommerce.core.inbound.aggregator;
//
//import io.scommerce.core.inbound.aggregator.unit.payload.UnitRequest;
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.Validation;
//import jakarta.validation.Validator;
//import jakarta.validation.ValidatorFactory;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.util.Set;
//import java.util.UUID;
//
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
//
//class UnitTests {
//
//	private static Validator validator;
//
//	@BeforeAll
//	static void setUp() {
//		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//		validator = factory.getValidator();
//	}
//
//	@Test
//	void whenAllFieldsCorrectThenValidationSucceeds() {
//		var unit =  new UnitRequest(UUID.randomUUID(), "zambezipro@gmail.com");
//		Set<ConstraintViolation<UnitRequest>> violations = validator.validate(unit);
//		assertThat(violations).isEmpty();
//	}
//
//	@Test
//	void emptyEmail() {
//		var unit =  new UnitRequest(UUID.randomUUID(), "");
//		Set<ConstraintViolation<UnitRequest>> violations = validator.validate(unit);
//		assertThat(violations).isNotEmpty();
//	}
//}
