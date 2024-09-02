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

    private EditText input_tonelaje_inicial;
    private EditText input_capacidad_volumen_carguio;
    private EditText input_capacidad_peso_acarreo;
    private EditText input_densidad_poligono;
    private EditText input_tiempo_carga;
    private EditText input_cantidad_equipos;

    private EditText input_horaini_turno;
    private EditText input_elevacion_poligono;
    private EditText input_PH003;
    private EditText input_CF002;
    private EditText input_CF001;
    private EditText input_PH001;

    private EditText input_PH002;
    private EditText input_material8;
    private EditText input_material7;
    private EditText input_material1;
    private EditText input_material4;
    private EditText input_material5;

    private EditText input_material9;
    private EditText input_material6;
    private EditText input_material12;
    private EditText input_material13;
    private EditText input_material11;
    private EditText input_material2;

    private EditText input_material15;



    private TextView textResultado;

    // Valores mínimos y máximos para el escalado

    private final float[] X_min = {0.0f, 19.0f, 0.0f, 0.0f, -1.0f, 3990.0f};

    private final float[] X_max = {3907670.29f, 27.0f, 22.5f, 2870.583f, 6.0f, 4365.0f};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar OrtEnvironment
        ortEnvironment = OrtEnvironment.getEnvironment();

        // Crear OrtSession
        ortSession = createORTSession(ortEnvironment);

        // Enlazar vistas
        input_tonelaje_inicial = findViewById(R.id.input_tonelaje_inicial);
        input_capacidad_volumen_carguio = findViewById(R.id.input_capacidad_volumen_carguio);
        input_capacidad_peso_acarreo = findViewById(R.id.input_capacidad_peso_acarreo);
        input_densidad_poligono = findViewById(R.id.input_densidad_poligono);
        input_tiempo_carga = findViewById(R.id.input_tiempo_carga);
        input_cantidad_equipos = findViewById(R.id.input_cantidad_equipos);
        input_horaini_turno = findViewById(R.id.input_horaini_turno);
        input_elevacion_poligono = findViewById(R.id.input_elevacion_poligono);
        input_PH003 = findViewById(R.id.input_PH003);
        input_CF002 = findViewById(R.id.input_CF002);
        input_CF001 = findViewById(R.id.input_CF001);
        input_PH001 = findViewById(R.id.input_PH001);
        input_PH002 = findViewById(R.id.input_PH002);
        input_material8 = findViewById(R.id.input_material8);
        input_material7 = findViewById(R.id.input_material7);
        input_material1 = findViewById(R.id.input_material1);
        input_material4 = findViewById(R.id.input_material4);
        input_material5 = findViewById(R.id.input_material5);
        input_material9 = findViewById(R.id.input_material9);
        input_material6 = findViewById(R.id.input_material6);
        input_material12 = findViewById(R.id.input_material12);
        input_material13 = findViewById(R.id.input_material13);
        input_material11 = findViewById(R.id.input_material11);
        input_material2 = findViewById(R.id.input_material2);
        input_material15 = findViewById(R.id.input_material15);

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
            InputStream modelInputStream = getResources().openRawResource(R.raw.model_xgb_vff);
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

    // Método para validar que el input es 0 o 1
    private float validateBooleanInput(String input) {
        float value = Float.parseFloat(input);
        return (value == 1.0f) ? 1.0f : 0.0f; // Asegura que solo sea 0 o 1
    }
    private void predecir() {
        try {
            // Obtener entradas del usuario
            float tonelaje_inicial = Float.parseFloat(input_tonelaje_inicial.getText().toString());
            float capacidad_volumen_carguio = Float.parseFloat(input_capacidad_volumen_carguio.getText().toString());
            float capacidad_peso_acarreo = Float.parseFloat(input_capacidad_peso_acarreo.getText().toString());
            float densidad_poligono = Float.parseFloat(input_densidad_poligono.getText().toString());
            float tiempo_carga = Float.parseFloat(input_tiempo_carga.getText().toString());
            float cantidad_equipos = Float.parseFloat(input_cantidad_equipos.getText().toString());

            // Obtener entradas del usuario
            float horaini_turno = Float.parseFloat(input_horaini_turno.getText().toString());
            float elevacion_poligono = Float.parseFloat(input_elevacion_poligono.getText().toString());
            // Convertir las entradas a float y asegurarse de que solo sean 0 o 1
            float PH003 = validateBooleanInput(input_PH003.getText().toString());
            float CF002 = validateBooleanInput(input_CF002.getText().toString());
            float CF001 = validateBooleanInput(input_CF001.getText().toString());
            float PH001 = validateBooleanInput(input_PH001.getText().toString());
            float PH002 = validateBooleanInput(input_PH002.getText().toString());

            float material8 = validateBooleanInput(input_material8.getText().toString());
            float material7 = validateBooleanInput(input_material7.getText().toString());
            float material1 = validateBooleanInput(input_material1.getText().toString());
            float material4 = validateBooleanInput(input_material4.getText().toString());
            float material5 = validateBooleanInput(input_material5.getText().toString());
            float material9 = validateBooleanInput(input_material9.getText().toString());
            float material6 = validateBooleanInput(input_material6.getText().toString());
            float material12 = validateBooleanInput(input_material12.getText().toString());
            float material13 = validateBooleanInput(input_material13.getText().toString());
            float material11 = validateBooleanInput(input_material11.getText().toString());
            float material2 = validateBooleanInput(input_material2.getText().toString());
            float material15 = validateBooleanInput(input_material15.getText().toString());

            // OPCION 1: LLevar todas las variables inputs escaladas al Modelo de Prediccion
            // Crear un tensor con las entradas del usuario
            // float[] inputs = {tonelaje_inicial, capacidad_volumen_carguio, capacidad_peso_acarreo, densidad_poligono, tiempo_carga, cantidad_equipos,
            // horaini_turno,elevacion_poligono};
            // long[] shape = {1, inputs.length};

            // OPCION 2: LLevar todas variables especificas a escalar y luego unir en inputs_global
            // Variables que deben ser escaladas
            float[] variablesToScale = {
                    tonelaje_inicial, capacidad_volumen_carguio,
                    densidad_poligono, tiempo_carga, cantidad_equipos,
                    elevacion_poligono
            };

            // Variables que no se escalan (variables booleanas)
            float[] variablesNoEscaladas = {capacidad_peso_acarreo,horaini_turno,
                    PH003, CF002, CF001, PH001, PH002, material8, material7, material1,
                    material4, material5, material9, material6, material12, material13,
                    material11, material2, material15
            };

            // Escalar las variables necesarias
            float[] scaledVariables = minMaxScale(variablesToScale, X_min, X_max);

            // Crear un nuevo arreglo que combine las variables escaladas y no escaladas
            float[] inputs = new float[scaledVariables.length + variablesNoEscaladas.length];

            // Insertar las variables escaladas en el arreglo final
            System.arraycopy(scaledVariables, 0, inputs, 0, scaledVariables.length);

            // Insertar las variables no escaladas en el arreglo final
            System.arraycopy(variablesNoEscaladas, 0, inputs, scaledVariables.length, variablesNoEscaladas.length);

            // Definir la forma del tensor
            long[] shape = {1, inputs.length};

            // Convertir el arreglo de entrada a un FloatBuffer
            FloatBuffer buffer = FloatBuffer.wrap(inputs);

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