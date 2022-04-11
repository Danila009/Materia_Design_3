package com.example.navigationrail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavigationRailData(
    val icon:ImageVector
){
    Home(Icons.Default.Home),
    History(Icons.Default.Refresh),
    Profile(Icons.Default.Person),
    Favorite(Icons.Default.Favorite),
    Settings(Icons.Default.Settings)
}
