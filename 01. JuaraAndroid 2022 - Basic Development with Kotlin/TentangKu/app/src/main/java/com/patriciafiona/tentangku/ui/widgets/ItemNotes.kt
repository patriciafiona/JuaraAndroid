package com.patriciafiona.tentangku.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.patriciafiona.tentangku.data.source.local.entity.Note
import com.patriciafiona.tentangku.navigation.TentangkuScreen
import com.patriciafiona.tentangku.ui.main.ui.theme.WhiteSmoke

@Composable
fun ItemNotes(data: Note, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                navController.navigate(TentangkuScreen.NotesAddUpdateScreen.route)
                navController.currentBackStackEntry?.arguments?.putParcelable("notes", data)
            },
        colors = CardDefaults.cardColors(
            containerColor =  WhiteSmoke,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            data.title?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = data.date.toString(),
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            )

            data.description?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        fontSize = 14.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}