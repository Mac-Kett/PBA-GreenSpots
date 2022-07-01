package com.example.pba_greenspots.fragments

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.TextPaint
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView

import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.pba_greenspots.R
import com.example.pba_greenspots.entities.Reserve
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details_reserve.*
import java.io.File
import java.io.FileOutputStream


class DetailsReserveFragment : Fragment() {

    //private var db = Firebase.firestore
    private var picasso = Picasso.get()
    //private var listaDB : MutableList<Reserve> = mutableListOf()
    private lateinit var v : View
    private lateinit var name : TextView
    private lateinit var muni : TextView
    private lateinit var acceso : TextView
    private lateinit var pubPriv : TextView
    private lateinit var mail : TextView
    private lateinit var telefono: TextView
    private lateinit var actividades : TextView
    private lateinit var entrada : TextView
    private lateinit var zonaServicios : TextView
    private lateinit var horario : TextView
    private lateinit var flora : TextView
    private lateinit var fauna : TextView
    private lateinit var geologia : TextView
    private lateinit var superficie : TextView
    private lateinit var btnPdf : Button
    private lateinit var ivImage : ImageView

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

        ivImage = v.findViewById(R.id.ivImage)
        ivImage.clipToOutline=true

        //trato las imagenes
        ivImage.visibility = View.GONE
        obtenerImagenes(reserve)


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

    private fun obtenerImagenes(reserve:Reserve){
        val refReserve = FirebaseStorage.getInstance().getReference().child("images").child(reserve.id)

        refReserve.listAll()
                .addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful){
                        var listReferences:List<StorageReference> = it.result.items
                        if (listReferences.isNotEmpty()){
                            for (ref:StorageReference in listReferences){
                                obtenerDownloadURL(ref)
                            }
                        }
                    }else{
                        lySuperiorImagenes.visibility = View.GONE
                        Toast.makeText(context, "Ha ocurrido un error obteniendo las imagenes!", Toast.LENGTH_LONG).show()
                        Log.d(tag, it.exception?.message, null)
                    }
                })
    }

    private fun obtenerDownloadURL(ref: StorageReference) {
        ref.downloadUrl
                .addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful) {
                        generarImagen(it.result)
                    }else{
                        Toast.makeText(context, "Ha ocurrido un error descargando la imagen: "+ref, Toast.LENGTH_LONG).show()
                        Log.d(tag, it.exception?.message, null)
                    }
                })
    }

    private fun generarImagen(uri: Uri) {
        var imageView:ImageView = ImageView(context)
        var params = LinearLayout.LayoutParams(300, 200)
        params.setMargins(5)
        params.gravity = Gravity.CENTER
        imageView.setBackgroundResource(R.drawable.border_black_round)
        imageView.clipToOutline = true
        imageView.layoutParams = params
        var progressBar:ProgressBar = ProgressBar(context)


        picasso
                .load(uri)
                .fit()
                .placeholder(com.google.android.material.R.drawable.mtrl_ic_error)
                .into(imageView)

        imageView.setOnClickListener {
            var circularProgressDrawable:CircularProgressDrawable
            circularProgressDrawable = context?.let { it1 -> CircularProgressDrawable(it1) }!!
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            ivImage.visibility = View.VISIBLE
            picasso.load(uri)
                    .fit()
                    .placeholder(circularProgressDrawable)
                    .into(ivImage)
        }


        lyImagesContainer.addView(imageView)
    }

}

