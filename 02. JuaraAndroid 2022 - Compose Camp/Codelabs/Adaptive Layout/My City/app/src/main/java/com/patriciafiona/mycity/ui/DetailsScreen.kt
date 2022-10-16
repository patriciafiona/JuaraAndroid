package com.patriciafiona.mycity.ui

import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.patriciafiona.mycity.R
import com.patriciafiona.mycity.data.MenuType
import com.patriciafiona.mycity.data.model.Data
import java.nio.file.Files.delete

@Composable
fun DetailsScreen(
    uiState: UiState,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    isFullScreen: Boolean = false
) {
    BackHandler {
        onBackPressed()
    }
    LazyColumn(
        modifier = modifier
            .testTag(stringResource(R.string.details_screen))
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.inverseOnSurface)
            .padding(top = 24.dp)
    ) {
        item {
            if (isFullScreen) {
                DetailsScreenTopBar(onBackPressed, uiState)
            }
            Box{
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(uiState.currentSelectedData.imageUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_image),
                    error = painterResource(id = R.drawable.ic_error),
                    contentDescription = stringResource(R.string.description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .drawWithCache {
                            val gradient = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.DarkGray),
                                startY = size.height / 3,
                                endY = size.height
                            )
                            onDrawWithContent {
                                drawContent()
                                drawRect(gradient, blendMode = BlendMode.Multiply)
                            }
                        }
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = uiState.currentSelectedData.name,
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    if (uiState.currentSelectedData.category != null &&
                        uiState.currentSelectedData.established != null) {
                        Row {
                            Text(
                                text = uiState.currentSelectedData.category ?: "Unknown Category",
                                color = Color.LightGray,
                                style = MaterialTheme.typography.labelMedium,
                            )
                            Text(
                                text = " | ${uiState.currentSelectedData.established ?: "Unknown Establish"}",
                                color = Color.LightGray,
                                style = MaterialTheme.typography.labelMedium,
                            )
                        }
                    }else{
                        Text(
                            text = "Shopping Mall in Jakarta",
                            color = Color.LightGray,
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                }
            }

            DetailsCard(
                data = uiState.currentSelectedData,
                menuType = uiState.currentMenu,
                isFullScreen = isFullScreen,
                modifier = if (isFullScreen)
                    Modifier.padding(horizontal = 16.dp)
                else
                    Modifier.padding(end = 16.dp)
            )
        }
    }
}

@Composable
private fun DetailsScreenTopBar(
    onBackButtonClicked: () -> Unit,
    uiState: UiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onBackButtonClicked,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.surface, shape = CircleShape),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.navigation_back)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 40.dp)
        ) {
            Text(
                text = uiState.currentSelectedData.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsCard(
    data: Data,
    menuType: MenuType,
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false
) {
    val context = LocalContext.current
    val displayToast = { text: String ->
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    if (isFullScreen) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            DescriptionCard(
                data = data,
                isFullScreen = isFullScreen,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LocationCard(
                data = data,
                modifier = Modifier.padding(16.dp)
            )
        }
    }else{
        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            DescriptionCard(
                data = data,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                isFullScreen = isFullScreen
            )

            Spacer(modifier = Modifier.width(8.dp))

            LocationCard(
                data = data,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
            )
        }
    }
}

@Composable
fun DescriptionCard(
    data: Data,
    isFullScreen: Boolean = false,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.description)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                if (!isFullScreen) {
                    Text(
                        text = data.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
                    )
                } else {
                    Spacer(modifier = Modifier.height(12.dp))
                }
                Text(
                    text = data.desc,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
fun LocationCard(
    data: Data,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.location)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = data.location,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun ActionButton(
    text: String,
    onButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    containIrreversibleAction: Boolean = false
) {
    Button(
        onClick = { onButtonClicked(text) },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor =
            if (!containIrreversibleAction)
                MaterialTheme.colorScheme.inverseOnSurface
            else MaterialTheme.colorScheme.onErrorContainer
        )
    ) {
        Text(
            text = text,
            color =
            if (!containIrreversibleAction)
                MaterialTheme.colorScheme.onSurfaceVariant
            else MaterialTheme.colorScheme.onError
        )
    }
}