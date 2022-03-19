package com.example.practicekotlin2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val numberPicker: NumberPicker by lazy {
        findViewById(R.id.numberPicker)
    }

    private val buttonAddNumber: Button by lazy {
        findViewById(R.id.buttonAddNumber)
    }

    private val buttonClearNumber: Button by lazy {
        findViewById(R.id.buttonClearNumber)
    }

    private val buttonCreateNumber: Button by lazy {
        findViewById(R.id.buttonCreateNumber)
    }

    private val listNumberTextView: List<TextView> by lazy {
        listOf(
            findViewById(R.id.textViewOne),
            findViewById(R.id.textViewTwo),
            findViewById(R.id.textViewThree),
            findViewById(R.id.textViewFour),
            findViewById(R.id.textViewFive),
            findViewById(R.id.textViewSix)
        )
    }

    private val setPickedNumber = hashSetOf<Int>()

    private var didRun = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initAddButton()
        initClearButton()
        initCreateButton()
    }

    private fun initAddButton() {
        buttonAddNumber.setOnClickListener {
            if (didRun) {
                Toast.makeText(this, "초기화 후에 시도해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (setPickedNumber.contains(numberPicker.value)) {
                Toast.makeText(this, "중복된 번호입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (setPickedNumber.size >= 5) {
                Toast.makeText(this, "번호는 5개까지만 선택 가능합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val textView = listNumberTextView[setPickedNumber.size]
            textView.text = numberPicker.value.toString()
            textView.isVisible = true

            setNumberBackground(textView, numberPicker.value)
            setPickedNumber.add(numberPicker.value)
        }
    }

    private fun initCreateButton() {
        buttonCreateNumber.setOnClickListener {
            didRun = true

            val list = getRandomNumber()
            list.forEachIndexed { index, number ->
                val textView = listNumberTextView[index]
                textView.text = number.toString()
                textView.isVisible = true

                setNumberBackground(textView, number)
            }
        }
    }

    private fun initClearButton() {
        buttonClearNumber.setOnClickListener {
            didRun = false
            setPickedNumber.clear()

            listNumberTextView.forEach {
                it.isVisible = false
            }
        }
    }

    private fun getRandomNumber(): List<Int> {
        val randomNumber = mutableListOf<Int>().apply {
            for (i in 1..45) {
                if (setPickedNumber.contains(i)) {
                    continue
                }
                this.add(i)
            }
        }

        randomNumber.shuffle()
        val newRandomNumber =
            setPickedNumber.toList() + randomNumber.subList(0, 6 - setPickedNumber.size)
        return newRandomNumber.sorted()
    }

    private fun setNumberBackground(textView: TextView, number: Int) {
        when (number) {
            in 1..10 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_yellow)
            in 11..20 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_blue)
            in 21..30 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_red)
            in 31..40 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_gray)
            else -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_green)
        }
    }
}