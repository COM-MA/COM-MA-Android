package com.green.comma.util

import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationHelper {
    companion object {
        fun triggerMenuItemSelected(bottomNavigationView: BottomNavigationView, itemId: Int) {
            bottomNavigationView.selectedItemId = itemId
        }
    }
}