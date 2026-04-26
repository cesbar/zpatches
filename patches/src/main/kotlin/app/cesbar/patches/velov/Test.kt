package app.cesbar.patches.velov

import app.morphe.patcher.patch.bytecodePatch

val mySimplePatch = bytecodePatch(
    name = "Test Patch",
    description = "Test",
    default = true
) {

    execute {
    }
}