package com.paperapp.dagger.modules

import com.paperapp.dagger.PerLoginActivity
import com.paperapp.interactors.*
import com.paperapp.network.api.AuthApi
import com.paperapp.repositories.login.*
import com.paperapp.storage.UserInfoStorage
import com.paperapp.ui.login.LoginActivity
import com.paperapp.ui.login.LoginRouter
import dagger.Module
import dagger.Provides

@Module
class LoginModule {
    @Provides
    @PerLoginActivity
    fun provideLoginActivity(): LoginActivity = LoginActivity.INSTANCE

    @Provides
    @PerLoginActivity
    fun provideLoginRouter(): LoginRouter = LoginRouter()

    @PerLoginActivity
    @Provides
    fun provideAuthRepository(
        loginActivity: LoginActivity,
        router: LoginRouter,
        authInteractor: AuthInteractor,
        googleInteractor: GoogleInteractor,
        vkInteractor: VkInteractor,
        fbInteractor: FacebookInteractor,
        branchInteractor: BranchInteractor,
        userInfoStorage: UserInfoStorage
    ) = LoginRepository(
        loginActivity,
        router,
        authInteractor,
        googleInteractor,
        vkInteractor,
        fbInteractor,
        branchInteractor,
        userInfoStorage
    )

    @PerLoginActivity
    @Provides
    fun provideBranchInteractor(loginActivity: LoginActivity) = BranchInteractor(loginActivity)

    @PerLoginActivity
    @Provides
    fun provideFacebookInteractor() = FacebookInteractor()

    @PerLoginActivity
    @Provides
    fun provideVkInteractor() = VkInteractor()

    @PerLoginActivity
    @Provides
    fun provideGoogleInteractor() = GoogleInteractor()

    @PerLoginActivity
    @Provides
    fun provideAuthInterator(authApi: AuthApi) = AuthInteractor(authApi)
}