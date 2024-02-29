package org.exam;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class InstanceNullTest {

    @Test
    void test() {
        Object a = null;
        assertThatCode(() -> {
            // 이 부분에서 a instanceof Integer를 실행하고 있으며, 이는 NullPointerException을 발생시키지 않아야 합니다.
            boolean result = a instanceof Integer;
            assertThat(result).isEqualTo(false);
            // 여기서 result 값을 사용할 수 있지만, 이 테스트의 목적은 예외 발생 여부를 확인하는 것이므로 따로 사용하지 않습니다.
        }).doesNotThrowAnyException();
    }
}
