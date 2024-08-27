import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.pavdev.autocommit.data.enums.SettingsOption

@Composable
fun SettingsIcon() {
    var showMenu by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { showMenu = !showMenu }) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Settings"
            )
        }

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            SettingsOption.entries.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.description) },
                    onClick = {
                        showMenu = false
                        onOptionSelected(option)
                    }
                )
            }
        }
    }
}

fun onOptionSelected(option: SettingsOption) {
    when (option) {
        SettingsOption.CHANGE_DEFAULTS -> onChangeDefaultsClicked()
        SettingsOption.CUSTOM_COMMIT -> onCustomCommitClicked()
        SettingsOption.CHECK_LOG -> onCheckLogClicked()
        SettingsOption.ABOUT -> onAboutClicked()
    }
}

fun onChangeDefaultsClicked() {
    println("Change Defaults clicked")
}

fun onCustomCommitClicked() {
    println("Custom Commit clicked")
}

fun onCheckLogClicked() {
    println("Check Log clicked")
}

fun onAboutClicked() {
    println("About clicked")
}