package com.example.pba_greenspots.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pba_greenspots.R
import com.example.pba_greenspots.Reserve
import com.example.pba_greenspots.adapters.ReserveAdapter
import com.example.pba_greenspots.entities.filtros.*
import com.example.pba_greenspots.viewmodels.ReservesViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ReservesFragment : Fragment() {

    companion object {
        fun newInstance() = ReservesFragment()
    }

    private lateinit var viewModel: ReservesViewModel
    private lateinit var v: View
    private lateinit var recycler : RecyclerView

    private lateinit var tvFiltros : TextView
    private lateinit var btnBuscar : Button
    private lateinit var cntFiltros : ViewGroup
    private lateinit var svBuscador : SearchView

   // private val reserveRepository = ReserveRepository ()
    private var db = Firebase.firestore
    private var listaDB : MutableList<Reserve> = mutableListOf()
    private var listaFiltros : MutableList<IFiltro> = mutableListOf()
    private var reservasMostrar : MutableList<Reserve> = mutableListOf()
    //var reserveList : MutableList<Reserve> = mutableListOf()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_reserves2, container, false)
        recycler = v.findViewById(R.id.recReserve)

        tvFiltros = v.findViewById(R.id.tvFiltros)
        btnBuscar = v.findViewById(R.id.btnBuscar)
        cntFiltros = v.findViewById(R.id.cntFiltros)
        svBuscador = v.findViewById(R.id.svBuscador)
        inicializarFiltros()


        return v
    }

    private fun inicializarFiltros() {
        listaFiltros.add(FiltroNombreUnidad(svBuscador.query.toString()))
        listaFiltros.add(FiltroMunicipio(v.findViewById(R.id.cbMunicipio),v.findViewById(R.id.spnMunicipio)))
        listaFiltros.add(FiltroDificultadSenderismo(v.findViewById(R.id.cbDificultadSenderismo), v.findViewById(R.id.spnDificultadSenderismo)))
        listaFiltros.add(FiltroZonaServicios(v.findViewById(R.id.cbZonaServicios), v.findViewById(R.id.spnZonaServicios)))
        listaFiltros.add(FiltroGratuitoPago(v.findViewById(R.id.cbGratuitoPago), v.findViewById(R.id.spnGratuitoPago)))
        listaFiltros.add(FiltroSenializacionSenderos(v.findViewById(R.id.cbSenializacionSenderos), v.findViewById(R.id.spnSenializacionSenderos)))
        listaFiltros.add(FiltroTipoAdministracion(v.findViewById(R.id.cbTipoAdministracion), v.findViewById(R.id.spnTipoAdministracion)))
    }

//    private fun obtenerReservasNaturales() {
//
//    db.collection("Reverves")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    listaDB.add(document.toObject())
//                }
//                Log.d("doc", listaDB.toString())
//            }
//            .addOnFailureListener { exception ->
//                Log.d("doc", "Error getting documents.", exception)
//            }
//    }

    override fun onStart() {
        super.onStart()

        //obtenerReservasNaturales();
        db.collection("Reserves")
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    Log.d("test", document.toObject<Reserve>().toString())
                   listaDB.add(document.toObject())
                }
                recycler.adapter = ReserveAdapter(listaDB){pos->
                    onItemClick(pos)
                }
                Log.d("test", "123")
            }
            .addOnFailureListener { exception ->
                Log.d("test", "Error getting documents.", exception)
            }
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(context)
       // recycler.adapter = ReserveAdapter(reserveRepository.getReserves()){pos->
        tvFiltros.setOnClickListener {
            showHide(cntFiltros)
        }
        btnBuscar.setOnClickListener{
            buscar()
        }
        svBuscador.setOnSearchClickListener{
            buscar()
        }


    }

    private fun buscar() {
        //1) se limpia la lista; 2) por cada reserva en la lista original se compara con cada Filtro hasta que alguno no sea compatible. Si todos lo son, se agrega a la lista.

            reservasMostrar.clear()
            var aplica : Boolean
            for (reserva : Reserve in listaDB){
                aplica=true
                var i = 0
                while (i<listaFiltros.size && aplica){
                    if(listaFiltros[i].estaActivado()){
                        aplica=listaFiltros[i].aplicarFiltro(reserva)
                    }
                    i++
                }
                if (aplica){
                    Log.d(tag, reserva.toString())
                    reservasMostrar.add(reserva)
                }
                //Log.d(tag, reserva.toString())

            }
            //Le seteo al recycler la lista de reservas ya filtrada.
            recycler.adapter = ReserveAdapter(reservasMostrar){pos->
                onItemClick(pos)
            }




    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReservesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun onItemClick (position : Int){ //deberia llevar al fragment detalle de la reseva
    Snackbar.make(v, "ReservaOK", Snackbar.LENGTH_SHORT).show()
    //Snackbar.make(v, reserveRepository.getReserves()[position].municipio, Snackbar.LENGTH_SHORT).show()
    }

    fun showHide(view: View) {
        view.visibility = if (view.visibility == View.VISIBLE){
            View.GONE
        } else{
            View.VISIBLE
        }
    }

    fun onClickCheckBox(checkBox: CheckBox, spinner : Spinner){
        spinner.isEnabled = checkBox.isChecked
    }

    fun enableDisable(view: View) {
        view.isEnabled
    }


}