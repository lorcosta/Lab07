package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

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
	
	public List<PowerOutage> getPowerOutages(LocalTime massimaDurataOutage, Nerc n, Map<Integer,Nerc> mapNerc){
		String sql="SELECT id,nerc_id,customers_affected,date_event_finished,"
				+ " TIMEDIFF(date_event_finished,date_event_began) as 'Tempo outage'" + 
				"FROM PowerOutages" + 
				"WHERE nerc_id=?";
		List<PowerOutage> po= new ArrayList<PowerOutage>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(2, n.getId());
			
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Duration duration= Duration.ofHours(res.getTime("Tempo_outage").toLocalTime().getHour());
				duration.plus(Duration.ofHours(res.getTime("Tempo_outage").toLocalTime().getMinute()));
				PowerOutage singlePO=new PowerOutage(res.getInt("id"),mapNerc.get(res.getInt("nerc_id")),res.getInt("customers_affected"),duration, res.getDate("date_event_finished").toLocalDate());
				po.add(singlePO);
			}

			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return po;
	}
}
