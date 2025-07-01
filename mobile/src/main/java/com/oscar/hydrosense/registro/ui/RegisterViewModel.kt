package com.oscar.hydrosense.registro.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oscar.hydrosense.registro.data.network.request.RegisterRequest
import com.oscar.hydrosense.registro.domain.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase): ViewModel() {

    private var _nombre = MutableLiveData<String>();
    private var _apellido_paterno = MutableLiveData<String>();
    private var _apellido_materno = MutableLiveData<String>();
    private var _telefono = MutableLiveData<String>();
    private var _edad = MutableLiveData<String>();
    private var _pais = MutableLiveData<String>();
    private var _correo = MutableLiveData<String>();
    private var _contrasenia = MutableLiveData<String>();

    var nombre: LiveData<String> = _nombre;
    var apellido_paterno: LiveData<String> = _apellido_paterno;
    var apellido_materno: LiveData<String> = _apellido_materno;
    var telefono: LiveData<String> = _telefono;
    var edad: LiveData<String> = _edad;
    var pais: LiveData<String> = _pais;
    var correo: LiveData<String> = _correo;
    var contrasenia: LiveData<String> = _contrasenia;


    fun setNombre (nombre: String){
        _nombre.value = nombre;
    }
    fun setApellido_paterno (apellido_paterno: String){
        _apellido_paterno.value = apellido_paterno;
    }
    fun setApellido_materno (apellido_materno: String){
        _apellido_materno.value = apellido_materno;
    }
    fun setTelefono (telefono: String){
        _telefono.value = telefono;
    }

    fun setEdad (edad: String){
        _edad.value = edad;
    }
    fun setPais (pais: String){
        _pais.value = pais;
    }
    fun setCorreo (correo: String){
        _correo.value = correo;
    }

    fun setContrasenia (contrasenia: String){
        _contrasenia.value = contrasenia;
    }





    fun registrarUsuario(nombre: String, correo: String, contrasenia: String, telefono: String, edad: String, pais: String, apellido_paterno: String, apellido_materno: String){

        val usuario = RegisterRequest(nombre, apellido_paterno, apellido_materno, telefono, edad.toInt(), pais, correo, contrasenia);

        viewModelScope.launch {
            registerUseCase.invoke(usuario);
        }

    }

}