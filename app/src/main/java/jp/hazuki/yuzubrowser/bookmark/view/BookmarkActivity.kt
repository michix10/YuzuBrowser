package jp.hazuki.yuzubrowser.bookmark.view

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import jp.hazuki.yuzubrowser.Constants
import jp.hazuki.yuzubrowser.R
import jp.hazuki.yuzubrowser.settings.data.AppData
import jp.hazuki.yuzubrowser.utils.app.LongPressFixActivity

class BookmarkActivity : LongPressFixActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_base)

        val intent = intent
        var pickMode = false
        var itemId: Long = -1
        var fullscreen = AppData.fullscreen.get()
        var orientation = AppData.oritentation.get()
        if (intent != null) {
            pickMode = Intent.ACTION_PICK == intent.action
            itemId = intent.getLongExtra("id", -1)

            fullscreen = intent.getBooleanExtra(Constants.intent.EXTRA_MODE_FULLSCREEN, fullscreen)
            orientation = intent.getIntExtra(Constants.intent.EXTRA_MODE_ORIENTATION, orientation)
        }

        if (fullscreen)
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestedOrientation = orientation

        supportFragmentManager.beginTransaction()
                .replace(R.id.container, BookmarkFragment.newInstance(pickMode, itemId))
                .commit()
    }

    override fun onBackKeyPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment is BookmarkFragment) {
            if (fragment.onBack()) {
                finish()
            }
        }
    }

    override fun onBackKeyLongPressed() {
        finish()
    }
}
