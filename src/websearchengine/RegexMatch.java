package websearchengine;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class RegexMatch {
    final static Set<String> protocols, protocolsHost;

    static {
        protocolsHost = new HashSet<>(Arrays.asList( new String[]{ "file", "ftp", "http", "https" } ));
        protocols = new HashSet<String>(Arrays.asList( new String[]{ "mailto", "news", "urn" } ));
        protocols.addAll(protocolsHost);
    }

    public static boolean isValidURL(String str) {
        int colonSeparator = str.indexOf(':');
        if (colonSeparator < 3) {
            return false;
        }

        String protocol = str.substring(0, colonSeparator).toLowerCase();

        if (!protocols.contains(protocol)){
            return false;
        }

        try {
            URI uri = new URI(str);
            if (protocolsHost.contains(protocol)) {
                if (uri.getHost() == null) {
                    return false;
                }

                String urlPath = uri.getPath();
                if (urlPath != null) {
                    for (int i=urlPath.length()-1; i >= 0; i--) {
                        if ("?<>:*|\"".indexOf( urlPath.charAt(i) ) > -1)
                            return false;
                    }
                }
            }

            return true;
        } catch ( Exception ex ) {}

        return false;
    }

    public static void main(String[] args) {
        System.out.println("THIS");
    }
}
