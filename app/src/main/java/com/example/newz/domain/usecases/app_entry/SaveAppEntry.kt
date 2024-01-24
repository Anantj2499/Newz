package com.example.newz.domain.usecases.app_entry

import com.example.newz.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke() {
        localUserManager.saveAppEntery()
    }
}