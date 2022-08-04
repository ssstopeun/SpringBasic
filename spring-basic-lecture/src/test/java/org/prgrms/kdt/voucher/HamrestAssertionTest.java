package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import static org.junit.jupiter.api.Assertions.*;

public class HamrestAssertionTest {

    @Test
    @DisplayName("여러 hamcrest matcher 테스트")
    void hamcrestTest(){
        assertEquals(2, 1+1);
        assertThat(1+1, equalTo(2));    //actual이 2와 같다.
        assertThat(1+1, is(2));           //actual이 2다.
        assertThat(1+1, anyOf(is(1), is(2)));   //acture이 1이거나 2다.

        assertNotEquals(1,1+1);
        assertThat(1+1,not(equalTo(1)));    //actual은 1과 다르다.
    }

    @Test
    @DisplayName("컬렉션에 대한 matcher 테스트")
    void hamcrestListMatcherTest(){
        var prices = List.of(1,2,3);
        assertThat(prices,hasSize(3));  // List size가 3이다.
        assertThat(prices, everyItem(greaterThan(0)));  //List요소가 모두 0보다 크다.
        assertThat(prices, containsInAnyOrder(2,3,1)); //순서상관없이 2,3,1이 있다.
        assertThat(prices,contains(1,2,3)); // 1,2,3 순서로 list가 있다.
        assertThat(prices,hasItem(2));  //List요소 중 2가 있다.
    }
}
