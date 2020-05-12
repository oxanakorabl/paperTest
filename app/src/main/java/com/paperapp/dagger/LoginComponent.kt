package com.paperapp.dagger

import com.paperapp.dagger.modules.LoginModule
import com.paperapp.interactors.AuthInteractor
import com.paperapp.repositories.login.LoginRepository
import com.paperapp.interactors.BranchInteractor
import com.paperapp.interactors.FacebookInteractor
import com.paperapp.interactors.VkInteractor
import com.paperapp.ui.login.LoginActivity
import com.paperapp.ui.login.LoginRouter
import dagger.Subcomponent

@PerLoginActivity
@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {
    fun getActivity(): LoginActivity
    fun getRouter(): LoginRouter

    fun getLoginRepository(): LoginRepository

    fun getBranchInteractor(): BranchInteractor
    fun getFacebookInteractor(): FacebookInteractor
    fun getVkInteractor(): VkInteractor
    fun getAuthInteractor(): AuthInteractor
}
