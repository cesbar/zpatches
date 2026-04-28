package app.cesbar.patches.velov

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

object contractsMapperPFingerprint : Fingerprint(
    definingClass = "Lcom/jcdecaux/cyclocity/vls/core/data/source/remote/retrofit/mapper/ContractsMapper;",
    parameters = listOf(
        "Lcom/jcdecaux/cyclocity/vls/core/data/source/remote/retrofit/model/contract/Contract;",
        "Ljava/util/List;"
    )
)

object signatureBytesToStringFingerprint : Fingerprint(
    parameters = listOf("[B", "Z"),
    returnType = "Ljava/lang/String;",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    definingClass = "Lcom/google/android/gms/common/util/"
)

object signatureFromPackageFingerprint : Fingerprint(
    parameters = listOf("Landroid/content/pm/PackageManager;", "Ljava/lang/String;"),
    returnType = "Ljava/lang/String;",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC)
)