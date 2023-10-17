package com.example.nbatrivial.utils

sealed class UIEvent {
    data class ShowSnackBar(val message: String) : UIEvent()
}
