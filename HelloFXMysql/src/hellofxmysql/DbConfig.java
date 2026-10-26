package hellofxmysql;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Lee y guarda los parámetros de conexión MySQL en un archivo .txt
 */
public class DbConfig {

    public static final String CONFIG_FILE = "db_config.txt";

    private String host = "localhost";
    private String port = "3306";
    private String database = "dad";
    private String user = "root";
    private String password = "";

    public static DbConfig load() {
        DbConfig config = new DbConfig();
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#") || !line.contains("=")) {
                    continue;
                }
                String[] parts = line.split("=", 2);
                String key = parts[0].trim();
                String value = parts.length > 1 ? parts[1].trim() : "";

                switch (key) {
                    case "host" -> config.host = value;
                    case "port" -> config.port = value;
                    case "database" -> config.database = value;
                    case "user" -> config.user = value;
                    case "password" -> config.password = value;
                    default -> { }
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer " + CONFIG_FILE + ": " + e.getMessage());
        }
        return config;
    }

    public void save() throws IOException {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("host", host);
        data.put("port", port);
        data.put("database", database);
        data.put("user", user);
        data.put("password", password);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE))) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        }
    }

    public String getJdbcUrl() {
        return "jdbc:mysql://" + host + ":" + port + "/" + database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
