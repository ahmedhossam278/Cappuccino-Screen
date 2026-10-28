package com.example.cappuccinoscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cappuccinoscreen.data.DataSource
import com.example.cappuccinoscreen.model.Category
import com.example.cappuccinoscreen.model.Product
import com.example.cappuccinoscreen.model.Volume
import com.example.cappuccinoscreen.ui.theme.ProductScreenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductScreenTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductScreen(
                        product = DataSource.cappuccino,
                        category = DataSource.category,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductScreen(
    product: Product,
    category: Category,
    modifier: Modifier = Modifier
) {
    var selectedVolume by remember { mutableStateOf<Volume>(Volume.SMALL) }
    var isExpanded by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ProductHeader(
                product = product,
                category = category,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            ProductDetails(
                product = product,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        item {
            ProductAbout(
                product = product,
                expandedText = isExpanded,
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        item {
            ProductSize(
                categoryName = category.name,
                selectedVolume = selectedVolume,
                onVolumeSelected = { newVolume -> selectedVolume = newVolume },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        item {
            ProductStatus(
                volume = selectedVolume,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        item {
            ProductBottomBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun ProductHeader(
    product: Product,
    category: Category,
    modifier: Modifier = Modifier
) {
    var isFavorite by remember { mutableStateOf(false) }
    val favoriteButtonColor by animateColorAsState(
        targetValue = if (isFavorite) {
            MaterialTheme.colorScheme.error
        } else {
            MaterialTheme.colorScheme.onPrimary
        }
    )

    val categoryShape = RoundedCornerShape(
        bottomStart = 35.dp,
        bottomEnd = 35.dp
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(330.dp)
    ) {
        Image(
            painter = painterResource(id = category.imageId),
            contentDescription = category.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(250.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = categoryShape,
                    clip = false
                )
                .clip(categoryShape)
        )

        // Back Button
        IconButton(
            onClick = {/*TODO*/ },
            modifier = Modifier
                .padding(16.dp)
                .size(40.dp)
                .align(Alignment.TopStart)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = CircleShape
                )
                .clip(CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        // Favorite Button
        IconButton(
            onClick = { isFavorite = !isFavorite },
            modifier = Modifier
                .padding(16.dp)
                .size(40.dp)
                .align(Alignment.TopEnd)
                .border(
                    width = 1.dp,
                    color = favoriteButtonColor,
                    shape = CircleShape
                )
                .clip(CircleShape)
        ) {
            Icon(
                imageVector = Icons.Outlined.Favorite,
                contentDescription = "Favorite",
                tint = favoriteButtonColor
            )
        }

        // Product Image
        Image(
            painter = painterResource(id = product.image),
            contentDescription = product.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(200.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = CircleShape,
                    clip = false
                )
                .clip(CircleShape)
        )
    }
}

@Composable
fun ProductDetails(
    product: Product,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                )
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Rating Star",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = product.rate.toString(),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        shape = CircleShape
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "$ ${product.price}",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun ProductSize(
    categoryName: String,
    selectedVolume: Volume = Volume.SMALL,
    onVolumeSelected: (Volume) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "$categoryName size",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(Volume.entries.toTypedArray()) { volume ->
                val isSelected = volume == selectedVolume
                val backgroundColor = if (isSelected) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.onPrimaryContainer
                }
                val textColor = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(backgroundColor)
                        .clickable { onVolumeSelected(volume) }
                        .padding(horizontal = 24.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = volume.name.lowercase().replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = textColor
                    )
                }
            }
        }
    }
}

@Composable
fun ProductAbout(
    product: Product,
    expandedText: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Text(
            text = "About",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Column(
            modifier = Modifier.animateContentSize()
        ) {
            Text(
                text = stringResource(product.description),
                maxLines = if (!expandedText) 3 else Int.MAX_VALUE,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = if (expandedText) "Read less" else "Read more",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.scrim,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable { onClick() }
            )
        }
    }
}

@Composable
fun ProductStatus(
    volume: Volume,
    modifier: Modifier = Modifier
) {
    var quantity by remember { mutableIntStateOf(1) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Volume",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "${volume.ml}ml",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = CircleShape
                )
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text = "-",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable {
                    if (quantity > 1) quantity--
                }
            )

            Text(
                text = "$quantity",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "+",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable {
                    quantity++
                }
            )
        }
    }
}

@Composable
fun ProductBottomBar(modifier: Modifier = Modifier) {
    var isCartAdded by remember { mutableStateOf(false) }
    var isBuyClicked by remember { mutableStateOf(false) }

    val cartBackgroundColor by animateColorAsState(
        targetValue = if (isCartAdded) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
    )
    val cartIconColor by animateColorAsState(
        targetValue = if (isCartAdded) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
    )

    val buyButtonBackgroundColor by animateColorAsState(
        targetValue = if (isBuyClicked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = {
                isCartAdded = !isCartAdded
            },
            modifier = Modifier
                .size(54.dp)
                .background(cartBackgroundColor, CircleShape)
                .border(
                    width = 1.dp,
                    color = if (isCartAdded) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.outlineVariant,
                    shape = CircleShape
                )
                .clip(CircleShape)
        ) {
            Icon(
                imageVector = Icons.Outlined.ShoppingBag,
                contentDescription = "Shopping Bag",
                tint = cartIconColor
            )
        }
        Button(
            onClick = {
                isBuyClicked = !isBuyClicked
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = buyButtonBackgroundColor,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .weight(1f)
                .height(54.dp)
        ) {
            Text(
                text = if (isBuyClicked) "Added to Order" else "Buy now",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductScreenPreview() {
    ProductScreenTheme {
        ProductScreen(product = DataSource.cappuccino, category = DataSource.category)
    }
}
