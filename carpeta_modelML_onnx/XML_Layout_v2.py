<?xml version="1.0" encoding="utf-8"?>
<ScrollView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
    <EditText
        android:id="@+id/input_tonelaje_inicial"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="tonelaje_inicial_poligono"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_capacidad_volumen_carguio"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Capacidad_en_volumen_carguio(m3)"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_tonelaje_inicial"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_capacidad_peso_acarreo"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="capacidad_peso_acarreo"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_capacidad_volumen_carguio"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_densidad_poligono"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Densidad_inicial_poligono(tn/m3)"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_capacidad_peso_acarreo"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_tiempo_carga"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Tiempo_carga"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_densidad_poligono"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_cantidad_equipos"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Cantidad_de_equipos_espera_al_termino_carga"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_tiempo_carga"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_horaini_turno"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Hora_inicio_turno"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_cantidad_equipos"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_elevacion_poligono"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Elevacion_poligono(mts)"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_horaini_turno"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_PH003"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Equipo_PH003"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_elevacion_poligono"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_CF002"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Equipo_CF002"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_PH003"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_CF001"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="input_CF001"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_CF002"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_PH001"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="input_PH001"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_CF001"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_PH002"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="input_PH002"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_PH001"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_material8"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Material_8"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_PH002"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_material7"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Material_7"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_material8"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_material1"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Material_1"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_material7"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_material4"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Material_4"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_material1"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_material5"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Material_5"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_material4"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_material9"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Material_9"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_material5"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_material6"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Material_6"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_material9"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_material12"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Material_12"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_material6"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_material13"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Material_13"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_material12"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_material11"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Material_11"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_material13"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_material2"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Material_2"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_material11"
        app:layout_constraintWidth_percent="0.8"/>

    <EditText
        android:id="@+id/input_material15"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Material_15"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input_material2"
        app:layout_constraintWidth_percent="0.8"/>

    <Button
        android:id="@+id/btn_predecir"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Predecir"
        app:layout_constraintTop_toBottomOf="@+id/input_material15"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <TextView
        android:id="@+id/text_resultado"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Resultado"
        app:layout_constraintTop_toBottomOf="@id/btn_predecir"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>
    </androidx.constraintlayout.widget.ConstraintLayout>
</ScrollView>

<!--    <?xml version="1.0" encoding="utf-8"?>-->
<!--<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"-->
<!--xmlns:app="http://schemas.android.com/apk/res-auto"-->
<!--xmlns:tools="http://schemas.android.com/tools"-->
<!--android:layout_width="match_parent"-->
<!--android:layout_height="match_parent"-->
<!--tools:context=".MainActivity">-->
