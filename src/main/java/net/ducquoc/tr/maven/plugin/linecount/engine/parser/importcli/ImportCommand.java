package net.ducquoc.tr.maven.plugin.linecount.engine.parser.importcli;

import java.util.Map;

/**
 * Import command.
 *
 * @since 2.0
 */
public class ImportCommand {

    /**
     * Matches input str txt.
     */
    public boolean match(String txt) {
        if (txt != null && txt.trim().contains("import")) {
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

        return (String) contextHolder.get("import");
    }

}
