package com.example.myapplication.utility

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.*
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.*
import androidx.annotation.AnimRes
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.appcompat.widget.SearchView
import androidx.core.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.BuildConfig
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.util.*
import kotlin.math.roundToInt

/*
* Return true if view is visible otherwise return false
* */
fun View.isVisible() = visibility == View.VISIBLE
fun View.isGone(): Boolean = visibility == View.GONE
fun View.isInvisible(): Boolean = visibility == View.INVISIBLE

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

inline fun EditText.afterTextChanged(crossinline listener: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            listener(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    })
}

inline fun EditText.onTextChanged(crossinline listener: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener(s, start, before, count)
        }
    })
}

/**
 * Enable/Disable EditText to editable
 * */
fun EditText.setEditable(enable: Boolean) {
    isFocusable = enable
    isFocusableInTouchMode = enable
    isClickable = enable
    isCursorVisible = enable
}


/*
* Make EditText Scrollable inside scrollview
* */
@SuppressLint("ClickableViewAccessibility")
fun EditText.makeScrollableInScrollView() {
    setOnTouchListener(View.OnTouchListener { v, event ->
        if (hasFocus()) {
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_SCROLL -> {
                    v.parent.requestDisallowInterceptTouchEvent(false)
                    return@OnTouchListener true
                }
            }
        }
        false
    })
}


/*
* Execute block if OS version is greater or equal Lolipop(21)
* */
inline fun lollipopAndAbove(block: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        block()
    }
}

/*
* Execute block if OS version is greater than or equal Naugat(24)
* */
inline fun nougatAndAbove(block: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        block()
    }
}

/**
 * Check internet connection.
 * */
inline fun Context.withNetwork(block: () -> Unit, blockError: () -> Unit) {
    val connectivityManager = this
        .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    connectivityManager?.let { it ->
        val netInfo = it.activeNetworkInfo
        netInfo?.let {
            if (netInfo.isConnected)
                block()
            else
                blockError()
        }
    }
}

/*
* Execute block into try...catch
* */
inline fun <T> justTry(tryBlock: () -> T) = try {
    tryBlock()
} catch (e: Exception) {
    e.printStackTrace()
}

// Start new Activity functions

/*
* Start Activity from Activity
* */
inline fun <reified T : Any> Context.launchActivity(
    noinline init: Intent.() -> Unit = {},
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent)
}

/*
* Start Activity from Activity
* */
inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    noinline init: Intent.() -> Unit = {},
) {
    val intent = newIntent<T>(this)
    intent.init()
    if (requestCode == -1) startActivity(intent)
    else startActivityForResult(intent, requestCode)
}

inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    extras: Bundle.() -> Unit = {},
    noinline init: Intent.() -> Unit = {},
) {
    val intent = newIntent<T>(this)
    intent.init()
    intent.putExtras(Bundle().apply(extras))
    if (requestCode == -1) startActivity(intent)
    else startActivityForResult(intent, requestCode)
}


inline fun <reified T : Any> Fragment.launchActivity(
    requestCode: Int = -1,
    noinline init: Intent.() -> Unit = {},
) {
    val intent = newIntent<T>(this.requireContext())
    intent.init()
    if (requestCode == -1)
        startActivity(intent)
    else
        startActivityForResult(intent, requestCode)

}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)

fun Context.openPdfFromUrl(pdfUrl: String?) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl))
    startActivity(browserIntent)
}

fun Fragment.openPdfFromUrl(pdfUrl: String?) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl))
    startActivity(browserIntent)
}

fun Context.openCall(call: String?) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$call")
    startActivity(intent)
}

fun Fragment.openCall(call: String?) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$call")
    startActivity(intent)
}

fun Context.openMap(latitude: Double?, longitude: Double?, name: String) {
    try {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude($name)")
        )
        startActivity(intent)
    } catch (ane: ActivityNotFoundException) {
        Toast.makeText(this, "Please Install Google Maps ", Toast.LENGTH_LONG).show()
    } catch (ex: java.lang.Exception) {
        ex.message
    }
}
//
//fun Context.openMapNavigation(startLatLng: LatLng, endLatLng: LatLng) {
//    try {
//        val uri: String = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f", startLatLng.latitude, startLatLng.longitude, endLatLng.latitude, endLatLng.longitude)
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
//        intent.setPackage("com.google.android.apps.maps")
//        startActivity(intent)
//    } catch (ane: ActivityNotFoundException) {
//        Toast.makeText(this, "Please Install Google Maps ", Toast.LENGTH_LONG).show()
//    } catch (ex: java.lang.Exception) {
//        ex.message
//    }
//}

fun Fragment.openMap(latitude: Double?, longitude: Double?) {
    try {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude('')")
        )
        startActivity(intent)
    } catch (ane: ActivityNotFoundException) {
        Toast.makeText(context, "Please Install Google Maps ", Toast.LENGTH_LONG).show()
    } catch (ex: java.lang.Exception) {
        ex.message
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}

/**
 * Return simple class name
 * */
fun Any.getClassName(): String {
    return this::class.java.simpleName
}


fun Intent.getInt(key: String, defaultValue: Int = 0): Int {
    return extras?.getInt(key, defaultValue) ?: defaultValue
}

fun Intent.getString(key: String, defaultValue: String = ""): String {
    return extras?.getString(key, defaultValue) ?: defaultValue
}

///*
//* Return activity main content view
//* */
//val Activity.contentView: View?
//    get() = findViewById<ViewGroup>(R.id.content)?.getChildAt(0)


/**
 * Hide/Show view with scale animation
 * */
fun View.setVisibilityWithScaleAnim(visibility: Int) {
    this.clearAnimation()
    this.visibility = View.VISIBLE
    val scale = if (visibility == View.GONE)
        0f
    else
        1f

    val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
        this,
        PropertyValuesHolder.ofFloat("scaleX", scale),
        PropertyValuesHolder.ofFloat("scaleY", scale)
    )
    scaleDown.duration = 300
    scaleDown.interpolator = DecelerateInterpolator()
    scaleDown.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            this@setVisibilityWithScaleAnim.visibility = visibility
        }
    })
    scaleDown.start()
}

fun View.setAnimationWithVisibility(@AnimRes animationRes: Int, visibility: Int) {
    setVisibility(visibility)
    clearAnimation()
    val viewAnim = AnimationUtils.loadAnimation(context, animationRes)
    animation = viewAnim
    viewAnim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {
        }

        override fun onAnimationEnd(animation: Animation?) {
            setVisibility(visibility)
        }

        override fun onAnimationStart(animation: Animation?) {
            setVisibility(View.VISIBLE)
        }
    })
    // viewAnim.start()
}

fun Context.getAppVersionName(): String {
    return packageManager.getPackageInfo(packageName, 0).versionName
}

fun Context.showToast(
    message: String?,
    duration: Int = Toast.LENGTH_SHORT,
    gravity: Int = Gravity.CENTER,
) {
    if (!message.isNullOrEmpty())
        Toast.makeText(this, message, duration).run {
            setGravity(gravity, 0, 0)
            show()
        }
}

/*fun setSpannableColor(){
    var word: SpannableString =  SpannableString("Your message");

    word.setSpan(new ForegroundColorSpan(Color.BLUE), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    textView.setText(word);
    Spannable wordTwo = new SpannableString("Your new message");

    wordTwo.setSpan(new ForegroundColorSpan(Color.RED), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    textView.append(wordTwo);
}*/

fun SpannableString.setClickableSpan(
    start: Int,
    end: Int,
    @ColorInt color: Int,
    block: (view: View?) -> Unit,
) {
    setSpan(object : ClickableSpan() {
        override fun onClick(view: View) {
            block(view)
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = false // set to false to remove underline
        }

    }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    setSpan(ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
}


/*
* Set enabled/disable
* */
fun View.setEnabledWithAlpha(enabled: Boolean, disabledAlpha: Float = 0.5f) {
    isEnabled = enabled
    alpha = if (isEnabled) 1f else disabledAlpha
}


fun String?.nullSafe(defaultValue: String = ""): String {
    return this ?: defaultValue
}

fun Int?.nullSafe(defaultValue: Int = 0): Int {
    return this ?: defaultValue
}

fun Float?.nullSafe(defaultValue: Float = 0f): Float {
    return this ?: defaultValue
}

fun Long?.nullSafe(defaultValue: Long = 0L): Long {
    return this ?: defaultValue
}

fun Double?.nullSafe(defaultValue: Double = 0.0): Double {
    return this ?: defaultValue
}

fun Boolean?.nullSafe(defaultValue: Boolean = false): Boolean {
    return this ?: defaultValue
}

fun <T> List<T>?.nullSafe(): List<T> {
    return this ?: ArrayList()
}


fun String?.toLongOrDefaultValue(defaultValue: Long = 0L): Long {
    return if (isNullOrEmpty()) {
        defaultValue
    } else {
        try {
            this?.toLong().nullSafe()
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }
}


@SuppressWarnings("deprecation")
fun String?.fromHtml(): Spanned {
    if (this == null)
        return SpannableString("")
    else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
        // we are using this flag to give a consistent behaviour
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        return Html.fromHtml(this)
    }
}

/**
 * Return ActionBar height
 * */
//fun Activity.getActionBarHeight(): Int {
//    val tv = TypedValue()
//    return if (theme.resolveAttribute(R.attr.actionBarSize, tv, true))
//        TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
//    else 0
//}

fun View.measureWidthHeight(onCompleteMeasure: (width: Int, height: Int) -> Unit) {
    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            viewTreeObserver.removeOnPreDrawListener(this)
            onCompleteMeasure.invoke(measuredWidth, measuredHeight)
            return true
        }
    })
}

fun getDummyList(length: Int): ArrayList<String> {
    val list: MutableList<String> =
        ArrayList()
    for (i in 0 until length) {
        list.add(i.toString())
    }
    return list as ArrayList<String>
}

fun getListHour(length: Int): List<String> {
    val list: MutableList<String> =
        ArrayList()
    for (i in 1 until length) {
        list.add("$i Hour")
    }
    return list
}

fun Drawable.setTintColor(@ColorInt colorTint: Int): Drawable {
    colorFilter = PorterDuffColorFilter(colorTint, PorterDuff.Mode.SRC_ATOP)
    return this
}

fun SearchView.setHintColor(@ColorInt hintColor: Int) {
    (findViewById<EditText>(androidx.appcompat.R.id.search_src_text)).setHintTextColor(hintColor)
}

/**
 * Convert string to int, If string is empty or null return default value
 * */
fun String?.toIntOrDefaultValue(defaultValue: Int = 0): Int {
    return if (isNullOrEmpty()) {
        defaultValue
    } else {
        try {
            this.toInt().nullSafe()
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }
}

/**
 * Convert string to double, If string is empty or null return default value
 * */
fun String?.toDoubleOrDefaultValue(defaultValue: Double = 0.0): Double {
    return if (isNullOrEmpty()) {
        defaultValue
    } else {
        try {
            this.toDouble().nullSafe()
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }
}

/**
 * Return Tag of view as string
 * */
fun View.getStringTag(): String = if (tag == null) "" else tag.toString()

fun RadioGroup.getCheckedButtonText(): String {
    return if (checkedRadioButtonId != -1)
        findViewById<RadioButton>(checkedRadioButtonId).text.toString()
    else
        ""
}

/**
 * Return trimmed text of EditText
 * */
fun EditText.getTrimText(): String = text.toString().trim()

fun TextView.getTrimText(): String = text.toString().trim()


/** multipart*/
//fun EditText.toRequestBody(): RequestBody {
//    return getTrimText().requestBody()
//}
//
//fun String.requestBody(): RequestBody {
//    return toRequestBody(("text/plain").toMediaTypeOrNull())
//}
//
//fun File.toMultipartBody(parameterName: String): MultipartBody.Part {
//    return MultipartBody.Part.createFormData(
//        parameterName,
//        name,
//        this.asRequestBody("image/*".toMediaType())
//    )
//}


fun Int.toPx(context: Context) =
    (this * context.resources.displayMetrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT

fun dpToPx(dp: Int, context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (dp.toFloat() * density).roundToInt()
}

fun Location.getPostalCode(context: Context): String {
    var postalCode = "000000"
    try {
        val geocoder: Geocoder? = Geocoder(context)
        if (geocoder != null) {
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                for (element in addresses) {
                    if (element.postalCode != null) {
                        postalCode = element.postalCode
                        break
                    }
                }
                return postalCode
            }
        }
    } catch (e: Exception) {
        return postalCode
    }
    return postalCode
}

fun Context.openPlayStore() {
    val appPackageName: String = BuildConfig.APPLICATION_ID
    try {
        this.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$appPackageName")
            )
        )
    } catch (e: Exception) {
        this.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
            )
        )
    }
}


fun getHashUId(s: String): Long {
    return UUID.nameUUIDFromBytes(s.toByteArray()).mostSignificantBits
}

fun String?.long(defaultValue: Long = 0L): Long {
    return if (isNullOrEmpty()) defaultValue
    else {
        try {
            this.toLong().nullSafe()
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }
}


val gson = GsonBuilder().disableHtmlEscaping().create()
fun Any?.toJSONObject(): JSONObject {
    return JSONObject(this.toJson())
}

fun Any?.toJson(): String = gson.toJson(this)

fun JSONObject?.toMap(): HashMap<String, Any?> {
    try {
        return gson.fromJson(
            this?.toString(), object : TypeToken<HashMap<String?, Any?>>() {}.type
        )
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return hashMapOf()
}

fun String?.double(defaultValue: Double = 0.0): Double {
    return if (isNullOrEmpty()) {
        defaultValue
    } else {
        try {
            this.toDouble().nullSafe()
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }
}

fun Context.openWeb(webUrl: String?) {
    var url = webUrl
    try {
        if (url?.startsWith("http://")?.not() == true && !url.startsWith("https://"))
            url = "http://$url"

        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        startActivity(intent)
    } catch (ane: ActivityNotFoundException) {
        Toast.makeText(
            this,
            "No application can handle this request." + " Please install a web browser",
            Toast.LENGTH_LONG
        ).show()
    } catch (ex: java.lang.Exception) {
        ex.message
    }
}

fun View.handleVisualOverlaps(
    marginInsteadOfPadding: Boolean = true, gravity: Int, isLandScape: Boolean = false,
) {
    val marginTop = marginTop
    val marginBottom = marginBottom
    val marginLeft = marginLeft
    val marginRight = marginRight

    val paddingTop = paddingTop
    val paddingBottom = paddingBottom
    val paddingLeft = paddingLeft
    val paddingRight = paddingRight

    setOnApplyWindowInsetsListener { view, insets ->
        val insetLeft = insets.systemWindowInsetLeft
        val insetRight = insets.systemWindowInsetRight
        val insetTop = insets.systemWindowInsetTop
        val insetBottom = insets.systemWindowInsetBottom
        if (isLandScape) {
            if (marginInsteadOfPadding) {
                when (gravity) {
                    Gravity.TOP -> {
                        view.updateMargin(start = insetLeft + marginLeft)
                    }

                    Gravity.BOTTOM -> {
                        view.updateMargin(end = insetRight + marginRight)
                    }

                    else -> {
                        view.updateMargin(start = insetLeft + marginLeft)
                        view.updateMargin(end = insetRight + marginRight)
                    }
                }

            } else {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    view.setOnApplyWindowInsetsListener { view, windowInsets ->
                        val systemWindowInsets =
                            windowInsets.getInsets(WindowInsets.Type.systemBars())
                        // It's also possible to use multiple types
                        val insetsValue = windowInsets.getInsets(
                            WindowInsets.Type.ime() or
                                    WindowInsets.Type.systemGestures()
                        )
                        when (gravity) {
                            Gravity.TOP -> {
                                view.updatePaddingRelative(start = insetsValue.left.plus(paddingLeft))
                            }

                            Gravity.BOTTOM -> {
                                view.updatePaddingRelative(end = insetsValue.right.plus(paddingRight))
                            }

                            else -> {
                                view.updatePaddingRelative(start = insetsValue.left.plus(paddingLeft))
                                view.updatePaddingRelative(end = insetsValue.right.plus(paddingRight))
                            }
                        }
                        windowInsets
                    }
                } else {
                    when (gravity) {
                        Gravity.TOP -> {
                            view.updatePaddingRelative(start = insetLeft + paddingLeft)
                        }

                        Gravity.BOTTOM -> {
                            view.updatePaddingRelative(end = insetRight + paddingRight)
                        }

                        else -> {
                            view.updatePaddingRelative(start = insetLeft + paddingLeft)
                            view.updatePaddingRelative(end = insetRight + paddingRight)
                        }
                    }
                }

            }
        } else {
            if (marginInsteadOfPadding) {
                when (gravity) {
                    Gravity.TOP -> {
                        view.updateMargin(top = insetTop + marginTop)
                    }

                    Gravity.BOTTOM -> {
                        view.updateMargin(bottom = insetBottom + marginBottom)
                    }

                    else -> {
                        view.updateMargin(top = insetTop + marginTop)
                        view.updateMargin(bottom = insetBottom + marginBottom)
                    }
                }
            } else {
                when (gravity) {
                    Gravity.TOP -> {
                        view.updatePaddingRelative(top = insetTop + paddingTop)
                    }

                    Gravity.BOTTOM -> {
                        view.updatePaddingRelative(bottom = insetBottom + paddingBottom)
                    }

                    else -> {
                        view.updatePaddingRelative(top = insetTop + paddingTop)
                        view.updatePaddingRelative(bottom = insetBottom + paddingBottom)
                    }
                }

            }
        }
        insets
    }
}

fun View.updateMargin(
    @Px top: Int = 0, @Px bottom: Int = 0, @Px start: Int = 0, @Px end: Int = 0,
) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(
        if (start == 0) marginStart else start,
        if (top == 0) marginTop else top,
        if (end == 0) marginEnd else end,
        if (bottom == 0) marginBottom else bottom
    )
    layoutParams = params
}


fun String.convertToList(): ArrayList<String> {
    val list = ArrayList<String>()
    this.forEach {
        list.add(it.toString())
    }
    return list
}

fun View.hapticFeedbackEnabled() {
    this.isHapticFeedbackEnabled = true
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        this.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_PRESS)
    } else {
        this.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
    }
}

//fun ImageView.loadImage(
//    imageUrl: String?,
//    @DrawableRes placeholder: Int = 0,
//) {
//    justTry {
//        Glide.with(InviteInitializer().mContext).load(imageUrl).override(512).placeholder(placeholder)
//            .into(this)
//    }
//}

fun TextView.setBoldText(string: String) {
    justTry {
        val str = SpannableStringBuilder(string)
        str.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            this.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.text = str
    }
}

fun Context.getPackageInfo(packageName: String): PackageInfo {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        packageManager.getPackageInfo(packageName, 0)
    } else {
        packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
    }
}