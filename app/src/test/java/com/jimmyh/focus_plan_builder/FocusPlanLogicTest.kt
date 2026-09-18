package com.jimmyh.focus_plan_builder

import org.junit.Assert.*
import org.junit.Test

class FocusPlanLogicTest {

    @Test
    fun durationCategory_below10_isInvalid() {
        assertEquals("Invalid", durationCategory(9))
    }

    @Test
    fun durationCategory_10to29_isQuickReview() {
        assertEquals("Quick Review", durationCategory(10))
        assertEquals("Quick Review", durationCategory(29))
    }

    @Test
    fun durationCategory_30to60_isFocusedSession() {
        assertEquals("Focused Session", durationCategory(30))
        assertEquals("Focused Session", durationCategory(60))
    }

    @Test
    fun durationCategory_above60_isExtendedSession() {
        assertEquals("Extended Session", durationCategory(61))
        assertEquals("Extended Session", durationCategory(180))
    }

    @Test
    fun recommendedBreak_10to29_is5() {
        assertEquals(5, recommendedBreak(10))
        assertEquals(5, recommendedBreak(29))
    }

    @Test
    fun recommendedBreak_30to60_is10() {
        assertEquals(10, recommendedBreak(30))
        assertEquals(10, recommendedBreak(60))
    }

    @Test
    fun recommendedBreak_above60_is15() {
        assertEquals(15, recommendedBreak(61))
        assertEquals(15, recommendedBreak(180))
    }
}