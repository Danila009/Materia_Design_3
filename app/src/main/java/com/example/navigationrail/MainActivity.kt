package com.example.navigationrail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.*
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val id = remember { mutableStateOf(NavigationRailData.Home) }

            Scaffold(
                topBar = {
                    when(id.value){
                        NavigationRailData.Home -> {
                            SmallTopAppBar(
                                title = { Text("Simple TopAppBar") },
                                navigationIcon = {
                                    IconButton(onClick = { }) {
                                        Icon(
                                            imageVector = Icons.Filled.Menu,
                                            contentDescription = null
                                        )
                                    }
                                },
                                actions = {
                                    IconButton(onClick = {  }) {
                                        Icon(
                                            imageVector = Icons.Filled.Favorite,
                                            contentDescription = null
                                        )
                                    }
                                }
                            )
                        }
                        NavigationRailData.History -> {

                        }
                        NavigationRailData.Profile -> {

                        }
                        NavigationRailData.Favorite -> {
                            val decayAnimationSpec = rememberSplineBasedDecay<Float>()
                            val scrollBehavior = remember(decayAnimationSpec) {
                                TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
                            }

                            LargeTopAppBar(
                                title = { Text("Large TopAppBar") },
                                navigationIcon = {
                                    IconButton(onClick = {  }) {
                                        Icon(
                                            imageVector = Icons.Filled.Menu,
                                            contentDescription = "Localized description"
                                        )
                                    }
                                },
                                actions = {
                                    IconButton(onClick = {  }) {
                                        Icon(
                                            imageVector = Icons.Filled.Favorite,
                                            contentDescription = "Localized description"
                                        )
                                    }
                                },
                                scrollBehavior = scrollBehavior
                            )
                        }
                        NavigationRailData.Settings -> {
                            CenterAlignedTopAppBar(
                                title = { Text("Centered TopAppBar") },
                                navigationIcon = {
                                    IconButton(onClick = {  }) {
                                        Icon(
                                            imageVector = Icons.Filled.Menu,
                                            contentDescription = "Localized description"
                                        )
                                    }
                                },
                                actions = {
                                    IconButton(onClick = {  }) {
                                        Icon(
                                            imageVector = Icons.Filled.Favorite,
                                            contentDescription = "Localized description"
                                        )
                                    }
                                }
                            )
                        }
                    }
                }, floatingActionButton = {
                    SmallFloatingActionButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null
                        )
                    }
                } , content = {
                    Row {
                        NavigationRail{
                            NavigationRailData.values().forEach { item ->
                                NavigationRailItem(
                                    selected = id.value == item,
                                    onClick = { id.value = item },
                                    label = { Text(text = item.name) },
                                    icon = { Icon(imageVector = item.icon, contentDescription = null) }
                                )
                            }
                        }

                        when(id.value){
                            NavigationRailData.Home -> {
                                Column {
                                    BadgedBox(
                                        modifier = Modifier.padding(15.dp),
                                        badge = { Badge { Text("8") } }) {
                                        Icon(
                                            Icons.Filled.Notifications,
                                            contentDescription = "Favorite"
                                        )
                                    }

                                    ElevatedCard(Modifier.size(width = 180.dp, height = 100.dp)) {
                                        Text(text = "Elevated Card")
                                    }

                                    OutlinedCard(Modifier.size(width = 180.dp, height = 100.dp)) {
                                        Text(text = "Outlined Card")
                                    }

                                    var checked by remember { mutableStateOf(false) }

                                    IconToggleButton(checked = checked, onCheckedChange = { checked = it }) {
                                        val tint by animateColorAsState(if (checked) Color(0xFFEC407A) else Color(0xFFB0BEC5))
                                        Icon(Icons.Filled.Favorite, contentDescription = "Localized description", tint = tint)
                                    }

                                    var progress by remember { mutableStateOf(0.1f) }
                                    val animatedProgress by animateFloatAsState(
                                        targetValue = progress,
                                        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
                                    )

                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        LinearProgressIndicator(progress = animatedProgress)
                                        Spacer(Modifier.requiredHeight(30.dp))
                                        OutlinedButton(
                                            onClick = {
                                                if (progress < 1f) progress += 0.1f
                                            }
                                        ) {
                                            Text("Increase")
                                        }
                                    }
                                }
                            }
                            NavigationRailData.History -> {
                                val scope = rememberCoroutineScope()
                                val drawerState = rememberDrawerState(DrawerValue.Closed)
                                val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
                                val selectedItem = remember { mutableStateOf(items[0]) }

                                ModalNavigationDrawer(
                                    drawerState = drawerState,
                                    drawerContent = {
                                        items.forEach { item ->
                                            NavigationDrawerItem(
                                                icon = { Icon(item, contentDescription = null) },
                                                label = { Text(item.name) },
                                                selected = item == selectedItem.value,
                                                onClick = {
                                                    scope.launch { drawerState.close() }
                                                    selectedItem.value = item
                                                },
                                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                            )
                                        }
                                    }, content = {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(16.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(text = if (drawerState.isClosed) ">>> Swipe >>>" else "<<< Swipe <<<")
                                            Spacer(Modifier.height(20.dp))
                                        }
                                    }
                                )
                            }
                            NavigationRailData.Profile -> {
                                val drawerState = rememberDrawerState(DrawerValue.Closed)
                                val scope = rememberCoroutineScope()
                                val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
                                val selectedItem = remember { mutableStateOf(items[0]) }
                                DismissibleNavigationDrawer(
                                    drawerState = drawerState,
                                    drawerContent = {
                                        items.forEach { item ->
                                            NavigationDrawerItem(
                                                icon = { Icon(item, contentDescription = null) },
                                                label = { Text(item.name) },
                                                selected = item == selectedItem.value,
                                                onClick = {
                                                    scope.launch { drawerState.close() }
                                                    selectedItem.value = item
                                                },
                                                modifier = Modifier.padding(horizontal = 12.dp)
                                            )
                                        }
                                    },
                                    content = {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(16.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(text = if (drawerState.isClosed) ">>> Swipe >>>" else "<<< Swipe <<<")
                                            Spacer(Modifier.height(20.dp))

                                        }
                                    }
                                )
                            }
                            NavigationRailData.Favorite -> {
                                Column {

                                }
                            }
                            NavigationRailData.Settings -> {

                            }
                        }
                    }
                }
            )
        }
    }
}
