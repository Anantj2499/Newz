package com.example.newz.presentation.news_navigator.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newz.R
import com.example.newz.ui.theme.NewzTheme

@Composable
fun NewsBottomNavigation(
    items: List<BottomNavigationItem>,
    selected: Int,
    onItemClick: (Int) -> Unit,
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 10.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                       Column (
                           horizontalAlignment = Alignment.CenterHorizontally
                       ){
                           Icon(
                               painter = painterResource(id = item.icon),
                                 contentDescription = item.title,
                               modifier = Modifier.size(20.dp)
                           )
                           Spacer(modifier = Modifier.size(6.dp))
                           Text(text = item.title,
                               style = MaterialTheme.typography.labelSmall)
                       }
                },
                selected = index == selected,
                onClick = { onItemClick(index) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor  = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = colorResource(id = R.color.body),
                    unselectedTextColor = colorResource(id = R.color.body),
                    indicatorColor = MaterialTheme.colorScheme.background
                ),
                )
        }
    }
}
data class BottomNavigationItem(
    @DrawableRes val icon: Int,
    val title: String,
)
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewsBottomNavigationPreview() {
        NewzTheme {
            NewsBottomNavigation(
                items = listOf(
                    BottomNavigationItem(
                        icon = R.drawable.ic_home,
                        title = "Home",
                    ),
                    BottomNavigationItem(
                        icon = R.drawable.ic_search,
                        title = "Search",
                    ),
                    BottomNavigationItem(
                        icon = R.drawable.ic_bookmark,
                        title = "Bookmarks",
                    ),
                ),
                selected = 0,
                onItemClick = { /*TODO*/ },
            )
        }

}