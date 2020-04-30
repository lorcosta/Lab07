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
	
	public List<PowerOutage> getPowerOutages(Nerc n, Map<Integer,Nerc> mapNerc){
		String sql="SELECT id,nerc_id,customers_affected,date_event_finished," + 
				" TIMEDIFF(date_event_finished,date_event_began) as 'Ore Outage',DATEDIFF(date_event_finished,date_event_began) as 'Tempo outage'" + 
				"  FROM PowerOutages" + 
				"  WHERE nerc_id=?";
		List<PowerOutage> po= new ArrayList<PowerOutage>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, n.getId());
			
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Integer id=res.getInt("id");//acquisisco id
				Nerc nerc=mapNerc.get(res.getInt("nerc_id"));//acquisisco id del nerc e prendo l'oggetto corrispondente
				Integer customers=res.getInt("customers_affected");//prendo numero di customers
				LocalTime ore=res.getTime("Ore Outage").toLocalTime();//acquisisco ore di outage relative ad uno stesso giorno
				Duration duration= Duration.ofHours(ore.getHour());
				Duration minutiD=Duration.ofMinutes(ore.getMinute());//se outage si verifica in un giorno solo imposto la sua durata
				Duration time=duration.plus(minutiD);
				Integer giorni=res.getInt("Tempo outage");//se eventualmente outage si verifica in più di un giorno  
				giorni=giorni*24;//moltiplico il numero di giorni per 24 ore
				minutiD=Duration.ofHours(giorni);//trasformo le ore in DUration
				duration=time.plus(minutiD);//aggiungo le eventuali ore che derivano da giorni al tempo definito precedentemente
				PowerOutage singlePO=new PowerOutage(id,nerc,customers,duration, res.getDate("date_event_finished").toLocalDate());
				po.add(singlePO);
			}

			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return po;
	}
}
