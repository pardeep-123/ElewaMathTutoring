package com.elewamathtutoring.dagger


import com.elewamathtutoring.Util.Validator
import com.elewamathtutoring.api.RestApiInterface
import com.elewamathtutoring.network.ServiceGenerator
import dagger.Provides
import javax.inject.Singleton



@dagger.Module
class DiModule {



    @Provides
    @Singleton
    fun validation(): Validator
    {
        return Validator()
    }
    /*  @Provides
     @Singleton
     fun sharedPref(): SharedPref
     {
         return SharedPref()
     }*/

    @Provides
    @Singleton
    fun getService(): RestApiInterface
    {
        return ServiceGenerator.createService(RestApiInterface::class.java)
    }


}