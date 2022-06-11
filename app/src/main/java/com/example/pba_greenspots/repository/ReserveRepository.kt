package com.example.pba_greenspots.repository

import com.example.pba_greenspots.entities.Reserve

class ReserveRepository {


    var reserveList : MutableList<Reserve> = mutableListOf()

    fun getReserves () : MutableList<Reserve>{
        reserveList.add(Reserve("Luminoso y familiar", "San Andres de Giles", "sdf"))
        reserveList.add(Reserve("familiar", "San Giles", "sdf"))
        reserveList.add(Reserve("Luminoso", "Andres de Giles", "sdf"))
        reserveList.add(Reserve("Luminoso y familiar", "San Andres de Giles", "sdf"))
        reserveList.add(Reserve("familiar", "San Andres", "sdf"))
        reserveList.add(Reserve("Luminoso", "San Giles", "sdf"))
        reserveList.add(Reserve("Luminoso y familiar", "San Giles", "sdf"))
        reserveList.add(Reserve("familiar", "San Andres", "sdf"))
        reserveList.add(Reserve("Luminoso", "San Andres de Giles", "sdf"))

        return reserveList
    }

}