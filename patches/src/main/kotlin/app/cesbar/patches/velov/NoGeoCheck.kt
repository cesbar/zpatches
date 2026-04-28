package app.cesbar.patches.velov

import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.builder.MutableMethodImplementation
import com.android.tools.smali.dexlib2.builder.instruction.BuilderInstruction21s
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.instruction.formats.Instruction11x
import com.android.tools.smali.dexlib2.iface.reference.StringReference

@Suppress("unused")
val noGeoCheckPatch = bytecodePatch(
    name = "No geolocalisation check",
    description = "Allows you to unlock a bike even if you're not near the station."
) {
    compatibleWith(Compatibility(
        name = "Vélo'v",
        packageName = "com.jcdecaux.vls.lyon",
        appIconColor = 0xF42F34,
        targets = listOf(AppTarget("3.1.1"))
    ))

    execute {
        val methodP = contractsMapperPFingerprint.method
        val instructions = (methodP.implementation as? MutableMethodImplementation)?.instructions ?: return@execute

        val stringIndex = instructions.indexOfFirst {
            val ref = (it as? ReferenceInstruction)?.reference as? StringReference
            ref?.string == "geolocation.check.disabled"
        }
        if (stringIndex == -1) return@execute

        val invokeIndex = instructions.withIndex().indexOfFirst { (idx, inst) ->
            idx > stringIndex && inst.opcode.name.startsWith("invoke-")
        }
        if (invokeIndex == -1) return@execute

        val moveResultIndex = invokeIndex + 1
        val moveResultInst = instructions.getOrNull(moveResultIndex) as? Instruction11x
        if (moveResultInst?.opcode?.name != "move-result") return@execute

        methodP.replaceInstruction(
            moveResultIndex,
            BuilderInstruction21s(Opcode.CONST_16, moveResultInst.registerA, 1)
        )
    }
}