package com.patriciafiona.tentangku.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.data.source.local.entity.FinanceTransaction
import com.patriciafiona.tentangku.navigation.TentangkuScreen
import com.patriciafiona.tentangku.ui.main.ui.theme.Green
import com.patriciafiona.tentangku.utils.Utils

@Composable
fun ItemFinance (
    navController: NavController,
    data: FinanceTransaction
) {
    Card(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 5.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(TentangkuScreen.FinanceAddUpdateScreen.route)
                navController.currentBackStackEntry?.arguments?.putParcelable("transaction", data)
            }
    ) {
        Row(
            modifier = Modifier.padding(20.dp)
        ) {
            Image(
                modifier = Modifier.weight(.2f),
                painter =
                if(data.type == "Income"){
                    painterResource(id = R.drawable.income_icon)
                } else{
                    painterResource(id = R.drawable.outcome_icon)
                },
                contentDescription = "transaction icon status"
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .weight(.4f)
            ) {
                Text(
                    text = data.type.toString(),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = data.date.toString(),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Gray,
                    )
                )

                Text(
                    text = data.description.toString(),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black,
                    )
                )
            }

            val nominalTransaction = if(data.nominal != null) { data.nominal } else { 0.0 }
            Text(
                modifier = Modifier.weight(.4f),
                text = Utils.setRupiahFormat(nominalTransaction!!),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = if(data.type == "Income"){
                        Green
                    } else{
                        Color.Red
                    },
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}