package dto;

import java.io.Reader;
import java.util.Properties;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class SqlConfig {

	// クライアント取得
	public static SqlMapClient getSqlMapInstance(String _host, String _port ,String resource) {
		try {

			Properties props = new Properties();
			props.setProperty("_host", _host);
			props.setProperty("_port", _port);
			Reader reader = Resources.getResourceAsReader(resource);
			return SqlMapClientBuilder.buildSqlMapClient(reader, props);

		} catch (Exception e) {
			return null;
		}
	}
}