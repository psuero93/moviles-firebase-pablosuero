package com.pablosuero.apppablo.ui.screen.DetailScreen

import ListViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pablosuero.apppablo.data.repositories.models.Meal
import com.pablosuero.apppablo.ui.theme.GreenDAM
import com.pablosuero.apppablo.ui.theme.OrangeDAM

// pasarle ruta en lugar de navController y pasarle viewModel
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    mealId: String,
    onBack: () -> Unit,
    listViewModel: ListViewModel,
) {
    val meal: Meal? = listViewModel.getMealById(mealId.toIntOrNull() ?: 0)

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "DAMeals",
                                color = Color.White,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 33.sp
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = GreenDAM
                    )
                )
                IconButton(
                    onClick = { onBack() },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 4.dp, top = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }
            }
        }
    ) {
        meal?.let {
            val ingredients = buildIngredientList(it)

            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .padding(top = 120.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Text(
                            text = meal.strMeal,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                        )

                        AsyncImage(
                            model = meal.strMealThumb,
                            contentDescription = meal.strMeal + " image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                        )
                    }
                }
                // Usamos itemsIndexed en vez de items
                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        ElevatedCard(
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.elevatedCardColors(containerColor = OrangeDAM)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Ingredientes",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                ingredients.forEach { ingredient ->
                                    Text(
                                        text = "• $ingredient",
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

        } ?: run {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("No se encontró la receta.")
            }
        }
    }
}

fun buildIngredientList(meal: Meal): List<String> {
    val ingredients = mutableListOf<String>()

    for (i in 1..20) {
        val ingredientField = Meal::class.java.getDeclaredField("strIngredient$i")
        val measureField = Meal::class.java.getDeclaredField("strMeasure$i")
        ingredientField.isAccessible = true
        measureField.isAccessible = true

        val ingredient = (ingredientField.get(meal) as? String)?.trim().orEmpty()
        val measure = (measureField.get(meal) as? String)?.trim().orEmpty()

        if (ingredient.isNotEmpty() && ingredient.lowercase() != "null") {
            val full = if (measure.isNotEmpty()) "$measure $ingredient" else ingredient
            ingredients.add(full)
        }
    }
    return ingredients
}