package com.example.bankapp.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bankapp.R
import com.example.bankapp.data.ScreenNavigation
import com.example.bankapp.data.UserData
import com.example.bankapp.ui.theme.BlueCenter
import com.example.bankapp.ui.theme.BlueEnd
import com.example.bankapp.ui.theme.DarkBlue
import com.example.bankapp.ui.theme.DarkPurple
import com.example.bankapp.ui.theme.SkyBlue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {

    var fname by rememberSaveable {
        mutableStateOf("")
    }
    var lname by rememberSaveable {
        mutableStateOf("")
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var cpassword by rememberSaveable {
        mutableStateOf("")
    }
    var isPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var isCPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate(ScreenNavigation.HomeScreen.route){
                popUpTo(ScreenNavigation.SignUpScreen.route){
                    inclusive = true
                }
                popUpTo(ScreenNavigation.LoginScreen.route){
                    inclusive = true
                }
            }
            is AuthState.Error -> Toast.makeText(
                context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT
            ).show()

            else -> Unit
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(DarkPurple, BlueCenter, SkyBlue, BlueEnd, DarkPurple)
                    )
                )
        )

        when (authState.value) {
            is AuthState.Loading -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            else -> Unit
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign Up To Muhoro",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))

            BasicTextField(
                value = fname,
                onValueChange = { fname = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                decorationBox = { innerTextField ->
                    Box(
                        Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(0.9f)
                    ) {
                        if (fname.isEmpty()) {
                            Text(
                                text = "First Name",
                                style = LocalTextStyle.current.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 16.sp
                                )
                            )
                        }
                        innerTextField()
                    }
                }
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(0.9f),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.5f)
            )
            BasicTextField(
                value = lname,
                onValueChange = { lname = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                    ),
                decorationBox = { innerTextField ->
                    Box(
                        Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(0.9f)
                    ) {
                        if (lname.isEmpty()) {
                            Text(
                                text = "Last Name",
                                style = LocalTextStyle.current.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 16.sp
                                )
                            )
                        }
                        innerTextField()
                    }
                }
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(0.9f),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.5f)
            )
            BasicTextField(
                value = email,
                onValueChange = { email = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                    ),
                decorationBox = { innerTextField ->
                    Box(
                        Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(0.9f)
                    ) {
                        if (email.isEmpty()) {
                            Text(
                                text = "Email",
                                style = LocalTextStyle.current.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 16.sp
                                )
                            )
                        }
                        innerTextField()
                    }
                }
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(0.9f),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.5f)
            )
            Row(
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    BasicTextField(
                        value = password,
                        onValueChange = { password = it },
                        singleLine = true,
                        visualTransformation = if (isPasswordVisible) {
                            VisualTransformation.None
                        } else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        decorationBox = { innerTextField ->
                            Box(
                                Modifier
                                    .padding(vertical = 16.dp)
                            ) {
                                if (password.isEmpty()) {
                                    Text(
                                        text = "Password",
                                        style = LocalTextStyle.current.copy(
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontSize = 16.sp
                                        )
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }

                IconButton(
                    onClick = { isPasswordVisible = !isPasswordVisible },
                ) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                    )
                }


            }

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(0.9f),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.5f)
            )

            Row(
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    BasicTextField(
                        value = cpassword,
                        onValueChange = { cpassword = it },
                        singleLine = true,
                        visualTransformation = if (isCPasswordVisible) {
                            VisualTransformation.None
                        } else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                authViewModel.signUp(email, password, cpassword)
                                keyboardController?.hide()
                            }
                        ),
                        decorationBox = { innerTextField ->
                            Box(
                                Modifier
                                    .padding(vertical = 16.dp)
                            ) {
                                if (cpassword.isEmpty()) {
                                    Text(
                                        text = "Confirm Password",
                                        style = LocalTextStyle.current.copy(
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontSize = 16.sp
                                        )
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }

                IconButton(
                    onClick = { isCPasswordVisible = !isCPasswordVisible },
                ) {
                    Icon(
                        imageVector = if (isCPasswordVisible) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                        contentDescription = if (isCPasswordVisible) "Hide password" else "Show password"
                    )
                }


            }

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(0.9f),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.5f)
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.7f),
                onClick = {
                    // Ensure these operations run on background threads
                   coroutineScope.launch {
                        try {
                            // Sign-up operation
                            authViewModel.signUp(email, password, cpassword)

                            // Create the UserData object
                            val userData = UserData(
                                fname = fname,
                                lname = lname,
                                email = email,
                                password = password
                            )

                            // Save data using the ViewModel
                            authViewModel.saveData(userData = userData, context = context)
                        } catch (e: Exception) {
                            // Handle any exception
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, e.message ?: "Something went wrong", Toast.LENGTH_SHORT).show()
                            }
                        }
                   }
                },
                enabled = authState.value != AuthState.Loading,
                colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
            ) {
                Text(
                    text = "Sign Up",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Or")

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(bottom = 16.dp),
                onClick = {  },
                colors = ButtonDefaults.buttonColors(containerColor = BlueCenter)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Login with Google",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

        }
    }

}