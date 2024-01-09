package com.aboutme.core.ui.text

import com.aboutme.core.model.data.NameInfo

fun NameInfo.formatText() =
    (if (title == null) "" else "$title ") +
            ("$firstName ") +
            (if (middleName == null) "" else "$middleName ") +
                    (if (lastName == null) "" else "$lastName")