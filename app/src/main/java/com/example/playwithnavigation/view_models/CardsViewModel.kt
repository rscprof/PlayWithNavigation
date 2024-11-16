package com.example.playwithnavigation.view_models

import androidx.lifecycle.ViewModel
import com.example.playwithnavigation.model.CardStack

class CardsViewModel: ViewModel() {
    val model = CardStack()

    enum class StartPage {
        Game,
        Editor
    }

    val startPage : StartPage = if (model.countCards==0) StartPage.Editor else StartPage.Game
}