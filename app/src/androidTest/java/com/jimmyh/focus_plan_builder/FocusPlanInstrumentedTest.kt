package com.jimmyh.focus_plan_builder

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class FocusPlanScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun setContent() {
        composeTestRule.setContent {
            FocusPlanRoute()
        }
    }

    @Test
    fun blankSubject_buttonDisabled() {
        setContent()
        composeTestRule.onNodeWithText("Available minutes (10-180)").performTextInput("25")
        composeTestRule.onNodeWithText("Create plan").assertIsNotEnabled()
    }

    @Test
    fun nonNumericMinutes_buttonDisabledAndNoCrash() {
        setContent()
        composeTestRule.onNodeWithText("Study subject").performTextInput("Kotlin")
        composeTestRule.onNodeWithText("Available minutes (10-180)").performTextInput("abc")
        composeTestRule.onNodeWithText("Create plan").assertIsNotEnabled()
    }

    @Test
    fun tenMinutes_quickReviewFiveMinuteBreak() {
        setContent()
        composeTestRule.onNodeWithText("Study subject").performTextInput("Kotlin")
        composeTestRule.onNodeWithText("Available minutes (10-180)").performTextInput("10")
        composeTestRule.onNodeWithText("Create plan").assertIsEnabled().performClick()

        composeTestRule.onNodeWithText("Category: Quick review").assertIsDisplayed()
        composeTestRule.onNodeWithText("Recommended break: 5 minutes").assertIsDisplayed()
    }

    @Test
    fun sixtyOneMinutes_extendedSessionFifteenMinuteBreak() {
        setContent()
        composeTestRule.onNodeWithText("Study subject").performTextInput("Kotlin")
        composeTestRule.onNodeWithText("Available minutes (10-180)").performTextInput("61")
        composeTestRule.onNodeWithText("Create plan").assertIsEnabled().performClick()

        composeTestRule.onNodeWithText("Category: Extended session").assertIsDisplayed()
        composeTestRule.onNodeWithText("Recommended break: 15 minutes").assertIsDisplayed()
    }

    @Test
    fun resultCard_disappearsWhenInputChangesAfterPlanCreated() {
        setContent()
        composeTestRule.onNodeWithText("Study subject").performTextInput("Kotlin")
        composeTestRule.onNodeWithText("Available minutes (10-180)").performTextInput("45")
        composeTestRule.onNodeWithText("Create plan").performClick()

        composeTestRule.onNodeWithText("Category: Focused session").assertIsDisplayed()

        composeTestRule.onNodeWithText("Study subject").performTextInput(" Advanced")

        composeTestRule.onNodeWithText("Category: Focused session").assertDoesNotExist()
    }

    @Test
    fun erasingMinutes_afterEntry_disablesButtonWithoutCrash() {
        setContent()
        composeTestRule.onNodeWithText("Study subject").performTextInput("Kotlin")
        val minutesField = composeTestRule.onNodeWithText("Available minutes (10-180)")
        minutesField.performTextInput("45")
        minutesField.performTextClearance()

        composeTestRule.onNodeWithText("Create plan").assertIsNotEnabled()
    }
}