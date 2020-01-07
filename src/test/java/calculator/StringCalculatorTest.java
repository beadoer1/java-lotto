package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class StringCalculatorTest {

    private StringCalculator stringCalculator;

    @BeforeEach
    void setUp() {
        stringCalculator = new StringCalculator();
    }

    @Test
    void add_null_or_empty() {
        assertThat(stringCalculator.splitAndSum(null)).isEqualTo(0);
        assertThat(stringCalculator.splitAndSum("")).isEqualTo(0);
    }

    @Test
    void add_input_one_argument() {
        assertThat(stringCalculator.splitAndSum("1")).isEqualTo(1);
    }

    @Test
    void add_input_string_with_colon() {
        assertThat(stringCalculator.splitAndSum("1:2:3")).isEqualTo(6);
    }

    @Test
    void add_input_string_with_comma() {
        assertThat(stringCalculator.splitAndSum("1,2,3")).isEqualTo(6);
    }

    @Test
    void add_custom_separator() {
        assertThat(stringCalculator.splitAndSum("//;\n1;2;3")).isEqualTo(6);
    }

    @Test
    void add_custom_separator_and_comma_colon() {
        assertThat(stringCalculator.splitAndSum("//;\n1;2:3,4")).isEqualTo(10);
    }

    @Test
    void validate() {
        assertThatIllegalArgumentException().isThrownBy(() -> stringCalculator.splitAndSum("-1,4,4"));
    }
}
