package com.example.noinstagram.ui.buttons

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.noinstagram.R
import com.example.noinstagram.ui.theme.LightBlue

@Composable
fun LoginWithEmailButton(navController: NavController) {
    FloatingActionButton(
        onClick = { navController.navigate("LoginPage") },
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .width(250.dp),
        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp),
        backgroundColor = LightBlue.copy(alpha = 0.3f)
    )
    {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logo_login_with_email),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Login with Email", color = Color.White)
        }
    }
}
