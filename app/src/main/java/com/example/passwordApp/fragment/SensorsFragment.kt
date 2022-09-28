package com.example.passwordApp.fragment

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.passwordApp.R

class SensorsFragment : Fragment(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var mLight: Sensor? = null
    private var mTemp: Sensor? = null
    private var mAcc: Sensor? = null
    private var mHum: Sensor? = null
    private var mPre: Sensor? = null

    private var lightTextView: TextView? = null
    private var tempTextView: TextView? = null
    private var accTextView: TextView? = null
    private var locTextView: TextView? = null
    private var humTextView: TextView? = null
    private var preTextView: TextView? = null

    lateinit var mainHandler: Handler
    private var prevAcc: Array<Float> = arrayOf(0.0f, 0.0f, 0.0f)
    private var currAcc: Array<Float> = arrayOf(0.0f, 0.0f, 0.0f)

    var currTemp = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sensors, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sensorManager = requireActivity().getSystemService(SENSOR_SERVICE) as SensorManager
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        mTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        mAcc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mHum = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
        mPre = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

        lightTextView = view.findViewById(R.id.textViewLightText)
        tempTextView = view.findViewById(R.id.textViewTemperatureText)
        accTextView = view.findViewById(R.id.textViewAccText)
        humTextView = view.findViewById(R.id.textViewHumText)
        preTextView = view.findViewById(R.id.textViewPreText)

        mainHandler = Handler(Looper.getMainLooper())
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do something here if sensor accuracy changes.
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor?.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            currTemp = event.values[0]
        }

        if (event.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            currAcc[0] = event.values[0]
            currAcc[1] = event.values[1]
            currAcc[2] = event.values[2]
        }

        if (event.sensor?.type == Sensor.TYPE_LIGHT) {
            lightTextView?.text = event.values[0].toString() + " lx"
        }

        if (event.sensor?.type == Sensor.TYPE_RELATIVE_HUMIDITY) {
            humTextView?.text = event.values[0].toString() + "%"
        }

        if (event.sensor?.type == Sensor.TYPE_PRESSURE) {
            preTextView?.text = event.values[0].toString() + "hPa"
        }
    }

    override fun onResume() {
        super.onResume()
        mLight?.also { light ->
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL)
        }
        mTemp?.also { temp ->
            sensorManager.registerListener(this, temp, SensorManager.SENSOR_DELAY_NORMAL)
        }
        mAcc?.also { acc ->
            sensorManager.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL)
        }
        mHum?.also { hum ->
            sensorManager.registerListener(this, hum, SensorManager.SENSOR_DELAY_NORMAL)
        }
        mPre?.also { pre ->
            sensorManager.registerListener(this, pre, SensorManager.SENSOR_DELAY_NORMAL)
        }
        mainHandler.post(updateTemp)
        mainHandler.post(handleMoving)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        mainHandler.removeCallbacks(updateTemp)
        mainHandler.removeCallbacks(handleMoving)
    }

    private val updateTemp = object : Runnable {
        override fun run() {
            tempTextView?.text = currTemp.toString() + " °C"
            mainHandler.postDelayed(this, 500)
        }
    }

    private val handleMoving = object : Runnable {
        override fun run() {

            println("prev: " + prevAcc[0].toString() + ' ' + prevAcc[1].toString() + ' ' + prevAcc[1].toString())
            println("curr: " + currAcc[0].toString() + ' ' + currAcc[1].toString() + ' ' + currAcc[1].toString())

            if (prevAcc[0] == currAcc[0] &&
                prevAcc[1] == currAcc[1] &&
                prevAcc[2] == currAcc[2]) {
                accTextView?.text = "Not moved recently."
            } else {
                accTextView?.text = "Moved recently."
                prevAcc[0] = currAcc[0]
                prevAcc[1] = currAcc[1]
                prevAcc[2] = currAcc[2]
            }
            mainHandler.postDelayed(this, 500)
        }
    }
}