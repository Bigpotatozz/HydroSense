package com.oscar.hydrosense.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oscar.hydrosense.theme.funnelSans


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalizedTextField(modifier: Modifier, value: String, icono: ImageVector, placeholder: String, onValueChange: (String) -> Unit){

    OutlinedTextField(value = value, onValueChange = onValueChange , placeholder = {
        Row(verticalAlignment = Alignment.CenterVertically){

            Text(text = placeholder,
                style = TextStyle( fontSize = 10.sp, fontWeight = FontWeight.Light, fontFamily = funnelSans))
        } },
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth().height(51.dp),
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = icono,
                contentDescription = placeholder)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF1A2130),
            unfocusedBorderColor = Color.Black,
            errorBorderColor = Color.Red
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalizedNumberField(modifier: Modifier, value: String, icono: ImageVector, placeholder: String, onValueChange: (String) -> Unit){

    OutlinedTextField(value = value, onValueChange = onValueChange , placeholder = {
        Row(verticalAlignment = Alignment.CenterVertically){

            Text(text = placeholder,
                style = TextStyle( fontSize = 15.sp, fontWeight = FontWeight.Light, fontFamily = funnelSans))
        } },
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth().height(51.dp),
        leadingIcon = {
            Icon(imageVector = icono,
                contentDescription = placeholder)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF1A2130),
            unfocusedBorderColor = Color.Black,
            errorBorderColor = Color.Red
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy( keyboardType = KeyboardType.Number)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalizedDropdownField(modifier: Modifier, icono: ImageVector, value: String, placeholder: String, trailingIcon: Boolean, onValueChange: (String) -> Unit){

    OutlinedTextField(value = value, onValueChange = onValueChange , placeholder = {
        Row(verticalAlignment = Alignment.CenterVertically){
            Text(text = placeholder,
                style = TextStyle( fontSize = 15.sp, fontWeight = FontWeight.Light, fontFamily = funnelSans))
        } },
        leadingIcon = {
            Icon(imageVector = icono,
                contentDescription = placeholder)
        },
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth().height(51.dp),
        readOnly = true,
        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(trailingIcon)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF1A2130),
            unfocusedBorderColor = Color.Black,
            errorBorderColor = Color.Red
        ),
        keyboardOptions = KeyboardOptions.Default.copy( keyboardType = KeyboardType.Number)
    )


}
