package com.example.pba_greenspots.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pba_greenspots.R
import com.example.pba_greenspots.adapters.ReserveAdapter
import com.example.pba_greenspots.repository.ReserveRepository
import com.example.pba_greenspots.viewmodels.ReservesViewModel
import com.google.android.material.snackbar.Snackbar

class ReservesFragment : Fragment() {

    companion object {
        fun newInstance() = ReservesFragment()
    }

    private lateinit var viewModel: ReservesViewModel
    lateinit var v: View
    lateinit var recycler : RecyclerView
    lateinit var adapter : ReserveAdapter
    private val reserveRepository = ReserveRepository ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_reserves2, container, false)
        recycler = v.findViewById(R.id.recReserve)
        return v
    }

    override fun onStart() {
        super.onStart()
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = ReserveAdapter(reserveRepository.getReserves()){pos->
            onItemClick(pos)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReservesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun onItemClick (position : Int){
        Snackbar.make(v, reserveRepository.getReserves()[position].location, Snackbar.LENGTH_SHORT).show()
    }

}