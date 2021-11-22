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
import id.co.qualitas.dao.interfaces.MaterialTypeDao;
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
public class MaterialTypeDaoImpl extends BaseDao implements MaterialTypeDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getMasterData(String idSloc, String idPlant) {
			String query = "SELECT material_type as materialTypeId, material_type_name as materialTypeName "
					+ "FROM material "
					+ "WHERE id_sloc = :idSloc AND id_plant = :idPlant and enabled = 1 and deleted = 0 "
					+ "GROUP BY material_type, material_type_name;";
			
		Session session = sessionFactory.getCurrentSession();
		return session.createSQLQuery(query)
				.setString("idSloc", idSloc)
				.setString("idPlant", idPlant)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}

}
