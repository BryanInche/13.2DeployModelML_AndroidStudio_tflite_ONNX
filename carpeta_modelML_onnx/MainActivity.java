package com.example.api_pases_xgb_onnx;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;  // libreria de onnxruntime
import ai.onnxruntime.OrtSession;  // libreria de onnxruntime

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.FloatBuffer;  //libreria que convierte el arreglo de float[] a un FloatBuffer

import ai.onnxruntime.OrtException; // manejo de excepciones de onnxruntime

import android.util.Log;



public class MainActivity extends AppCompatActivity {

    private OrtEnvironment ortEnvironment;
    private OrtSession ortSession;

    private EditText inputTiempoCarga;
    private EditText inputCapacidadVolumen;
    private EditText inputDensidad;
    private EditText inputElevacion;
    private EditText inputTonelajeInicial;
    private EditText inputCantidadEquipos;
    private TextView textResultado;

    // Valores mínimos y máximos para el escalado
    private final float[] X_min = {-242063.328f, 12.0f, 0.0f, 464.0f, 0.0f, -1.0f};
    private final float[] X_max = {3142.382f, 27.0f, 4.241f, 837.0f, 20000000.0f, 8.0f};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar OrtEnvironment
        ortEnvironment = OrtEnvironment.getEnvironment();

        // Crear OrtSession
        ortSession = createORTSession(ortEnvironment);

        // Enlazar vistas
        inputTiempoCarga = findViewById(R.id.input_tiempo_carga);
        inputCapacidadVolumen = findViewById(R.id.input_capacidad_volumen);
        inputDensidad = findViewById(R.id.input_densidad);
        inputElevacion = findViewById(R.id.input_elevacion);
        inputTonelajeInicial = findViewById(R.id.input_tonelaje_inicial);
        inputCantidadEquipos = findViewById(R.id.input_cantidad_equipos);
        textResultado = findViewById(R.id.text_resultado);

        Button btnPredecir = findViewById(R.id.btn_predecir);
        btnPredecir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                predecir();
            }
        });

    }

    private OrtSession createORTSession(OrtEnvironment ortEnvironment) {
        try {
            // Cargar el modelo desde los recursos raw
            InputStream modelInputStream = getResources().openRawResource(R.raw.xgboost_v1);
            byte[] modelBytes = new byte[modelInputStream.available()];
            modelInputStream.read(modelBytes);
            modelInputStream.close();

            // Crear la OrtSession
            Log.d("MainActivity", "Creando la sesión del modelo...");
            return ortEnvironment.createSession(modelBytes);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", "Error al crear la sesión del modelo: " + e.getMessage());
            return null; // Manejo de errores
        }
    }

    private float[] minMaxScale(float[] data, float[] X_min, float[] X_max) {
        float[] dataScaled = new float[data.length];
        for (int i = 0; i < data.length; i++) {
            dataScaled[i] = (data[i] - X_min[i]) / (X_max[i] - X_min[i]);
        }
        return dataScaled;
    }
    private void predecir() {
        try {
            // Obtener entradas del usuario
            float tiempoCarga = Float.parseFloat(inputTiempoCarga.getText().toString());
            float capacidadVolumen = Float.parseFloat(inputCapacidadVolumen.getText().toString());
            float densidad = Float.parseFloat(inputDensidad.getText().toString());
            float elevacion = Float.parseFloat(inputElevacion.getText().toString());
            float tonelajeInicial = Float.parseFloat(inputTonelajeInicial.getText().toString());
            float cantidadEquipos = Float.parseFloat(inputCantidadEquipos.getText().toString());


            // Crear un tensor con las entradas del usuario
            float[] inputs = {tiempoCarga, capacidadVolumen, densidad, elevacion, tonelajeInicial, cantidadEquipos};
            long[] shape = {1, inputs.length};

            // Escalar los datos
            float[] scaledInputs = minMaxScale(inputs, X_min, X_max);

            // Convertir el arreglo de entrada a un FloatBuffer
            FloatBuffer buffer = FloatBuffer.wrap(scaledInputs);

            // Crear un tensor con el inputs
            OnnxTensor tensor = OnnxTensor.createTensor(ortEnvironment, buffer, shape);

            // Realizar la predicción
            OrtSession.Result result = ortSession.run(Collections.singletonMap("input", tensor));
            float[][] predicciones = (float[][]) result.get(0).getValue();

            // Mostrar el resultado
            textResultado.setText("Predicción: " + predicciones[0][0]);
        } catch (OrtException e) {
            e.printStackTrace();
            Log.e("MainActivity", "Error en la predicción: " + e.getMessage());
            textResultado.setText("Error en la predicción");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", "Error: " + e.getMessage());
            textResultado.setText("Error en la predicción");
        }
    }
}

//Math.round(