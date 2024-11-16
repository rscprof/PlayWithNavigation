package com.example.playwithnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.playwithnavigation.ui.theme.PlayWithNavigationTheme
import com.example.playwithnavigation.view_models.CardsViewModel
import kotlinx.serialization.Serializable


// Карточки для запоминания

// Сверху - название
// в середине основная часть, которая...
// если карточки уже есть, то показывается первая карточка
// если карточек нет, то режим редактирования карточек
// Снизу - кнопки для переключения между режимами (одна кнопка - это собственно запоминание,
// а вторая - это режим редактирования, третья кнопка - статистика
// (список слов с количеством ошибок))

//при показе карточки мы вводим ответ

//при редактировании карточек - на первом экране у нас показывается список,
//где есть кнопка удаления
//есть кнопка редактирования - я переключаюсь на режим редактирования карточки
//есть кнопка добавления - я переключаюсь в режим добавления карточки (почти то же, что
// редактирование)

@Serializable
object Game
@Serializable
object Editor
@Serializable
object Statistics

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  enableEdgeToEdge()
        setContent {
            PlayWithNavigationTheme {

                //Это надо делать в том месте, где он нужен
                val navController = rememberNavController()
                val viewModel : CardsViewModel by viewModels()

                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Text("Карточки",
                            style = MaterialTheme.typography.displayLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().background(
                                MaterialTheme.colorScheme.primaryContainer
                            )
                            )
                    },
                    bottomBar = {
                        Row(modifier = Modifier.fillMaxWidth()) {

                            Button(onClick = {
                                navController.navigate(route = Game)
                            }) {
                                Text("Игра")
                            }

                            Button(onClick = {

                                navController.navigate(route = Editor)
                            }) {
                                Text("Редактор")
                            }

                            Button(onClick = {
                                navController.navigate(route = Statistics)
                            }) {
                                Text("Статистика")
                            }

                        }
                    }
                    ) { innerPadding ->

                    //Здесь будет то место, где будут переключаться страницы
                    //всего приложения

                    val startDestination = when(viewModel.startPage) {
                        CardsViewModel.StartPage.Game -> Game
                        CardsViewModel.StartPage.Editor -> Editor
                    }

                    NavHost(navController = navController, startDestination =  startDestination) {
                        composable<Game> {
                            PageGame(modifier = Modifier.padding(innerPadding))
                        }
                        composable<Editor> {
                            PageEditor(modifier = Modifier.padding(innerPadding))
                        }
                        composable<Statistics> {
                            PageStatistics(modifier = Modifier.padding(innerPadding))
                        }
                    }

                }
            }
        }
    }
    @Composable
    private fun PageStatistics(modifier: Modifier) {
        Text("Статистика", modifier = modifier)
    }
    @Composable
    private fun PageEditor(modifier: Modifier) {
        Text("Редактор", modifier = modifier)
    }
    @Composable
    private fun PageGame(modifier: Modifier) {
        Text("Игра", modifier = modifier)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PlayWithNavigationTheme {
        Greeting("Android")
    }
}