package ru.ermolnik.filmapp.shared_ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.ermolnik.filmapp.domain.model.Genre

@Composable
fun CategoriesItem(category: Genre, onclick: (Genre) -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        onClick = {
            onclick.invoke(category)
        }) {
        Text(
            text = category.name, modifier = Modifier
                .border(1.dp, Color.Black, CircleShape)
                .padding(horizontal = 15.dp, vertical = 4.dp)
        )
    }
}