package app.cesbar.patches.velov

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.rawResourcePatch

@Suppress("unused")
val enablePlusPatch = rawResourcePatch(
    name = "In-app login",
    description = "Login within the app (identities) instead of using the browser (keycloak)"
) {
    compatibleWith(Compatibility(
        name = "Vélo'v",
        packageName = "com.jcdecaux.vls.lyon",
        appIconColor = 0xF42F34,
        targets = listOf(AppTarget("3.1.1"))
    ))
}