package com.kharedji.memosphere.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kharedji.memosphere.R
import com.kharedji.memosphere.navigation.Navigation
import com.kharedji.memosphere.navigation.Screen
import com.kharedji.memosphere.presentation.activities.view_models.MainViewModel
import com.kharedji.memosphere.presentation.components.DrawerContent
import com.kharedji.memosphere.ui.theme.MemoSphereTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoSphereTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mainViewModel: MainViewModel = hiltViewModel()
                    MainScreen(mainViewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(mainViewModel: MainViewModel? = null) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
            ) {
                DrawerContent(
                    navController = navController,
                    drawerState = drawerState,
                    coroutineScope = coroutineScope,
                    mainViewModel = mainViewModel
                )
            }
        }
    ) {
        MainScaffold(
            drawerState = drawerState,
            coroutineScope = coroutineScope,
            navController = navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController
) {
    val backStackEntry =
        navController.currentBackStackEntryFlow.collectAsState(navController.currentBackStackEntry)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Memo Sphere",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    Image(
                        painter = painterResource(
                            id = R.drawable.ic_launcher
                        ),
                        contentDescription = "app icon",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(40.dp)
                    )
                },
                navigationIcon = {
                    backStackEntry.value?.destination?.route?.let {
                        when (it) {
                            Screen.Main.route -> {
                                IconButton(onClick = {
                                    coroutineScope.launch {
                                        drawerState.open()
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "drawer icon"
                                    )
                                }
                            }

                            Screen.SignIn.route -> {

                            }

                            Screen.SignUp.route -> {

                            }

                            else -> {
                                IconButton(onClick = {
                                    navController.navigateUp()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "back arrow"
                                    )
                                }
                            }
                        }

                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = Color.White,
                )
            )
        }
    ) {
        Navigation(it, navController)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MemoSphereTheme {
        MainScreen()
    }
}