package cc.sovellus.vrcaa.ui.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PictureInPicture
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cc.sovellus.vrcaa.R
import cc.sovellus.vrcaa.ui.screen.pictures.PicturesScreen

object PicturesTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.PictureInPicture)
            val tabName = stringResource(R.string.tabs_label_pictures)

            return remember {
                TabOptions(
                    index = 4u,
                    title = tabName,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(PicturesScreen())
    }
}