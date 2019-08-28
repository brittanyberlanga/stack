package me.tylerbwong.stack.ui.utils.markdown

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.TextUtils
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService
import io.noties.markwon.LinkResolver
import io.noties.markwon.core.spans.LinkSpan
import me.tylerbwong.stack.R
import me.tylerbwong.stack.data.DeepLinker
import me.tylerbwong.stack.ui.theme.ThemeManager
import timber.log.Timber

private const val STABLE_PACKAGE = "com.android.chrome"
private const val BETA_PACKAGE = "com.chrome.beta"
private const val DEV_PACKAGE = "com.chrome.dev"
private const val LOCAL_PACKAGE = "com.google.android.apps.chrome"

private var packageName: String? = null

class CustomTabsLinkResolver : LinkResolver {
    override fun resolve(view: View, link: String) {
        val context = view.context
        val deepLinkIntent = DeepLinker.resolvePath(context, Uri.parse(link))

        if (deepLinkIntent != null) {
            Timber.i("Resolving internal deep link for $link")
            context.startActivity(deepLinkIntent)
        } else {
            launchCustomTab(context, link)
        }
    }
}

fun launchCustomTab(context: Context, url: String) {
    val packageName = getPackageNameToUse(context, url)
    val themeColor = ThemeManager.resolveThemeAttribute(context, R.attr.viewBackgroundColor)
    val customTabsIntent = CustomTabsIntent.Builder()
            .setNavigationBarColor(themeColor)
            .setToolbarColor(themeColor)
            .setSecondaryToolbarColor(themeColor)
            .build()
    customTabsIntent.intent.`package` = packageName
    customTabsIntent.launchUrl(context, Uri.parse(url))
}

private fun getPackageNameToUse(context: Context, url: String): String? {
    if (packageName != null) return packageName

    val pm = context.packageManager
    // Get default VIEW intent handler.
    val activityIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    val defaultViewHandlerInfo = pm.resolveActivity(activityIntent, 0)
    var defaultViewHandlerPackageName: String? = null
    defaultViewHandlerInfo?.let {
        defaultViewHandlerPackageName = it.activityInfo.packageName
    }

    // Get all apps that can handle VIEW intents.
    val resolvedActivityList = pm.queryIntentActivities(activityIntent, 0)
    val packagesSupportingCustomTabs = mutableListOf<String>()
    resolvedActivityList.forEach {
        val serviceIntent = Intent()
        serviceIntent.action = CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION
        serviceIntent.`package` = it.activityInfo.packageName
        if (pm.resolveService(serviceIntent, 0) != null) {
            packagesSupportingCustomTabs.add(it.activityInfo.packageName)
        }
    }

    // Now packagesSupportingCustomTabs contains all apps that can handle both VIEW intents
    // and service calls.
    return when {
        packagesSupportingCustomTabs.isEmpty() -> null
        packagesSupportingCustomTabs.size == 1 -> packagesSupportingCustomTabs[0]
        !TextUtils.isEmpty(defaultViewHandlerPackageName)
                && !hasSpecializedHandlerIntents(context, activityIntent)
                && packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName) -> defaultViewHandlerPackageName
        packagesSupportingCustomTabs.contains(STABLE_PACKAGE) -> STABLE_PACKAGE
        packagesSupportingCustomTabs.contains(BETA_PACKAGE) -> BETA_PACKAGE
        packagesSupportingCustomTabs.contains(DEV_PACKAGE) -> DEV_PACKAGE
        packagesSupportingCustomTabs.contains(LOCAL_PACKAGE) -> LOCAL_PACKAGE
        else -> {
            Timber.e("Could not resolve package for custom tabs")
            null
        }
    }
}

private fun hasSpecializedHandlerIntents(context: Context, intent: Intent): Boolean {
    try {
        val pm = context.packageManager
        val handlers = pm.queryIntentActivities(
                intent,
                PackageManager.GET_RESOLVED_FILTER)
        if (handlers.size == 0) {
            return false
        }
        for (resolveInfo in handlers) {
            val filter = resolveInfo.filter ?: continue
            if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) continue
            if (resolveInfo.activityInfo == null) continue
            return true
        }
    } catch (e: RuntimeException) {
        Timber.e("Runtime exception while getting specialized handlers")
    }

    return false
}