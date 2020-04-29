package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutages;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<PowerOutages> getPowerOutages(Time massimaDurataOutage, Nerc n, Map<Integer,Nerc> mapNerc){
		String sql="SELECT id,nerc_id,customers_affected,"
				+ " TIMEDIFF(date_event_finished,date_event_began) as 'Tempo outage'" + 
				"FROM PowerOutages" + 
				"WHERE TIMEDIFF(date_event_finished,date_event_began)<? AND nerc_id=?";
		List<PowerOutages> po= new ArrayList<PowerOutages>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setTime(1, massimaDurataOutage);
			st.setInt(2, n.getId());
			
			ResultSet res = st.executeQuery();
			while (res.next()) {
				PowerOutages singlePO=new PowerOutages(res.getInt("id"),mapNerc.get(res.getInt("nerc_id")),res.getInt("customers_affected"),res.getTime("Tempo_outage").toLocalTime());
				po.add(singlePO);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return po;
	}

}
