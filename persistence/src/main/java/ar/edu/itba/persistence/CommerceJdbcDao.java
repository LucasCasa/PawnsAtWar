package ar.edu.itba.persistence;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.CommerceDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.Resource;
import ar.edu.itba.model.TradeOffer;

public class CommerceJdbcDao implements CommerceDao{
	
	@Autowired
	private UserDao ud;

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public CommerceJdbcDao(final DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("commerce").usingGeneratedKeyColumns("tradeid");
	}
	
	@Override
	public List<TradeOffer> getAllOffers() {
        List<TradeOffer> offersList = jdbcTemplate
                .query("SELECT * FROM commerce",(ResultSet resultSet, int rowNum) -> {
                    return new TradeOffer(resultSet.getInt("tradeId"),ud.findById(resultSet.getInt("ownerId")),
                    		new Resource(resultSet.getInt("offerType"),resultSet.getInt("offerAmount")),
                    		new Resource(resultSet.getInt("receiveType"),resultSet.getInt("receiveAmount")));
                });
        return offersList;
	}

	@Override
	public TradeOffer getOffer(int id) {
		 List<TradeOffer> offersList = jdbcTemplate
	                .query("SELECT * FROM commerce WHERE tradeId = ?",(ResultSet resultSet, int rowNum) -> {
	                    return new TradeOffer(resultSet.getInt("tradeId"),ud.findById(resultSet.getInt("ownerId")),
	                    		new Resource(resultSet.getInt("offerType"),resultSet.getInt("offerAmount")),
	                    		new Resource(resultSet.getInt("receiveType"),resultSet.getInt("receiveAmount")));
	                },id);
		return offersList.size()>0?offersList.get(0):null;
	}

	@Override
	public void removeOffer(int id) {
		jdbcTemplate.update("DELETE FROM commerce WHERE tradeId = ?",id);
	}

	@Override
	public List<TradeOffer> getAllOffers(int userid) {
		List<TradeOffer> offersList = jdbcTemplate
                .query("SELECT * FROM commerce WHERE ownerId = ?",(ResultSet resultSet, int rowNum) -> {
                    return new TradeOffer(resultSet.getInt("tradeId"),ud.findById(resultSet.getInt("ownerId")),
                    		new Resource(resultSet.getInt("offerType"),resultSet.getInt("offerAmount")),
                    		new Resource(resultSet.getInt("receiveType"),resultSet.getInt("receiveAmount")));
                },userid);
        return offersList;
	}

	@Override
	public void createOffer(int id, int giveType, int giveAmount, int getType, int receiveAmount) {
		final Map<String,Object> args = new HashMap<>();
		args.put("ownerId", id);
		args.put("offerType",giveType);
		args.put("offerAmount",giveAmount);
		args.put("receiveType",getType);
		args.put("receiveAmount",receiveAmount);
		jdbcInsert.executeAndReturnKey(args);
	}

}
