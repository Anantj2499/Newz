package com.example.newz.presentation.common

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newz.R
import com.example.newz.ui.theme.NewzTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    text : String,
    readOnly: Boolean,
    onClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    onSearch:()->Unit
){
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val keyboardController= LocalSoftwareKeyboardController.current
    val isClicked = interactionSource.collectIsPressedAsState().value
    LaunchedEffect(key1 = isClicked){
        if (isClicked){
            onClick?.invoke()
        }
    }
    Box(modifier = modifier){
        val containerColor = colorResource(id = R.color.input_background)
        TextField(
            modifier = Modifier
                .searchBarBorder()
                .fillMaxWidth(),
            value = text,
            readOnly = readOnly,
            onValueChange = onValueChange,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = colorResource(id = R.color.body)
                )
            },
            placeholder = {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.placeholder)
                )
            },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                focusedTextColor = if(isSystemInDarkTheme()) Color.White else Color.Black,
                unfocusedTextColor = if(isSystemInDarkTheme()) Color.White else Color.Black,
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                cursorColor = if(isSystemInDarkTheme()) Color.White else Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch()
                    keyboardController?.hide()
                }
            ),
            textStyle = MaterialTheme.typography.bodySmall,
            interactionSource = interactionSource
        )
    }
}
@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.searchBarBorder() = composed {
    if(!isSystemInDarkTheme()) {
        border(
            width = 1.dp,
            color =  Color.Black,
            shape = MaterialTheme.shapes.medium
        )
    }else{
        border(
            width = 1.dp,
            color = Color.White,
            shape = MaterialTheme.shapes.medium
        )
    }
}
@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchBarPreview() {
    NewzTheme {
        SearchBar(
            text = "Search",
            readOnly = false,
            onValueChange = {},
            onSearch = {}
        )
    }
}

