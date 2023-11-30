package com.codewars.kyu.six;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CountingDuplicatesTest {

    @Test
    void three() {
        String input = "aaA233";
        int actual = CountingDuplicates.duplicateCount(input);

        assertThat(actual).isEqualTo(2);
    }

    @Test
    @DisplayName("more than one")
    void mix() {
        String input = "aA11";
        int actual = CountingDuplicates.duplicateCount(input);

        assertThat(actual).isEqualTo(2);
    }


    @Test
    @DisplayName("no duplicate")
    void noMix() {
        String input = "abcdefg";
        int actual = CountingDuplicates.duplicateCount(input);

        assertThat(actual).isEqualTo(0);
    }
}

