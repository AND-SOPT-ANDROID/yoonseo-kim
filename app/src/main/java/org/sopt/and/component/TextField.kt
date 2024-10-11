@file:OptIn(ExperimentalMaterial3Api::class)

package org.sopt.and.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(id: String, onValueChange: (String) -> Unit, hint: String) {
    OutlinedTextField(
        value = id,
        onValueChange = onValueChange,
        placeholder = { Text(hint) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFF2F2F2F),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
        )
    )
}

@Composable
fun PasswordTextField(password: String, onValueChange: (String) -> Unit, hint: String) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = onValueChange,
        placeholder = { Text(hint) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            Text(
                text = if (passwordVisible) "hide" else "show",
                color = Color.White,
                modifier = Modifier
                    .clickable { passwordVisible = !passwordVisible }
                    .padding(8.dp)
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFF2F2F2F),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray
        )
    )
}
