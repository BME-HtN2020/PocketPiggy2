package com.example.projectpiggy2.user

import com.example.projectpiggy2.Goal
import com.example.projectpiggy2.UserController


/**
 * This class will handle interacting with the UserController static methods until they are
 * eventually refactored out
 */
class UserInfo: UserWrapper {
    override fun createUser(pin: String, name: String) {
        UserController.createUser(pin, name)
    }
}