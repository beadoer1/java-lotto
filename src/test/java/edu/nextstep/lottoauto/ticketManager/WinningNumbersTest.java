package edu.nextstep.lottoauto.ticketManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class WinningNumbersTest {

    @Test
    void from_숫자_6개_성공() {
        // given
        String numbersOfString = "1, 2, 3, 4, 5, 6";
        // when
        WinningNumbers winningNumbers = WinningNumbers.from(numbersOfString);
        // then
        assertThat(winningNumbers.getWinningNumbers()).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @ParameterizedTest(name = "from_숫자_6개_실패 {0}")
    @ValueSource(strings = {"1, 2, 3, 4, 5", "1, 2, 3, 4, 5, 6, 7"}) // given
    void from_숫자_6개_실패(String numbersOfString) {
        // when, then
        assertThatThrownBy(() -> WinningNumbers.from(numbersOfString))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest(name = "from_범위_밖_숫자_실패 {0}")
    @ValueSource(strings = {"-1, 1, 3, 4, 5, 45", "0, 1, 3, 4, 5, 45", "1, 3, 4, 5, 45, 46"}) // given
    void from_범위_밖_숫자_실패(String numbersOfString) {
        // when, then
        assertThatThrownBy(() -> WinningNumbers.from(numbersOfString))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void from_중복_실패() {
        // given
        String numbersOfString = "1, 1, 3, 4, 5, 6";
        // when, then
        assertThatThrownBy(() -> WinningNumbers.from(numbersOfString))
                .isInstanceOf(IllegalArgumentException.class);
    }
}