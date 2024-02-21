package com.green.comma.ui.card

import androidx.lifecycle.MutableLiveData

object CardSelect {
    private var cardList = mutableListOf<Long>()
    val count: MutableLiveData<Int> = MutableLiveData(0)

    fun addSelectedCard(userCardId: Long){
        if(cardList.size < 5 && cardList.find { it == userCardId } == null) {
            cardList.add(userCardId)
            count.value = cardList.size
        } else {
            cardList.remove(userCardId)
            count.value = cardList.size
        }
    }

    fun getSelectedCardList(): MutableList<Long> {
        return cardList
    }

    fun printCard(){
        println(cardList)
    }

    fun checkIsSelected(userCardId: Long): Boolean{
        return (cardList.find { it == userCardId } != null)
    }

    fun resetList(){
        cardList.clear()
        count.value = cardList.size
    }
}