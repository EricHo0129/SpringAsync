package com.eric.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.eric.model.EmployeModel;

/**
 * 員工資料存取(快取標的)
 * @author yung.ho
 *
 */
@Component
public class EmployeDAO {

	private final static List<EmployeModel> employeStoreList = new ArrayList<EmployeModel>();
	
	{
		employeStoreList.add(new EmployeModel(3337L, "艾瑞克", "工程部", 8377L, "7F"));
		employeStoreList.add(new EmployeModel(3338L, "傑糯米", "工程部", 8257L, "7F"));
		employeStoreList.add(new EmployeModel(3339L, "卡卡西", "工程部", 8903L, "6F"));
		employeStoreList.add(new EmployeModel(3350L, "熊大", "工程部", 8533L, "4F"));
		employeStoreList.add(new EmployeModel(3351L, "熊讚", "工程部", 8812L, "4F"));
	}
	
	/**
	 * 取得所有的員工資料(不快取,當作對照)
	 * @return List
	 */
	public List<EmployeModel> getAll() throws Exception {
		return employeStoreList;
	}
	
	/**
	 * 取得一筆員工資料(要快取)
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public EmployeModel getOne(Long id) throws Exception {
		int dbTime = 200+RandomUtils.nextInt(500);
		Thread.sleep(dbTime); //DB沒這麼慢...
		System.out.println("getOne from database. id="+id);
		for (EmployeModel employe: employeStoreList) {
			if (employe.getId().equals(id)) {
				return employe;
			}
		}
		return null;
	}
	
	/**
	 * 新增一筆員工資料(要快取)
	 * @param employe
	 * @return
	 * @throws Exception
	 */
	public EmployeModel create(EmployeModel employe) throws Exception {
		if (getOne(employe.getId()) == null) {
			employeStoreList.add(employe);
			return employe;
		}
		return null;
	}
	
	/**
	 * 修改一筆員工資料(要快取)
	 * @param employe
	 * @return
	 * @throws Exception
	 */
	public EmployeModel update(EmployeModel employe) throws Exception {
		EmployeModel employeStore = getOne(employe.getId());
		if ( employeStore!= null) {
			if (delete(employe.getId())) {				
				return create(employe);
			}
		}
		return null;
	}
	
	/**
	 * 刪除一筆員工資料(要清快取)
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean delete(Long id) throws Exception {
		EmployeModel employeStore = getOne(id);
		if ( employeStore!= null) {
			employeStoreList.remove(employeStore);
			return true;
		}
		return false;
	}
	
	
}
