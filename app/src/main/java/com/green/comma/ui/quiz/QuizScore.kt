package com.green.comma.ui.quiz

object QuizScore {
    private var count: Int = 0
    private var score: Int = 0
    private var totalNum: Int = 0

    fun reset(){
        count = 1
        score = 0
        totalNum = 0
    }
    fun setTotalQuiz(num: Int){
        totalNum = num
    }
    fun addCount(){
        count++
    }
    fun addScore(){
        score++
    }
    fun getCount(): Int {
        return count
    }
    fun getScore(): Int {
        return score
    }
    fun getTotalQuiz() : Int{
        return totalNum
    }
}