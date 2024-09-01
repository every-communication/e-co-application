package com.example.graduationproject_aos.data.source

import com.example.graduationproject_aos.data.service.AuthService
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val authService: AuthService
) {
}