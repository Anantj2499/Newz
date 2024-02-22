package com.example.newz.presentation.search.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newz.R

@Composable
fun SearchLog(
    modifier: Modifier,
    query: String,
    onTap: ()->Unit,
    onClear: ()->Unit
) {
    Column {
        Row (modifier.fillMaxWidth()){
            Text(
                text =query,
                color = colorResource(id = R.color.body),
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onTap() }
                    .padding(16.dp)
                    .align(Alignment.CenterVertically)
            )
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                tint = colorResource(id = R.color.body),
                modifier = Modifier
                    .clickable { onClear() }
                    .padding(16.dp)
            )

        }
        Divider(color = colorResource(id = R.color.body))
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchLogPreview() {
    SearchLog(
        modifier = Modifier,
        query = "Android",
        onTap = {},
        onClear = {}
    )
}