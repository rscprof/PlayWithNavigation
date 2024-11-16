package com.example.playwithnavigation.model

class CardStack {
    private class HolderBackAndCounter(
        val back: String,
        var countTrue: Int = 0, var countFalse: Int = 0)

    private val cardsWithStatistics = mutableMapOf<String, HolderBackAndCounter>()
    val countCards = cardsWithStatistics.count()

    fun add(card: Card) : Boolean {
        return if (cardsWithStatistics.containsKey(card.front)) false
        else {
            cardsWithStatistics[card.front] = HolderBackAndCounter(card.back)
            true
        }
    }

    fun del(card: Card) : Boolean {
        return if (!cardsWithStatistics.containsKey(card.front)) false
        else {
            cardsWithStatistics.remove(card.front)
            true
        }

    }

    //Поправляем карточку (опечатки и подобное, потому статистику не стираем)
    //это для редактирования
    fun change(oldCard: Card, newCard: Card):Boolean {
        val oldHolder = cardsWithStatistics[oldCard.front]

        return if (oldHolder==null) false
        else if ((oldCard.front!=newCard.front)&&(cardsWithStatistics.containsKey(newCard.front))) false
        else {
           cardsWithStatistics.remove(oldCard.front)
           cardsWithStatistics[newCard.front] =
               HolderBackAndCounter(newCard.back, oldHolder.countFalse, oldHolder.countTrue)
           true
        }
    }

    enum class ResultCheck {
        Correct, Incorrect, Error
    }

    fun checkCard(card: Card, answer: String): ResultCheck {
        val holder = cardsWithStatistics[card.front]
        return if (holder==null) ResultCheck.Error
        else {
            if (holder.back==answer) {
                holder.countTrue++
                ResultCheck.Correct
            } else {
                holder.countFalse++
                ResultCheck.Incorrect
            }
        }
    }

    fun getCards(): Sequence<Card>  = sequence{
        cardsWithStatistics.forEach {
            entry -> yield(Card(entry.key,entry.value.back))
        }
    }

    fun getCardsWithStatistics(): Sequence<CardWithStatistics> = sequence {
        cardsWithStatistics.forEach {
                entry -> yield(
            CardWithStatistics(Card(entry.key,entry.value.back),
                    countTrue = entry.value.countTrue, countFalse = entry.value.countFalse
                    ))
        }
    }


}