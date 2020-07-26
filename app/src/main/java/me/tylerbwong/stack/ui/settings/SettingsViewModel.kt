package me.tylerbwong.stack.ui.settings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.tylerbwong.stack.data.model.Site
import me.tylerbwong.stack.data.repository.SiteRepository
import me.tylerbwong.stack.ui.BaseViewModel

class SettingsViewModel @ViewModelInject constructor(
    private val siteRepository: SiteRepository
) : BaseViewModel() {

    internal val currentSite: LiveData<Site>
        get() = mutableCurrentSite
    private val mutableCurrentSite = MutableLiveData<Site>()

    internal fun fetchCurrentSite() {
        launchRequest {
            siteRepository.fetchSitesIfNecessary()
            mutableCurrentSite.value = siteRepository.getCurrentSite()
        }
    }
}
