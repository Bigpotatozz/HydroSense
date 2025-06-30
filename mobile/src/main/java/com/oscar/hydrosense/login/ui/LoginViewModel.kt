package com.oscar.hydrosense.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oscar.hydrosense.login.data.network.request.LoginRequest
import com.oscar.hydrosense.login.data.network.response.LoginResponse
import com.oscar.hydrosense.login.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private var _correo = MutableLiveData<String>();
    private var _contrasenia = MutableLiveData<String>();

    var correo: LiveData<String> = _correo;
    var contrasenia: LiveData<String> = _contrasenia;

    fun setCorreo (correo: String){
        _correo.value = correo;
    }
    fun setContrasenia (contrasenia: String){
        _contrasenia.value = contrasenia;
    }

    private var _response = MutableLiveData<LoginResponse>();

    fun login(correo: String, contrasenia: String){
        val loginRequest = LoginRequest(correo, contrasenia);

        viewModelScope.launch {
           _response.value = loginUseCase.invoke(loginRequest);
        }
    }
}