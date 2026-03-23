package bubble.keks.androtest

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    // Re-hide when user interacts (sticky immersive can be finicky)
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                            View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide both status bar and navigation bar
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or  // Stays hidden, swipe to show temporarily
                        View.SYSTEM_UI_FLAG_FULLSCREEN or        // Hide status bar
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION      // Hide nav bar
                )

        val tv = TextView(this)
        tv.text = "Full screen!"
        setContentView(tv)


        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.rgb(0x23, 0x23, 0x23))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            gravity = Gravity.CENTER
            setPadding(dp(16), dp(16), dp(16), dp(16))
        }

        val title = TextView(this).apply {
            text = "Hello"
            textSize = 24f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        val button = Button(this).apply {
            text = "Click Me"
            setOnClickListener {
                title.text = "Clicked at ${System.currentTimeMillis()}"
            }
            backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#FF0000"))
        }

        layout.addView(title)
        layout.addView(button)

        setContentView(layout)
    }

    private fun dp(px: Int): Int =
        (px * resources.displayMetrics.density).toInt()
}
