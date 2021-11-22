package id.co.qualitas.dao.interfaces;

import java.util.List;
import java.util.Map;

public interface MaterialTypeDao {
	public List<Map<String, Object>> getMasterData(String idSloc, String idPlant);
}
