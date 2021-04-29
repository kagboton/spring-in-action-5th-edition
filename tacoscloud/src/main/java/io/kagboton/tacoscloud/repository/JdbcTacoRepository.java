package io.kagboton.tacoscloud.repository;

import io.kagboton.tacoscloud.domain.Ingredient;
import io.kagboton.tacoscloud.domain.Order;
import io.kagboton.tacoscloud.domain.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private JdbcTemplate jdbc;

    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco taco) {
        // save taco to database and get it id
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);

        // loop through ingredients to save them with tacoId to taco_ingredient table
        for(Ingredient ingredient : taco.getIngredients()){
            saveIngredientToTaco(ingredient, tacoId);
        }
        return taco;
    }


    private long saveTacoInfo(Taco taco){
        taco.setCreateAt(new Date());

        // use PrepareStatementCreator to perform taco save to database
        PreparedStatementCreator psc =
                new PreparedStatementCreatorFactory(
                        "insert into Taco (name, createAt) values (?, ?)", Types.VARCHAR, Types.TIMESTAMP
                ).newPreparedStatementCreator(
                        Arrays.asList(
                                taco.getName(),
                                new Timestamp(taco.getCreateAt().getTime())
                        )
                );

        // generate a unique key for the taco
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // perform the save of the taco with the psc and the keyholder
        jdbc.update(psc, keyHolder);

        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(Ingredient ingredient, long tacoId){
        // perform the save of ingredient and taco ids to the Taco_Ingredients database
        jdbc.update(
                "insert into Taco_Ingredients (taco, ingredients) values (?, ?)",
                tacoId,
                ingredient.getId()
        );
    }


}
