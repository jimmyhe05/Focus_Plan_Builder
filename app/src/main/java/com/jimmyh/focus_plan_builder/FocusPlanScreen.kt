package com.jimmyh.focus_plan_builder

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
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
    Column(modifier = modifier.fillMaxWidth().padding(24.dp)) {
        Text("Focus Plan Builder", style = MaterialTheme.typography.headlineMedium)

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

        Button(
            onClick = onCreatePlan,
            enabled = canCreatePlan,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create plan")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (plan != null) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(plan.subject)
                    Text("Duration: ${plan.minutes} minutes")
                    Text("Category: ${plan.category}")
                    Text("Recommended break: ${plan.breakMinutes} minutes")
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