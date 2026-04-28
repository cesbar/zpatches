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
val inAppLoginPatch = bytecodePatch(
    name = "In-app login",
    description = "Login within the app (identities) instead of using the browser (keycloak)"
) {
    compatibleWith(Constants.COMPATIBILITY)

    execute {
        val methodP = contractsMapperPFingerprint.method
        val instructions = (methodP.implementation as? MutableMethodImplementation)?.instructions ?: return@execute

        val stringIndex = instructions.indexOfFirst {
            val ref = (it as? ReferenceInstruction)?.reference as? StringReference
            ref?.string == "keycloak.enabled"
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
            BuilderInstruction21s(Opcode.CONST_16, moveResultInst.registerA, 0)
        )
    }
}