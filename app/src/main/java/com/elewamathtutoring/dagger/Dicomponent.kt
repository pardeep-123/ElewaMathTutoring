package com.elewamathtutoring.dagger

import android.app.Application
import com.elewamathtutoring.Activity.*
import com.elewamathtutoring.Activity.Auth.ForgotPassword
import com.elewamathtutoring.Activity.Auth.SignupTeacherActivity
import com.elewamathtutoring.Activity.Auth.login.LoginScreen
import com.elewamathtutoring.Activity.Auth.signup.SignUp
import com.elewamathtutoring.Activity.ParentOrStudent.resources.ResoucesActivity
import com.elewamathtutoring.Activity.ParentOrStudent.resources.changepassword.ChangePassword
import com.elewamathtutoring.Activity.TeacherOrTutor.AboutYouActivity
import com.elewamathtutoring.Activity.TeacherOrTutor.availability.AvailablityActivity
import com.elewamathtutoring.Activity.TeacherOrTutor.TeachingInfo.TeachingInfoActivity
import com.elewamathtutoring.viewmodel.BaseViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DiModule::class])
interface Dicomponent {
    fun inject(loginScreen: LoginScreen)
    fun inject(viewModel: BaseViewModel)
    fun inject(signUp: SignUp)
    fun inject(aboutYouActivity: AboutYouActivity)
    fun inject(teachingInfoActivity: TeachingInfoActivity)
    fun inject(availablityActivity: AvailablityActivity)
    fun inject(addBankAccountActivity: AddBankAccountActivity)
    fun inject(addCardActivity: AddCardActivity)
    fun inject(paymentInfoActivity: PaymentInfoActivity)
    fun inject(changePassword: ChangePassword)
    fun inject(sendFeedback: SendFeedback)
    fun inject(forgotPassword: ForgotPassword)
    fun inject(signupTeacherActivity: SignupTeacherActivity)
    fun inject(resoucesActivity: ResoucesActivity)

    @Component.Builder
    interface Builder {
        fun build(): Dicomponent

        @BindsInstance
        fun application(application: Application): Builder

    }
}