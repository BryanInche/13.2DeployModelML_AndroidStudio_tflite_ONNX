package com.example.redneuronalapptflite; //indica que la clase MainActivity está dentro del paquete com.example.redneuronalapptflite

import androidx.appcompat.app.AppCompatActivity; //AppCompatActivity es una clase base para actividades en Android

//import android.content.res.AssetManager;
import android.os.Bundle; //Bundle es un contenedor para pasar datos entre componentes de la aplicación, como entre actividades.

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.tensorflow.lite.Interpreter;
import java.io.IOException;
//import java.nio.MappedByteBuffer;
import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
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


public class MainActivity extends AppCompatActivity {
    private EditText editTextVolumeCarguio, editTextCapacidadCarguio, editTextCapacidadAcarreo, editTextDensidad, editTextVolumenAcarreo;
    private TextView textViewPrediction;
    private Button buttonPredict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar los EditText
        editTextVolumeCarguio = findViewById(R.id.editTextVolumeCarguio);
        editTextCapacidadCarguio = findViewById(R.id.editTextCapacidadCarguio);
        editTextCapacidadAcarreo = findViewById(R.id.editTextCapacidadAcarreo);
        editTextDensidad = findViewById(R.id.editTextDensidad);
        editTextVolumenAcarreo = findViewById(R.id.editTextVolumenAcarreo);

        // Inicializar el botón de predicción
        buttonPredict = findViewById(R.id.buttonPredict);
        buttonPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                predict();
            }
        });

        // Inicializar el TextView para la predicción
        textViewPrediction = findViewById(R.id.textViewPrediction);
    }

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
    private void predict() {
        try {
            // Cargar el modelo TensorFlow Lite desde la carpeta de activos
            Interpreter.Options options = new Interpreter.Options();
            options.setNumThreads(4); // Opcional: ajusta el número de hilos según sea necesario
            Interpreter interpreter = new Interpreter(loadModelFile(), options);

            // Obtener los valores de entrada del usuario
            float VolumeCarguio = Float.parseFloat(editTextVolumeCarguio.getText().toString()); // ''capacidad_en_volumen_equipo_carguio_m3',
            float CapacidadCarguio = Float.parseFloat(editTextCapacidadCarguio.getText().toString()); // ''capacidad_en_peso_equipo_carguio',
            float CapacidadAcarreo = Float.parseFloat(editTextCapacidadAcarreo.getText().toString()); // ''capacidad_en_peso_equipo_acarreo',
            float Densidad = Float.parseFloat(editTextDensidad.getText().toString()); // 'densidad_inicial_poligono_creado_tn/m3',
            float VolumenAcarreo = Float.parseFloat(editTextVolumenAcarreo.getText().toString()); // 'capacidad_en_volumen_equipo_acarreo_m3'

            // Convertir los datos de entrada a un array unidimensional
            float[] inputData = {VolumeCarguio, CapacidadCarguio, CapacidadAcarreo, Densidad, VolumenAcarreo};

            // Crear un buffer de entrada con los datos
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

            // Liberar recursos del intérprete
            interpreter.close();
        } catch (IOException e) {
            // Manejar la excepción de carga del modelo
            e.printStackTrace();
        }
    }

}
