package org.sopt.and.model

import androidx.annotation.StringRes
import org.sopt.and.R

enum class TabMenuItem(@StringRes val titleResId: Int) {
    NEW_CLASSIC(R.string.tab_menu_new_classic_title),
    DRAMA(R.string.tab_menu_drama_title),
    ENTERTAIN(R.string.tab_menu_entertain_title),
    MOVIE(R.string.tab_menu_movie_title),
    ANIMATION(R.string.tab_menu_animation_title),
    GLOBAL_SERIES(R.string.tab_menu_global_series_title)
}