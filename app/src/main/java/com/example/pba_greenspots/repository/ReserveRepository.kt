package com.example.pba_greenspots.repository

import com.example.pba_greenspots.entities.Reserve

class ReserveRepository {


    var reserveList : MutableList<Reserve> = mutableListOf()

    fun getReserves () : MutableList<Reserve>{
        reserveList.add(Reserve("RESERVA1", "Luminoso y familiar", "San Andres de Giles"))
        reserveList.add(Reserve("RESERVA2", "familiar", "San Giles"))
        reserveList.add(Reserve("RESERVA3", "Luminoso", "Andres de Giles"))
        reserveList.add(Reserve("RESERVA4", "Luminoso y familiar", "San Andres de Giles"))
        reserveList.add(Reserve("RESERVA5", "familiar", "San Andres"))
        reserveList.add(Reserve("RESERVA6", "Luminoso", "San Giles"))
        reserveList.add(Reserve("RESERVA7", "Luminoso y familiar", "San Giles"))
        reserveList.add(Reserve("RESERVA8", "familiar", "San Andres"))
        reserveList.add(Reserve("RESERVA9", "Luminoso", "San Andres de Giles"))

        return reserveList
    }

}