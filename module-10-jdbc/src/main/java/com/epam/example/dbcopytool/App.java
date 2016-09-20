package com.epam.example.dbcopytool;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class App {
	private static final Set<String> NON_SIZEABLE_TYPES = new HashSet<>(Arrays.asList(new String[] { 
			"DATE", "DATETIME", "TIMESTAMP", "TIME", "BLOB", "TEXT", "TINYBLOB", "TINYTEXT", 
			"MEDIUMBLOB", "MEDIUMTEXT", "LONGBLOB", "LONGTEXT", "ENUM" 
		}));
	
	public static void main(String[] args) throws SQLException {
		// args[0] URL/Name, example: jdbc:mysql://localhost:3306/dbname?user=root&password=pass
		// args[1] Rows in direct or reverse order. Example: --order=reverse
		
		String connUrl = args[0];
		String[] url = connUrl.split("\\?")[0].split("\\/");
		String schema = url[url.length - 1];
		String directOrReverseOrder = null;
		if (args.length > 1) {
			directOrReverseOrder = args[1];
		}
		boolean reverse = directOrReverseOrder != null && "reverse".equals(directOrReverseOrder.split("=")[1]);
		
		Connection conn = DriverManager.getConnection(connUrl);
		String newSchema = createNewSchema(conn, schema);
		DatabaseMetaData dbMetaData = conn.getMetaData();
		ResultSet tables = dbMetaData.getTables(null, null, null, null);
		
		while(tables.next()) {
			String tableName = tables.getString(3);
			
			StringBuilder createTableClause = new StringBuilder("CREATE TABLE ");
			StringBuilder selectClause = new StringBuilder("SELECT * FROM ");
			StringBuilder insertClause = new StringBuilder("INSERT INTO "); 
			
			createTableClause.append("`").append(newSchema).append("`.`").append(tableName).append("` (\n");
			selectClause.append("`").append(schema).append("`.`").append(tableName).append("`;");
			insertClause.append("`").append(newSchema).append("`.`").append(tableName).append("` (");
			
			ResultSet columns = dbMetaData.getColumns(null, null, tableName, null);
			boolean hasAnother = false;
			
			StringBuilder valuesString = new StringBuilder(" VALUES (");
			int count = 0;
			while(columns.next()) {
				if (hasAnother) {
					createTableClause.append(",\n");
					insertClause.append(", ");
					valuesString.append(", ");
				}
				hasAnother = true;
				count++;
				String columnName = columns.getString("COLUMN_NAME");
				String columnType = columns.getString("TYPE_NAME");
				int size = columns.getInt("COLUMN_SIZE");
				String columnNull = columns.getString("IS_NULLABLE");
				int decimalDigits = columns.getInt("DECIMAL_DIGITS");
				
				insertClause.append("`").append(columnName).append("`");
				valuesString.append("?");
				createTableClause.append("`").append(columnName).append("` ").append(columnType);
				if (!NON_SIZEABLE_TYPES.contains(columnType)) {
					createTableClause.append("(").append(size);
					if (decimalDigits > 0) {
						createTableClause.append(",").append(decimalDigits);
					}
					createTableClause.append(")");
				}
				createTableClause.append(("YES".equals(columnNull) ? " NULL" : ("NO".equals(columnNull) ? " NOT NULL" : "")));
			}
			columns.close();
			valuesString.append(");");
			insertClause.append(") ").append(valuesString.toString());
			
			ResultSet primaryKeys = dbMetaData.getPrimaryKeys(null, null, tableName);
			boolean entered = true;
			hasAnother = false;
			while(primaryKeys.next()) {
				if (entered) {
					createTableClause.append(",\n").append("PRIMARY KEY (");
					entered = false;
				}
				if (hasAnother) {
					createTableClause.append(",");
				}
				hasAnother = true;
				String columnName = primaryKeys.getString("COLUMN_NAME");
				createTableClause.append("`").append(columnName).append("`");
			}
			if (!entered) {
				createTableClause.append(")");
			}
			createTableClause.append(");");
			primaryKeys.close();
			
			createTable(conn, createTableClause.toString());
			selectAndInsert(conn, selectClause.toString(), insertClause.toString(), count, reverse);
		}
		tables.close();
		
		conn.close();
		System.out.println("Complete!");
	}

	private static String createNewSchema(Connection conn, String oldSchema) throws SQLException {
		Statement statement = conn.createStatement();
		int next = 0;
		String newSchema = null;
		while (true) {
			newSchema = oldSchema + "_"+ next;
			try {
				int created = statement.executeUpdate("CREATE SCHEMA `" + newSchema + "` DEFAULT CHARACTER SET utf8");
				System.out.println("Created  database " + newSchema + " = " + created);
				break;
			} catch (SQLException e) {
				next++;
			}
		}
		statement.close();
		return newSchema;
	}
	
	private static void createTable(Connection conn, String query) throws SQLException {
		Statement statement = conn.createStatement();
		int created = statement.executeUpdate(query);
		System.out.println("Table created = " + created);
		statement.close();
	}
	
	private static void selectAndInsert(Connection conn, String selectQuery, String insertQuery, int count, boolean reverse) throws SQLException {
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(selectQuery);
		if (reverse && rs.last()) {
			insert(conn, insertQuery, count, rs);
		} 
		while (reverse ? rs.previous() : rs.next()) {
			insert(conn, insertQuery, count, rs);
		}
		
		rs.close();
		statement.close();
	}

	private static void insert(Connection conn, String insertQuery, int count, ResultSet rs) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(insertQuery);
		for (int i = 1; i <= count; i++) {
			ps.setString(i, rs.getString(i));
		}
		int inserted = ps.executeUpdate();
		System.out.println("Row inserted = " + inserted);
		ps.close();
	}
}
