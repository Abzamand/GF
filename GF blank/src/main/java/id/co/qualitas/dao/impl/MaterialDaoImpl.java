package id.co.qualitas.dao.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import id.co.qualitas.component.ConsParam;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.BaseDao;
import id.co.qualitas.dao.interfaces.MaterialDao;
import id.co.qualitas.dao.interfaces.TransferPostingConfirmDao;
import id.co.qualitas.dao.interfaces.TransferPostingDao;
import id.co.qualitas.domain.request.TransferPostingConfirmDetailRequest;
import id.co.qualitas.domain.request.TransferPostingRequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.request.TransferPostingDetail;
import id.co.qualitas.domain.request.TransferPostingHeader;
import id.co.qualitas.domain.request.TransferPostingRequest;
import id.co.qualitas.domain.response.WSMessage;

@Repository
@Transactional
public class MaterialDaoImpl extends BaseDao implements MaterialDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getMasterData(String idSloc, String idPlant) {
			String query = "SELECT m.id as materialId, m.name as materialName, material_type as materialTypeId, mt.name as materialTypeName "
					+ "FROM material m  "
					+ "LEFT JOIN material_type mt ON m.material_type = mt.id "
					+ "WHERE m.id_sloc = :idSloc AND m.id_plant = :idPlant";
			
		Session session = sessionFactory.getCurrentSession();
		return session.createSQLQuery(query)
				.setString("idSloc", idSloc)
				.setString("idPlant", idPlant)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}

}
