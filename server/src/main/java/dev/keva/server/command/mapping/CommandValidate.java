package dev.keva.server.command.mapping;

import dev.keva.protocol.resp.reply.ErrorReply;
import dev.keva.server.command.annotation.ParamLength;

public class CommandValidate {
    public static ErrorReply validate(ParamLength.Type paramLengthType, int paramLength, int commandLength, String commandName) {
        if (paramLength != -1 && paramLengthType != null) {
            if (paramLengthType == ParamLength.Type.EXACT && commandLength - 1 != paramLength) {
                return error(commandName);
            }
            if (paramLengthType == ParamLength.Type.AT_LEAST && commandLength - 1 < paramLength) {
                return error(commandName);
            }
            if (paramLengthType == ParamLength.Type.AT_MOST && commandLength - 1 > paramLength) {
                return error(commandName);
            }
        }
        return null;
    }

    public static ErrorReply error(String commandName) {
        return new ErrorReply("ERR wrong number of arguments for '" + commandName + "' command");
    }
}
