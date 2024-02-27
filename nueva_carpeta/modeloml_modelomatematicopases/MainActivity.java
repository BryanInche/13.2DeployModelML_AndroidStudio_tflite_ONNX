//Librerias necesarias para la Aplicacion
package com.example.modeloml_modelomatematicopases;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity; //AppCompatActivity es una clase base para actividades en Android

//import android.content.res.AssetManager;
//import android.os.Bundle; //Bundle es un contenedor para pasar datos entre componentes de la aplicación, como entre actividades.

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.tensorflow.lite.Interpreter;
import java.io.IOException;
//import java.nio.MappedByteBuffer;
import java.nio.ByteBuffer;
//import android.content.res.AssetFileDescriptor;

import java.io.InputStream;
//import java.nio.channels.FileChannel;
//import java.io.FileDescriptor;
//import java.io.FileInputStream;

//import java.nio.channels.FileChannel.MapMode;
//import java.io.File;
//import java.io.FileOutputStream;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
//import android.widget.TextView;
import android.widget.CheckBox;

//Metodo para definir variables generales, y las que ingresara el usuario o se recibiran como INPUT
public class MainActivity extends AppCompatActivity {
    private EditText editTextCapacidadVolumenCarguio,editTextCapacidadPesoCarguio,editTextCapacidadPesoAcarreo,editTextDensidadInicial,editTextCapacidadVolumenAcarreo;
    private TextView textViewPrediction, textViewPaseadicional;
    private Button buttonPredict, buttonAgregarPase;
    private CheckBox checkboxTieneCamion; // Variable de Nombre "isspot" de la tabla tp.palas
    private int pasesAdicionales = 0; // Contador para agregar pases adicionales


    //1. Método protegido que no devuelve ningún valor (void). Este método es un método de ciclo de vida de una
    // actividad en Android.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar los EditText
        editTextCapacidadVolumenCarguio = findViewById(R.id.editTextCapacidadVolumenCarguio);
        editTextCapacidadPesoCarguio = findViewById(R.id.editTextCapacidadPesoCarguio);
        editTextCapacidadPesoAcarreo = findViewById(R.id.editTextCapacidadPesoAcarreo);
        editTextDensidadInicial = findViewById(R.id.editTextDensidadInicial);
        editTextCapacidadVolumenAcarreo = findViewById(R.id.editTextCapacidadVolumenAcarreo);

        // Asocia el CheckBox definido en el layout con la variable checkboxTieneCamion
        checkboxTieneCamion = findViewById(R.id.checkboxTieneCamion);

        // Inicializar el botón de predicción
        buttonPredict = findViewById(R.id.buttonPredict);
        buttonPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamar a predict() sin aumentar el pase shouldIncreasePass establecido en false
                predict(false);
            }
        });

        // Inicializar el botón para agregar 1 pase
        buttonAgregarPase = findViewById(R.id.buttonAgregarPase);
        buttonAgregarPase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamar a predict() con aumento de 1 pase shouldIncreasePass establecido en true
                predict(true);
            }
        });


        // Inicializar el TextView para la predicción
        textViewPrediction = findViewById(R.id.textViewPrediction);

        // Inicializar el TextView para la predicción
        textViewPaseadicional = findViewById(R.id.textViewPaseadicional);

    }

    //2. Método privado que devuelve un objeto ByteBuffer. Su propósito es cargar el archivo del modelo TensorFlow
    // Lite desde la carpeta de activos y devolverlo como un ByteBuffer. Puede lanzar una excepción IOException
    // si ocurre un error durante la carga del archivo.
    private ByteBuffer loadModelFile() throws IOException {
        // Cargar el archivo del modelo TensorFlow Lite desde la carpeta de activos
        ByteBuffer buffer = null;
        try (InputStream inputStream = getAssets().open("modelrnn_vfinal.tflite")) {
            int modelSize = inputStream.available();
            byte[] modelBuffer = new byte[modelSize];
            inputStream.read(modelBuffer);
            buffer = ByteBuffer.allocateDirect(modelSize);
            buffer.put(modelBuffer);
            buffer.rewind();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    //3. Método privado, pero no devuelve ningún valor (void). En cambio, realiza una serie de acciones relacionadas con la predicción de Modelo ML
    private void predict(boolean shouldIncreasePass) {
        try {
            // Cargar el modelo TensorFlow Lite desde la carpeta de activos
            Interpreter.Options options = new Interpreter.Options();
            options.setNumThreads(4); // Opcional: ajusta el número de hilos según sea necesario
            Interpreter interpreter = new Interpreter(loadModelFile(), options);

            // Obtener los valores de entrada del usuario
            float CapacidadVolumenCarguio = Float.parseFloat(editTextCapacidadVolumenCarguio.getText().toString());
            float CapacidadPesoCarguio = Float.parseFloat(editTextCapacidadPesoCarguio.getText().toString());
            float CapacidadPesoAcarreo = Float.parseFloat(editTextCapacidadPesoAcarreo.getText().toString());
            float DensidadInicial = Float.parseFloat(editTextDensidadInicial.getText().toString());
            float CapacidadVolumenAcarreo = Float.parseFloat(editTextCapacidadVolumenAcarreo.getText().toString());

            // Obtener el valor de tiene_camion_cuadrado desde el CheckBox
            boolean tiene_camion_cuadrado = checkboxTieneCamion.isChecked();


            // restablecer el contador pasesAdicionales a 0 cada vez que se realice una nueva predicción
            if (!shouldIncreasePass) {
                pasesAdicionales = 0;
            }

            // Convertir los datos de entrada a un array unidimensional(Variables que ingresan al Modelo ML
            float[] inputData = {CapacidadVolumenCarguio, CapacidadPesoCarguio, CapacidadPesoAcarreo, DensidadInicial, CapacidadVolumenAcarreo};

            // Crear un buffer de entrada con los datos (5 variables)
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 5}, DataType.FLOAT32);
            inputFeature0.loadArray(inputData);

            // Ejecutar la inferencia del modelo y obtener los resultados
            float[][] output = new float[1][1];
            interpreter.run(inputFeature0.getBuffer(), output);

            // Procesar los resultados (aquí asumimos que el modelo tiene un solo tensor de salida)
            float prediction = output[0][0];

            // Redondear la predicción al entero más cercano
            int roundedPrediction = Math.round(prediction);

            // Mostrar la predicción en un TextView
            textViewPrediction.setText("Número de Pases Predicho: " + roundedPrediction);

            // Calcular tonelaje acumulado anterior a la carga
            float tonelaje_acumulado_anterior_carga = CapacidadPesoCarguio * (roundedPrediction - 1);

            // Calcular tonelaje acumulado de la carga
            float tonelaje_acumulado_carga = CapacidadPesoCarguio * roundedPrediction;

            // Calcular si se necesita un pase adicional: Variables para el Modelo Matematico
            boolean needsAdditionalPass = calculateAdditionalPass(CapacidadPesoCarguio, CapacidadVolumenCarguio, CapacidadPesoAcarreo, DensidadInicial,
                    tiene_camion_cuadrado, tonelaje_acumulado_anterior_carga, tonelaje_acumulado_carga,
                    CapacidadVolumenAcarreo);

            // Mostrar si se necesita un pase adicional en otro TextView
            textViewPaseadicional.setText("¿Necesita un pase adicional?: " + (needsAdditionalPass ? "Sí" : "No"));

            // Si se indica que debe aumentarse el pase, incrementar el número de pases predicho y recalcular variables
            if (shouldIncreasePass) {
                // Incrementar el contador de pases adicionales
                pasesAdicionales++;

                // Incrementar el número de pases predicho con el contador pasesAdicionales
                roundedPrediction += pasesAdicionales;

                // Recalcular las variables necesarias según el nuevo número de pases predicho
                tonelaje_acumulado_anterior_carga = CapacidadPesoCarguio * (roundedPrediction - 1);
                tonelaje_acumulado_carga = CapacidadPesoCarguio * roundedPrediction;

                // Verificar si se necesita un pase adicional con el nuevo número de pases predicho
                needsAdditionalPass = calculateAdditionalPass(CapacidadPesoCarguio, CapacidadVolumenCarguio, CapacidadPesoAcarreo, DensidadInicial,
                        tiene_camion_cuadrado, tonelaje_acumulado_anterior_carga, tonelaje_acumulado_carga,
                        CapacidadVolumenAcarreo);

                // Actualizar los textos en los TextView correspondientes
                textViewPrediction.setText("Número de Pases_total: " + roundedPrediction);
                textViewPaseadicional.setText("¿Necesita un pase adicional?: " + (needsAdditionalPass ? "Sí" : "No"));
            }

        } catch (IOException e) {
            // Manejar la excepción de carga del modelo
            e.printStackTrace();
        }
    }



    //Método privado que devuelve un valor booleano (true o false)
    private boolean calculateAdditionalPass(float capacidad_en_peso_equipo_carguio, float capacidad_en_volumen_equipo_carguio_m3,
                                            float capacidad_en_peso_equipo_acarreo, float densidad_inicial_poligono_creado_tn_m3,
                                            boolean tiene_camion_cuadrado, float tonelaje_acumulado_anterior_carga,
                                            float tonelaje_acumulado_carga, float capacidad_en_volumen_equipo_acarreo_m3) {

        // Constantes
        float factor_llenado_peso = 0.9f;
        float factor_llenado_volumen = 0.9f;
        float payload = 1.05f;
        float capacidad_llenado_vol = 1.05f;

        // Verificar si el denominador es diferente de cero
        float cargado_real_cuchara_ton;
        if (densidad_inicial_poligono_creado_tn_m3 != 0) {
            if (((capacidad_en_peso_equipo_carguio * factor_llenado_peso) / densidad_inicial_poligono_creado_tn_m3) >
                    (capacidad_en_volumen_equipo_carguio_m3 * factor_llenado_volumen)) {
                cargado_real_cuchara_ton = capacidad_en_volumen_equipo_carguio_m3 * densidad_inicial_poligono_creado_tn_m3 * factor_llenado_volumen;
            } else {
                cargado_real_cuchara_ton = capacidad_en_peso_equipo_carguio * factor_llenado_peso;
            }
        } else {
            // Si el denominador es cero, establecer cargado_real_cuchara_ton en cero
            cargado_real_cuchara_ton = 0;
        }

        // Verificar si cargado_real_cuchara_ton es diferente de cero antes de realizar la división
        float pases_depositados = (cargado_real_cuchara_ton != 0) ? (tonelaje_acumulado_carga / cargado_real_cuchara_ton) : 0;


        float calculo_promedio_densidad_ton_m3;
        if (capacidad_en_volumen_equipo_carguio_m3 == 0) {
            calculo_promedio_densidad_ton_m3 = densidad_inicial_poligono_creado_tn_m3;
        } else {
            calculo_promedio_densidad_ton_m3 = (cargado_real_cuchara_ton == 0) ? 0 :
                    (densidad_inicial_poligono_creado_tn_m3 * (tonelaje_acumulado_carga - tonelaje_acumulado_anterior_carga)) /
                            cargado_real_cuchara_ton;
        }

        // Verificar si pases_depositados es diferente de cero
        float denominador_pases = capacidad_en_volumen_equipo_carguio_m3 * pases_depositados;
        float densidad_material_pase_ton_m3;
        if (denominador_pases != 0) {
            // Calcular densidad_material_pase_ton_m3
            densidad_material_pase_ton_m3 = (float) ((densidad_inicial_poligono_creado_tn_m3 + calculo_promedio_densidad_ton_m3 +
                    tonelaje_acumulado_carga / denominador_pases) / 3);
        } else {
            densidad_material_pase_ton_m3 = 0;
        }

        // Calcular tonelaje_requerido_camion_ton
        float tonelaje_requerido_camion_ton;
        if (densidad_material_pase_ton_m3 != 0) {
            if (((capacidad_en_peso_equipo_acarreo * payload) / densidad_material_pase_ton_m3) >
                    (capacidad_en_volumen_equipo_acarreo_m3 * capacidad_llenado_vol)) {
                tonelaje_requerido_camion_ton = capacidad_en_volumen_equipo_acarreo_m3 * densidad_material_pase_ton_m3 * capacidad_llenado_vol;
            } else {
                tonelaje_requerido_camion_ton = capacidad_en_peso_equipo_acarreo * payload;
            }
        } else {
            tonelaje_requerido_camion_ton = 0;
        }

        // Calcular numero_cucharadas_requeridos
        float numero_cucharadas_requeridos = (cargado_real_cuchara_ton != 0) ?
                Math.round(tonelaje_requerido_camion_ton / cargado_real_cuchara_ton * 100) / 100.0f : 0; //se redondeo a 2 decimales al dividir entre 100

        // Calcular diferencia
        float diferencia = numero_cucharadas_requeridos - pases_depositados;

        // Verificar si necesita_un_pase_mas
        boolean necesita_un_pase_mas;
        if (tiene_camion_cuadrado) {
            if (diferencia > 0.8) {
                necesita_un_pase_mas = true;
            } else {
                necesita_un_pase_mas = false;
            }
        } else {
            if (diferencia > 0.2) {
                necesita_un_pase_mas = true;
            } else {
                necesita_un_pase_mas = false;
            }
        }

        return necesita_un_pase_mas;

    }

}
