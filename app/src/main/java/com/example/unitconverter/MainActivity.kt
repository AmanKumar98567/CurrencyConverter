package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.ui.theme.UnitConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter() {
    var inputValue by remember { mutableStateOf("1") }
    var outputValue by remember { mutableStateOf("84") }
    var iOpen by remember { mutableStateOf(false) }
    var oOpen by remember { mutableStateOf(false) }
    var iCurrency by remember { mutableStateOf("Dollar") }
    var oCurrency by remember { mutableStateOf("Rupees") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Unit Converter", fontSize = 30.sp, style = MaterialTheme.typography.bodyLarge,color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputValue,
            label = { Text(text = "Enter value") },
            onValueChange = { inputValue = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            // Input currency selection
            Box {
                Button(onClick = { iOpen = true }) {
                    Text(text = iCurrency)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "")
                }
                DropdownMenu(expanded = iOpen, onDismissRequest = { iOpen = false }) {
                    DropdownMenuItem(text = { Text(text = "Dollar") }, onClick = {
                        iCurrency = "Dollar"
                        iOpen = false
                    })
                    DropdownMenuItem(text = { Text(text = "Rupees") }, onClick = {
                        iCurrency = "Rupees"
                        iOpen = false
                    })
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Output currency selection
            Box {
                Button(onClick = { oOpen = true }) {
                    Text(text = oCurrency)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "")
                }
                DropdownMenu(expanded = oOpen, onDismissRequest = { oOpen = false }) {
                    DropdownMenuItem(text = { Text(text = "Dollar") }, onClick = {
                        oCurrency = "Dollar"
                        oOpen = false
                        outputValue = currencyConverter(inputValue, iCurrency, oCurrency)
                    })
                    DropdownMenuItem(text = { Text(text = "Rupees") }, onClick = {
                        oCurrency = "Rupees"
                        oOpen = false
                        outputValue = currencyConverter(inputValue, iCurrency, oCurrency)
                    })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { outputValue = currencyConverter(inputValue, iCurrency, oCurrency) }) {
            Text(text = "Convert")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Result: $outputValue", fontSize = 20.sp)
    }
}

// Function to convert currency
fun currencyConverter(input: String, iCurrency: String, oCurrency: String): String {
    val value = input.toDoubleOrNull() ?: return "Invalid Input"

    val dollarToRupee = 84.0
    val rupeeToDollar = 1 / 84.0

    val result = when {
        iCurrency == "Dollar" && oCurrency == "Rupees" -> value * dollarToRupee
        iCurrency == "Rupees" && oCurrency == "Dollar" -> value * rupeeToDollar
        iCurrency == oCurrency -> value // No conversion needed
        else -> return "Invalid Currency Type"
    }

    return if (result % 1 == 0.0) result.toInt().toString() else String.format("%.2f", result)
}
//
//@Preview(showBackground = true)
//@Composable
//fun UnitConverterPreview() {
//    UnitConverter()
//}
