package com.dbaneman.constgen;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 9/24/14.
 */
public class ConstGen {
    public static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    // config constants
    public static final String PREFIX = "const-gen.";
    public static final String DB_NAME = PREFIX + "db.name";
    public static final String HOST = PREFIX + "db.host";
    public static final String PORT = PREFIX + "db.port";
    public static final String USERNAME = PREFIX + "db.username";
    public static final String PASSWORD = PREFIX + "db.password";
    public static final String NAMESPACE = PREFIX + "code.namespace";
    public static final String CLASSNAME = PREFIX + "code.classname";
    public static final String OUTDIR = PREFIX + "code.outdir";

    public static void main(String[] args) throws Exception {
        // create config object
        if (args.length < 1) {
            throw new RuntimeException("Please supply the conf file location as the first (and only) arg.");
        }
        Config conf = ConfigFactory.parseFile(new File(args[0])).withFallback(ConfigFactory.load()).resolve();

        // open JDBC connection
        Class.forName(DRIVER_CLASS_NAME);
        DataSource dataSource = new DriverManagerDataSource("jdbc:mysql://" + conf.getString(HOST) + ":" + conf.getString(PORT), conf.getString(USERNAME), conf.getString(PASSWORD));
        Connection conn = dataSource.getConnection();
        Statement stm = conn.createStatement();

        // create outfile
        String outDir = conf.getString(OUTDIR);
        if (!outDir.endsWith("/")) {
            outDir += "/";
        }
        String className = conf.getString(CLASSNAME);
        String outFile = outDir + className + ".java";
        PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream(outFile)));

        // query database and write constants
        header(out, conf.getString(NAMESPACE), className);
        String dbName = conf.getString(DB_NAME);
        staticClass(out, 1, dbName);
        nameConst(out, 2, dbName);
        stm.execute("USE " + dbName);
        ResultSet tablesRS = stm.executeQuery("SHOW TABLES");
        List<String> tableNames = new ArrayList<String>();
        while (tablesRS.next()) {
            String tableName = tablesRS.getString("Tables_in_" + dbName);
            tableNames.add(tableName);
        }
        tablesRS.close();
        for (String tableName : tableNames) {
            staticClass(out, 2, tableName.equals(dbName) ? tableName + "1" : tableName);
            nameConst(out, 3, tableName);
            ResultSet columnsRS = stm.executeQuery("SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '" + dbName + "' AND TABLE_NAME = '" + tableName + "'");
            while (columnsRS.next()) {
                String columnName = columnsRS.getString(1);
                columnConst(out, 3, columnName);
            }
            columnsRS.close();
            closeBrace(out, 2);
        }
        closeBrace(out, 1);
        closeBrace(out, 0);

        // close resources
        out.close();
        stm.close();
        conn.close();
    }

    private static void header(PrintStream out, String nameSpace, String className) {
        out.println("package " + nameSpace + ";");
        out.println();
        out.println("public class " + className + " {");
    }

    private static void staticClass(PrintStream out, int tabs, String name) {
        out.println(tabs(tabs) + "public static class " + name.toUpperCase() + " {");
    }

    private static void nameConst(PrintStream out, int tabs, String name) {
        out.println(tabs(tabs) + "public static final String NAME = \"" + name + "\";");
    }

    private static void columnConst(PrintStream out, int tabs, String name) {
        out.println(tabs(tabs) + "public static final String " + name.toUpperCase() + " = \"" + name + "\";");
    }

    private static void closeBrace(PrintStream out, int tabs) {
        out.println(tabs(tabs) + "}");
    }

    private static String tabs(int numTabs) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<numTabs; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }
}
