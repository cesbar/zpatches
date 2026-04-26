package app.cesbar.patches.velov

import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.builder.MutableMethodImplementation
import com.android.tools.smali.dexlib2.builder.instruction.BuilderInstruction11n
import com.android.tools.smali.dexlib2.builder.instruction.BuilderInstruction21c
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.StringReference
import com.android.tools.smali.dexlib2.immutable.reference.ImmutableStringReference

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
        val methodP = ContractsMapperPFingerprint.method
        val instructions = (methodP.implementation as? MutableMethodImplementation)?.instructions ?: return@execute


        val stringIndex = instructions.indexOfFirst {
            val ref = (it as? ReferenceInstruction)?.reference as? StringReference
            ref?.string == "geolocation.check.disabled"
        }
        if (stringIndex == -1) return@execute


        val stringInst = instructions[stringIndex] as BuilderInstruction21c
        val boolInst = instructions.getOrNull(stringIndex + 1) as? BuilderInstruction11n
        if (boolInst?.opcode != Opcode.CONST_4) return@execute


        methodP.replaceInstruction(
            stringIndex,
            BuilderInstruction21c(Opcode.CONST_STRING, stringInst.registerA, ImmutableStringReference("inexistent.key"))
        )
        methodP.replaceInstruction(
            stringIndex + 1,            // set default value to true (1)
            BuilderInstruction11n(Opcode.CONST_4, boolInst.registerA, 1)
        )
    }
}