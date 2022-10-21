package me.bega.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidatorTest {
    @Test
    public void validate_SUCCESS(){
        Assertions.assertTrue(Validator.validateDocument("Gem.xml", "Gem.xsd"));
    }

    @Test
    public void validate_FAILURE(){
        Assertions.assertFalse(Validator.validateDocument("Gem1.xml", "aaa.xsd"));
        Assertions.assertFalse(Validator.validateDocument("aaa.xml", "Gem1.xsd"));
        Assertions.assertFalse(Validator.validateDocument("GEem.xml", "GAD.xsd"));
    }
}
