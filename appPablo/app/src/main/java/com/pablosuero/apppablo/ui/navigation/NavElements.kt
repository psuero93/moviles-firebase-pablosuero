package com.pablosuero.apppablo.ui.navigation

import kotlinx.serialization.Serializable


@Serializable
object List

@Serializable
data class Detail(val id: String)

@Serializable
object SignUp

@Serializable
object Home

@Serializable
object ForgotPassword