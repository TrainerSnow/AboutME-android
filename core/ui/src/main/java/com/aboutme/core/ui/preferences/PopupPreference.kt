package com.aboutme.core.ui.preferences

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aboutme.core.ui.util.loremIpsum

@Composable
fun <Item> PopupPreference(
    modifier: Modifier = Modifier,
    items: List<Item>,
    selectedItem: Item,
    nameOf: @Composable Item.() -> String,
    enabled: Boolean = true,
    onSelect: (Item) -> Unit,
    title: @Composable () -> Unit
) {
    var showPopup by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
    ) {
        BasePreference(
            modifier = Modifier
                .fillMaxWidth(),
            title = title,
            subtitle = {
                Text(
                    text = selectedItem.nameOf()
                )
            },
            enabled = enabled,
            onClick = {
                showPopup = !showPopup
            }
        )

        DropdownMenu(
            expanded = showPopup && enabled,
            onDismissRequest = { showPopup = false }
        ) {
            items.forEach {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = it.nameOf()
                        )
                    },
                    onClick = {
                        onSelect(it)
                        showPopup = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PopupPreferencePreview() {
    Surface {
        PopupPreference(
            items = (1..3).map { "Item $it" },
            selectedItem = "Item 1",
            nameOf = { this },
            onSelect = {}
        ) {
            Text(
                text = loremIpsum(3)
            )
        }
    }
}