package com.patriciafiona.tentangku.ui.widgets

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.data.source.local.entity.Reminder
import com.patriciafiona.tentangku.navigation.TentangkuScreen
import com.patriciafiona.tentangku.ui.main.reminder.AlarmReceiver
import com.patriciafiona.tentangku.ui.main.reminder.ReminderViewModel
import com.patriciafiona.tentangku.ui.main.ui.theme.WhiteSmoke

@Composable
fun ItemReminder(
    navController: NavController,
    data: Reminder,
    context: Context,
    reminderViewModel: ReminderViewModel
){
    val checkedState = remember { mutableStateOf(false) }
    checkedState.value = (data.status == "ON")
    val alarmReceiver = AlarmReceiver()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                navController.navigate(TentangkuScreen.ReminderAddUpdateScreen.route)
                navController.currentBackStackEntry?.arguments?.putParcelable("reminder", data)
            },
        colors = CardDefaults.cardColors(
            containerColor =  WhiteSmoke,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        shape = RoundedCornerShape(15.dp)
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(.6f)
                    .padding(20.dp)
            ) {
                data.type?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = if(data.type.equals("Daily")){ "Every ${data.time}" } else{ "${data.date.toString()} on ${data.time.toString()}" },
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

            if (data.type == "Daily") {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(.4f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = stringResource(id = R.string.reminder),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Right
                    )
                    Switch(
                        checked = checkedState.value,
                        onCheckedChange = {
                            checkedState.value = it

                            data.let { reminder ->
                                reminder.id = data.id
                                reminder.time = data.time
                                reminder.description = data.description
                                reminder.type = data.type
                                reminder.date = data.date
                            }

                            if (checkedState.value){
                                // turn on again
                                alarmReceiver.setRepeatingAlarm(context, AlarmReceiver.TYPE_REPEATING,
                                    data.time.toString(),
                                    data.description.toString(),
                                    data.id)
                            } else {
                                // turn of reminder
                                data.let {reminder ->
                                    alarmReceiver.cancelAlarm(
                                        context,
                                        reminder.id
                                    )
                                }

                                val myReminder = Reminder(
                                    id = data.id,
                                    time =  data.time,
                                    description = data.description,
                                    type = data.type,
                                    status = "ON",
                                )

                                //update database
                                reminderViewModel.update(myReminder)
                            }
                        }
                    )
                }
            }
        }
    }
}