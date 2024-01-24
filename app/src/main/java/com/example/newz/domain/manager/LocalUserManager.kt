package com.example.newz.domain.manager

import kotlinx.coroutines.flow.Flow


interface LocalUserManager {
    suspend fun saveAppEntery()
    fun readAppEntry(): Flow<Boolean>
}