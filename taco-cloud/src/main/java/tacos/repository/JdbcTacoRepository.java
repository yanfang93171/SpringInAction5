package tacos.repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import tacos.model.Ingredient;
import tacos.model.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository {

	private JdbcTemplate jdbc;

	@Autowired
	JdbcTacoRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Taco save(Taco design) {
		// TODO Auto-generated method stub
		long id = saveToTacoInfo(design);
		design.setId(id);
		for (Ingredient ingredient : design.getIngredients()) {
			saveIngredientToTaco(ingredient, id);
		}
		return design;
	}

	private void saveIngredientToTaco(Ingredient ingredient, long id) {
		// TODO Auto-generated method stub
		jdbc.update("insert into Taco_Ingredients (taco,ingredient) " + "values (?,?)", id, ingredient.getId());
	}

	private long saveToTacoInfo(Taco taco) {
		taco.setCreatedAt(new Date());
		PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
				"insert into Taco (name, createdAt) values (?, ?)", Types.VARCHAR, Types.TIMESTAMP);
		preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
		PreparedStatementCreator psc = preparedStatementCreatorFactory

				.newPreparedStatementCreator(
						Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));

		System.out.println("taco=" + taco);
		System.out.println(psc.toString());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(psc, keyHolder);
		System.out.println("keyHolder=" + keyHolder.toString());
		System.out.println("keyHolder.getKeys()=" + keyHolder.getKeys().toString());
		return keyHolder.getKey().longValue();
	}
}
