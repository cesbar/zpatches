package app.cesbar.patches.velov

import app.morphe.patcher.Fingerprint

object ContractsMapperPFingerprint : Fingerprint(
    definingClass = "Lcom/jcdecaux/cyclocity/vls/core/data/source/remote/retrofit/mapper/ContractsMapper;",
    parameters = listOf(
        "Lcom/jcdecaux/cyclocity/vls/core/data/source/remote/retrofit/model/contract/Contract;",
        "Ljava/util/List;"
    )
)