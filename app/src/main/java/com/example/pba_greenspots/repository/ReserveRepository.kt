package com.example.pba_greenspots.repository

import com.example.pba_greenspots.entities.Reserve
import com.example.pba_greenspots.entities.Reserve2

class ReserveRepository {


    var reserveList : MutableList<Reserve2> = mutableListOf()

    fun getReserves () : MutableList<Reserve2> {
        reserveList.add(Reserve2("Luminoso y familiar", "San Andres de Giles", "sdf"))
        reserveList.add(Reserve2("familiar", "San Giles", "sdf"))
        reserveList.add(Reserve2("Luminoso", "Andres de Giles", "sdf"))
        reserveList.add(Reserve2("Luminoso y familiar", "San Andres de Giles", "sdf"))
        reserveList.add(Reserve2("familiar", "San Andres", "sdf"))
        reserveList.add(Reserve2("Luminoso", "San Giles", "sdf"))
        reserveList.add(Reserve2("Luminoso y familiar", "San Giles", "sdf"))
        reserveList.add(Reserve2("familiar", "San Andres", "sdf"))
        reserveList.add(Reserve2("Luminoso", "San Andres de Giles", "sdf"))

        return reserveList
    }

}