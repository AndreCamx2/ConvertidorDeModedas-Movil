package com.AndresFlorezBustamante.convertidormoneda;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Tasas de cambio respecto a 1 Dólar (USD), solo para el ejercicio
    private final String[] monedas = {"USD", "EUR", "COP", "MXN"};
    private final double[] tasasRespectoAUSD = {1.0, 0.92, 4050.0, 17.5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias a los componentes del layout
        EditText campoCantidad = findViewById(R.id.campoCantidad);
        Spinner spinnerOrigen = findViewById(R.id.spinnerOrigen);
        Spinner spinnerDestino = findViewById(R.id.spinnerDestino);
        Button botonConvertir = findViewById(R.id.botonConvertir);
        TextView etiResultado = findViewById(R.id.etiResultado);

        // Llenar los dos Spinners con la lista de monedas
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                monedas
        );
        spinnerOrigen.setAdapter(adaptador);
        spinnerDestino.setAdapter(adaptador);

        // Evento del botón
        botonConvertir.setOnClickListener(view -> {
            String textoCantidad = campoCantidad.getText().toString();

            if (textoCantidad.isEmpty()) {
                etiResultado.setText("Por favor ingresa una cantidad");
                return;
            }

            double cantidad = Double.parseDouble(textoCantidad);

            int posOrigen = spinnerOrigen.getSelectedItemPosition();
            int posDestino = spinnerDestino.getSelectedItemPosition();

            // Convertimos primero a USD, y de USD a la moneda destino
            double enUSD = cantidad / tasasRespectoAUSD[posOrigen];
            double resultado = enUSD * tasasRespectoAUSD[posDestino];

            String mensaje = String.format(
                    "%.2f %s = %.2f %s",
                    cantidad, monedas[posOrigen],
                    resultado, monedas[posDestino]
            );
            etiResultado.setText(mensaje);
        });
    }
}