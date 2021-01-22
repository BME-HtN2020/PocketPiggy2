package com.example.projectpiggy2.user

import com.example.projectpiggy2.Goal

/**
 * Since the user controller is static, this is a wrapper to define a class that will handle
 * all accesses to the UserController class
 *
 * This also makes it easier to define the dependency because we inject the implementation at
 * runtime, but when it comes time to test we can simply mock out the interface
 *
 * This also makes it so we can change the implementation of this interface without impacting
 * the returns from this wrapper
 */
interface UserWrapper {
    fun createUser(pin: String, name: String)
}