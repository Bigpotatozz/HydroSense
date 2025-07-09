package com.oscar.hydrosense.login.ui

import android.util.Log
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
    private var _response = MutableLiveData<LoginResponse?>();
    private var _loginState = MutableLiveData<Boolean>();
    private var _intento = MutableLiveData<Int>(0);


    var correo: LiveData<String> = _correo;
    var contrasenia: LiveData<String> = _contrasenia;
    var response: LiveData<LoginResponse?> = _response;
    var loginState: LiveData<Boolean> = _loginState;
    var intento: LiveData<Int> = _intento;

    fun setCorreo (correo: String){
        _correo.value = correo;
    }
    fun setContrasenia (contrasenia: String){
        _contrasenia.value = contrasenia;
    }

    fun setIntento (){
        _intento.value = (_intento.value ?: 0) + 1
    }



    fun login(correo: String, contrasenia: String){
        val loginRequest = LoginRequest(correo, contrasenia);

        viewModelScope.launch {
           val response = loginUseCase.invoke(loginRequest);
            _response.value = response;

            if(_response.value != null){
                _loginState.value = true;
                _intento.value = 0;
            }else{
                _loginState.value = false
            }

        }
    }
}