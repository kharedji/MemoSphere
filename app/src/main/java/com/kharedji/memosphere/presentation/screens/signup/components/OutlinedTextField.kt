package com.kharedji.memosphere.presentation.screens.signup.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation


/**
 * A custom [OutlinedTextField] that allows masking the input text.
 *
 * @param value The current value of the text field.
 * @param onValueChanged The callback that is called when the value of the text field changes.
 * @param keyboardOptions The keyboard options for the text field.
 * @param leadingIcon The leading icon for the text field.
 * @param label The label for the text field.
 * @param placeholder The placeholder for the text field.
 * @param trailingIcon The trailing icon for the text field.
 * @param applyVisualTransformation Whether to apply visual transformation to the text field.
 */
@Composable
fun RegisterOutlinedText(
    value: String,
    onValueChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    leadingIcon: @Composable (() -> Unit)?,
    label: @Composable (() -> Unit)?,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    applyVisualTransformation: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        keyboardOptions = keyboardOptions,
        label = label,
        placeholder = placeholder ,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = if (applyVisualTransformation) PasswordVisualTransformation() else VisualTransformation.None
    )
}

// To mask the password entry using the * character, use the following function:
private class PasswordVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            AnnotatedString("*".repeat(text.text.length)),

            /**
             * [OffsetMapping.Identity] is a predefined [OffsetMapping] that can be used for the
             * transformation that does not change the character count.
             */

            /**
             * [OffsetMapping.Identity] is a predefined [OffsetMapping] that can be used for the
             * transformation that does not change the character count.
             */
            OffsetMapping.Identity
        )
    }
}