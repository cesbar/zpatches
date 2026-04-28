package app.cesbar.patches.velov

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.returnEarly

val spoofSignatureFirebasePatch = bytecodePatch (
    name = "Spoof package signature",
    description = "Spoofs the package signature required for Firebase, fixes notifications"
){
    compatibleWith(Constants.COMPATIBILITY)

    execute {
        signatureBytesToStringFingerprint.method.returnEarly(Constants.SIGNATURE)
        signatureFromPackageFingerprint.method.returnEarly(Constants.SIGNATURE)
    }
}