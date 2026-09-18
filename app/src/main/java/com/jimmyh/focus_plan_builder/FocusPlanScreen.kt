package com.jimmyh.focus_plan_builder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FocusPlanScreen(
    subject: String,
    minutesText: String,
    plan: FocusPlan?,
    onSubjectChange: (String) -> Unit,
    onMinutesChange: (String) -> Unit,
    canCreatePlan: Boolean,
    onCreatePlan: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(24.dp)) {
        Text("Focus Plan Builder", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.screen_instructions),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = subject,
            onValueChange = onSubjectChange,
            label = { Text("Study subject") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = minutesText,
            onValueChange = onMinutesChange,
            label = { Text("Available minutes (10-180)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = onCreatePlan,
                enabled = canCreatePlan,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create plan")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (plan != null) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(plan.subject, style = MaterialTheme.typography.titleLarge)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Duration: ${plan.minutes} minutes")
                    Text("Category: ${plan.category}")
                    Text("Recommended break: ${plan.breakMinutes} minutes")

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(plan.summary)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FocusPlanScreenPreview() {
    FocusPlanScreen(
        subject = "Kotlin",
        minutesText = "45",
        plan = FocusPlan("Kotlin", 45, "Focused session", 10),
        onSubjectChange = {},
        onMinutesChange = {},
        canCreatePlan = true,
        onCreatePlan = {}
    )
}

@Composable
fun FocusPlanRoute(modifier: Modifier = Modifier) {
    var subject by rememberSaveable { mutableStateOf("") }
    var minutesText by rememberSaveable { mutableStateOf("") }
    var plan by remember { mutableStateOf<FocusPlan?>(null) }

    val minutes = minutesText.toIntOrNull()

    val canCreatePlan by remember(subject, minutes) {
        // tells Compose only recalculate when subject or minutes change
        derivedStateOf {
            subject.isNotBlank() && minutes != null && minutes in 10..180
        }
    }

    FocusPlanScreen(
        subject = subject,
        minutesText = minutesText,
        plan = plan,
        onSubjectChange = { newValue ->
            subject = newValue
            plan = null
        },
        onMinutesChange = { newValue ->
            minutesText = newValue
            plan = null
        },
        canCreatePlan = canCreatePlan,
        onCreatePlan = {
            if (subject.isNotBlank() && minutes != null && minutes in 10..180) {
                plan = FocusPlan(
                    subject = subject.trim(),
                    minutes = minutes,
                    category = durationCategory(minutes),
                    breakMinutes = recommendedBreak(minutes)
                )
            }
        },
        modifier = modifier
    )
}