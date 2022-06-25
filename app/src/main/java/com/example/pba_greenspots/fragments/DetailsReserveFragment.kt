package com.example.pba_greenspots.fragments

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.pba_greenspots.R
import com.example.pba_greenspots.entities.Reserve
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.File
import java.io.FileOutputStream


class DetailsReserveFragment : Fragment() {

    var db = Firebase.firestore
    private var listaDB : MutableList<Reserve> = mutableListOf()
    lateinit var v : View
    lateinit var name : TextView
    lateinit var muni : TextView
    lateinit var acceso : TextView
    lateinit var pubPriv : TextView
    lateinit var mail : TextView
    lateinit var telefono: TextView
    lateinit var actividades : TextView
    lateinit var entrada : TextView
    lateinit var zonaServicios : TextView
    lateinit var horario : TextView
    lateinit var flora : TextView
    lateinit var fauna : TextView
    lateinit var geologia : TextView
    lateinit var superficie : TextView

    lateinit var btnPdf : Button


    companion object {
        fun newInstance() = DetailsReserveFragment()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lateinit var reserve : Reserve
         reserve = (arguments?.getSerializable("reserve") as Reserve?)!!
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_details_reserve, container, false)

        // referencio los textView
        name = v.findViewById(R.id.nameReserve)
        muni = v.findViewById(R.id.muniReserve)
        acceso = v.findViewById(R.id.accesoReserve)
        pubPriv = v.findViewById(R.id.pubPrivReserve)
        mail = v.findViewById(R.id.emailReserve)
        telefono = v.findViewById(R.id.telReserve)
        actividades = v.findViewById(R.id.actividadesReserve)
        entrada = v.findViewById(R.id.costoEntradaReserve)
        zonaServicios = v.findViewById(R.id.serviciosReserve)
        horario = v.findViewById(R.id.horariosReserve)
        flora = v.findViewById(R.id.floraReserve)
        fauna = v.findViewById(R.id.faunaReserve)
        geologia = v.findViewById(R.id.geoloReserve)
        superficie = v.findViewById(R.id.superfReserve)

        //referencio el boton de descarga
        btnPdf = v.findViewById(R.id.generarPDF)

        // seteo los TextView con los datos de la db
        name.text = reserve.nombreUnidad
        muni.text = reserve.municipio
        acceso.text = reserve.acceso
        pubPriv.text = reserve.administracionPublicaPrivada
        mail.text = reserve.correo
        telefono.text = reserve.telefono
        actividades.text = reserve.actividadesDelArea
        entrada.text = reserve.ingresoGratuitoPago
        zonaServicios.text = reserve.zonaServicios
        horario.text = reserve.horarios
        flora.text = reserve.flora
        fauna.text = reserve.fauna
        geologia.text = reserve.geologia
        superficie.text = reserve.superficie

        //pregunto si estan los permisos
        if(checkPermission()) {
            Toast.makeText(activity, "Permiso Aceptado", Toast.LENGTH_LONG)
        } else {
            //requestPermissions()
            Toast.makeText(activity, "No estan los perimsos", Toast.LENGTH_LONG)
        }

        btnPdf.setOnClickListener(View.OnClickListener {
            generarPdf()
        })


       return v
    }

    private fun checkPermission(): Boolean {
        val permission1 = ContextCompat.checkSelfPermission(requireContext(), WRITE_EXTERNAL_STORAGE)
        val permission2 = ContextCompat.checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE)
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    private fun generarPdf() {
        var pdfDocument = PdfDocument()
        var paint = Paint()
        var titulo = TextPaint()
        var descripcion = TextPaint()
        var municipio = TextPaint()


        var paginaInfo = PdfDocument.PageInfo.Builder(816, 1054, 1).create()
        var pagina1 = pdfDocument.startPage(paginaInfo)

        var canvas = pagina1.canvas

        var bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_people)
        var bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80,80, false)
        canvas.drawBitmap(bitmapEscala, 368f, 20f, paint)

        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        titulo.textSize = 20f
        canvas.drawText(name.text.toString(), 10f, 150f, titulo)

        descripcion.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        descripcion.textSize = 14f
        var arrDescripcion = muni.text.toString().split("\n")+
                acceso.text.toString().split("\n")+
                pubPriv.text.toString().split("\n")+
                mail.text.toString().split("\n")+
                telefono.text.toString().split("\n")+
                actividades.text.toString().split("\n")+
                entrada.text.toString().split("\n")+
                zonaServicios.text.toString().split("\n")+
                horario.text.toString().split("\n")+
                flora.text.toString().split("\n")+
                fauna.text.toString().split("\n")+
                geologia.text.toString().split("\n")+
                superficie.text.toString().split("\n")
        ////////////////////////////////////////////////////////////////////////////////////////////
        var y = 200f
        for (item in arrDescripcion) {
            canvas.drawText(item, 10f, y, descripcion)
            y += 15
        }

        pdfDocument.finishPage(pagina1)


        //val file = File(Environment.getExternalStorageDirectory(), "Reserva.pdf")
        val file = File(Environment.getExternalStorageDirectory(), "Reserva"+name.text.toString()+".pdf")
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(activity, "Se creo el PDF correctamente", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        pdfDocument.close()

    }

}

