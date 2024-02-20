package com.green.comma.ui.card

object CardSelect {
    private var cardList = mutableListOf<Long>()

    fun addSelectedCard(userCardId: Long){
        if(cardList.size < 5 && cardList.find { it == userCardId } == null)
            cardList.add(userCardId)
        else cardList.remove(userCardId)
    }

    fun printCard(){
        println(cardList)
    }

    fun checkIsSelected(userCardId: Long): Boolean{
        return (cardList.find { it == userCardId } != null)
    }

    fun resetList(){
        cardList.clear()
    }
}