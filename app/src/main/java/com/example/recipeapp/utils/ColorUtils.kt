
package com.example.recipeapp.utils

import android.app.Activity
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

class ColorUtils
{
   fun Activity.getColorRes(@ColorRes id:Int)=ContextCompat.getColor(applicationContext,id)
}
