package com.example.quickexpense_compose.presentation.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickexpense_compose.R

@Composable
fun About() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.lightGrey))
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic),
            contentDescription = "App Logo",
            modifier = Modifier.size(150.dp)
        )
        Text(text = "Track your Income and Expenses:", color = Color(0xFF5F7464), fontSize = 24.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "From", fontStyle = FontStyle.Italic, fontSize = 20.sp)
        Text(text = "Geoffrey Maina", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_twitter),
                contentDescription = "Twitter",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {

                    }
            )
            Spacer(modifier = Modifier.width(16.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_ig),
                contentDescription = "Instagram",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {

                    }
            )
            Spacer(modifier = Modifier.width(16.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_fb),
                contentDescription = "Facebook",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {

                    }
            )
        }
    }
}