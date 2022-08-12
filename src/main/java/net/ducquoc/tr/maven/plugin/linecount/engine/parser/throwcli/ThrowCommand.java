package net.ducquoc.tr.maven.plugin.linecount.engine.parser.throwcli;

import java.util.Map;

/**
 * Throw command.
 *
 * @since 2.0
 */
public class ThrowCommand {

    /**
     * Matches input str txt.
     */
    public boolean match(String txt) {
        if (txt != null && txt.trim().contains("throw")) {
            return true;
        }
        return false;
    }

    /**
     * Internal Resolve value.
     *
     * @param resolvedValue
     * @param contextHolder
     * @return
     */
    protected String internalResolve(Object resolvedValue, Map<String, Object> contextHolder) {

        if (resolvedValue == null) {
            return null;
        }

        return (String) contextHolder.get("throw");
    }

}
