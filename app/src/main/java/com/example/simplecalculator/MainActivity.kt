package com.example.simplecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorApp()
                }
            }
        }
    }
}

@Composable
fun CalculatorApp() {
    var displayNumber1 by remember { mutableStateOf("") }
    var displayNumber2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("0") }


    fun calculation(operation: (Double, Double) -> Double) {
        val num1 = displayNumber1.toDoubleOrNull()
        val num2 = displayNumber2.toDoubleOrNull()

        if (num1 != null && num2 != null) {
            try {
                val res = operation(num1, num2)

                result = if (res == res.toInt().toDouble()) res.toInt().toString()
                else res.toString()

            } catch (e: ArithmeticException) {
                result = "Error: Div by zero"
            } catch (e: Exception) {
                result = "Error"
            }
        } else {
            result = "Invalid Input"
        }
    }

    fun clear() {
        displayNumber1 = ""
        displayNumber2 = ""
        result = "0"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = result,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(bottom = 5.dp, top = 20.dp),
            fontSize = 40.sp,
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.onSurface
        )

        TextField(
            value = displayNumber1,
            onValueChange = { displayNumber1 = it.filter { char -> char.isDigit() || char == '.' } },
            label = { Text("Number 1") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 24.sp),
        )

        TextField(
            value = displayNumber2,
            onValueChange = { displayNumber2 = it.filter { char -> char.isDigit() || char == '.' } },
            label = { Text("Number 2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 20.sp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Memanggil CalculatorButton tanpa parameter warna
            CalculatorButton(
                text = "+",
                modifier = Modifier.weight(1f),
                onClick = { calculation { a, b -> a + b } }
            )
            CalculatorButton(
                text = "-",
                modifier = Modifier.weight(1f),
                onClick = { calculation { a, b -> a - b } }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CalculatorButton(
                text = "x",
                modifier = Modifier.weight(1f),
                onClick = { calculation { a, b -> a * b } }
            )
            CalculatorButton(
                text = "/",
                modifier = Modifier.weight(1f),
                onClick = {
                    if (displayNumber2.toDoubleOrNull() == 0.0) {
                        result = "Error: Div by zero"
                    } else {
                        calculation { a, b -> a / b }
                    }
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CalculatorButton(
                text = "C",
                modifier = Modifier.weight(1f),
                onClick = { clear() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        InfoDisplay()
    }
}

@Composable
fun CalculatorButton(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF4A4A4A),
    textColor: Color = Color.White,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(70.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

// Composable untuk label info
@Composable
fun InfoDisplay() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nama: Abiyu Ramadhan Kiesly",
            fontSize = 20.sp,
            color = Color.Black
        )
        Text(
            text = "NRP: 5025221123",
            fontSize = 20.sp,
            color = Color.Black
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimpleCalculatorTheme {
        CalculatorApp()
    }
}