package com.example.calculator_37

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var current = ""
    private var operator = ""
    private var firstNumber = 0.0
    private var secondNumber = 0.0
    private var result = 0.0
    private var isNewOperation = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //เชื่อม textView เข้ากับ Layout
        display = findViewById(R.id.txtDisplay)

        val numberButton = listOf(
            R.id.btn0,
            R.id.num1,
            R.id.num2,
            R.id.num3,
            R.id.num4,
            R.id.num5,
            R.id.num6,
            R.id.num7,
            R.id.num8,
            R.id.num9
        )
        //กำหนด Event
        for (id in numberButton) {
            findViewById<Button>(id).setOnClickListener {
                numberClicked((it as Button).text.toString())
            }
        }
        //เชื่อมโยงปุ่มคำนวณ

        findViewById<Button>(R.id.btnplus).setOnClickListener { operatorClicked("+") }
        findViewById<Button>(R.id.btn14).setOnClickListener { operatorClicked("-") }
        findViewById<Button>(R.id.btn4).setOnClickListener { operatorClicked("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { operatorClicked("/") }
        findViewById<Button>(R.id.dtnMod).setOnClickListener { operatorClicked("%") }

        findViewById<Button>(R.id.btnequal).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.btnAc).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.dtndot).setOnClickListener { addDOt() }
        findViewById<Button>(R.id.btpm).setOnClickListener { toggleSign() }
        findViewById<Button>(R.id.btnDel).setOnClickListener { backspace() }
    }

    private fun numberClicked(value: String) {
        if (isNewOperation) {
            current = ""
            display.text = ""
            isNewOperation = false
        }
        current += value
        display.text = current
    }

    private fun operatorClicked(op: String) {
        if (current.isNotEmpty()) {
            firstNumber = current.toDouble()
            operator = op
            current = ""
        }
    }

    private fun calculateResult() {
        if (current.isNotEmpty() && operator.isNotEmpty()) {
            secondNumber = current.toDouble()
            result = when (operator) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "*" -> firstNumber * secondNumber
                "%" -> firstNumber % secondNumber
                "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else Double.NaN
                else -> 0.0
            }
            display.text = if (result.isNaN()) "Cannot divine by zero" else result.toString()
            current = result.toString()
            isNewOperation = true
        }
    }
    //ฟังชั่นของปุ่ม AC (all clear)
    private fun clearAll() {
        current = ""
        operator = ""
        firstNumber = 0.0
        secondNumber = 0.0
        result = 0.0
        display.text = "0"
        isNewOperation = true

    }

    //ฟังชั่นของปุ่ม dot
    private fun addDOt() {
        if (!current.contains(".")) {
            if (current.isEmpty()) current = "0" //ถ้ายังไม่มีตัวเลขใน current ให้ตั้งเป็น 0ก่อน
            current += "."
            display.text = current
        }
    }

    //ฟังชั่นของปุ่ม toggle sign สลับค่า
    private fun toggleSign() {
        if (current.isNotEmpty() && current != "0") {
            current = if (current.startsWith("-")) {
                current.substring(1)
            } else {
                "-$current"
            }
            display.text = current
        }
    }

    //ฟังชั่นของปุ่มลบทีล่ะตัว
    private fun backspace() {
        if (isNewOperation) return
        if (current.isNotEmpty())
        current = current.dropLast(1) //ตัดตัวสุดท้ายออก 1 ตัว

            if (current == "-" || current.isEmpty()) {
                current = ""
                display.text = "0"
            } else {
                display.text = current
            }
    }
}
