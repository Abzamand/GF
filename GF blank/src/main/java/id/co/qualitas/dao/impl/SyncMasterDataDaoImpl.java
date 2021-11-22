package id.co.qualitas.dao.impl;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import id.co.qualitas.dao.BaseDao;
import id.co.qualitas.dao.interfaces.SyncMasterDataDao;
import id.co.qualitas.domain.webservice.material.ZFMWBMATNRResponse;
import id.co.qualitas.domain.webservice.materialtype.ZFMWBMTARTResponse;
import id.co.qualitas.domain.webservice.plant.ZFMWBPLANTResponse;
import id.co.qualitas.domain.webservice.sloc.ZFMWBSLOCResponse;
import id.co.qualitas.domain.webservice.vendor.ZFMWBVENDORResponse;

@Repository
@Transactional
public class SyncMasterDataDaoImpl extends BaseDao implements SyncMasterDataDao {
	@Autowired
	private SessionFactory sessionFactory;

//	@Override
//	public boolean saveSloc(ZFMWBVENDORResponse response) {
//		
//		/*
//		 * String query = "UPDATE SLOC " +
//		 * "SET name = :name, plant_name = :plantName, updated_by='SYSTEM', updated_date=GETDATE() "
//		 * + "WHERE id_plant=:plant and id_sloc = :sloc " + "IF @@ROWCOUNT = 0 " +
//		 * "INSERT INTO sloc " +
//		 * "(id_plant, plant_name, id_sloc, name, created_by, created_date, enabled, deleted) "
//		 * + "VALUES (:idPlant, :plantName, :sloc, :name, 'SYSTEM', " +
//		 * "GETDATE(), 1, 0) ";
//		 */
//		
//		String queryDelete = "DELETE FROM sloc";
//		
//		String queryInsert = "INSERT INTO sloc "
//				+ "(id_plant, plant_name, id_sloc, name, created_by, created_date, enabled, deleted) "
//				+ "VALUES (:idPlant, :plantName, :sloc, :name, 'SYSTEM', " + "GETDATE(), 1, 0) ";
//
//		Session session = sessionFactory.getCurrentSession();
//		boolean result = true;
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
//			
//			session.createSQLQuery(queryDelete).executeUpdate();
//			
//			for (int i = 0; i < response.getRESULT().getItem().size(); i++) {
//				session.createSQLQuery(queryInsert)
//						.setString("idPlant", response.getRESULT().getItem().get(i).getWERKS())
//						.setString("plantName", response.getRESULT().getItem().get(i).getWERKSNAME())
//						.setString("sloc", response.getRESULT().getItem().get(i).getLGORT())
//						.setString("name", response.getRESULT().getItem().get(i).getLGORTNAME())
//						.executeUpdate();
//			}
//			tx.commit();
//		} catch (Exception e) {
//			// TODO: handle exception
//			if (tx != null)
//				tx.rollback();
//			result = false;
//		}
//
//		return result;
//	}

	@Override
	public boolean saveMaterial(ZFMWBMATNRResponse response) {
		// TODO Auto-generated method stub

//		String queryDelete = "DELETE FROM material";

//		String queryInsert = "INSERT INTO material "
//				+ "(id, name, material_type, material_type_name, id_sloc, id_plant, created_by, created_date, enabled, deleted) "
//				+ "VALUES (:id, :name, :materialType, :materialTypeName, :sloc, :plant, 'SYSTEM', "
//				+ "GETDATE(), 1, 0) ";

		
		String query = "UPDATE material "
			+ "set name = :name, material_type = :materialType, "
			+ "material_type_name = :materialTypeName, id_sloc = :sloc, "
			+ "id_plant = :plant, updated_by='SYSTEM', updated_date=GETDATE() " 
			+ "where id=:id and id_plant = :plant and id_sloc = :sloc " 
			+ "IF @@ROWCOUNT = 0 " 
			+ "INSERT INTO material "
			+ "(id, name, material_type, material_type_name, id_sloc, id_plant, "
			+ "created_by, created_date, enabled, deleted) "
			+ "VALUES (:id, :name, :materialType, :materialTypeName, :sloc, :plant, "
			+ "'SYSTEM', GETDATE(), 1, 0) ";
		

		Session session = sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

//			session.createSQLQuery(queryDelete).executeUpdate();

			for (int i = 0; i < response.getRESULT().getItem().size(); i++) {
				session.createSQLQuery(query)
						.setString("id", response.getRESULT().getItem().get(i).getMATNR().replaceFirst("^0+(?!$)", ""))
						.setString("name", response.getRESULT().getItem().get(i).getMAKTX())
						.setString("sloc", response.getRESULT().getItem().get(i).getLGORT())
						.setString("plant", response.getRESULT().getItem().get(i).getWERKS())
						.setString("materialType", response.getRESULT().getItem().get(i).getMTART())
						.setString("materialTypeName", response.getRESULT().getItem().get(i).getMTBEZ())
						.executeUpdate();
			}
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if (tx != null)
				tx.rollback();
			result = false;
		}

		return result;
	}

	@Override
	public boolean saveVendor(ZFMWBVENDORResponse response) {
		// TODO Auto-generated method stub

		String queryVendor = "UPDATE vendor set full_name = :fullName, "
				+ "id_sloc = :sloc, id_plant = :plant, address = :addr, phone = :phone, plant_name = :plantName, sloc_name = :slocName, "
				+ "updated_by='SYSTEM', updated_date=GETDATE() " + "where id_sap = :idSAP " + "IF @@ROWCOUNT = 0 "
				+ "INSERT INTO vendor (id_sap, full_name, id_sloc, id_plant, address, phone, plant_name, sloc_name, "
				+ "created_by, created_date, enabled, deleted) "
				+ "VALUES (:idSAP, :fullName, :sloc, :plant, :addr, :phone, :plantName, :slocName, "
				+ "'SYSTEM', GETDATE(), 1, 0) ";

//		String queryDelete = "DELETE FROM vendor";
//		
//		String queryInsert = "INSERT INTO vendor (id_sap, full_name, id_sloc, id_plant, address, phone, plant_name, sloc_name, "
//				+ "created_by, created_date, enabled, deleted) "
//				+ "VALUES (:idSAP, :fullName, :sloc, :plant, :addr, :phone, :plantName, :slocName, "
//				+ "'SYSTEM', GETDATE(), 1, 0) ";

		String querySloc = "UPDATE SLOC "
				+ "SET name = :name, plant_name = :plantName, updated_by='SYSTEM', updated_date=GETDATE() "
				+ "WHERE id_plant=:idPlant and id_sloc = :sloc " + "IF @@ROWCOUNT = 0 " + "INSERT INTO sloc "
				+ "(id_plant, plant_name, id_sloc, name, created_by, created_date, enabled, deleted) "
				+ "VALUES (:idPlant, :plantName, :sloc, :name, 'SYSTEM', " + "GETDATE(), 1, 0) ";

//		String queryDeleteSloc = "DELETE FROM sloc";
//		
//		String queryInsertSloc = "INSERT INTO sloc "
//				+ "(id_plant, plant_name, id_sloc, name, created_by, created_date, enabled, deleted) "
//				+ "VALUES (:idPlant, :plantName, :sloc, :name, 'SYSTEM', " + "GETDATE(), 1, 0) ";

		Session session = sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

//			session.createSQLQuery(queryDelete).executeUpdate();
//			session.createSQLQuery(queryDeleteSloc).executeUpdate();

			for (int i = 0; i < response.getRESULT().getItem().size(); i++) {
				session.createSQLQuery(queryVendor).setString("idSAP",
						response.getRESULT().getItem().get(i).getLIFNR().replaceFirst("^0+(?!$)", "").equals("") ? null
								: response.getRESULT().getItem().get(i).getLIFNR().replaceFirst("^0+(?!$)", ""))
						.setString("fullName",
								response.getRESULT().getItem().get(i).getNAME1().equals("") ? null
										: response.getRESULT().getItem().get(i).getNAME1())
						.setString("sloc",
								response.getRESULT().getItem().get(i).getLGORT().equals("") ? null
										: response.getRESULT().getItem().get(i).getLGORT())
						.setString("plant",
								response.getRESULT().getItem().get(i).getWERKS().equals("") ? null
										: response.getRESULT().getItem().get(i).getWERKS())
						.setString("addr",
								response.getRESULT().getItem().get(i).getADDRESS().equals("") ? null
										: response.getRESULT().getItem().get(i).getADDRESS())
						.setString("phone",
								response.getRESULT().getItem().get(i).getTELF1().equals("") ? null
										: response.getRESULT().getItem().get(i).getTELF1())
						.setString("plantName",
								response.getRESULT().getItem().get(i).getWERKSNAME().equals("") ? null
										: response.getRESULT().getItem().get(i).getWERKSNAME())
						.setString("slocName", response.getRESULT().getItem().get(i).getLGORTNAME().equals("") ? null
								: response.getRESULT().getItem().get(i).getLGORTNAME())
						.executeUpdate();

				session.createSQLQuery(querySloc)
						.setString("idPlant", response.getRESULT().getItem().get(i).getWERKS())
						.setString("plantName", response.getRESULT().getItem().get(i).getWERKSNAME())
						.setString("sloc", response.getRESULT().getItem().get(i).getLGORT())
						.setString("name", response.getRESULT().getItem().get(i).getLGORTNAME()).executeUpdate();
			}

			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if (tx != null)
				tx.rollback();
			result = false;
		}

		return result;
	}
	

	@Override
	public boolean savePlant(ZFMWBPLANTResponse response) {
		// TODO Auto-generated method stub
		
		 String queryDelete = "DELETE FROM plant";
		 
		 String queryInsert = "INSERT INTO plant " + "(id, name) " +
		 "VALUES (:id, :name )";
		 
		
		/*
		 * String query = "UPDATE PLANT set id = :id, name = :name, " +
		 * "updated_by='SYSTEM', updated_date=GETDATE() " + "IF @@ROWCOUNT = 0 " +
		 * "INSERT INTO PLANT (id, name, created_by, created_date, enabled, deleted) " +
		 * "VALUES (:id, :name, 'SYSTEM', GETDATE(), 1, 0)" ;
		 */

		/*
		 * String query =
		 * "UPDATE material set name = :name, material_type = :materialType, id_sloc = :sloc, id_plant = :plant, "
		 * + "updated_by='SYSTEM', updated_date=GETDATE() " +
		 * "where id=:id and id_plant = :plant and id_sloc = :sloc " +
		 * "IF @@ROWCOUNT = 0 " + "INSERT INTO material " +
		 * "(id, name, material_type, id_sloc, id_plant, created_by, created_date, enabled, deleted) "
		 * + "VALUES (:id, :name, :materialType, :sloc, :plant, 'SYSTEM', " +
		 * "GETDATE(), 1, 0) ";
		 */
		Session session = sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.createSQLQuery(queryDelete).executeUpdate();
			for (int i = 0; i < response.getRESULT().getItem().size(); i++) {
				session.createSQLQuery(queryInsert)
						.setString("id", response.getRESULT().getItem().get(i).getWERKS())
						.setString("name", response.getRESULT().getItem().get(i).getNAME1())
						.executeUpdate();
			}
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if (tx != null)
				tx.rollback();
			result = false;
		}

		return result;
	}

}
