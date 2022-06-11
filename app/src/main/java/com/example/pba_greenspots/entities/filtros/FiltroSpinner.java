package com.example.pba_greenspots.entities.filtros;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.example.pba_greenspots.Reserve;

public abstract class FiltroSpinner implements IFiltro {
    private CheckBox checkBox;
    private Spinner spinner;

    FiltroSpinner(CheckBox checkBox, Spinner spinner){
        this.checkBox=checkBox;
        this.spinner=spinner;
        this.spinner.setEnabled(false);
        this.configurarCheckBoxSetListener();
        this.cargarSpinner();
    }

    public abstract boolean aplicarFiltro(Reserve reserva);

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public void setSpinner(Spinner spinner) {
        this.spinner = spinner;
    }

    public boolean estaActivado(){
        return checkBox.isChecked();
    }

    private void configurarCheckBoxSetListener(){
        this.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                spinner.setEnabled(isChecked);
            }
        });
    }

    abstract void cargarSpinner();

}
