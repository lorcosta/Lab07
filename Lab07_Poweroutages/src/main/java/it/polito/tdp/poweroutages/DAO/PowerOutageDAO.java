package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<PowerOutages> getPowerOutages(LocalDateTime massimaDurataOutage){
		String sql="SELECT id, nerc_id,customers_affected,"
				+ " TIMEDIFF(date_event_finished,date_event_began) as 'Tempo outage'" + 
				"FROM PowerOutages" + 
				"WHERE TIMEDIFF(date_event_finished,date_event_began)< '46:00:00' AND nerc_id=1";//TODO inserisci i ? per mettere i parametri di selezione che sono definiti dall'utente
		List<PowerOutages> po= new ArrayList<PowerOutages>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				PowerOutages singlePO=new PowerOutages();
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return po;
	}

}
