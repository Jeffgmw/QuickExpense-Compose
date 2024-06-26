package com.example.quickexpense_compose.presentation.TransactionCard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NorthEast
import androidx.compose.material.icons.outlined.SouthWest
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickexpense_compose.domain.Transaction
import com.example.quickexpense_compose.R


@Composable
fun TransactionCard(transaction: Transaction, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .background(colorResource(id = R.color.darkGrey))
            .padding(16.dp)
            ,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = if (transaction.transactionType == "Expense") {
                Icons.Outlined.NorthEast
            } else {
                Icons.Outlined.SouthWest
            },
            contentDescription = transaction.tags,
            modifier = Modifier.size(40.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = transaction.title,
                    color = Color.White,
                    fontSize = 16.sp,
                )

                Text(
                    modifier = Modifier.width(64.dp),
                    text = transaction.tags,
                    color = Color.White.copy(0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = if (transaction.transactionType == "Expense") {
                        "-KES ${transaction.amount}"
                    } else {
                        "+KES ${transaction.amount}"
                    },
                    color = Color.White,
                    fontSize = 16.sp,
                )

                Text(
                    text = transaction.date,
                    color = Color.White.copy(0.7f),
                )
            }
        }
    }
}
