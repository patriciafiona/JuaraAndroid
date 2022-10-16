package com.patriciafiona.mycity.ui

import android.app.Activity
import android.media.tv.TvContract
import android.provider.ContactsContract
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.patriciafiona.mycity.data.model.Data
import com.patriciafiona.mycity.R
import com.patriciafiona.mycity.data.MenuType

@Composable
fun ListOnlyContent(
    uiState: UiState,
    onCardPressed: (Data) -> Unit,
    modifier: Modifier = Modifier
) {
    val datas = uiState.currentMenuData

    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
        item {
            HomeTopBar(modifier = Modifier.fillMaxWidth())
        }
        items(datas, key = { email -> email.id }) { data ->
            ListItem(
                data = data,
                onCardClick = {
                    onCardPressed(data)
                }
            )
        }
    }
}

@Composable
fun ListAndDetailContent(
    uiState: UiState,
    onCardPressed: (Data) -> Unit,
    modifier: Modifier = Modifier
) {
    val datas = uiState.currentMenuData

    Row(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .weight(0.7f)
                .padding(end = 16.dp, top = 20.dp)
        ) {
            items(datas, key = { data -> data.id }) { data ->
                ListItem(
                    data = data,
                    onCardClick = {
                        onCardPressed(data)
                    }
                )
            }
        }
        val activity = LocalContext.current as Activity
        DetailsScreen(
            uiState = uiState,
            modifier = Modifier.weight(1.3f),
            onBackPressed = { activity.finish() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItem(
    data: Data,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(vertical = 4.dp),
        onClick = onCardClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            ItemHeader(data)
            Text(
                text = data.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
            )
            Text(
                text = data.desc,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun ItemHeader(data: Data, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        val iconResource: Int = if(data.type == MenuType.Museum){
            R.drawable.icon_museum
        }else{
            R.drawable.icon_shopping
        }

        Image(
            painter = painterResource(id = iconResource),
            contentDescription = "Menu Type Icon",
            modifier = Modifier.size(40.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if(data.category != null) {
                Text(
                    text = data.category,
                    style = MaterialTheme.typography.labelMedium
                )
            }else{
                Text(
                    text = "Shopping Mall",
                    style = MaterialTheme.typography.labelMedium
                )
            }

            if (data.established != null) {
                Text(
                    text = data.established,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }else{
                Text(
                    text = "Jakarta, Indonesia",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
private fun HomeTopBar(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        AppLogo(
            modifier = Modifier
                .size(64.dp)
                .padding(start = 4.dp)
        )
//        ProfileImage(
//            drawableResource = LocalAccountsDataProvider.userAccount.avatar,
//            description = stringResource(R.string.profile),
//            modifier = Modifier
//                .padding(end = 8.dp)
//                .size(48.dp)
//        )
    }
}