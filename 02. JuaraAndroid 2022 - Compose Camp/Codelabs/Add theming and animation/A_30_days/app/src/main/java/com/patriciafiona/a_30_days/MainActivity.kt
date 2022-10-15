package com.patriciafiona.a_30_days

import android.annotation.SuppressLint
import android.graphics.Movie
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.*
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.request.ImageRequest
import com.example.woof.ui.theme.Grey50
import com.patriciafiona.a_30_days.data.model.*
import com.patriciafiona.a_30_days.ui.theme.A_30_daysTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = MainViewModel()
        setContent {
            A_30_daysTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RecipesApp(viewModel)
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun RecipesApp(
    viewModel: MainViewModel
) {
    val isLoading = viewModel.isLoading.observeAsState()
    val recipeResponse = viewModel.recipes.observeAsState()

    isLoading.value?.let {
        Scaffold(
            topBar = {
                RecipesTopAppBar()
            }
        ) {
            if (isLoading.value == false) {
                recipeResponse.value?.let { it1 -> RecipeList(it1) }
            }else{
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeList(response: RecipesResponse) {
    var selectedIndex by remember { mutableStateOf(-1) }
    val recipes:List<ResultsItem> = response.results as List<ResultsItem>

    LazyColumn {
        itemsIndexed(items = recipes) { index, item ->
            RecipeItem(recipe = item, day = index, selectedIndex) { i ->
                selectedIndex = i
            }
        }
    }

}

@Composable
fun RecipesTopAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun RecipeItem(recipe: ResultsItem,
               day: Int,
               selectedIndex: Int,
               onClick: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colors.onSurface
        else MaterialTheme.colors.surface,
    )

    Card(
        elevation = 4.dp,
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                .background(color = color)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                SubcomposeAsyncImage(
                    model = recipe.thumbnail_url,
                    contentDescription = "Recipe Image",
                    Modifier
                        .width(80.dp)
                        .height(80.dp)
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        CircularProgressIndicator()
                    } else {
                        SubcomposeAsyncImageContent(
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        text = "Menu Day ${day+1}",
                        color = MaterialTheme.colors.onSecondary,
                        fontSize = 12.sp
                    )
                    RecipeInfo(
                        name = (recipe.name?: "Unknown Name"),
                        desc = (recipe.description?: "Unknown Description"),
                    )
                }
                ItemButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded },
                )
            }

            if (expanded) {
                Divider(Modifier.padding(vertical = 10.dp))

                RecipeMoreDetail(recipe)
            }
        }
    }
}

@Composable
private fun RecipeInfo(name: String, desc: String){
    Column{
        if (name.isEmpty()) {
            Text(
                text = "Unknown Name",
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onPrimary
            )
        }else{
            Text(
                text = name,
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (desc.isEmpty()) {
            Text(
                text = "Unknown description",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onPrimary
            )
        }else{
            Text(
                text = desc,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Justify,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
fun RecipeMoreDetail(recipe: ResultsItem, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(
                start = 16.dp,
                top = 8.dp,
                bottom = 16.dp,
                end = 16.dp
            )
    ) {
        Text(
            text = "Recipe details",
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onPrimary
        )

        if(recipe.tags.isNullOrEmpty() && recipe.sections?.get(0)?.components == null &&
            recipe.instructions == null){
            Text(
                text = "This recipe don't have any details",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onPrimary
            )
        }

        if (!recipe.tags.isNullOrEmpty()) {
            val tags:List<TagsItem> = recipe.tags as List<TagsItem>

            Text(
                text = ">>> Tag",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onPrimary
            )
            LazyRow {
                itemsIndexed(recipe.tags) { index, item ->
                    TagItem(item, index)
                }
            }
        }

        if (recipe.sections?.get(0)?.components != null) {
            val ingredients:List<ComponentsItem> = recipe.sections[0]?.components as List<ComponentsItem>
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = ">>> Ingredients",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onPrimary
            )
            LazyRow {
                itemsIndexed(ingredients) { index, item ->
                    IngredientItem(item, index)
                }
            }
        }

        if (recipe.instructions != null) {
            val instruction:List<InstructionsItem> = recipe.instructions as List<InstructionsItem>

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = ">>> Instruction",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onPrimary
            )
            LazyRow {
                itemsIndexed(recipe.instructions) { index, item ->
                    InstructionItem(item, index)
                }
            }
        }
    }
}

@Composable
fun TagItem(tag: TagsItem, index: Int){
    Card(
        modifier = Modifier
            .padding(end = 8.dp, top = 8.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 5.dp)
        ) {
            Text(
                text = tag.display_name?: "Unknown Tag",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
fun IngredientItem(component: ComponentsItem, index: Int){
    val ingredient:Ingredient = component.ingredient as Ingredient
    Card(
        modifier = Modifier
            .padding(end = 8.dp, top = 8.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 5.dp)
        ) {
            Text(
                text = ingredient.name?: "Unknown Ingredient",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
fun InstructionItem(instruction: InstructionsItem, index: Int){
    Card(
        modifier = Modifier
            .padding(end = 8.dp, top = 8.dp)
            .width(250.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(contentAlignment= Alignment.Center,
                modifier = Modifier
                    .background(Color.Black, shape = CircleShape)
                    .layout() { measurable, constraints ->
                        // Measure the composable
                        val placeable = measurable.measure(constraints)

                        //get the current max dimension to assign width=height
                        val currentHeight = placeable.height
                        val currentWidth = placeable.width
                        val newDiameter = maxOf(currentHeight, currentWidth)

                        //assign the dimension and the center position
                        layout(newDiameter, newDiameter) {
                            // Where the composable gets placed
                            placeable.placeRelative(
                                (newDiameter - currentWidth) / 2,
                                (newDiameter - currentHeight) / 2
                            )
                        }
                    }) {

                Text(
                    text = instruction.position.toString(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(4.dp),
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 5.dp)
                    .weight(1f)
            ) {
                Text(
                    text = instruction.display_text.toString(),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
private fun ItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = stringResource(R.string.expand_button_content_description)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    A_30_daysTheme {
        val viewModel = MainViewModel()
        RecipesApp(viewModel)
    }
}